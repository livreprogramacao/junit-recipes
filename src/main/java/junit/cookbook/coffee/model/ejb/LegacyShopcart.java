package junit.cookbook.coffee.model.ejb;

import javax.ejb.EJBLocalObject;
import java.util.Set;


public interface LegacyShopcart extends EJBLocalObject {
    Set getItems();

    boolean containsCoffeeNamed(String eachCoffeeName);

    int getQuantity(String eachCoffeeName);

    void setQuantity(String eachCoffeeName, int i);
}
