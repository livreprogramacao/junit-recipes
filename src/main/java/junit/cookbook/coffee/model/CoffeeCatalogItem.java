package junit.cookbook.coffee.model;

import com.diasparsoftware.java.util.Money;

public class CoffeeCatalogItem {
    public String productId = "";
    public String coffeeName = "";
    public Money unitPrice = Money.ZERO;

    public CoffeeCatalogItem(
            String productId,
            String coffeeName,
            Money unitPrice) {

        this.productId = productId;
        this.coffeeName = coffeeName;
        this.unitPrice = unitPrice;
    }

    public boolean equals(Object other) {
        if (other instanceof CoffeeCatalogItem) {
            CoffeeCatalogItem that = (CoffeeCatalogItem) other;

            return this.productId.equals(that.productId)
                    && this.coffeeName.equals(that.coffeeName)
                    && this.unitPrice.equals(that.unitPrice);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return coffeeName.hashCode();
    }

    public String toString() {
        return "a CoffeeCatalogItem ("
                + productId
                + ") "
                + coffeeName
                + " @ "
                + unitPrice
                + "/kg";
    }
}
