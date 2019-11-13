package junit.cookbook.coffee.web.test;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.custommonkey.xmlunit.XMLUnit;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.FileReader;

public class StrutsNavigationTest extends StrutsConfigFixture {
    private static Document strutsConfigDocument;

    public static Test suite() {
        TestSetup setup =
                new TestSetup(new TestSuite(StrutsNavigationTest.class)) {
                    private String strutsConfigFilename =
                            "test/data/sample-struts-config.xml";

                    protected void setUp() throws Exception {
                        XMLUnit.setIgnoreWhitespace(true);
                        strutsConfigDocument =
                                XMLUnit.buildTestDocument(
                                        new InputSource(
                                                new FileReader(
                                                        new File(strutsConfigFilename))));
                    }
                };

        return setup;
    }

    public void testLogonSubmitActionExists() throws Exception {
        assertXpathExists(
                getActionXpath("/LogonSubmit"),
                strutsConfigDocument);
    }

    public void testLogonSubmitActionSuccessMappingExists()
            throws Exception {

        assertXpathExists(
                getActionForwardXpath("/LogonSubmit"),
                strutsConfigDocument);
    }

    public void testLogonSubmitActionSuccessMapsToWelcome()
            throws Exception {
        assertXpathEvaluatesTo(
                "/Welcome.do",
                getActionForwardPathXpath("/LogonSubmit", "success"),
                strutsConfigDocument);
    }
}
