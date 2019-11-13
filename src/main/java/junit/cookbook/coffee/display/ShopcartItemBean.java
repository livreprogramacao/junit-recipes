package junit.cookbook.coffee.display;

import com.diasparsoftware.java.util.Money;

public class ShopcartItemBean implements Comparable {
    public String coffeeName = "";
    public String productId = "";
    public int quantityInKilograms;
    public Money unitPrice = Money.ZERO;

    public ShopcartItemBean(
            String coffeeName,
            String productId,
            int quantityInKilograms,
            Money unitPrice) {

        this.coffeeName = coffeeName;
        this.productId = productId;
        this.quantityInKilograms = quantityInKilograms;
        this.unitPrice = unitPrice;
    }

    public String getCoffeeName() {
        return coffeeName;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantityInKilograms() {
        return quantityInKilograms;
    }

    public Money getUnitPrice() {
        return unitPrice;
    }

    public Money getTotalPrice() {
        return unitPrice.multipliedBy(quantityInKilograms);
    }

    public int compareTo(Object other) {
        ShopcartItemBean that = (ShopcartItemBean) other;
        return this.coffeeName.compareTo(that.coffeeName);
    }

    public boolean equals(Object other) {
        if (other instanceof ShopcartItemBean) {
            ShopcartItemBean that = (ShopcartItemBean) other;
            return this.productId.equals(that.productId)
                    && this.coffeeName.equals(that.coffeeName)
                    && this.quantityInKilograms == that.quantityInKilograms
                    && this.unitPrice.equals(that.unitPrice);
        } else {
            return false;
        }
    }

    public String toString() {
        return "a ShopcartItemBean ("
                + productId
                + ") "
                + quantityInKilograms
                + " kg of "
                + coffeeName
                + " @ "
                + unitPrice;
    }
}