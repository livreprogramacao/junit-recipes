package junit.cookbook.coffee.web.test;

import org.apache.xerces.parsers.DOMParser;
import org.custommonkey.xmlunit.XMLTestCase;
import org.cyberneko.html.HTMLConfiguration;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.FileInputStream;

public class WelcomePageTest extends XMLTestCase {
    private Document welcomePageDom;

    protected void setUp() throws Exception {
        DOMParser nekoParser = new DOMParser(new HTMLConfiguration());

        nekoParser.setFeature(
                "http://cyberneko.org/html/features/augmentations",
                true);

        nekoParser.setProperty(
                "http://cyberneko.org/html/properties/names/elems",
                "lower");

        nekoParser.setProperty(
                "http://cyberneko.org/html/properties/names/attrs",
                "lower");

        nekoParser.setFeature(
                "http://cyberneko.org/html/features/report-errors",
                true);

        nekoParser.parse(
                new InputSource(
                        new FileInputStream("../CoffeeShopWeb/Web Content/index.html")));

        welcomePageDom = nekoParser.getDocument();
        assertNotNull("Could not load DOM", welcomePageDom);
    }

    public void testCanNavigateToCatalog() throws Exception {
        assertXpathExists("//form", welcomePageDom);

        assertXpathEvaluatesTo(
                "coffee",
                "//form/@action",
                welcomePageDom);

        assertXpathExists(
                "//form[@action='coffee']//input[@type='submit']",
                welcomePageDom);

        assertXpathEvaluatesTo(
                "browseCatalog",
                "//form[@action='coffee']"
                        + "//input[@type='submit']/@name",
                welcomePageDom);
    }
}
