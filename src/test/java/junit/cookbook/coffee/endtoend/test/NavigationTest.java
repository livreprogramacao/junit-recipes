package junit.cookbook.coffee.endtoend.test;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import junit.framework.TestCase;

import java.net.URL;

public class NavigationTest extends TestCase {
    private WebClient webClient;

    protected void setUp() throws Exception {
        webClient = new WebClient();
        webClient.setRedirectEnabled(true);
    }

    public void testNavigateToCatalog() throws Exception {
        Page page =
                webClient.getPage(
                        new URL("http://localhost:8080/coffeeShop/"));

        assertTrue(
                "Welcome page not an HTML page",
                page instanceof HtmlPage);

        HtmlPage welcomePage = (HtmlPage) page;
        HtmlForm launchPointsForm =
                welcomePage.getFormByName("launchPoints");

        HtmlInput htmlInput =
                launchPointsForm.getInputByName("browseCatalog");

        assertTrue(
                "'browseCatalog' is not a submit button",
                htmlInput instanceof HtmlSubmitInput);

        HtmlSubmitInput browseCatalogSubmit =
                (HtmlSubmitInput) htmlInput;

        Page page2 = browseCatalogSubmit.click();
        assertTrue(
                "Catalog page not an HTML page",
                page2 instanceof HtmlPage);

        HtmlPage catalogPage = (HtmlPage) page2;
        assertEquals(
                "Coffee Shop - Catalog",
                catalogPage.getTitleText());
    }
}
