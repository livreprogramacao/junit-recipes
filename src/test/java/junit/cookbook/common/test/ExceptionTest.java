package junit.cookbook.common.test;

import junit.framework.TestCase;

public class ExceptionTest extends TestCase {
    public static void assertThrows(
            Class expectedExceptionClass,
            Block block) {

        String expectedExceptionClassName = expectedExceptionClass.getName();
        try {
            block.execute();
            fail(
                    "Block did not throw an exception of type "
                            + expectedExceptionClassName);
        } catch (Exception e) {
            assertTrue(
                    "Caught exception of type <"
                            + e.getClass().getName()
                            + ">, expected one of type <"
                            + expectedExceptionClassName
                            + ">",
                    expectedExceptionClass.isInstance(e));
        }
    }

    public void testThrowsException() throws Exception {
        try {
            Integer nullInteger = null;
            nullInteger.toString();
            fail("Called a method on null?!");
        } catch (NullPointerException expected) {
        }
    }

    public void testThrowsExceptionOoStyle() {
        assertThrows(NullPointerException.class, new Block() {
            public void execute() throws Exception {
                throw new NullPointerException();
            }
        });
    }

    public interface Block {
        void execute() throws Exception;
    }
}
