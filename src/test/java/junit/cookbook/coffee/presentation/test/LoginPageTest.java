package junit.cookbook.coffee.presentation.test;

import com.diasparsoftware.htmlunitx.InputStreamWebResponse;
import com.diasparsoftware.htmlunitx.TestableWebConnection;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import junit.framework.TestCase;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

/**
 * An example testing a static web page.
 */
public class LoginPageTest extends TestCase {
    private File webContentDirectory = new File("test/data");

    public void testContent() throws Exception {
        URL loginPageUrl = new URL(
                "http://localhost/coffeeShop/login.html");

        File loginPageFile = new File(webContentDirectory,
                "login.html");

        FileInputStream webPageAsInputStream = new FileInputStream(
                loginPageFile);

        TextUtil.toInputStream("The actual content");

        WebClient webClient = new WebClient();

        // This used to be InputStreamWebConnection, but that class
        // was not InputStream-specific
        TestableWebConnection inputStreamWebConnection = new TestableWebConnection(
                webClient);

        InputStreamWebResponse webResponse = new InputStreamWebResponse(
                loginPageUrl, webPageAsInputStream);
        webResponse.setContentType("text/html");

        inputStreamWebConnection.setResponse(webResponse);

        webClient.setWebConnection(inputStreamWebConnection);
        HtmlPage loginPage = (HtmlPage) webClient
                .getPage(loginPageUrl);

        assertEquals("Login", loginPage.getTitleText());
        assertTrue(loginPage.asText().indexOf(
                "Enter your user name and password") >= 0);

        HtmlForm loginForm = loginPage
                .getFormByName("loginForm");
        assertNotNull(loginForm);
        assertEquals("/coffeeShop", loginForm
                .getActionAttribute());
        assertTrue("post".equalsIgnoreCase(loginForm
                .getMethodAttribute()));

        HtmlInput usernameInput = loginForm
                .getInputByName("username");
        assertNotNull(usernameInput);
        assertEquals(12, Integer.parseInt(usernameInput
                .getSizeAttribute()));

        // Better than checking type attribute; ensures the value
        // is supported by HTML
        assertTrue(usernameInput instanceof HtmlTextInput);
        assertEquals("", usernameInput.getValueAttribute());

        HtmlInput passwordInput = loginForm
                .getInputByName("password");
        assertNotNull(passwordInput);
        assertTrue(passwordInput instanceof HtmlPasswordInput);
        assertEquals(12, Integer.parseInt(passwordInput
                .getSizeAttribute()));
        assertEquals("", passwordInput.getValueAttribute());

        HtmlInput loginInput = loginForm
                .getInputByName("login");
        assertNotNull(loginInput);
        assertTrue(loginInput instanceof HtmlSubmitInput);
        assertEquals("Login", loginInput.getValueAttribute());
    }

    // Use this test to verify that FileSystemWebConnection
    // does not cause a problem
    public void testDisplayUsingFakeConnection()
            throws Exception {
        URL loginPageUrl = new URL(
                "http://localhost/coffeeShop/login.html");
        File loginPageFile = new File(webContentDirectory,
                "login.html");

        WebClient webClient = new WebClient();

        FakeWebConnection fakeWebConnection = new FakeWebConnection(
                webClient);
        fakeWebConnection
                .setContent("<html><head><title>Login</title></head></html>");

        webClient.setWebConnection(fakeWebConnection);
        HtmlPage loginPage = (HtmlPage) webClient
                .getPage(new URL("http://foo"));

        assertEquals("Login", loginPage.getTitleText());
    }
}