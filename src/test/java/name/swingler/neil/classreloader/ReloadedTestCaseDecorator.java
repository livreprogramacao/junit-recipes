package name.swingler.neil.classreloader;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;

public class ReloadedTestCaseDecorator implements Test {
    private final Class myTestClass;
    private final String myTestMethodName;
    private TestCase myDecoratedtest = null;
    private ClassReloader myReloader = new ClassReloader();

    public ReloadedTestCaseDecorator(
            Class theTestClass,
            String theTestMethodName) {
        myTestClass = theTestClass;
        myTestMethodName = theTestMethodName;
    }

    private static Object newClassInstance(Class clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void impersonateClassWith(
            Class theImpersonated,
            Class theImpersonator) {
        myReloader.impersonateClassWith(
                theImpersonated,
                theImpersonator);
    }

    public int countTestCases() {
        return myDecoratedTest().countTestCases();
    }

    public void run(TestResult result) {
        myDecoratedTest().run(result);
    }

    public String toString() {
        return myDecoratedTest().toString();
    }

    private TestCase myDecoratedTest() {
        if (myDecoratedtest == null) {
            myDecoratedtest =
                    (TestCase) newClassInstance(myReloader
                            .reload(myTestClass));
            myDecoratedtest.setName(myTestMethodName);
        }
        return myDecoratedtest;
    }
}
