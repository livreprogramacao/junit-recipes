package name.swingler.neil.classreloader;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import javax.ws.rs.NotFoundException;
import java.util.HashMap;
import java.util.Map;

class ClassReloader extends ClassLoader {
    private final Map myImpersonatedToImpersonatorMap = new HashMap();
    private ClassPool myClassPool =
            new ClassPool(ClassPool.getDefault());

    public Class loadClass(String name) throws ClassNotFoundException {
        return new Clazz(name).load();
    }

    public Class reload(Class clazz) {
        try {
            return loadClass(clazz.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void impersonateClassWith(
            Class theImpersonated,
            Class theImpersonator) {
        myImpersonatedToImpersonatorMap.put(
                theImpersonated.getName(),
                theImpersonator.getName());
    }

    private CtClass theCtClassFor(String theClassName) {
        try {
            return myClassPool.get(theClassName);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Class defineClass(CtClass imposterClass) {
        try {
            return defineClass(imposterClass.toBytecode());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Class defineClass(byte[] byteCodes) {
        return defineClass(null, byteCodes, 0, byteCodes.length);
    }

    private class Clazz {
        private final String myName;

        Clazz(String theName) {
            myName = theName;
        }

        public Class load() throws ClassNotFoundException {
            return hasImposter() ? loadImposter() : loadReal();
        }

        private boolean hasImposter() {
            return myImposterName() != null;
        }

        private String myImposterName() {
            return (String) myImpersonatedToImpersonatorMap.get(myName);
        }

        private Class loadImposter() {
            try {
                CtClass imposterClass = theCtClassFor(myImposterName());
                imposterClass.setName(myName);
                return defineClass(imposterClass);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        private Class loadReal() throws ClassNotFoundException {
            return canDuplicate() ? define() : findSystemClass(myName);
        }

        private Class define() {
            return defineClass(theCtClassFor(myName));
        }

        private boolean canDuplicate() {
            return !(
                    myName.startsWith("java.")
                            || myName.startsWith("junit."))
                    || myName.startsWith("junit.cookbook.");
        }
    }
}