package junit.cookbook.coffee.data;

public class PercentageOffSubtotalDiscountDefintion
        extends DiscountDefinition {

    public int percentageOffSubtotal;

    public boolean equals(Object other) {
        if (other instanceof PercentageOffSubtotalDiscountDefintion) {
            PercentageOffSubtotalDiscountDefintion that =
                    (PercentageOffSubtotalDiscountDefintion) other;
            return this.percentageOffSubtotal
                    == that.percentageOffSubtotal;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return percentageOffSubtotal;
    }

    public String toString() {
        return "(" + percentageOffSubtotal + "% off)";
    }
}
