package junit.cookbook.xmlunit.test;

import junit.cookbook.xmlunit.ArticleBuilder;
import org.custommonkey.xmlunit.*;
import org.custommonkey.xmlunit.XMLTestCase;

public class BuildArticleTest extends XMLTestCase {
    public void testMultipleParagraphs() throws Exception {
        XMLUnit.setIgnoreWhitespace(true);

        ArticleBuilder builder =
                new ArticleBuilder("Testing and XML" + "");

        builder.addAuthorName("J. B. Rainsberger");
        builder.addHeading("A heading.");
        builder.addParagraph("This is a paragraph.");

        String expected =
                "<?xml version=\"1.0\" ?>"
                        + "<article>"
                        + "<title>Testing and XML</title>"
                        + "<author>J. B. Rainsberger</author>"
                        + "<h1>A heading.</h1>"
                        + "<p>This is a paragraph.</p>"
                        + "</article>";

        String actual = builder.toXml();

        Diff diff = new Diff(expected, actual);
        assertTrue(new DetailedDiff(diff).toString(), diff.identical());
    }
}
