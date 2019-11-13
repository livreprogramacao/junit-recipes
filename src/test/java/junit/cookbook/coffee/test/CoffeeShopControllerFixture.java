package junit.cookbook.coffee.test;

import com.meterware.httpunit.*;

public abstract class CoffeeShopControllerFixture
        extends CoffeeShopFixture {

    protected WebRequest makeAddCoffeeRequest(
            String coffeeProductId,
            int expectedQuantity) {

        WebRequest addToShopcartRequest =
                new PostMethodWebRequest("http://localhost/coffeeShop/coffee");
        addToShopcartRequest.setParameter(
                "quantity-" + coffeeProductId,
                String.valueOf(expectedQuantity));
        addToShopcartRequest.setParameter(
                "addToShopcart-" + coffeeProductId,
                "Buy Now!");
        return addToShopcartRequest;
    }
}
