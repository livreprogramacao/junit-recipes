package junit.cookbook.reporting;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;
import junit.framework.ComparisonFailure;

public class CountingAssert extends Assert {
    private static int assertCount = 0;

    protected CountingAssert() {
    }

    /**
     * getAssertCount() should be called by a TestRunner or TestListener at the
     * end of a test suite execution. It returns a count of how many assertions
     * were executed during the run.
     *
     * @return assertionCounter count of assertions executed during a run.
     */
    public static int getAssertCount() {
        return assertCount;
    }

    /**
     * Asserts that a condition is true. If it isn't, it throws an
     * AssertionFailedError with the given message. Most of the other assert*()
     * methods delegate to this one.
     */
    static public void assertTrue(String message, boolean condition) {
        assertCount++;
        if (!condition) fail(message);
    }

    static public void assertTrue(boolean condition) {
        assertTrue(null, condition);
    }

    static public void assertFalse(String message, boolean condition) {
        assertTrue(message, !condition);
    }

    static public void assertFalse(boolean condition) {
        assertFalse(null, condition);
    }

    /**
     * Fails a test with the given message.
     */
    static public void fail(String message) {
        throw new AssertionFailedError(message);
    }

    static public void fail() {
        fail(null);
    }

    /**
     * Asserts that two objects are equal. If they are not, an
     * AssertionFailedError is thrown with the given message.
     */
    static public void assertEquals(String message, Object expected,
                                    Object actual) {
        assertCount++;
        if (expected == null && actual == null) return;
        if (expected != null && expected.equals(actual)) return;
        failNotEquals(message, expected, actual);
    }

    static public void assertEquals(Object expected, Object actual) {
        assertEquals(null, expected, actual);
    }

    /**
     * Asserts that two Strings are equal.
     */
    static public void assertEquals(String message, String expected,
                                    String actual) {
        assertCount++;
        if (expected == null && actual == null) return;
        if (expected != null && expected.equals(actual)) return;
        throw new ComparisonFailure(message, expected, actual);
    }

    static public void assertEquals(String expected, String actual) {
        assertEquals(null, expected, actual);
    }

    /**
     * Asserts that two doubles are equal concerning a delta. If they are not,
     * an AssertionFailedError is thrown with the given message. If the expected
     * value is infinity then the delta value is ignored.
     */
    static public void assertEquals(String message, double expected,
                                    double actual, double delta) {
        assertCount++;
        // handle infinity specially since subtracting
        // to infinite values gives NaN and the
        // the following test fails
        if (Double.isInfinite(expected)) {
            if (!(expected == actual))
                failNotEquals(message, new Double(expected), new Double(
                        actual));
        } else if (!(Math.abs(expected - actual) <= delta))
            // Because comparison with NaN always returns false
            failNotEquals(message, new Double(expected), new Double(actual));
    }

    static public void assertEquals(double expected, double actual, double delta) {
        assertEquals(null, expected, actual, delta);
    }

    /**
     * Asserts that two floats are equal concerning a delta. If they are not, an
     * AssertionFailedError is thrown with the given message. If the expected
     * value is infinity then the delta value is ignored.
     */
    static public void assertEquals(String message, float expected,
                                    float actual, float delta) {
        assertCount++;
        if (Float.isInfinite(expected)) {
            if (!(expected == actual))
                failNotEquals(message, new Float(expected), new Float(
                        actual));
        } else if (!(Math.abs(expected - actual) <= delta))
            failNotEquals(message, new Float(expected), new Float(actual));
    }

    static public void assertEquals(float expected, float actual, float delta) {
        assertEquals(null, expected, actual, delta);
    }

    static public void assertEquals(String message, long expected, long actual) {
        assertEquals(message, new Long(expected), new Long(actual));
    }

    static public void assertEquals(long expected, long actual) {
        assertEquals(null, expected, actual);
    }

    static public void assertEquals(String message, boolean expected,
                                    boolean actual) {
        assertEquals(message, new Boolean(expected), new Boolean(actual));
    }

    static public void assertEquals(boolean expected, boolean actual) {
        assertEquals(null, expected, actual);
    }

    static public void assertEquals(String message, byte expected, byte actual) {
        assertEquals(message, new Byte(expected), new Byte(actual));
    }

    static public void assertEquals(byte expected, byte actual) {
        assertEquals(null, expected, actual);
    }

    static public void assertEquals(String message, char expected, char actual) {
        assertEquals(message, new Character(expected), new Character(actual));
    }

    static public void assertEquals(char expected, char actual) {
        assertEquals(null, expected, actual);
    }

    static public void assertEquals(String message, short expected, short actual) {
        assertEquals(message, new Short(expected), new Short(actual));
    }

    static public void assertEquals(short expected, short actual) {
        assertEquals(null, expected, actual);
    }

    static public void assertEquals(String message, int expected, int actual) {
        assertEquals(message, new Integer(expected), new Integer(actual));
    }

    static public void assertEquals(int expected, int actual) {
        assertEquals(null, expected, actual);
    }

    static public void assertNotNull(Object object) {
        assertNotNull(null, object);
    }

    static public void assertNotNull(String message, Object object) {
        assertTrue(message, object != null);
    }

    static public void assertNull(Object object) {
        assertNull(null, object);
    }

    static public void assertNull(String message, Object object) {
        assertTrue(message, object == null);
    }

    /**
     * Asserts that two objects refer to the same object. If they are not, an
     * AssertionFailedError is thrown with the given message.
     */
    static public void assertSame(String message, Object expected, Object actual) {
        assertCount++;
        if (expected == actual) return;
        failNotSame(message, expected, actual);
    }

    static public void assertSame(Object expected, Object actual) {
        assertSame(null, expected, actual);
    }

    /**
     * Asserts that two objects refer to the same object. If they are not, an
     * AssertionFailedError is thrown with the given message.
     */
    static public void assertNotSame(String message, Object expected,
                                     Object actual) {
        assertCount++;
        if (expected == actual) failSame(message);
    }

    static public void assertNotSame(Object expected, Object actual) {
        assertNotSame(null, expected, actual);
    }

    static private void failSame(String message) {
        String formatted = "";
        if (message != null) formatted = message + " ";
        fail(formatted + "expected not same");
    }

    static private void failNotSame(String message, Object expected,
                                    Object actual) {
        String formatted = "";
        if (message != null) formatted = message + " ";
        fail(formatted + "expected same:<" + expected + "> was not:<" + actual
                + ">");
    }

    static private void failNotEquals(String message, Object expected,
                                      Object actual) {
        String formatted = "";
        if (message != null) formatted = message + " ";
        fail(formatted + "expected:<" + expected + "> but was:<" + actual + ">");
    }
}