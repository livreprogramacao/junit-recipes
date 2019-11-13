package junit.cookbook.listener;

import junit.cookbook.util.BulletinBoard;
import junit.cookbook.util.MessageCollector;
import junit.cookbook.util.PrintStreamBulletinBoard;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;
import junitx.runner.listener.AbstractRunListener;

import java.text.ChoiceFormat;
import java.util.Date;
import java.util.Properties;

public class TestRunReporter extends AbstractRunListener {

    private static final ChoiceFormat regularPluralFormat =
            new ChoiceFormat(
                    new double[]{0, 1, 2},
                    new String[]{"s", "", "s"});

    private MessageCollector outputWriter;
    private BulletinBoard bulletinBoard;

    public TestRunReporter() {
        this(new PrintStreamBulletinBoard(System.out));
    }

    public TestRunReporter(BulletinBoard bulletinBoard) {
        this.bulletinBoard = bulletinBoard;
    }

    public void init(Properties props) throws Exception {
        super.init(props);
    }

    public void runStarted(Test test, long atTime) {
        bulletinBoard.println(
                "Starting run at " + formatDate(atTime));
    }

    private String formatDate(long atTime) {
        return new Date(atTime).toString();
    }

    public void runStopped(Test test, long duration) {
        bulletinBoard.println(
                "Run stopped after " + duration + " ms");
    }

    public void runEnded(
            Test test,
            TestResult result,
            long duration) {

        bulletinBoard.println(
                "Run ends after " + duration + " ms");
    }

    public void testIgnored(Test test) {
        bulletinBoard.println("IGNORED");
    }

    public void testStarted(Test test, TestResult result) {
        if (test instanceof TestCase) {
            bulletinBoard.print(
                    ((TestCase) test).getName() + "... ");
        } else if (test instanceof TestSuite) {
            TestSuite testSuite = (TestSuite) test;
            bulletinBoard.println(
                    "SUITE: "
                            + testSuite.getName()
                            + " ("
                            + formatTestCount(
                            testSuite.countTestCases()));
        }
    }

    private String formatTestCount(int testCaseCount) {
        return testCaseCount
                + " test"
                + regularPluralFormat.format(testCaseCount)
                + ")";
    }

    public void testFailure(
            Test test,
            TestResult result,
            Throwable t) {

        bulletinBoard.println("FAILED with " + t.toString());

    }

    public void testError(
            Test test,
            TestResult result,
            Throwable t) {

        bulletinBoard.println(
                "INCONCLUSIVE due to " + t.toString());
    }

    public void testSuccess(Test test, TestResult result) {
        bulletinBoard.println("OK");
    }
}
