package junit.cookbook.addons.test;

import junitx.extensions.ComparabilityTestCase;

public class StringCompareToTest extends ComparabilityTestCase {
    public StringCompareToTest(String name) {
        super(name);
    }

    protected Comparable createLessInstance() throws Exception {
        return "abc";
    }

    protected Comparable createEqualInstance() throws Exception {
        return "abcd";
    }

    protected Comparable createGreaterInstance() throws Exception {
        return "abcde";
    }
}
