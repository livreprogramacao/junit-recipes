package junit.cookbook.coffee.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

public class ShopcartLogic implements Serializable {
    private Map coffeeQuantities;

    public ShopcartLogic() {
        coffeeQuantities = new HashMap();
    }

    public void addToShopcart(Vector requestedCoffeeQuantities) {
        for (Iterator i = requestedCoffeeQuantities.iterator(); i
                .hasNext(); ) {

            CoffeeQuantity each = (CoffeeQuantity) i.next();

            String eachCoffeeName = each.getCoffeeName();
            CoffeeQuantity currentQuantity;

            if (coffeeQuantities.containsKey(eachCoffeeName)) {

                currentQuantity = (CoffeeQuantity) coffeeQuantities
                        .get(eachCoffeeName);

            } else {
                currentQuantity = new CoffeeQuantity(0,
                        eachCoffeeName);

                coffeeQuantities.put(eachCoffeeName, currentQuantity);
            }

            coffeeQuantities.put(eachCoffeeName, currentQuantity
                    .add(each));
        }
    }

    public Vector getShopcartItems() {
        return new Vector(coffeeQuantities.values());
    }
}