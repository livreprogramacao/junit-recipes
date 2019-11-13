package name.swingler.neil.classreloader;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ImposterTest extends TestCase {
    public static TestSuite suite() {
        TestSuite theSuite = new TestSuite();
        ReloadedTestCaseDecorator theTest1 =
                new ReloadedTestCaseDecorator(
                        ImposterTest.class,
                        "test1");
        theTest1.impersonateClassWith(RealMcCoy.class, Imposter.class);
        theSuite.addTest(theTest1);
        theSuite.addTest(
                new ReloadedTestCaseDecorator(
                        ImposterTest.class,
                        "test2"));
        return theSuite;
    }

    public void test1() throws Exception {
        assertEquals("Fake", new RealMcCoy().getName());
    }

    public void test2() throws Exception {
        assertEquals("Real McCoy", new RealMcCoy().getName());
    }

    public static class Singleton {
        private static int i = 0;

        public static int nextI() {
            return i++;
        }
    }

    public static class RealMcCoy {
        public String getName() {
            return "Real McCoy";
        }

        public void someMethodThatWereNotInterestedIn() {
        }
    }

    public static class Imposter {
        public String getName() {
            return "Fake";
        }
    }
}
