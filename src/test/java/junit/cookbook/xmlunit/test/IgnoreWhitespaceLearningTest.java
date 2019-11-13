package junit.cookbook.xmlunit.test;

import org.custommonkey.xmlunit.*;

public class IgnoreWhitespaceLearningTest extends XMLTestCase {
    protected void setUp() throws Exception {
        XMLUnit.setIgnoreWhitespace(true);
    }

    public void testWhitespaceNodesInDocument() throws Exception {
        assertXMLEqual(
                "<person><firstName>J. B.</firstName><lastName>Rainsberger</lastName></person>",
                "<person>\n\t<firstName>J. B.</firstName>\n\t<lastName>\n\t\tRainsberger\n\t</lastName>\n</person>");
    }

    public void testInternalWhitespaceInText() throws Exception {
        assertFalse(
                new DetailedDiff(
                        new Diff("<hello>A B</hello>", "<hello>A  B</hello>"))
                        .similar());

        assertTrue(
                new DetailedDiff(
                        new Diff(
                                "<hello>   A B</hello>",
                                "<hello>A B</hello>"))
                        .similar());
    }
}
