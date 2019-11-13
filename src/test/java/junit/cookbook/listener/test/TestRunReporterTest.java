package junit.cookbook.listener.test;

import junit.cookbook.listener.TestRunReporter;
import junit.cookbook.util.MessageCollector;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TestRunReporterTest extends TestCase {
    private TestRunReporter testRunReporter;
    private List actualMessages;
    private MessageCollector outputWriter;

    public static void main(String[] args) {
        junitx.runner.TestRunner.main(
                new String[]{
                        "-class",
                        TestRunReporterTest.class.getName(),
                        "-runner.properties",
                        "./test/properties/dumpTestNameRunner.properties"});

    }

    protected void setUp() throws Exception {
        actualMessages = new LinkedList();
        outputWriter = new MessageCollector(actualMessages);
        testRunReporter = new TestRunReporter(outputWriter);
    }

    public void testRunHeader() {
        Test dummyTest = makeDummyTest();
        testRunReporter.runStarted(dummyTest, 0);

        assertSingleMessageEquals("Starting run at Wed Dec 31 19:00:00 EST 1969");
    }

    public void testRunFooter_RunEnds() {
        testRunReporter.runEnded(
                makeDummyTest(),
                makeDummyTestResult(),
                100);

        assertSingleMessageEquals("Run ends after 100 ms");
    }

    public void testRunFooter_RunStops() {
        testRunReporter.runStopped(makeDummyTest(), 50);

        assertSingleMessageEquals("Run stopped after 50 ms");
    }

    public void testTestStarted_TestCase() {
        testRunReporter.testStarted(
                makeDummyTestCase(),
                makeDummyTestResult());

        assertMessageBufferEquals(
                makeDummyTestCase().getName() + "... ");
    }

    public void testTestIgnored() {
        testRunReporter.testIgnored(makeDummyTest());

        assertSingleMessageEquals("IGNORED");
    }

    public void testTestFailure() {
        testRunReporter.testFailure(
                makeDummyTest(),
                makeDummyTestResult(),
                new Exception());

        assertSingleMessageEquals("FAILED with java.lang.Exception");
    }

    public void testTestError() {
        testRunReporter.testError(
                makeDummyTest(),
                makeDummyTestResult(),
                new Exception());

        assertSingleMessageEquals("INCONCLUSIVE due to java.lang.Exception");
    }

    public void testTestSuccess() {
        testRunReporter.testSuccess(
                makeDummyTest(),
                makeDummyTestResult());

        assertSingleMessageEquals("OK");
    }

    public void testTestStarted_TestSuite() {
        TestSuite suite = new TestSuite("Dummy Test Suite");
        suite.addTest(makeDummyTest());

        testRunReporter.testStarted(
                suite,
                makeDummyTestResult());

        assertSingleMessageEquals("SUITE: Dummy Test Suite (1 test)");
    }

    public void testTestStarted_TestSuite_TwoTests() {
        doTestTestStarted_TestSuite(2);
    }

    public void testTestStarted_TestSuite_ZeroTests() {
        doTestTestStarted_TestSuite(0);
    }

    private void doTestTestStarted_TestSuite(int numberOfTests) {
        TestSuite suite = new TestSuite("Dummy Test Suite");
        for (int i = 0; i < numberOfTests; i++)
            suite.addTest(makeDummyTest());

        testRunReporter.testStarted(
                suite,
                makeDummyTestResult());

        assertSingleMessageEquals(
                "SUITE: Dummy Test Suite ("
                        + numberOfTests
                        + " tests)");
    }

    private Exception makeDummyThrowable() {
        return new Exception();
    }

    private TestCase makeDummyTestCase() {
        return new TestCase("DummyTestCase") {
        };
    }

    private TestResult makeDummyTestResult() {
        return new TestResult();
    }

    private Test makeDummyTest() {
        return new Test() {
            public int countTestCases() {
                return 1;
            }

            public void run(TestResult testResult) {
            }
        };
    }

    private void assertMessageBufferEquals(String expectedBufferContents) {
        assertEquals(
                expectedBufferContents,
                this.outputWriter.getBufferContents());
    }

    private void assertSingleMessageEquals(String expectedSingleMessage) {
        List expectedMessages =
                Collections.singletonList(expectedSingleMessage);

        assertEquals(expectedMessages, actualMessages);
    }
}
