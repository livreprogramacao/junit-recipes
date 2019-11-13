package junit.cookbook.running.test;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class TextBasedOneTimeEnvironmentSetupTestRunner {
    public static Test suite() {
        TestSuite suite = new TestSuite();
        // Create your test suite...
        return suite;
    }

    private static void oneTimeEnvironmentSetUp() {
        System.out.println("Setup");
    }

    private static void oneTimeEnvironmentTearDown() {
        System.out.println("Teardown");
    }

    public static void main(String[] args) throws Exception {
        oneTimeEnvironmentSetUp();
        TestRunner.run(suite());
        oneTimeEnvironmentTearDown();
    }
}
