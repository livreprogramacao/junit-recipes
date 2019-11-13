package junit.cookbook.gsbase.test;

import com.gargoylesoftware.base.testing.TestUtil;
import junit.cookbook.util.Money;
import junit.framework.TestCase;
import junitx.runner.TestRunner;

public class CloneTest extends TestCase {
    public static void main(String[] args) {
        runTestsByClass(CloneTest.class);
    }

    private static void runTestsByClass(Class testClass) {
        TestRunner.main(
                new String[]{
                        "-class",
                        testClass.getName(),
                        "-runner.properties",
                        "./test/properties/runner.properties"});
    }

    public void testCloneMoney() throws Exception {
        Money moneyToClone = Money.dollars(1000);
        TestUtil.testClone(moneyToClone, true);
    }
}
