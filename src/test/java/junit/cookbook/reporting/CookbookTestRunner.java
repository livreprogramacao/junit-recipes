package junit.cookbook.reporting;

import junit.framework.Test;
import junit.framework.TestResult;
import junit.textui.TestRunner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class CookbookTestRunner extends TestRunner {
    private static CookbookTestListener testListener;
    TestResult results = null;

    /**
     * Constructor for use with an output file.
     *
     * @param testCase name of Test to run
     * @param fileName
     */
    public CookbookTestRunner(String fileName, String testClassName)
            throws FileNotFoundException, ParserConfigurationException {
        if (fileName != null) {
            FileOutputStream fos = new FileOutputStream(fileName);
            PrintStream printStream = new PrintStream(fos);
            testListener = new CookbookTestListener(printStream);
        } else {
            testListener = new CookbookTestListener();
        }
        Test test = super.getTest(testClassName);
        results = this.doRun(test);
    }

    /**
     * Constructor for use without an output file.
     *
     * @param testCase name of Test to run
     * @throws ParserConfigurationException
     */
    public CookbookTestRunner(String testClassName)
            throws ParserConfigurationException {
        testListener = new CookbookTestListener();
        Test test = super.getTest(testClassName);
        results = this.doRun(test);
    }

    public static void main(String args[]) {
        TestResult results = null;
        CookbookTestRunner runner = null;
        if (args.length == 3 && args[0].equals("-o")) {
            try {
                runner = new CookbookTestRunner(args[1], args[2]);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
        } else if (args.length == 1) {
            try {
                runner = new CookbookTestRunner(args[0]);
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException(
                    "Usage: java TestRunner [-o outputFile] Test "
                            + System.getProperty("line.separator")
                            + "where Test is the fully qualified name of "
                            + "a TestCase or TestSuite");
        }
    }

    /**
     * The default implementation of TestRunner.start() calls this method, so we
     * need to override it or else we don't get a chance to register the
     * TestListener for the TestRunner. Otherwise, override start(), which you
     * might want to do if extending the TestRunner's supported command-line
     * arguments.
     *
     * @param test test case to execute, time and collect results from.
     */
    public TestResult doRun(Test test) {
        TestResult testEventDriver = createTestResult();
        testEventDriver.addListener(testListener);
        testListener.startSuite(test);
        long startTime = System.currentTimeMillis();
        test.run(testEventDriver);
        long endTime = System.currentTimeMillis();
        long runTime = endTime - startTime;
        testListener.endSuite(testEventDriver, runTime);
        try {
            testListener.print();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return testEventDriver;
    }

    /**
     * The CookbookTestRunner constructor runs a test class and collects the
     * results in a TestResult. This accessor makes the TestResult accessible to
     * clients.
     *
     * @return TestResult of test run executed by CookbookTestRunner
     */
    public TestResult getResults() {
        return this.results;
    }
}