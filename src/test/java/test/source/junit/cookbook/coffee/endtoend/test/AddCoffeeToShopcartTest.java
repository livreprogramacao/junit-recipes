package junit.cookbook.coffee.endtoend.test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;
import junit.framework.TestCase;

import java.net.URL;

public class AddCoffeeToShopcartTest extends TestCase {
    public void testAddToEmptyShopcart() throws Exception {
        String coffeeProductId = "1";
        int expectedQuantity = 5;

        WebClient webClient = new WebClient();
        HtmlPage welcomePage =
                (HtmlPage) webClient.getPage(
                        new URL("http://localhost:9080/coffeeShop"));

        HtmlForm launchForm = welcomePage.getFormByName("launchPoints");
        HtmlPage catalogPage =
                (HtmlPage) launchForm
                        .getInputByName("browseCatalog")
                        .click();

        HtmlForm catalogForm = catalogPage.getFormByName("catalogForm");
        catalogForm
                .getInputByName("quantity-" + coffeeProductId)
                .setValueAttribute(String.valueOf(expectedQuantity));
        HtmlPage shopcartPage =
                (HtmlPage) catalogForm
                        .getInputByName("addToShopcart-" + coffeeProductId)
                        .click();

        HtmlTableDataCell quantityInShopcartCell =
                (HtmlTableDataCell) shopcartPage.getHtmlElementById(
                        "product-" + coffeeProductId);

        assertEquals(
                expectedQuantity + " kg",
                quantityInShopcartCell.asText());
    }
}
