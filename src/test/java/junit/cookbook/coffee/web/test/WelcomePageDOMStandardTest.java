package junit.cookbook.coffee.web.test;

import org.apache.xerces.parsers.DOMParser;
import org.custommonkey.xmlunit.XMLTestCase;
import org.cyberneko.html.HTMLConfiguration;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.FileInputStream;

public class WelcomePageDOMStandardTest extends XMLTestCase {
    private Document welcomePageDom;

    protected void setUp() throws Exception {
        DOMParser nekoParser = new DOMParser(new HTMLConfiguration());

        nekoParser.parse(
                new InputSource(
                        new FileInputStream(
                                "../CoffeeShopWeb/Web Content/"
                                        + "index-DOMStandard.html")));

        welcomePageDom = nekoParser.getDocument();
        assertNotNull("Could not load DOM", welcomePageDom);
    }

    public void testCanNavigateToCatalog() throws Exception {
        assertXpathExists("//FORM", welcomePageDom);

        assertXpathEvaluatesTo(
                "coffee",
                "//FORM/@action",
                welcomePageDom);

        assertXpathExists(
                "//FORM[@action='coffee']//INPUT[@type='submit']",
                welcomePageDom);

        assertXpathEvaluatesTo(
                "browseCatalog",
                "//FORM[@action='coffee']"
                        + "//INPUT[@type='submit']/@name",
                welcomePageDom);
    }
}
