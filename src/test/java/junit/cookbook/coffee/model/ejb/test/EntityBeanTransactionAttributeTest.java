package junit.cookbook.coffee.model.ejb.test;

import org.apache.xpath.XPathAPI;
import org.custommonkey.xmlunit.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.FileInputStream;

public class EntityBeanTransactionAttributeTest extends XMLTestCase {
    protected void setUp() throws Exception {
        XMLUnit.setIgnoreWhitespace(true);
    }

    public void testOrder() throws Exception {
        doTestTransactionAttribute(
                "../CoffeeShopLegacyEJB/META-INF/ejb-jar.xml",
                "Order");
    }

    private void doTestTransactionAttribute(
            String ejbDeploymentDescriptorFilename,
            String ejbName)
            throws Exception {

        Document ejbDeploymentDescriptor =
                XMLUnit.buildTestDocument(
                        new InputSource(
                                new FileInputStream(
                                        new File(ejbDeploymentDescriptorFilename))));

        String transactionAttributeXpath =
                "/ejb-jar/assembly-descriptor"
                        + "/container-transaction"
                        + "[method/ejb-name='"
                        + ejbName
                        + "' and "
                        + "(method/method-intf='Remote' or "
                        + "method/method-intf='Local')]"
                        + "/trans-attribute";

        NodeList transactionAttributeNodes =
                XPathAPI.selectNodeList(
                        ejbDeploymentDescriptor,
                        transactionAttributeXpath);

        assertTrue(
                "No transaction attribute setting for " + ejbName + " EJB",
                transactionAttributeNodes.getLength() > 0);

        for (int i = 0;
             i < transactionAttributeNodes.getLength();
             i++) {

            Node each = transactionAttributeNodes.item(i);
            Text text = (Text) each.getFirstChild();

            Node assemblyDescriptorNode =
                    each.getParentNode().getParentNode();

            assertEquals(
                    "Transaction attribute incorrect at "
                            + assemblyDescriptorNode.toString(),
                    "Required",
                    text.getData());
        }
    }
}
