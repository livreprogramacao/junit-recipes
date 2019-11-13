package junit.cookbook.addons.listener;

import junit.framework.Test;
import junit.framework.TestResult;
import junitx.runner.listener.AbstractRunListener;

public class DumpTestNameListener
        extends AbstractRunListener {

    public void testStarted(Test test, TestResult result) {
        System.out.println("> " + test);
    }

    public void testFailure(
            Test test,
            TestResult result,
            Throwable t) {
    }

    public void testError(
            Test test,
            TestResult result,
            Throwable t) {
    }

    public void runStarted(Test test, long time) {
    }

    public void runStopped(Test test, long duration) {
    }

    public void runEnded(
            Test test,
            TestResult result,
            long duration) {
    }

    public void testIgnored(Test test) {
    }

    public void testSuccess(Test test, TestResult result) {
    }

}
