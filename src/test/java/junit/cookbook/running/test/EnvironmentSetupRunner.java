package junit.cookbook.running.test;

import junit.swingui.TestRunner;

public class EnvironmentSetupRunner {
    private static void oneTimeEnvironmentTearDown() {
        System.out.println("Teardown");
    }

    private static void oneTimeEnvironmentSetUp() {
        System.out.println("Setup");
    }

    public static void main(String[] args) throws Exception {
        oneTimeEnvironmentSetUp();

        TestRunner testRunner = new TestRunner() {
            public void terminate() {
                oneTimeEnvironmentTearDown();
                super.terminate();
            }
        };

        testRunner.start(new String[]{"com.mycom.MyTestSuiteClass"});
    }

}
