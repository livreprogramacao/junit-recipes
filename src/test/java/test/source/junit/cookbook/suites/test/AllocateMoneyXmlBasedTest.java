package junit.cookbook.suites.test;

import junit.cookbook.util.Money;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AllocateMoneyXmlBasedTest extends TestCase {
    private Money amountToSplit;
    private int nWays;
    private Map expectedCuts;
    private Map actualCuts;

    public AllocateMoneyXmlBasedTest(Money amountToSplit,
                                     int nWays, Map expectedCuts) {

        super("testAllocate");

        this.amountToSplit = amountToSplit;
        this.nWays = nWays;
        this.expectedCuts = expectedCuts;
    }

    public static Test suite() throws Exception {
        TestSuite suite = new TestSuite();

        String testFileName = "/junit/cookbook/suites/test/allocate-money-tests.xml";

        List tests = AllocateMoneyTestBuilder
                .makeTests(testFileName);

        for (Iterator i = tests.iterator(); i.hasNext(); ) {
            AllocateMoneyTest eachTest = (AllocateMoneyTest) i
                    .next();
            suite.addTest(eachTest);
        }
        return suite;
    }

    public void testAllocate() {
        List allocatedAmounts = amountToSplit.split(nWays);
        Map actualCuts = organizeIntoBag(allocatedAmounts);
        assertEquals(expectedCuts, actualCuts);
    }

    private Map organizeIntoBag(List allocatedAmounts) {
        Map bagOfCuts = new HashMap();

        for (Iterator i = allocatedAmounts.iterator(); i
                .hasNext(); ) {

            Money eachAmount = (Money) i.next();
            incrementCountForCutAmount(bagOfCuts, eachAmount);
        }
        return bagOfCuts;
    }

    private void incrementCountForCutAmount(Map bagOfCuts,
                                            Money eachAmount) {

        Object cutsForAmountAsObject = bagOfCuts
                .get(eachAmount);

        int cutsForAmount;
        if (cutsForAmountAsObject == null) {
            cutsForAmount = 0;
        } else {
            cutsForAmount = ((Integer) cutsForAmountAsObject)
                    .intValue();
        }

        bagOfCuts.put(eachAmount,
                new Integer(cutsForAmount + 1));
    }
}