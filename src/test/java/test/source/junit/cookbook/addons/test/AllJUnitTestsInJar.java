package junit.cookbook.addons.test;

import junit.framework.Test;
import junitx.util.ArchiveSuiteBuilder;

public class AllJUnitTestsInJar {
    public static Test suite() throws Exception {
        return new ArchiveSuiteBuilder().suite(
                "d:/junit3.8.1/junit-tests.jar");
    }
}
