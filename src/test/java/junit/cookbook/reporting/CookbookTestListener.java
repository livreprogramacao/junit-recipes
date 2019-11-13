package junit.cookbook.reporting;

import junit.framework.*;
import junit.runner.BaseTestRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.PrintStream;
import java.io.StringWriter;
import java.text.NumberFormat;

public class CookbookTestListener implements TestListener {
    PrintStream printStream;
    Document xmlOutput;
    Element xmlRoot;
    Element testCase;
    int errorCount = 0;
    int failureCount = 0;
    int testCaseCount = 0;

    /**
     * Default constructor creates a CookbookTestListener that streams results
     * to System.out.
     *
     * @throws ParserConfigurationException
     */
    public CookbookTestListener() throws ParserConfigurationException {
        this(System.out);
    }

    /**
     * Creates a new CookbookTestListener that captures results in an XML
     * Document and serializes the XML to the specified <code>printStream</code>
     *
     * @param printStream to use for serializing XML results
     * @throws ParserConfigurationException
     */
    public CookbookTestListener(PrintStream printStream)
            throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        xmlOutput = builder.newDocument();
        this.printStream = printStream;
    }

    public void startSuite(Test suite) {
        xmlRoot = (Element) xmlOutput.createElement("testsuite");
        xmlRoot.setAttribute("class", suite.toString());
        xmlOutput.appendChild(xmlRoot);
    }

    public void addError(Test test, Throwable t) {
        errorCount++;
        Element error = (Element) xmlOutput.createElement("error");
        addThrowable(t, error);
    }

    public void addFailure(Test test, AssertionFailedError t) {
        failureCount++;
        Element failure = (Element) xmlOutput.createElement("failure");
        addThrowable(t, failure);
    }

    public void startTest(Test test) {
        testCase = (Element) xmlOutput.createElement("test");
        String methodStr = ((TestCase) test).getName();
        testCase.setAttribute("name", methodStr);
        xmlRoot.appendChild(testCase);
    }

    public void endTest(Test test) {
        testCaseCount = testCaseCount + test.countTestCases();
    }

    public void print() throws TransformerException {
        Transformer transformer = getTransformer();
        DOMSource source = new DOMSource(xmlOutput);
        StreamResult streamResult = new StreamResult(printStream);
        transformer.transform(source, streamResult);
    }

    /**
     * @return output of the test results as a String
     */
    public String getXmlAsString() throws TransformerException {
        Transformer transformer = getTransformer();
        DOMSource source = new DOMSource(xmlOutput);
        StringWriter xmlString = new StringWriter();
        StreamResult streamResult = new StreamResult(xmlString);
        transformer.transform(source, streamResult);
        return xmlString.toString();
    }

    public void endSuite(TestResult testResult, long runTime) {
        Element summary = (Element) xmlOutput.createElement("summary");

        Element tests = (Element) xmlOutput.createElement("tests");
        Element errors = (Element) xmlOutput.createElement("errors");
        Element failures = (Element) xmlOutput.createElement("failures");
        Element runtime = (Element) xmlOutput.createElement("runtime");

        String testCount = String.valueOf(testResult.runCount());
        String errCount = String.valueOf(testResult.errorCount());
        String failCount = String.valueOf(testResult.failureCount());
        String runTimeStr = NumberFormat.getInstance().format(
                (double) runTime / 1000);

        tests.appendChild(xmlOutput.createTextNode(testCount));
        errors.appendChild(xmlOutput.createTextNode(errCount));
        failures.appendChild(xmlOutput.createTextNode(failCount));
        runtime.appendChild(xmlOutput.createTextNode(runTimeStr));

        xmlRoot.appendChild(summary);
        summary.appendChild(tests);
        summary.appendChild(errors);
        summary.appendChild(failures);
        summary.appendChild(runtime);
    }

    private void addThrowable(Throwable t, Element elem) {
        String trace = BaseTestRunner.getFilteredTrace(t);
        elem.setAttribute("message", t.getMessage());
        elem.appendChild(xmlOutput.createCDATASection(trace));
        testCase.appendChild(elem);
    }

    private Transformer getTransformer() throws TransformerException {
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer();
        transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT,
                "yes");
        transformer.setOutputProperty(
                javax.xml.transform.OutputKeys.STANDALONE, "yes");
        return transformer;
    }
}