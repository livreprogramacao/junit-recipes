package junit.cookbook.coffee.test;

import junit.cookbook.coffee.NavigationEngine;
import junit.framework.TestCase;

public class NavigationEngineTest extends TestCase {
    private NavigationEngine navigationEngine;

    protected void setUp() throws Exception {
        navigationEngine = new NavigationEngine();
    }

    public void testBrowseCatalog() {
        assertEquals(
                "Catalog Page",
                navigationEngine.getNextLocation("Browse Catalog", "OK"));
    }

    public void testAddToShopcart() {
        assertEquals(
                "Shopcart Page",
                navigationEngine.getNextLocation("Add to Shopcart", "OK"));
    }
}
