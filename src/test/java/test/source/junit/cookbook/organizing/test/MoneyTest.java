package junit.cookbook.organizing.test;

import junit.cookbook.util.Money;
import junit.framework.TestCase;

public class MoneyTest extends TestCase {
    private Money money;

    public static void main(String[] args) {
        junit.swingui.TestRunner.run(MoneyTest.class);
    }

    protected void setUp() throws Exception {
        money = new Money(12, 50);
    }

    public void testAdd() {
        Money augend = new Money(12, 50);
        Money sum = money.add(augend);
        assertEquals(2500, sum.inCents());
    }

    public void testNegate() {
        Money opposite = money.negate();
        assertEquals(-1250, opposite.inCents());
    }

    public void testRound() {
        Money rounded = money.roundToNearestDollar();
        assertEquals(1300, rounded.inCents());
    }
}
