package junit.cookbook.patterns.test;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class SpyTestCase extends TestCase {
    public List invokedMethodNames = new ArrayList();

    protected void runTest() throws Throwable {
        invokedMethodNames.add("runTest");
    }

    protected void setUp() throws Exception {
        invokedMethodNames.add("setUp");
    }

    protected void tearDown() throws Exception {
        invokedMethodNames.add("tearDown");
    }
}
