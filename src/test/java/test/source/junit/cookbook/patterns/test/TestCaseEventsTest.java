package junit.cookbook.patterns.test;

import junit.framework.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestCaseEventsTest
        extends TestCase
        implements TestListener {

    private List events;
    private Exception expectedException;

    public TestCaseEventsTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        events = new ArrayList();
        expectedException = new Exception("I threw this on purpose");
    }

    public void dummyPassingTest() {
    }

    public void testPassingTestCase() throws Exception {
        final TestCase testCase =
                new TestCaseEventsTest("dummyPassingTest");

        TestResult testResult = new TestResult();
        testResult.addListener(this);

        testCase.run(testResult);

        List expectedEvents = new ArrayList();
        expectedEvents.add(
                Arrays.asList("startTest", testCase));
        expectedEvents.add(
                Arrays.asList("endTest", testCase));

        assertEquals(expectedEvents, events);
    }

    public void dummyFailingTest() {
        fail("I failed on purpose");
    }

    public void testFailingTestCase() throws Exception {
        final TestCase testCase =
                new TestCaseEventsTest("dummyFailingTest");

        TestResult testResult = new TestResult();
        testResult.addListener(this);

        testCase.run(testResult);

        List expectedEvents = new ArrayList();
        expectedEvents.add(
                Arrays.asList("startTest", testCase));

        expectedEvents.add(
                Arrays.asList(
                        "addFailure",
                        testCase,
                        "I failed on purpose"));

        expectedEvents.add(
                Arrays.asList("endTest", testCase));

        assertEquals(expectedEvents, events);
    }

    public void dummyExceptionThrowingTest() throws Exception {
        throw expectedException;
    }

    public void testError() throws Exception {
        final TestCaseEventsTest testCase =
                new TestCaseEventsTest("dummyExceptionThrowingTest");

        TestResult testResult = new TestResult();
        testResult.addListener(this);

        testCase.run(testResult);

        List expectedEvents = new ArrayList();
        expectedEvents.add(
                Arrays.asList("startTest", testCase));

        expectedEvents.add(
                Arrays.asList(
                        "addError",
                        testCase,
                        testCase.expectedException));

        expectedEvents.add(
                Arrays.asList("endTest", testCase));

        assertEquals(expectedEvents, events);
    }

    public void addError(Test test, Throwable throwable) {
        events.add(
                Arrays.asList(
                        "addError", test, throwable));
    }

    public void addFailure(Test test, AssertionFailedError failure) {
        events.add(
                Arrays.asList(
                        "addFailure",
                        test,
                        failure.getMessage()));
    }

    public void endTest(Test test) {
        events.add(Arrays.asList("endTest", test));
    }

    public void startTest(Test test) {
        events.add(Arrays.asList("startTest", test));
    }
}
