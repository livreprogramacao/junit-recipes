package junit.cookbook.coffee.logic.test;

import com.diasparsoftware.java.util.Money;
import junit.cookbook.coffee.model.CoffeeCatalog;
import junit.cookbook.coffee.model.CoffeeQuantity;
import junit.cookbook.coffee.model.ShopcartModel;
import junit.framework.TestCase;

import java.util.Collections;
import java.util.List;

public class AddToShopcartModelTest extends TestCase {
    public void testAddToEmptyShopcart() {
        String coffeeProductId = "0";
        String coffeeName = "Sumatra";
        int requestedQuantity = 5;

        CoffeeCatalog catalog = new CoffeeCatalog();
        catalog.addCoffee(coffeeProductId, coffeeName, Money.dollars(7, 50));

        ShopcartModel model = new ShopcartModel();

        List requestedQuantities =
                Collections.singletonList(
                        new CoffeeQuantity(
                                requestedQuantity,
                                catalog.lookupCoffeeById(coffeeProductId)));

        model.addCoffeeQuantities(requestedQuantities);

        assertEquals(5, model.getQuantity("Sumatra"));
        assertEquals(5, model.getTotalQuantity());
    }

    public void testAddMoreOfOneCoffee() {
        String coffeeProductId = "0";
        String coffeeName = "Sumatra";
        int requestedQuantity = 5;

        CoffeeCatalog catalog = new CoffeeCatalog();
        catalog.addCoffee(coffeeProductId, coffeeName, Money.dollars(7, 50));

        ShopcartModel model = new ShopcartModel();

        List requestedQuantities =
                Collections.singletonList(
                        new CoffeeQuantity(
                                requestedQuantity,
                                catalog.lookupCoffeeById(coffeeProductId)));

        model.addCoffeeQuantities(requestedQuantities);
        model.addCoffeeQuantities(requestedQuantities);

        assertEquals(10, model.getQuantity("Sumatra"));
        assertEquals(10, model.getTotalQuantity());
    }
}
