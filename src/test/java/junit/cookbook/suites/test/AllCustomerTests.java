package junit.cookbook.suites.test;

import com.gargoylesoftware.base.testing.RecursiveTestSuite;
import com.gargoylesoftware.base.testing.TestFilter;
import junit.framework.Test;

public class AllCustomerTests {
    public static Test suite() throws Exception {
        return new RecursiveTestSuite("classes", new TestFilter() {
            public boolean accept(Class eachTestClass) {
                return (
                        CustomerTest.class.isAssignableFrom(
                                eachTestClass));
            }
        });
    }
}
