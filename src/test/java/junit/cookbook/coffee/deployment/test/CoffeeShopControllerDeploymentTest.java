package junit.cookbook.coffee.deployment.test;

import org.custommonkey.xmlunit.*;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.FileReader;

public class CoffeeShopControllerDeploymentTest extends XMLTestCase {
    private Document webDeploymentDescriptorDocument;

    protected void setUp() throws Exception {
        XMLUnit.setIgnoreWhitespace(true);

        webDeploymentDescriptorDocument =
                XMLUnit.buildTestDocument(
                        new InputSource(
                                new FileReader("../CoffeeShopWeb/Web Content/WEB-INF/web.xml")));
    }
}
