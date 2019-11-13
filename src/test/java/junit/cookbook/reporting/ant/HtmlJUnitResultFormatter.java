package junit.cookbook.reporting.ant;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.optional.junit.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.NumberFormat;
import java.util.Hashtable;

public class HtmlJUnitResultFormatter implements JUnitResultFormatter {
    /**
     * Formatter for timings.
     */
    private NumberFormat nf = NumberFormat.getInstance();

    /**
     * Timing helper.
     */
    private Hashtable testStarts = new Hashtable();

    /**
     * Where to write the log to.
     */
    private OutputStream out;

    /**
     * Helper to store intermediate output.
     */
    private StringWriter middle;

    /**
     * Convenience layer on top of {@link #middle middle}.
     */
    private PrintWriter wri;

    /**
     * Suppress endTest if testcase failed.
     */
    private Hashtable failed = new Hashtable();

    private String systemOutput = null;

    private String systemError = null;

    public HtmlJUnitResultFormatter() {
        middle = new StringWriter();
        wri = new PrintWriter(middle);
    }

    public void setOutput(OutputStream out) {
        this.out = out;
    }

    public void setSystemOutput(String out) {
        systemOutput = out;
    }

    public void setSystemError(String err) {
        systemError = err;
    }

    /**
     * The whole testsuite ended.
     */
    public void endTestSuite(JUnitTest suite) throws BuildException {
        String nl = System.getProperty("line.separator");
        StringBuffer header = new StringBuffer("<html>" + nl
                + "<head><title>JUnit Results</title></head>" + nl + "<body>"
                + nl + "<table border=\"1\">" + nl);
        header.append("<tr><th>Suite: " + suite.getName()
                + "</th><th>Time</th></tr>" + nl);
        StringBuffer footer = new StringBuffer();
        footer.append(nl + "<tr><td>");
        footer.append("Tests run:");
        footer.append("</td><td>");
        footer.append(suite.runCount());
        footer.append("</td></tr>" + nl + "<tr><td>");
        footer.append("Failures:");
        footer.append("</td><td>");
        footer.append(suite.failureCount());
        footer.append("</td></tr>" + nl + "<tr><td>");
        footer.append("Errors:");
        footer.append("</td><td>");
        footer.append(suite.errorCount());
        footer.append("</td></tr>" + nl + "<tr><td>");
        footer.append("Time elapsed:");
        footer.append("</td><td>");
        footer.append(nf.format(suite.getRunTime() / 1000.0));
        footer.append(" sec");
        footer.append("</td></tr>");
        footer.append(nl);
        // append both the output and error streams to the log
        if (systemOutput != null && systemOutput.length() > 0) {
            footer.append("<tr><td>Standard Output</td><td>").append("<pre>")
                    .append(systemOutput).append("</pre></td></tr>");
        }
        if (systemError != null && systemError.length() > 0) {
            footer.append("<tr><td>Standard Error</td><td>").append("<pre>")
                    .append(systemError).append("</pre></td></tr>");
        }
        footer.append("</table>" + nl + "</body>" + nl + "</html>");
        if (out != null) {
            try {
                out.write(header.toString().getBytes());
                out.write(middle.toString().getBytes());
                out.write(footer.toString().getBytes());
                wri.close();
                out.flush();
            } catch (IOException ioe) {
                throw new BuildException("Unable to write output", ioe);
            } finally {
                if (out != System.out && out != System.err) {
                    try {
                        out.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
    }

    /**
     * From interface TestListener.
     * <p>
     * A new Test is started.
     */
    public void startTest(Test test) {
        testStarts.put(test, new Long(System.currentTimeMillis()));
        failed.put(test, Boolean.FALSE);
        wri.print("<tr><td>");
        wri.print(JUnitVersionHelper.getTestCaseName(test));
        wri.print("</td>");
    }

    /**
     * From interface TestListener.
     * <p>
     * A Test is finished.
     */
    public void endTest(Test test) {
        synchronized (wri) {
            if (Boolean.TRUE.equals(failed.get(test))) {
                return;
            }
            Long secondsAsLong = (Long) testStarts.get(test);
            double seconds = 0;
            // can be null if an error occured in setUp
            if (secondsAsLong != null) {
                seconds = (System.currentTimeMillis() - secondsAsLong
                        .longValue()) / 1000.0;
            }
            wri.print("<td>");
            wri.print(nf.format(seconds));
            wri.print(" sec</td></tr>");
        }
    }

    /**
     * Interface TestListener for JUnit > 3.4.
     *
     * <p>
     * A Test failed.
     */
    public void addFailure(Test test, AssertionFailedError t) {
        formatThrowable("failure", test, (Throwable) t);
    }

    /**
     * Interface TestListener.
     *
     * <p>
     * An error occured while running the test.
     */
    public void addError(Test test, Throwable t) {
        formatThrowable("error", test, t);
    }

    private void formatThrowable(String type, Test test, Throwable t) {
        synchronized (wri) {
            if (test != null) {
                failed.put(test, Boolean.TRUE);
                endTest(test);
            }
            wri.println("<td><pre>");
            wri.println(t.getMessage());
            // filter the stack trace to squelch Ant and JUnit stack
            // frames in the report
            String strace = JUnitTestRunner.getFilteredTrace(t);
            wri.print(strace);
            wri.println("</pre></td></tr>");
        }
    }

    /**
     * From interface JUnitResultFormatter. We do nothing with this method, but
     * we have to implement all the interface�s methods.
     */
    public void startTestSuite(JUnitTest suite) throws BuildException {
    }
}