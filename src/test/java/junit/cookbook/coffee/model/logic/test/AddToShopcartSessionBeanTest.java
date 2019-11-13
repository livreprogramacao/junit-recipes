package junit.cookbook.coffee.model.logic.test;

import junit.cookbook.coffee.model.CoffeeQuantity;
import junit.cookbook.coffee.model.ejb.ShopcartOperationsBean;
import junit.framework.TestCase;

import java.util.HashSet;
import java.util.Vector;

public class AddToShopcartSessionBeanTest extends TestCase {
    public void testComplexCase() throws Exception {
        ShopcartOperationsBean shopcart = new ShopcartOperationsBean();
        shopcart.ejbCreate();

        Vector coffeeQuantities1 = new Vector();
        coffeeQuantities1.add(new CoffeeQuantity(2, "A"));
        coffeeQuantities1.add(new CoffeeQuantity(3, "B"));

        shopcart.addToShopcart(coffeeQuantities1);
        assertEquals(
                coffeeQuantities1,
                shopcart.getShopcartItems());

        Vector coffeeQuantities2 = new Vector();
        coffeeQuantities2.add(new CoffeeQuantity(1, "A"));
        coffeeQuantities2.add(new CoffeeQuantity(2, "C"));

        shopcart.addToShopcart(coffeeQuantities2);

        Vector expectedTotalQuantities = new Vector();
        expectedTotalQuantities.add(
                new CoffeeQuantity(3, "A"));
        expectedTotalQuantities.add(
                new CoffeeQuantity(3, "B"));
        expectedTotalQuantities.add(
                new CoffeeQuantity(2, "C"));

        assertEquals(
                new HashSet(expectedTotalQuantities),
                new HashSet(shopcart.getShopcartItems()));
    }
}
