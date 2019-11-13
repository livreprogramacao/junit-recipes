package junit.cookbook.coffee.model;

import com.diasparsoftware.java.util.Money;

import java.util.*;

public class CoffeeCatalog {
    private Set items = new HashSet();

    public void addCoffee(
            String productId,
            String coffeeName,
            Money unitPrice) {

        items.add(
                new CoffeeCatalogItem(
                        productId,
                        coffeeName,
                        unitPrice));
    }

    public Money getUnitPrice(String coffeeName) {
        for (Iterator i = items.iterator(); i.hasNext(); ) {
            CoffeeCatalogItem each =
                    (CoffeeCatalogItem) i.next();
            if (each.coffeeName.equals(coffeeName))
                return each.unitPrice;
        }

        throw new NoSuchElementException("Coffee name " + coffeeName);
    }

    public String lookupCoffeeById(String productId) {
        for (Iterator i = items.iterator(); i.hasNext(); ) {
            CoffeeCatalogItem each =
                    (CoffeeCatalogItem) i.next();
            if (each.productId.equals(productId))
                return each.coffeeName;
        }

        throw new NoSuchElementException("Product " + productId);
    }

    public String getProductId(String coffeeName) {
        for (Iterator i = items.iterator(); i.hasNext(); ) {
            CoffeeCatalogItem each =
                    (CoffeeCatalogItem) i.next();
            if (each.coffeeName.equals(coffeeName))
                return each.productId;
        }

        throw new NoSuchElementException("Coffee name " + coffeeName);
    }

    public int size() {
        return items.size();
    }

    public boolean equals(Object other) {
        if (other != null && other instanceof CoffeeCatalog) {

            CoffeeCatalog that = (CoffeeCatalog) other;
            return this.items.equals(that.items);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return items.hashCode();
    }

    public String toString() {
        return "a CoffeeCatalog with items " + items;
    }

    public Set getItems() {
        return Collections.unmodifiableSet(items);
    }

}
