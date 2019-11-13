package junit.cookbook.xmlunit.test;

import org.apache.xpath.XPathAPI;
import org.custommonkey.xmlunit.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class StrutsDeploymentTest extends XMLTestCase {
    public void testActionServletInitializationParameters()
            throws Exception {

        assertEquals(
                getInitializationParametersAsMapFromFile("test/data/struts/expected-web.xml"),
                getInitializationParametersAsMapFromFile("test/data/struts/web.xml"));
    }

    private Map getInitializationParametersAsMapFromFile(
            String filename) throws Exception {

        File webXmlFile = new File(filename);
        Document webXmlDocument = buildXmlDocument(webXmlFile);
        return getInitializationParametersAsMap(webXmlDocument);
    }

    private Document buildXmlDocument(File file)
            throws Exception {
        return XMLUnit.buildTestDocument(new InputSource(
                new FileInputStream(file)));
    }

    private Map getInitializationParametersAsMap(
            Document webXmlDocument) throws TransformerException {

        Map initializationParameters = new HashMap();

        NodeList initParamNodes = XPathAPI
                .selectNodeList(
                        webXmlDocument.getDocumentElement(),
                        "/web-app/servlet[servlet-name='action']/init-param");

        int matchingNodes = initParamNodes.getLength();

        assertFalse(
                "Found no nodes. Something wrong with XPath statement",
                matchingNodes == 0);

        for (int i = 0; i < matchingNodes; i++) {
            Node currentNode = initParamNodes.item(i);

            addInitializationParameter(
                    initializationParameters, currentNode);
        }

        return initializationParameters;
    }

    private void addInitializationParameter(
            Map initializationParameters, Node currentNode) {

        String name = null;
        String value = null;

        NodeList childNodes = currentNode.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node each = childNodes.item(i);
            if ("param-name".equals(each.getNodeName())) {
                name = getText(each);
            } else if ("param-value".equals(each.getNodeName())) {
                value = getText(each);
            }
        }

        initializationParameters.put(name, value);
    }

    private String getText(Node each) {
        String nodeText = each.getFirstChild().getNodeValue();

        // What a shame we have to innoculate ourselves
        // against the DOM API returning us a null!
        return (nodeText == null) ? null : nodeText.trim();
    }
}