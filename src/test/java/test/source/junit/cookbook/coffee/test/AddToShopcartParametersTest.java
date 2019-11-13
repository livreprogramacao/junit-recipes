package junit.cookbook.coffee.test;

import com.diasparsoftware.java.util.CollectionUtil;
import com.diasparsoftware.java.util.MapEntryClosure;
import com.diasparsoftware.java.util.Money;
import com.diasparsoftware.javax.servlet.http.FakeHttpSession;
import com.diasparsoftware.javax.servlet.http.RequestDispatcherAdapter;
import junit.cookbook.coffee.CoffeeShopController;
import junit.cookbook.coffee.model.CoffeeQuantity;
import junit.cookbook.coffee.model.CoffeeShopModel;
import junit.cookbook.coffee.model.ShopcartModel;
import junit.cookbook.coffee.model.logic.AddToShopcartCommand;
import junit.framework.TestCase;
import org.apache.catalina.connector.HttpRequestBase;
import org.easymock.MockControl;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AddToShopcartParametersTest extends TestCase {
    protected boolean makeAddToShopcartCommandInvoked;
    protected boolean executeCommandInvoked;

    protected void setUp() throws Exception {
        makeAddToShopcartCommandInvoked = false;
        executeCommandInvoked = false;
    }

    public void testMakeCommandValidRequest() {
        CoffeeShopController controller = new CoffeeShopController();

        CoffeeShopModel coffeeShopModel = new CoffeeShopModel();
        coffeeShopModel.getCatalog().addCoffee(
                "0",
                "Sumatra",
                Money.dollars(7, 50));

        controller.setModel(coffeeShopModel);

        Map parameters = new HashMap();
        parameters.put("quantity-0", new String[]{"2"});
        parameters.put("addToShopcart-0", new String[]{"Buy!"});

        ShopcartModel shopcartModel = new ShopcartModel();
        Map sessionAttributes =
                Collections.singletonMap("shopcartModel", shopcartModel);

        AddToShopcartCommand actualCommand =
                controller.makeAddToShopcartCommand(
                        parameters,
                        sessionAttributes);

        AddToShopcartCommand expectedCommand =
                new AddToShopcartCommand(
                        new CoffeeQuantity(2, "Sumatra"),
                        shopcartModel);

        assertEquals(expectedCommand, actualCommand);
    }

    public void testServletInvokesMakeAddToShopcartCommand()
            throws Exception {

        final Map expectedRequestParameters = new HashMap() {
            {
                put("quantity-0", new String[]{"2"});
                put("addToShopcart-0", new String[]{"Buy!"});
            }
        };

        final ShopcartModel shopcartModel = new ShopcartModel();
        final Map expectedSessionAttributes =
                Collections.singletonMap("shopcartModel", shopcartModel);

        CoffeeShopModel coffeeShopModel = new CoffeeShopModel();
        coffeeShopModel.getCatalog().addCoffee(
                "0",
                "Sumatra",
                Money.dollars(7, 50));

        CoffeeShopController controller = new CoffeeShopController() {
            public AddToShopcartCommand makeAddToShopcartCommand(
                    Map parameters,
                    Map sessionAttributes) {

                makeAddToShopcartCommandInvoked = true;
                assertEquals(expectedRequestParameters, parameters);
                assertEquals(
                        expectedSessionAttributes,
                        sessionAttributes);

                return null;
            }

            public void executeCommand(AddToShopcartCommand command) {
                /* Intentionally do nothing */
            }
        };

        controller.setModel(coffeeShopModel);

        MockControl httpServletResponseControl =
                MockControl.createNiceControl(HttpServletResponse.class);

        HttpServletResponse httpServletResponse =
                (HttpServletResponse) httpServletResponseControl.getMock();

        final HttpRequestBase httpServletRequest =
                new HttpRequestBase() {

                    public HttpSession getSession(boolean create) {
                        return new FakeHttpSession(expectedSessionAttributes);
                    }

                    public RequestDispatcher getRequestDispatcher(String path) {
                        return new RequestDispatcherAdapter();
                    }
                };

        httpServletRequest.clearParameters();

        CollectionUtil
                .forEachDo(
                        expectedRequestParameters,
                        new MapEntryClosure() {
                            public void eachMapEntry(Object key, Object value) {
                                httpServletRequest.addParameter(
                                        (String) key,
                                        (String[]) value);
                            }
                        });

        controller.doPost(httpServletRequest, httpServletResponse);
        assertTrue(
                "Did not invoke makeAddToShopcartCommand()",
                makeAddToShopcartCommandInvoked);
    }

    public void testServletInvokesExecuteCommand() throws Exception {
        final AddToShopcartCommand expectedCommand =
                new AddToShopcartCommand(
                        new CoffeeQuantity(200, "Special Blend"),
                        new ShopcartModel());

        CoffeeShopController controller = new CoffeeShopController() {
            public AddToShopcartCommand makeAddToShopcartCommand(HttpServletRequest request) {
                return expectedCommand;
            }

            public void executeCommand(AddToShopcartCommand command) {
                executeCommandInvoked = true;
                assertEquals(expectedCommand, command);
            }
        };

        final HttpRequestBase httpServletRequest =
                new HttpRequestBase() {

                    public HttpSession getSession(boolean create) {
                        return new FakeHttpSession(Collections.EMPTY_MAP);
                    }

                    public RequestDispatcher getRequestDispatcher(String path) {
                        return new RequestDispatcherAdapter();
                    }
                };

        httpServletRequest.clearParameters();

        Map requestParameters = new HashMap() {
            {
                put("quantity-0", new String[]{"2"});
                put("addToShopcart-0", new String[]{"Buy!"});
            }
        };

        CollectionUtil
                .forEachDo(requestParameters, new MapEntryClosure() {

                    public void eachMapEntry(Object key, Object value) {
                        httpServletRequest.addParameter(
                                (String) key,
                                (String[]) value);
                    }
                });

        MockControl httpServletResponseControl =
                MockControl.createNiceControl(HttpServletResponse.class);

        HttpServletResponse httpServletResponse =
                (HttpServletResponse) httpServletResponseControl.getMock();

        controller.doPost(httpServletRequest, httpServletResponse);

        assertTrue(
                "Did not invoke executeCommand()",
                executeCommandInvoked);
    }
}
