package junit.cookbook.common.test;

import junit.cookbook.util.Money;
import junit.framework.TestCase;

public class MoneyEqualsTest extends TestCase {
    private Money a;
    private Money b;
    private Money c;

    protected void setUp() {
        a = new Money(100, 0);
        b = new Money(100, 0);
        c = new Money(200, 0);
    }

    public void testReflexive() {
        assertEquals(a, a);
        assertEquals(b, b);
        assertEquals(c, c);
    }

    public void testSymmetric() {
        assertEquals(a, b);
        assertEquals("Symmetric property failed", b, a);
        assertFalse(a.equals(c));
        assertFalse(c.equals(a));
    }

    public void testConsistent() {
        for (int i = 0; i < 1000; i++) {
            assertEquals(a, b);
            assertFalse(a.equals(c));
        }
    }

    public void testNotEqualToNull() {
        assertFalse(a.equals(null));
        assertFalse(c.equals(null));
    }
}
