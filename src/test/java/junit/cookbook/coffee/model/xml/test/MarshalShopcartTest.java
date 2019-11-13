package junit.cookbook.coffee.model.xml.test;

import com.diasparsoftware.java.util.Money;
import junit.cookbook.coffee.display.*;
import junit.cookbook.coffee.model.CoffeeCatalog;
import junit.cookbook.coffee.model.CoffeeQuantity;
import junit.cookbook.coffee.model.ShopcartModel;
import org.custommonkey.xmlunit.*;

import java.util.Arrays;

public class MarshalShopcartTest extends XMLTestCase {
    private CoffeeCatalog catalog;

    protected void setUp() throws Exception {
        XMLUnit.setIgnoreWhitespace(true);
        catalog = new CoffeeCatalog() {
            public String getProductId(String coffeeName) {
                return "001";
            }

            public Money getUnitPrice(String coffeeName) {
                return Money.ZERO;
            }
        };
    }

    public void testEmpty() throws Exception {
        String expectedXml =
                "<?xml version='1.0' ?>\n"
                        + "<shopcart>\n"
                        + "<subtotal>$0.00</subtotal>\n"
                        + "</shopcart>\n";

        ShopcartBean shopcart = new ShopcartBean();
        assertXMLEqual(expectedXml, shopcart.asXml());
    }

    public void testOneItem() throws Exception {
        String expectedXml =
                "<?xml version='1.0' ?>\n"
                        + "<shopcart>\n"
                        + "<item id=\"762\">"
                        + "<name>Sumatra</name>"
                        + "<quantity>2</quantity>"
                        + "<unit-price>$7.50</unit-price>"
                        + "<total-price>$15.00</total-price>"
                        + "</item>\n"
                        + "<subtotal>$15.00</subtotal>\n"
                        + "</shopcart>\n";

        ShopcartBean shopcart = new ShopcartBean();
        shopcart.shopcartItems.add(
                new ShopcartItemBean(
                        "Sumatra",
                        "762",
                        2,
                        Money.dollars(7, 50)));

        assertXMLEqual(expectedXml, shopcart.asXml());
    }

    public void testOneItemIgnoreCatalogDetails() throws Exception {
        String expectedXml =
                "<?xml version='1.0' ?>\n"
                        + "<shopcart>\n"
                        + "<item id=\"762\">"
                        + "<name>Sumatra</name>"
                        + "<quantity>2</quantity>"
                        + "<unit-price>$7.50</unit-price>"
                        + "<total-price>$15.00</total-price>"
                        + "</item>\n"
                        + "<subtotal>$15.00</subtotal>\n"
                        + "</shopcart>\n";

        ShopcartModel shopcart = new ShopcartModel();
        shopcart.addCoffeeQuantities(
                Arrays.asList(
                        new Object[]{new CoffeeQuantity(2, "Sumatra")}));

        String shopcartAsXml =
                ShopcartBean.create(shopcart, catalog).asXml();

        Diff diff = new Diff(expectedXml, shopcartAsXml);

        diff.overrideDifferenceListener(
                new IgnoreCatalogDetailsDifferenceListener());

        assertTrue(diff.toString(), diff.similar());
    }
}
