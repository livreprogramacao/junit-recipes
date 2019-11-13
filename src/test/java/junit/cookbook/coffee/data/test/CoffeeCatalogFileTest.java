package junit.cookbook.coffee.data.test;

import com.diasparsoftware.java.util.Money;
import junit.cookbook.coffee.data.CoffeeCatalogFileReader;
import junit.cookbook.coffee.model.CoffeeCatalog;
import junit.framework.TestCase;

public class CoffeeCatalogFileTest extends TestCase {
    public void testReadCatalogFile() throws Exception {
        String catalogText = "762,Sumatra,$7.50\r\n" + "800,Special Blend,$9.50\r\n" + "900,Colombiano,$10.00\r\n";

        CoffeeCatalog expected = new CoffeeCatalog();
        expected.addCoffee("762", "Sumatra", Money.dollars(7,
                50));
        expected.addCoffee("800", "Special Blend", Money
                .dollars(9, 50));
        expected.addCoffee("900", "Colombiano", Money
                .dollars(10, 0));

        CoffeeCatalogFileReader reader = new CoffeeCatalogFileReader();

        assertEquals(expected, reader.load());
    }
}