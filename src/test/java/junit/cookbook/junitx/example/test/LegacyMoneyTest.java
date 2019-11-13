package junit.cookbook.junitx.example.test;

import junit.cookbook.junitx.example.LegacyMoney;
import junitx.framework.PrivateTestCase;
import junitx.framework.TestAccessException;

public class LegacyMoneyTest extends PrivateTestCase {
    public LegacyMoneyTest(String name) {
        super(name);
    }

    public void testAddZero() throws TestAccessException {
        LegacyMoney money = LegacyMoney.dollars(25);
        money.add(0);
        assertEquals(25, getInt(money, "dollars"));
    }

    public void testClear() throws TestAccessException {
        LegacyMoney money = LegacyMoney.dollars(30);
        invoke(money, "reset", NOARGS);
        assertEquals(0, getInt(money, "dollars"));
    }
}
