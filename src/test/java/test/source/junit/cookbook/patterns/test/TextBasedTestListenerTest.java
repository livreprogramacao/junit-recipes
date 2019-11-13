package junit.cookbook.patterns.test;

import junit.cookbook.patterns.TextBasedTestListener;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import java.io.PrintWriter;
import java.io.StringWriter;

public class TextBasedTestListenerTest extends TestCase {
    private TextBasedTestListener testListener;
    private StringWriter stringWriter;

    protected void setUp() throws Exception {
        stringWriter = new StringWriter();
        testListener =
                new TextBasedTestListener(new PrintWriter(stringWriter));
    }

    public void testStartTestEvent() throws Exception {
        testListener.startTest(this);
        assertEquals(".", stringWriter.toString());
    }

    public void testAddFailureEvent() throws Exception {
        testListener.addFailure(this, new AssertionFailedError());
        assertEquals("F", stringWriter.toString());
    }

    public void testAddErrorEvent() throws Exception {
        testListener.addError(this, new RuntimeException());
        assertEquals("E", stringWriter.toString());
    }

    public void testEndTestEvent() throws Exception {
        testListener.endTest(this);
        assertEquals("", stringWriter.toString());
    }

    public void testAddLineBreakAfterFortyTests() throws Exception {
        for (int i = 0; i < 41; i++) {
            testListener.startTest(this);
        }

        assertEquals(
                "........................................\r\n.",
                stringWriter.toString());
    }

    public void testCompletePassingTestScenario() throws Exception {
        testListener.startTest(this);
        testListener.endTest(this);
        assertEquals(".", stringWriter.toString());
    }

    public void testCompleteTestFailureScenario() throws Exception {
        testListener.startTest(this);
        testListener.addFailure(this, new AssertionFailedError());
        testListener.endTest(this);
        assertEquals(".F", stringWriter.toString());
    }

    public void testCompleteTestErrorScenario() throws Exception {
        testListener.startTest(this);
        testListener.addError(this, new RuntimeException());
        testListener.endTest(this);
        assertEquals(".E", stringWriter.toString());
    }
}
