package junit.cookbook.coffee.data;


public class NullDiscountDefinition extends DiscountDefinition {

    public boolean equals(Object other) {
        return (other instanceof NullDiscountDefinition);
    }

    public int hashCode() {
        return NullDiscountDefinition.class.hashCode();
    }

    public String toString() {
        return "a NullDiscountDefinition";
    }

}
