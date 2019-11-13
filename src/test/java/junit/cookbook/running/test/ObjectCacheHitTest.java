package junit.cookbook.running.test;

import junit.cookbook.running.Directory;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import name.swingler.neil.classreloader.ReloadedTestCaseDecorator;

import java.lang.reflect.Method;

public class ObjectCacheHitTest extends TestCase implements Directory {
    public static TestSuite suite() {
        TestSuite suite = new TestSuite();

        Method[] methods = ObjectCacheHitTest.class.getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            String methodName = method.getName();
            if (methodName.startsWith("test")) {
                suite.addTest(
                        new ReloadedTestCaseDecorator(
                                ObjectCacheHitTest.class,
                                methodName));
            }
        }

        return suite;
    }

    protected void setUp() throws Exception {
        ObjectCache.directory = this;
    }

    public void testFirstLookup() throws Exception {
        assertEquals("there", ObjectCache.lookup("hello"));
        assertEquals(0, ObjectCache.countCacheHits());
    }

    public void testExpectingCacheHit() throws Exception {
        assertEquals("there", ObjectCache.lookup("hello"));
        assertEquals("there", ObjectCache.lookup("hello"));
        assertEquals(1, ObjectCache.countCacheHits());
    }

    // Self-Shunt method
    public Object get(String name) {
        return "there";
    }
}
