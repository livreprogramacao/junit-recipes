package junit.cookbook.patterns;

import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestListener;

import java.io.PrintWriter;

public class TextBasedTestListener implements TestListener {
    private PrintWriter out;
    private int testCount;

    public TextBasedTestListener(PrintWriter out) {
        this.out = out;
        this.testCount = 0;
    }

    public void addError(Test test, Throwable throwable) {
        out.print("E");
    }

    public void addFailure(Test test, AssertionFailedError failure) {
        out.print("F");
    }

    public void endTest(Test test) {
        // TODO Auto-generated method stub

    }

    public void startTest(Test test) {
        out.print(".");

        testCount++;
        if (testCount % 40 == 0)
            out.println();
    }

}
