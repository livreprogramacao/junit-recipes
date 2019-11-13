package junit.cookbook.patterns.test;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class TestCaseTest extends TestCase {
    public void testRunBareTemplate() throws Throwable {
        SpyTestCase spyTestCase = new SpyTestCase();
        spyTestCase.runBare();

        List expectedMethodNames = new ArrayList() {
            {
                add("setUp");
                add("runTest");
                add("tearDown");
            }
        };

        assertEquals(
                expectedMethodNames,
                spyTestCase.invokedMethodNames);
    }

    public void testRunBareInvokesTearDownOnTestFailure()
            throws Throwable {

        SpyTestCase spyTestCase = new SpyTestCase() {
            protected void runTest() throws Throwable {
                super.runTest();
                fail("I failed on purpose");
            }
        };

        try {
            spyTestCase.runBare();
        } catch (AssertionFailedError expected) {
            assertEquals(
                    "I failed on purpose",
                    expected.getMessage());
        }

        List expectedMethodNames = new ArrayList() {
            {
                add("setUp");
                add("runTest");
                add("tearDown");
            }
        };

        assertEquals(
                expectedMethodNames,
                spyTestCase.invokedMethodNames);
    }

    public void testRunBareExecutesAPassingTest() {
        TestCase testCase = new TestCase() {
            protected void runTest() throws Throwable {
                assertTrue(true);
            }
        };

        try {
            testCase.runBare();
        } catch (Throwable oops) {
            fail("Test should not have thrown any kind of exception");
        }
    }

    public void testRunBareExecutesAFailingTest() throws Throwable {
        TestCase testCase = new TestCase() {
            protected void runTest() throws Throwable {
                fail("Intentional failure");
            }
        };

        try {
            testCase.runBare();
            fail("Test should have failed!");
        } catch (AssertionFailedError expected) {
            assertEquals(
                    "Intentional failure",
                    expected.getMessage());
        }
    }

}
