package junit.cookbook.coffee.endtoend.test;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;

public class WelcomeHtmlPage {
    private static final String controllerUri = "coffee";

    private HtmlPage htmlPage;

    public WelcomeHtmlPage(Page page) {
        if (page instanceof HtmlPage == false) {
            throw new IllegalArgumentException(
                    "Expected an HtmlPage, but was an "
                            + page.getClass().getName());
        }

        this.htmlPage = (HtmlPage) page;
    }

    public String getTitleText() {
        return htmlPage.getTitleText();
    }

    public boolean containsLaunchPoints() {
        try {
            getLaunchPointsForm();
            return true;
        } catch (ElementNotFoundException e) {
            return false;
        }
    }

    private HtmlForm getLaunchPointsForm() {
        return htmlPage.getFormByName("launchPoints");
    }

    public boolean canBrowseToCatalog() {
        try {
            getBrowseCatalogButton();
            return controllerUri.equals(
                    getLaunchPointsForm().getActionAttribute());
        } catch (ElementNotFoundException e) {
            return false;
        }
    }

    private HtmlInput getBrowseCatalogButton() {
        return getLaunchPointsForm().getInputByName("browseCatalog");
    }

    public Page clickBrowseCatalog() throws Exception {
        return getBrowseCatalogButton().click();
    }
}
