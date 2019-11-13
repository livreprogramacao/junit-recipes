package junit.cookbook.suites.test;

import com.diasparsoftware.java.util.Money;
import junit.framework.Assert;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllocateMoneyTestBuilder extends Assert {
    private String testFileName;

    public AllocateMoneyTestBuilder(String testFileName) {
        this.testFileName = testFileName;
    }

    public static List makeTests(String testFileName)
            throws Exception {

        return new AllocateMoneyTestBuilder(testFileName)
                .makeTests();
    }

    private static int parseAsInt(Node fromNode,
                                  String xpathToInt) throws TransformerException {

        String intAsString = getNodeText(fromNode, xpathToInt);
        return Integer.parseInt(intAsString);
    }

    private static String getNodeText(Node fromNode,
                                      String xpath) throws TransformerException {

        Text text = (Text) XPathAPI.selectSingleNode(fromNode,
                xpath).getFirstChild();

        return text.getData();
    }

    private static Money parseAsMoney(Node fromNode,
                                      String xpathToMoneyObject) throws TransformerException,
            ParseException {

        String moneyAsString = getNodeText(fromNode,
                xpathToMoneyObject);

        return new Money(moneyAsString);
    }

    private List makeTests() throws Exception {
        List tests = new ArrayList();

        Document document = makeDocument(testFileName);

        NodeList testNodes = XPathAPI.selectNodeList(document,
                "/tests/test");

        for (int i = 0; i < testNodes.getLength(); i++) {
            Node eachTestNode = testNodes.item(i);

            AllocateMoneyTest eachAllocateMoneyTest = makeAllocateMoneyTest(eachTestNode);

            tests.add(eachAllocateMoneyTest);
        }

        return tests;
    }

    private AllocateMoneyTest makeAllocateMoneyTest(
            Node eachTestNode) throws TransformerException,
            ParseException {

        Money amountToSplit = parseAsMoney(eachTestNode,
                "input/amount-to-split");

        int nWays = parseAsInt(eachTestNode,
                "input/number-of-ways");

        NodeList expectedCutNodes = XPathAPI.selectNodeList(
                eachTestNode, "expected-result/cut");

        Map expectedCuts = parseExpectedCuts(expectedCutNodes);

        AllocateMoneyTest eachAllocateMoneyTest = new AllocateMoneyTest(
                amountToSplit, nWays, expectedCuts);

        return eachAllocateMoneyTest;
    }

    private Document makeDocument(String documentFileName)
            throws FactoryConfigurationError,
            ParserConfigurationException, SAXException, IOException {

        DocumentBuilderFactory factory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        InputStream testDataAsStream = AllocateMoneyTestBuilder.class
                .getResourceAsStream(documentFileName);

        Document document = builder.parse(testDataAsStream);
        return document;
    }

    private Map parseExpectedCuts(NodeList expectedCutNodes)
            throws TransformerException, ParseException {

        Map expectedCuts = new HashMap();

        for (int i = 0; i < expectedCutNodes.getLength(); i++) {
            Node eachCutNode = expectedCutNodes.item(i);

            Money cutAmount = parseAsMoney(eachCutNode,
                    "amount");
            int numberOfCuts = parseAsInt(eachCutNode, "number");

            expectedCuts.put(cutAmount, new Integer(
                    numberOfCuts));
        }

        return expectedCuts;
    }
}