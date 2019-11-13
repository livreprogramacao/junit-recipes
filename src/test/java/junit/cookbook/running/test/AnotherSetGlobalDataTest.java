package junit.cookbook.running.test;

import junit.framework.TestCase;

public class AnotherSetGlobalDataTest extends TestCase {
    public void testFirstTime() {
        assertFalse(GlobalData.calledMe);
        GlobalData.calledMe = true;
        assertTrue(GlobalData.calledMe);
    }
}
