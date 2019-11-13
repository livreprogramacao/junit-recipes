package junit.cookbook.coffee.model;

import com.diasparsoftware.java.util.Quantity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ShopcartModel implements Serializable {
    private Map coffeeQuantities = new HashMap();

    public void addCoffeeQuantities(List requestedQuantities) {
        for (Iterator i = requestedQuantities.iterator(); i
                .hasNext(); ) {

            CoffeeQuantity each = (CoffeeQuantity) i.next();

            String coffeeName = each.getCoffeeName();
            CoffeeQuantity currentQuantity = getCoffeeQuantity(coffeeName);

            Quantity sum = each.add(currentQuantity);
            coffeeQuantities.put(coffeeName, sum);
        }
    }

    private CoffeeQuantity getCoffeeQuantity(String coffeeName) {
        CoffeeQuantity currentQuantity = (CoffeeQuantity) coffeeQuantities
                .get(coffeeName);

        return (currentQuantity == null) ? new CoffeeQuantity(
                0, coffeeName) : currentQuantity;
    }

    public int getQuantity(String coffeeName) {
        return getCoffeeQuantity(coffeeName)
                .getAmountInKilograms();
    }

    public int getTotalQuantity() {
        int totalQuantity = 0;
        for (Iterator i = coffeeQuantities.values().iterator(); i
                .hasNext(); ) {
            CoffeeQuantity each = (CoffeeQuantity) i.next();
            totalQuantity += each.getAmountInKilograms();
        }
        return totalQuantity;
    }

    public Iterator items() {
        return coffeeQuantities.values().iterator();
    }

    public boolean isEmpty() {
        return coffeeQuantities.isEmpty();
    }

    public boolean equals(Object other) {
        if (other != null && other instanceof ShopcartModel) {

            ShopcartModel that = (ShopcartModel) other;
            return this.coffeeQuantities
                    .equals(that.coffeeQuantities);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return coffeeQuantities.hashCode();
    }

    public String toString() {
        return "a ShopcartModel with " + coffeeQuantities;
    }
}