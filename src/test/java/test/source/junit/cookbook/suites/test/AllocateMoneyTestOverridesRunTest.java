package junit.cookbook.suites.test;

import junit.cookbook.util.Money;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.*;

public class AllocateMoneyTestOverridesRunTest extends TestCase {
    private Money amountToSplit;
    private int nWays;
    private Map expectedCuts;
    private Map actualCuts;

    public AllocateMoneyTestOverridesRunTest(
            String testName,
            Money amountToSplit,
            int nWays,
            Map expectedCuts) {

        super(testName);

        this.amountToSplit = amountToSplit;
        this.nWays = nWays;
        this.expectedCuts = expectedCuts;
    }

    public static Test suite() throws Exception {
        TestSuite suite = new TestSuite();

        Map oneGSixWays = new HashMap();
        oneGSixWays.put(new Money(166, 66), new Integer(2));
        oneGSixWays.put(new Money(166, 67), new Integer(4));
        suite.addTest(
                new AllocateMoneyTestOverridesRunTest(
                        "testAllocate/Requires Rounding",
                        new Money(1000, 0),
                        6,
                        oneGSixWays));

        Map oneGTwoWays =
                Collections.singletonMap(
                        new Money(500, 0),
                        new Integer(2));
        suite.addTest(
                new AllocateMoneyTestOverridesRunTest(
                        "testAllocate/Goes Evenly",
                        new Money(1000, 0),
                        2,
                        oneGTwoWays));

        Map oneGOneWay =
                Collections.singletonMap(
                        new Money(1000, 0),
                        new Integer(1));
        suite.addTest(
                new AllocateMoneyTestOverridesRunTest(
                        "testAllocate/One Way",
                        new Money(1000, 0),
                        1,
                        oneGOneWay));

        return suite;
    }

    protected void runTest() throws Throwable {
        List allocatedAmounts = amountToSplit.split(nWays);
        Map actualCuts = organizeIntoBag(allocatedAmounts);
        assertEquals(expectedCuts, actualCuts);
    }

    private Map organizeIntoBag(List allocatedAmounts) {
        Map bagOfCuts = new HashMap();

        for (Iterator i = allocatedAmounts.iterator();
             i.hasNext();
        ) {

            Money eachAmount = (Money) i.next();
            int cutsForAmount =
                    getNumberOfCutsForAmount(bagOfCuts, eachAmount);

            bagOfCuts.put(
                    eachAmount,
                    new Integer(cutsForAmount + 1));
        }
        return bagOfCuts;
    }

    private int getNumberOfCutsForAmount(Map cuts, Money amount) {

        Object cutsForAmountAsObject = cuts.get(amount);
        int cutsForAmount;
        if (cutsForAmountAsObject == null) {
            cutsForAmount = 0;
        } else {
            cutsForAmount =
                    ((Integer) cutsForAmountAsObject).intValue();
        }
        return cutsForAmount;
    }
}
