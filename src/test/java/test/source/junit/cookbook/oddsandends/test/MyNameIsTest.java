package junit.cookbook.oddsandends.test;

import junit.framework.TestCase;

public class MyNameIsTest extends TestCase {
    public void testDummy() {
    }

    public void testDummy2() {
    }

    public void testDummy3() {
    }

    public void testDummy4() {
    }

    protected void runTest() throws Throwable {
        System.out.println("Starting test " + toString());
        super.runTest();
        System.out.println("Ending test " + toString());
    }

}
