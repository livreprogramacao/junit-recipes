package junit.cookbook.coffee;

import com.diasparsoftware.java.util.CollectionUtil;
import com.diasparsoftware.java.util.Selector;
import junit.cookbook.coffee.display.CatalogView;
import junit.cookbook.coffee.model.CoffeeCatalog;
import junit.cookbook.coffee.model.CoffeeQuantity;
import junit.cookbook.coffee.model.CoffeeShopModel;
import junit.cookbook.coffee.model.ShopcartModel;
import junit.cookbook.coffee.model.logic.AddToShopcartCommand;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

public class CoffeeShopControllerDecoupledNavigator extends
        HttpServlet {
    private CoffeeShopModel model;
    private HttpServletRequestToActionMapper actionMapper;
    private NavigationEngine navigationEngine;
    private LocationToUriMapper locationMapper;
    private Collection knownActions;

    // TODO Move to Diasparsoft Toolkit; HttpUtil.
    public static Map getSessionAttributes(
            HttpServletRequest request) {
        Map attributes = new HashMap();

        HttpSession httpSession = request.getSession(true);
        Enumeration enumeration = httpSession
                .getAttributeNames();
        while (enumeration.hasMoreElements()) {
            String eachAttributeName = (String) enumeration
                    .nextElement();

            Object eachAttributeValue = httpSession
                    .getAttribute(eachAttributeName);

            attributes.put(eachAttributeName,
                    eachAttributeValue);
        }

        return attributes;
    }

    public void destroy() {
    }

    public void doPost(HttpServletRequest req,
                       HttpServletResponse resp) throws ServletException,
            IOException {

        handleRequest(req, resp);
    }

    private void handleRequest(HttpServletRequest request,
                               HttpServletResponse response) throws IOException,
            ServletException {

        String userName = "jbrains";
        String nextLocationName = "Welcome";

        try {
            String actionName = actionMapper
                    .getActionName(request);
            log("Performing action: " + actionName);

            if (knownActions.contains(actionName) == false) {
                log("I don't understand action " + actionName);
            } else {
                String actionResult = "OK";
                if ("Browse Catalog".equals(actionName)) {
                    CoffeeCatalog catalog = model.getCatalog();

                    CatalogView view = new CatalogView(request);
                    view.setCatalog(catalog);
                } else if ("Add to Shopcart".equals(actionName)) {
                    AddToShopcartCommand command = makeAddToShopcartCommand(request);
                    executeCommand(command);
                }

                nextLocationName = navigationEngine
                        .getNextLocation(actionName, "OK");
            }

        } catch (Exception wrapped) {
            throw new ServletException(wrapped);
        }

        String forwardUri = locationMapper
                .getUri(nextLocationName);
        request.getRequestDispatcher(forwardUri)
                .forward(request, response);
    }

    public void executeCommand(AddToShopcartCommand command) {
        command.execute();
    }

    public void init() throws ServletException {
        try {
            model = new CoffeeShopModel();
            actionMapper = new HttpServletRequestToActionMapper();
            navigationEngine = new NavigationEngine();
            locationMapper = new LocationToUriMapper();
            knownActions = Arrays.asList(new String[]{
                    "Browse Catalog", "Add to Shopcart"});
        } catch (Exception e) {
            UnavailableException toThrow = new UnavailableException(
                    "Unable to initialize");

            toThrow.initCause(e);
            throw toThrow;
        }
    }

    public AddToShopcartCommand makeAddToShopcartCommand(
            HttpServletRequest request) {
        Map parameters = request.getParameterMap();
        Map sessionAttributes = getSessionAttributes(request);

        return makeAddToShopcartCommand(parameters,
                sessionAttributes);
    }

    public void setModel(CoffeeShopModel model) {
        this.model = model;
    }

    public AddToShopcartCommand makeAddToShopcartCommand(
            Map parameters, Map sessionAttributes) {

        Selector quantityParameterSelector = new Selector() {
            public boolean accept(Object object) {
                String eachKey = (String) object;
                return eachKey.startsWith("quantity-");
            }
        };

        String quantityParameter = (String) CollectionUtil
                .select(parameters.keySet(),
                        quantityParameterSelector);

        String amountParameter = ((String[]) parameters
                .get(quantityParameter))[0];
        int amount = Integer.parseInt(amountParameter);

        String productId = quantityParameter
                .substring(1 + quantityParameter.indexOf("-"));
        String coffeeName = model.getCatalog()
                .lookupCoffeeById(productId);

        ShopcartModel shopcartModel = (ShopcartModel) sessionAttributes
                .get("shopcartModel");

        return new AddToShopcartCommand(new CoffeeQuantity(
                amount, coffeeName), shopcartModel);
    }
}