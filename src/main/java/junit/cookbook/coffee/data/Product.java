package junit.cookbook.coffee.data;

import com.diasparsoftware.java.util.Money;

public class Product {
    public String productId = "";
    public String coffeeName = "";
    public Money unitPrice = Money.ZERO;

    public Product() {
    }

    public Product(
            String productId,
            String coffeeName,
            Money unitPrice) {

        this.productId = productId;
        this.coffeeName = coffeeName;
        this.unitPrice = unitPrice;
    }

    public boolean equals(Object other) {
        if (other instanceof Product) {
            Product that = (Product) other;
            return this.productId.equals(that.productId)
                    && this.coffeeName.equals(that.coffeeName)
                    && this.unitPrice.equals(that.unitPrice);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return productId.hashCode();
    }

    public String toString() {
        return "a Product ("
                + productId
                + ") "
                + coffeeName
                + " @ "
                + unitPrice
                + "/kg";
    }
}
