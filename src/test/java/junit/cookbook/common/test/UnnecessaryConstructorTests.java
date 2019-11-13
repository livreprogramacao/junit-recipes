package junit.cookbook.common.test;

import junit.framework.TestCase;

public class UnnecessaryConstructorTests extends TestCase {
    public void testConstructorDoesNotAnswerNull() {
        assertNotNull(new Integer(762));
    }

    public void testConstructorAnswersRightType() {
        assertTrue(new Integer(762) instanceof Integer);
    }
}
