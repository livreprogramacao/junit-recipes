package junit.cookbook.coffee.model.logic;

import junit.cookbook.coffee.model.CoffeeQuantity;
import junit.cookbook.coffee.model.ShopcartModel;

import java.util.Collections;

public final class AddToShopcartCommand {
    private CoffeeQuantity coffeeQuantity;
    private ShopcartModel shopcartModel;

    public AddToShopcartCommand(
            CoffeeQuantity coffeeQuantity,
            ShopcartModel shopcartModel) {

        this.coffeeQuantity = coffeeQuantity;
        this.shopcartModel = shopcartModel;
    }

    public CoffeeQuantity getCoffeeQuantity() {
        return coffeeQuantity;
    }

    public void execute() {
        shopcartModel.addCoffeeQuantities(
                Collections.singletonList(coffeeQuantity));
    }

    public boolean equals(Object other) {
        if (other instanceof AddToShopcartCommand) {
            AddToShopcartCommand that = (AddToShopcartCommand) other;
            return this.coffeeQuantity.equals(that.coffeeQuantity)
                    && this.shopcartModel.equals(that.shopcartModel);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        return "an AddToShopcartCommand (add "
                + coffeeQuantity
                + " to "
                + shopcartModel
                + ")";
    }

}
