package junit.cookbook.gettingstarted.test;

import junit.cookbook.util.Money;
import junit.framework.TestCase;

public class MoneyTest extends TestCase {
    public void testAdd() {
        Money addend = new Money(30, 0);
        Money augend = new Money(20, 0);

        Money sum = addend.add(augend);
        assertEquals(5000, sum.inCents());
    }
}
