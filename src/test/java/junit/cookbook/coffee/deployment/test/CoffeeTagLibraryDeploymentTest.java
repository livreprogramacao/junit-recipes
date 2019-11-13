package junit.cookbook.coffee.deployment.test;

import junit.cookbook.coffee.display.ShopcartItemBean;
import org.apache.crimson.jaxp.DocumentBuilderFactoryImpl;
import org.custommonkey.xmlunit.XMLTestCase;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class CoffeeTagLibraryDeploymentTest
        extends XMLTestCase {
    private Document tagLibraryDescriptorDocument;

    protected void setUp() throws Exception {
        DocumentBuilderFactory factory =
                new DocumentBuilderFactoryImpl();

        factory.setNamespaceAware(true);
        factory.setValidating(false);

        DocumentBuilder documentBuilder =
                factory.newDocumentBuilder();

        documentBuilder.setEntityResolver(
                new StringEntityResolver(""));

        File file =
                new File(
                        "../CoffeeShopWeb/Web Content/WEB-INF/template/jsp/coffeeShop.tld");

        tagLibraryDescriptorDocument =
                documentBuilder.parse(file);
    }

    public void testShopcartTagDeployedCorrectly()
            throws Exception {

        String[] expectedRelativeXpaths =
                new String[]{
                        "",
                        "/attribute[name='shopcartBean']",
                        "/attribute[name='each']",
                        "/attribute[name='each' and required='true']",
                        "/variable[name-from-attribute='each']",
                        "/variable[name-from-attribute='each' and variable-class='"
                                + ShopcartItemBean.class.getName()
                                + "']"};

        for (int i = 0;
             i < expectedRelativeXpaths.length;
             i++) {

            assertXpathExists(
                    "/taglib/tag[name='eachShopcartItem']"
                            + expectedRelativeXpaths[i],
                    tagLibraryDescriptorDocument);
        }
    }
}
