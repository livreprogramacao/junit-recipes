package junit.cookbook.coffee.data;

import java.util.Date;

public class Discount {
    public Date fromDate;
    public Date toDate;
    public DiscountDefinition discountDefinition;

    public Discount() {
        this(new Date(0), new Date(0), new NullDiscountDefinition());
    }

    public Discount(
            Date fromDate,
            Date toDate,
            DiscountDefinition discountDefinition) {

        this.fromDate = fromDate;
        this.toDate = toDate;
        this.discountDefinition = discountDefinition;
    }

    public boolean equals(Object other) {
        if (other instanceof Discount) {
            Discount that = (Discount) other;
            return this.fromDate.equals(that.fromDate)
                    && this.toDate.equals(that.toDate)
                    && this.discountDefinition.equals(that.discountDefinition);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        return "a Discount from "
                + fromDate
                + " to "
                + toDate
                + " for "
                + discountDefinition;
    }
}
