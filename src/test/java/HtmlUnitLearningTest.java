import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import junit.framework.TestCase;

import java.net.URL;
import java.util.Iterator;
import java.util.List;

public class HtmlUnitLearningTest extends TestCase {
    private WebClient client = new WebClient();

    public void testAnchorWithNoHref() throws Exception {
        HtmlPage page =
                (HtmlPage) client.getPage(
                        new URL("http://www.diasparsoftware.com/articles/refactorings/replaceSubclassWithCollaborator.html"));

        List anchors = page.getAnchors();
        for (Iterator i = anchors.iterator(); i.hasNext(); ) {
            HtmlAnchor each = (HtmlAnchor) i.next();
            assertNotNull(
                    "Null href attribute: " + each,
                    each.getHrefAttribute());

            assertTrue(
                    "Empty href attribute: " + each,
                    each.getHrefAttribute().trim().length() > 0);
        }
    }

    public void testProblemWithTopTarget() throws Exception {
        HtmlPage page =
                (HtmlPage) client.getPage(
                        new URL("http://www.diasparsoftware.com/articles/refactorings/replaceSubclassWithCollaborator.html"));

        URL url = page.getFullyQualifiedUrl("#Top");

        assertEquals(
                "http://www.diasparsoftware.com/articles/refactorings/replaceSubclassWithCollaborator.html#Top",
                url.toExternalForm());

        HtmlAnchor htmlAnchor = page.getAnchorByHref("#Top");
        HtmlPage nextPage = (HtmlPage) htmlAnchor.click();

        url = nextPage.getFullyQualifiedUrl("#Top");

        assertEquals(
                "http://www.diasparsoftware.com/articles/refactorings/replaceSubclassWithCollaborator.html#Top",
                url.toExternalForm());
    }
}
