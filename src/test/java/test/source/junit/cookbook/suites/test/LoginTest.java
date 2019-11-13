package junit.cookbook.suites.test;

import junit.framework.TestCase;

public class LoginTest extends TestCase {
    public void testNameNotEntered() {
        try {
            login("", "password");
            fail("User logged in without a user name!");
        } catch (MissingEntryException expected) {
            assertEquals("userName", expected.getEntryName());
            assertEquals("Please enter a user name and try again.", expected.getMessage());
        }
    }

    private void login(String userName, String password) throws MissingEntryException {
    }

    private static class MissingEntryException extends Exception {
        public String getEntryName() {
            return "";
        }
    }
}
