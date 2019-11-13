package junit.cookbook.common.test;

import junit.cookbook.util.Money;
import junit.framework.TestCase;

public class ConstructorTest extends TestCase {
    public void testInitializationParameters() {
        assertEquals(762, new Integer(762).intValue());
    }

    public void testDefaultInitializationParameters_ignored() {
        assertEquals(0L, new Money().inCents());
    }

    public void testValueInCents() {
        assertTrue(new Money(0, 50).valueInCentsIs(50));
    }

    public void testObjectIsValid() {
        assertTrue(new Money(150, 0).isValid());
    }
}
