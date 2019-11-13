package junit.cookbook.applications.test;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import junit.framework.TestCase;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class LinksTest extends TestCase {
    private WebClient client;
    private List urlsChecked;
    private Map failedLinks;
    private String domainName;

    protected void setUp() throws Exception {
        client = new WebClient();
        client.setJavaScriptEnabled(false);
        client.setRedirectEnabled(true);
        urlsChecked = new ArrayList();
        failedLinks = new HashMap();
    }

    public void testFindABrokenLink() throws Exception {
        domainName = "diasparsoftware.com";
        URL root = new URL("http://www." + domainName + "/");

        Page rootPage = client.getPage(root);
        checkAllLinksOnPage(rootPage);

        assertTrue(
                "Failed links (from => to): " + failedLinks.toString(),
                failedLinks.isEmpty());
    }

    private void checkAllLinksOnPage(Page page) throws IOException {
        if (!(page instanceof HtmlPage))
            return;

        URL currentUrl = page.getWebResponse().getUrl();
        String currentUrlAsString = currentUrl.toExternalForm();

        if (urlsChecked.contains(currentUrlAsString)) {
            return;
        }

        if (currentUrlAsString.indexOf(domainName) < 0) {
            return;
        }

        urlsChecked.add(currentUrlAsString);
        System.out.println("Checking URL: " + currentUrlAsString);

        HtmlPage rootHtmlPage = (HtmlPage) page;
        List anchors = rootHtmlPage.getAnchors();
        for (Iterator i = anchors.iterator(); i.hasNext(); ) {
            HtmlAnchor each = (HtmlAnchor) i.next();

            String hrefAttribute = each.getHrefAttribute();

            boolean isMailtoLink = hrefAttribute.startsWith("mailto:");
            boolean isHypertextLink = hrefAttribute.trim().length() > 0;

            if (!isMailtoLink && isHypertextLink) {
                try {
                    Page nextPage = each.click();
                    checkAllLinksOnPage(nextPage);
                } catch (Exception e) {
                    failedLinks.put(currentUrlAsString, each);
                }
            }
        }
    }
}
