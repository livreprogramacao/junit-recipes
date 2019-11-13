package junit.cookbook.common.test;

import com.gargoylesoftware.base.testing.EqualsTester;
import junit.cookbook.util.Money;
import junit.framework.TestCase;

public class MoneyTest extends TestCase {
    public void testEquals() {
        Money a = new Money(100, 0);
        Money b = new Money(100, 0);
        Money c = new Money(50, 0);
        Money d = new Money(100, 0) {
            // Trivial subclass
        };

        new EqualsTester(a, b, c, d);
    }
}
