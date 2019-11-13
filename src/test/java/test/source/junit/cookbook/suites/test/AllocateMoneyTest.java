package junit.cookbook.suites.test;

import com.diasparsoftware.java.util.Money;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.*;

public class AllocateMoneyTest extends TestCase {
    private Money amountToSplit;
    private int nWays;
    private Map expectedCuts;
    private Map actualCuts;

    public AllocateMoneyTest(Money amountToSplit, int nWays,
                             Map expectedCuts) {

        super("testAllocate");

        this.amountToSplit = amountToSplit;
        this.nWays = nWays;
        this.expectedCuts = expectedCuts;
    }

    public static Test suite() throws Exception {
        TestSuite suite = new TestSuite();

        Map oneGSixWays = new HashMap();
        oneGSixWays.put(new Money(166, 66), new Integer(2));
        oneGSixWays.put(new Money(166, 67), new Integer(4));
        suite.addTest(new AllocateMoneyTest(new Money(1000, 0),
                6, oneGSixWays));

        Map oneGTwoWays = Collections.singletonMap(new Money(
                500, 0), new Integer(2));
        suite.addTest(new AllocateMoneyTest(new Money(1000, 0),
                2, oneGTwoWays));

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