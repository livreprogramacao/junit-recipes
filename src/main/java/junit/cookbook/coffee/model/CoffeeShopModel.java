package junit.cookbook.coffee.model;

import com.diasparsoftware.java.util.Money;


public class CoffeeShopModel {
    private CoffeeCatalog catalog;

    public CoffeeShopModel() {
        this.catalog = new HardcodedCoffeeCatalogFactory().createCatalog();
    }

    public String getProductId(String coffeeName) {
        return catalog.getProductId(coffeeName);
    }

    public Money getUnitPrice(String coffeeName) {
        return catalog.getUnitPrice(coffeeName);
    }

    public CoffeeCatalog getCatalog() {
        return catalog;
    }
}
