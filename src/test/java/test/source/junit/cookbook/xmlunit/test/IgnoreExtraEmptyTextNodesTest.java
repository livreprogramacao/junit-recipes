package junit.cookbook.xmlunit.test;

import junit.cookbook.xmlunit.IgnoreExtraEmptyTextNodesDifferenceListener;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.custommonkey.xmlunit.*;

public class IgnoreExtraEmptyTextNodesTest extends XMLTestCase {
    private static final int EXPECT_SIMILAR = 0;
    private static final int EXPECT_DIFFERENT = 1;

    private DifferenceListener differenceListener;
    private String expected;
    private String actual;
    private boolean expectSimilar;

    public IgnoreExtraEmptyTextNodesTest(
            String testName,
            String expectedXmlDocument,
            String actualXmlDocument) {

        this(
                testName,
                expectedXmlDocument,
                actualXmlDocument,
                EXPECT_SIMILAR);
    }

    public IgnoreExtraEmptyTextNodesTest(
            String testName,
            String expectedXmlDocument,
            String actualXmlDocument,
            int expectedResult) {

        setName(testName);
        this.expected = expectedXmlDocument;
        this.actual = actualXmlDocument;
        this.expectSimilar = (expectedResult == EXPECT_SIMILAR);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();

        suite.addTest(
                new IgnoreExtraEmptyTextNodesTest(
                        "testOnlyRootElement",
                        "<?xml version=\"1.0\" ?>\n<person></person>",
                        "<?xml version=\"1.0\" ?><person></person>"));

        suite.addTest(
                new IgnoreExtraEmptyTextNodesTest(
                        "testOnlyRootElement",
                        "<?xml version=\"1.0\" ?>\n<person>\n</person>",
                        "<?xml version=\"1.0\" ?><person></person>"));

        suite.addTest(
                new IgnoreExtraEmptyTextNodesTest(
                        "testRootElementContainsText",
                        "<?xml version=\"1.0\" ?>\n<person>hello</person>",
                        "<?xml version=\"1.0\" ?><person>hello</person>"));

        suite.addTest(
                new IgnoreExtraEmptyTextNodesTest(
                        "testRootElementContainsEmptyElement",
                        "<?xml version=\"1.0\" ?>\n<person>\n<firstName></firstName>\n</person>",
                        "<?xml version=\"1.0\" ?><person><firstName></firstName></person>"));

        suite.addTest(
                new IgnoreExtraEmptyTextNodesTest(
                        "testRootElementContainsEmptyElementWithSpacing",
                        "<?xml version=\"1.0\" ?>\n<person>\n\t<firstName>\n\t</firstName>\n</person>",
                        "<?xml version=\"1.0\" ?><person><firstName></firstName></person>"));

        suite.addTest(
                new IgnoreExtraEmptyTextNodesTest(
                        "testRootElementContainsNonemptyElementWithSpacing",
                        "<?xml version=\"1.0\" ?>\n<person>\n\t<firstName>J. B.</firstName>\n</person>",
                        "<?xml version=\"1.0\" ?><person><firstName>J. B.</firstName></person>"));

        suite.addTest(
                new IgnoreExtraEmptyTextNodesTest(
                        "testRootElementContainsNonemptyElementWithSpacingAndDifferentValues",
                        "<?xml version=\"1.0\" ?>\n<person>\n\t<firstName>J. B.</firstName>\n</person>",
                        "<?xml version=\"1.0\" ?><person><firstName>Joe</firstName></person>",
                        EXPECT_DIFFERENT));

        return suite;
    }

    protected void setUp() throws Exception {
        XMLUnit.setIgnoreWhitespace(true);
        differenceListener =
                new IgnoreExtraEmptyTextNodesDifferenceListener();
    }

    protected void runTest() throws Throwable {
        Diff diff = new DetailedDiff(new Diff(expected, actual));
        diff.overrideDifferenceListener(differenceListener);

        if (expectSimilar) {
            assertTrue("Differences: " + diff, diff.similar());
        } else {
            assertFalse(
                    "Documents are similar?!",
                    diff.similar());
        }
    }
}

