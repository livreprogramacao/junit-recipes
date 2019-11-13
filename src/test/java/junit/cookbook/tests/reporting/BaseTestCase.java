package junit.cookbook.tests.reporting;

import junit.framework.TestCase;
import org.apache.log.*;
import org.apache.log.format.ExtendedPatternFormatter;
import org.apache.log.output.io.StreamTarget;

public class BaseTestCase extends TestCase {
    /**
     * Sets the default log level for both embedded loggers. The default log
     * level setting can be overridden via -Dlog.level=... on the command line
     * or with a &lt;sysproperty key="log.level" value="${value}"/&gt; in an Ant
     * &lt;java/&gt; task running this test. Valid values, in ascending order or
     * severity, are DEBUG, INFO, WARN, ERROR, FATAL_ERROR.
     */
    protected static String logLevel = "INFO";

    /**
     * Embedded <b>staticLogger </b>. This reference is static and should be
     * used for messages logged from static code, such as static initializers
     * and TestCase.suite() methods.
     */
    protected static Logger staticLogger = Hierarchy.getDefaultHierarchy()
            .getLoggerFor("static.");
    /**
     * Logkit Logger output string format for <b>staticLogger </b>
     */
    protected static String staticPattern = "%{priority}: %{message} in %{method}\n %{throwable}";
    /**
     * Logkit extended formatter class, provides method and thread info. This
     * one is for the <b>staticLogger </b>
     */
    protected static ExtendedPatternFormatter staticFormatter = new ExtendedPatternFormatter(
            staticPattern);

    static {
        setLogLevelFromSystemProperty();
        // log everything to System.out target for now
        StreamTarget target = new StreamTarget(System.out, staticFormatter);
        staticLogger.setLogTargets(new LogTarget[]{target});
        Priority priority = Priority.getPriorityForName(logLevel);
        staticLogger.setPriority(priority);
    }

    /**
     * <b>logger </b> is not static and should be used everywhere except in
     * places where a statically configured logger is necessary.
     */
    protected Logger logger = Hierarchy.getDefaultHierarchy().getLoggerFor(
            "test.");
    /**
     * Logkit Logger output string format for non-static <b>logger </b>
     */
    protected String pattern = "%{priority}: %{message} in %{method}\n %{throwable}";
    /**
     * Logkit extended formatter class, provides method and thread info. This
     * one is for the non-static <b>logger </b>
     */
    protected ExtendedPatternFormatter formatter = new ExtendedPatternFormatter(
            pattern);

    public BaseTestCase() {
        setLogLevelFromSystemProperty();
        // log everything to System.out target for now
        StreamTarget target = new StreamTarget(System.out, formatter);
        logger.setLogTargets(new LogTarget[]{target});
        Priority priority = Priority.getPriorityForName(logLevel);
        logger.setPriority(priority);
    }

    private static final void setLogLevelFromSystemProperty() {
        String log_level = System.getProperty("log.level");
        if (null != log_level) {
            logLevel = log_level;
        }
    }
}