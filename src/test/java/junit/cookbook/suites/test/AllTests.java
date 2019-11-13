package junit.cookbook.suites.test;

import com.gargoylesoftware.base.testing.RecursiveTestSuite;
import com.gargoylesoftware.base.testing.TestFilter;
import junit.framework.Test;

import java.lang.reflect.Modifier;

public class AllTests {
    public static Test suite() throws Exception {
        return new RecursiveTestSuite("classes", new TestFilter() {
            public boolean accept(Class eachTestClass) {
                boolean classIsAbstract =
                        Modifier.isAbstract(
                                eachTestClass.getModifiers());

                return (classIsAbstract == false);
            }
        });
    }
}
