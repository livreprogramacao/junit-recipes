package name.swingler.neil.classreloader;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ReloadTest extends TestCase {
    public static TestSuite suite() {
        TestSuite theSuite = new TestSuite();
        theSuite.addTest(
                new ReloadedTestCaseDecorator(
                        ReloadTest.class,
                        "test1"));
        theSuite.addTest(
                new ReloadedTestCaseDecorator(
                        ReloadTest.class,
                        "test2"));
        return theSuite;
    }

    public void test1() throws Exception {
        assertEquals(0, Singleton.nextI());
    }

    public void test2() throws Exception {
        assertEquals(0, Singleton.nextI());
    }

    public static class Singleton {
        private static int i = 0;

        public static int nextI() {
            return i++;
        }
    }

}
