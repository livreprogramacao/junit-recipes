package junit.cookbook.coffee.test;

import com.diasparsoftware.java.util.Money;
import com.meterware.httpunit.*;
import com.meterware.servletunit.*;
import junit.cookbook.coffee.CoffeeShopController;
import junit.cookbook.coffee.model.ShopcartModel;
import junit.framework.TestCase;

import javax.servlet.http.*;

public class AddToShopcartControllerTest extends TestCase {
    private static final String webApplicationRoot = "../CoffeeShopWeb/Web Content";

    private static WebRequest makeAddCoffeeRequest(
            String coffeeProductId, int expectedQuantity) {

        WebRequest addToShopcartRequest = new PostMethodWebRequest(
                "http://localhost/coffeeShop/coffee");

        addToShopcartRequest
                .setParameter("quantity-" + coffeeProductId, String
                        .valueOf(expectedQuantity));

        addToShopcartRequest
                .setParameter("addToShopcart-" + coffeeProductId,
                        "Buy Now!");

        return addToShopcartRequest;
    }

    public void testAddToEmptyShopcart() throws Exception {
        ServletRunner servletRunner = new ServletRunner(
                webApplicationRoot + "/WEB-INF/web.xml",
                "/coffeeShop");

        String coffeeName = "Sumatra";
        String coffeeProductId = "1";
        int expectedQuantity = 5;

        CoffeeShopController coffeeShopController = new CoffeeShopController();
        coffeeShopController.init();

        coffeeShopController.getCatalog()
                .addCoffee(coffeeProductId, coffeeName,
                        Money.dollars(7, 50));

        WebRequest addToShopcartRequest = makeAddCoffeeRequest(
                coffeeProductId,
                expectedQuantity);

        ServletUnitClient client = servletRunner.newClient();

        InvocationContext invocationContext = client
                .newInvocation(addToShopcartRequest);

        coffeeShopController.service(invocationContext
                .getRequest(), invocationContext.getResponse());

        ShopcartModel shopcartModel = checkShopcartModel(invocationContext
                .getRequest());

        assertEquals(expectedQuantity, shopcartModel
                .getQuantity(coffeeName));
    }

    public ShopcartModel checkShopcartModel(
            HttpServletRequest request) {

        HttpSession session = request.getSession();
        assertNotNull(session);

        ShopcartModel shopcartModel = (ShopcartModel) session
                .getAttribute("shopcartModel");
        assertNotNull(shopcartModel);

        return shopcartModel;
    }
}