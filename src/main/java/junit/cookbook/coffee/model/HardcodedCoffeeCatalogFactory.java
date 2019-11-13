package junit.cookbook.coffee.model;

import com.diasparsoftware.java.util.Money;

public class HardcodedCoffeeCatalogFactory {
    public CoffeeCatalog createCatalog() {
        CoffeeCatalog catalog = new CoffeeCatalog();
        catalog.addCoffee("762", "Sumatra", Money.dollars(7, 50));
        catalog.addCoffee(
                "801",
                "Special Blend",
                Money.dollars(10, 25));
        catalog.addCoffee("009", "Colombiano", Money.dollars(8, 72));
        return catalog;
    }
}
