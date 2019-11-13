package junit.cookbook.coffee.endtoend.test;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Element;

public class CatalogHtmlPage {
    private HtmlPage htmlPage;

    public CatalogHtmlPage(Page page) {
        if (page instanceof HtmlPage == false) {
            throw new IllegalArgumentException(
                    "Expected an HtmlPage, but was an "
                            + page.getClass().getName());
        }

        this.htmlPage = (HtmlPage) page;
    }

    public boolean containsCatalog() {
        try {
            getCatalogTable();
            return true;
        } catch (ElementNotFoundException e) {
            return false;
        }
    }

    public HtmlTable getCatalogTable() {
        return (HtmlTable) htmlPage.getHtmlElementById("catalog");
    }

    public HtmlSubmitInput getFirstProductBuyButton() {
        HtmlTable catalogHtmlTable = getCatalogTable();

        HtmlTableBody firstTableBody =
                (HtmlTableBody) catalogHtmlTable.getBodies().get(0);

        HtmlTableRow firstTableRow =
                (HtmlTableRow) firstTableBody.getRows().get(0);

        HtmlTableCell buyButtonCell =
                (HtmlTableCell) firstTableRow.getCells().get(3);

        HtmlSubmitInput buyButton =
                (HtmlSubmitInput) buyButtonCell
                        .getHtmlElementsByAttribute("input", "value", "Buy!")
                        .get(0);

        return buyButton;
    }

    public boolean catalogHasItems() throws Exception {
        Element tableElement = getCatalogTable().getElement();
        return (
                XPathAPI
                        .selectNodeList(tableElement, "TBODY/TR")
                        .getLength()
                        > 0);
    }
}
