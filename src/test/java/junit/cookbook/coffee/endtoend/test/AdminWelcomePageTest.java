package junit.cookbook.coffee.endtoend.test;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import junit.framework.TestCase;

import javax.servlet.http.HttpServletResponse;
import java.net.URL;

public class AdminWelcomePageTest extends TestCase {
    private WebClient webClient;

    protected void setUp() throws Exception {
        webClient = new WebClient();
        webClient.setThrowExceptionOnFailingStatusCode(false);
    }

    public void testWithoutCredentials() throws Exception {
        Page page =
                webClient.getPage(
                        new URL("http://localhost:8080/coffeeShop/admin/"));

        assertEquals(
                HttpServletResponse.SC_UNAUTHORIZED,
                page.getWebResponse().getStatusCode());
    }

    public void testAdminLoggedIn() throws Exception {
        webClient.setCredentialProvider(
                new SimpleCredentialProvider("admin", "adm1n"));

        Page page =
                webClient.getPage(
                        new URL("http://localhost:8080/coffeeShop/admin/"));

        assertEquals(
                HttpServletResponse.SC_OK,
                page.getWebResponse().getStatusCode());

        assertTrue(page instanceof HtmlPage);

        HtmlPage welcomePage = (HtmlPage) page;
        assertEquals(
                "Coffee Shop - Administration",
                welcomePage.getTitleText());
    }

    public void testShopperLoggedIn() throws Exception {
        webClient.setCredentialProvider(
                new SimpleCredentialProvider("shopper", "sh0pper"));

        Page page =
                webClient.getPage(
                        new URL("http://localhost:8080/coffeeShop/admin/"));

        assertEquals(
                HttpServletResponse.SC_FORBIDDEN,
                page.getWebResponse().getStatusCode());
    }
}
