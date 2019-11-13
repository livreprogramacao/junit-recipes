package junit.cookbook.coffee.data;


public abstract class DiscountDefinition {
    // Probably an applyTo class or something
    public abstract boolean equals(Object other);

    public abstract int hashCode();

    public abstract String toString();
}
