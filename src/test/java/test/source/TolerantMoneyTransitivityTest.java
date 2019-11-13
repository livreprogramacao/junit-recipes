import junit.framework.TestCase;

public class TolerantMoneyTransitivityTest extends TestCase {
    public void testRstProperties() throws Exception {
        TolerantMoney a = new TolerantMoney(1.00d);
        TolerantMoney b = new TolerantMoney(1.0033d);
        TolerantMoney c = new TolerantMoney(1.0067d);
        TolerantMoney d = new TolerantMoney(1.01d);

        // Reflexive property OK...
        assertEquals(a, a);
        assertEquals(b, b);
        assertEquals(c, c);
        assertEquals(d, d);

        // Symmetric property OK...
        assertEquals(a, b);
        assertEquals(b, a);

        assertEquals(b, c);
        assertEquals(c, b);

        assertEquals(c, d);
        assertEquals(d, c);

        // Transitive property?
        assertEquals(a, b);
        assertEquals(b, c);
        assertEquals(c, d);
        // therefore...
        assertEquals(a, d);
    }
}
