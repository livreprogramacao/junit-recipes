package junit.cookbook.coffee.presentation.xsl.test;

import com.diasparsoftware.java.util.Money;
import junit.cookbook.coffee.display.ShopcartItemBean;
import junit.cookbook.coffee.presentation.xsl.TransformXmlService;
import org.apache.xpath.XPathAPI;
import org.custommonkey.xmlunit.XMLTestCase;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class DisplayShopcartXslTest extends XMLTestCase {
    private String displayShopcartXslFilename =
            "../CoffeeShopWeb/Web Content/WEB-INF/template/style/"
                    + "displayShopcart.xsl";

    private Source displayShopcartXsl;
    private Reader displayShopcartXslReader;
    private String shopcartDocumentPremable;

    protected void setUp() throws Exception {
        displayShopcartXsl =
                new StreamSource(
                        new FileInputStream(displayShopcartXslFilename));

        displayShopcartXslReader =
                new FileReader(new File(displayShopcartXslFilename));

        shopcartDocumentPremable =
                "<?xml version=\"1.0\" ?>\n"
                        + "<!DOCTYPE shopcart ["
                        + "<!ELEMENT shopcart (item*, subtotal)>"
                        + "<!ELEMENT item (name, quantity, unit-price, total-price)>"
                        + "<!ATTLIST item id CDATA #REQUIRED>"
                        + "<!ELEMENT name (#PCDATA)>"
                        + "<!ELEMENT quantity (#PCDATA)>"
                        + "<!ELEMENT unit-price (#PCDATA)>"
                        + "<!ELEMENT total-price (#PCDATA)>"
                        + "<!ELEMENT subtotal (#PCDATA)>"
                        + "]>";
    }

    public void testEmpty() throws Exception {
        String shopcartXmlAsString =
                shopcartDocumentPremable
                        + "<shopcart>"
                        + "<subtotal>$0.00</subtotal>"
                        + "</shopcart>";

        Document displayShopcartDom =
                doDisplayShopcartTransformation(shopcartXmlAsString);

        assertShopcartTableExists(displayShopcartDom);
        assertSubtotalEquals("$0.00", displayShopcartDom);

        assertXpathNotExists(
                "//tr[@class='shopcartItem']",
                displayShopcartDom);
    }

    public void testOneItem() throws Exception {
        String shopcartXmlAsString =
                shopcartDocumentPremable
                        + "<shopcart>"
                        + "<item id='762'>"
                        + "<name>Special Blend</name>"
                        + "<quantity>1</quantity>"
                        + "<unit-price>$7.25</unit-price>"
                        + "<total-price>$7.25</total-price>"
                        + "</item>"
                        + "<subtotal>$7.25</subtotal>"
                        + "</shopcart>";

        Document displayShopcartDom =
                doDisplayShopcartTransformation(shopcartXmlAsString);

        assertShopcartTableExists(displayShopcartDom);
        assertSubtotalEquals("$7.25", displayShopcartDom);

        assertShopcartItemAtRowIndexEquals(
                new ShopcartItemBean(
                        "Special Blend",
                        "762",
                        1,
                        Money.dollars(7, 25)),
                displayShopcartDom,
                1);
    }

    public void testThreeItems() throws Exception {
        // NOTE: Be sure to put line breaks after each <item>
        // tag to avoid overstepping the limit for characters
        // on a single line.

        String shopcartXmlAsString =
                shopcartDocumentPremable
                        + "<shopcart>\n"
                        + "<item id=\"762\">"
                        + "<name>Special Blend</name>"
                        + "<quantity>1</quantity>"
                        + "<unit-price>$7.25</unit-price>"
                        + "<total-price>$7.25</total-price>"
                        + "</item>\n"
                        + "<item id=\"001\">"
                        + "<name>Short</name>"
                        + "<quantity>2</quantity>"
                        + "<unit-price>$6.50</unit-price>"
                        + "<total-price>$13.00</total-price>"
                        + "</item>\n"
                        + "<item id=\"803\">"
                        + "<name>Colombiano</name>"
                        + "<quantity>4</quantity>"
                        + "<unit-price>$8.00</unit-price>"
                        + "<total-price>$32.00</total-price>"
                        + "</item>\n"
                        + "<subtotal>$52.25</subtotal>"
                        + "</shopcart>";

        Document displayShopcartDom =
                doDisplayShopcartTransformation(shopcartXmlAsString);

        assertShopcartTableExists(displayShopcartDom);
        assertSubtotalEquals("$52.25", displayShopcartDom);

        assertShopcartItemAtRowIndexEquals(
                new ShopcartItemBean(
                        "Special Blend",
                        "762",
                        1,
                        Money.dollars(7, 25)),
                displayShopcartDom,
                1);

        assertShopcartItemAtRowIndexEquals(
                new ShopcartItemBean(
                        "Short",
                        "001",
                        2,
                        Money.dollars(6, 50)),
                displayShopcartDom,
                2);

        assertShopcartItemAtRowIndexEquals(
                new ShopcartItemBean(
                        "Colombiano",
                        "803",
                        4,
                        Money.dollars(8, 0)),
                displayShopcartDom,
                3);
    }

    public void assertSubtotalEquals(
            String expectedSubtotal,
            Document displayShopcartDom)
            throws TransformerException {

        assertXpathEvaluatesTo(
                expectedSubtotal,
                "//table[@name='shopcart']//td[@id='subtotal']",
                displayShopcartDom);
    }

    public void assertShopcartTableExists(Document displayShopcartDom)
            throws TransformerException {

        assertXpathExists(
                "//table[@name='shopcart']",
                displayShopcartDom);
    }

    public void assertShopcartItemAtRowIndexEquals(
            ShopcartItemBean expectedShopcartItemBean,
            Document displayShopcartDom,
            int rowIndex)
            throws TransformerException {

        Node productIdAttributeNode =
                XPathAPI.selectSingleNode(
                        displayShopcartDom,
                        "//tr[@class='shopcartItem'][" + rowIndex + "]/@id");

        assertNotNull(
                "Cannot find product ID at row index " + rowIndex,
                productIdAttributeNode);

        String productId = ((Attr) productIdAttributeNode).getValue();

        NodeList columnNodes =
                XPathAPI.selectNodeList(
                        displayShopcartDom,
                        "//tr[@class='shopcartItem'][" + rowIndex + "]/td");

        String actualCoffeeName = getTextAtNode(columnNodes.item(0));
        String actualQuantityAsString =
                getTextAtNode(columnNodes.item(1));
        String actualUnitPriceAsString =
                getTextAtNode(columnNodes.item(2));

        ShopcartItemBean actualShopcartItemBean =
                new ShopcartItemBean(
                        actualCoffeeName,
                        productId,
                        Integer.parseInt(actualQuantityAsString),
                        Money.parse(actualUnitPriceAsString));

        assertEquals(
                "Wrong shopcart item in row #" + rowIndex,
                expectedShopcartItemBean,
                actualShopcartItemBean);
    }

    public String getTextAtNode(Node tableDataNode) {
        return tableDataNode.getFirstChild().getNodeValue();
    }

    public Document doDisplayShopcartTransformation(String shopcartXmlAsString)
            throws Exception {

        TransformXmlService service =
                new TransformXmlService(displayShopcartXslReader);

        service.setSourceDocumentValidationEnabled(true);

        DOMResult documentResult = new DOMResult();

        service.transform(
                new StreamSource(new StringReader(shopcartXmlAsString)),
                documentResult);

        assertTrue(
                "Incoming XML document failed validation: "
                        + service.getValidationProblems(),
                service.isSourceDocumentValid());

        return (Document) documentResult.getNode();
    }
}
