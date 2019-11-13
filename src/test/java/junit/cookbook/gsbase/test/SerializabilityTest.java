package junit.cookbook.gsbase.test;

import com.gargoylesoftware.base.testing.TestUtil;
import junit.cookbook.util.Money;
import junit.framework.TestCase;

public class SerializabilityTest extends TestCase {
    public void testSerializable() throws Exception {
        Money money = Money.dollars(1000);
        TestUtil.testSerialization(money, true);
    }
}
