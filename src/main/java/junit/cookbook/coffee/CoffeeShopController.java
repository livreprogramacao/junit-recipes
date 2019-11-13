package junit.cookbook.coffee;

import com.diasparsoftware.java.util.CollectionUtil;
import com.diasparsoftware.java.util.Selector;
import com.diasparsoftware.javax.servlet.ServletUtil;
import junit.cookbook.coffee.display.CatalogView;
import junit.cookbook.coffee.model.CoffeeCatalog;
import junit.cookbook.coffee.model.CoffeeQuantity;
import junit.cookbook.coffee.model.CoffeeShopModel;
import junit.cookbook.coffee.model.ShopcartModel;
import junit.cookbook.coffee.model.logic.AddToShopcartCommand;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class CoffeeShopController extends HttpServlet {
    private CoffeeShopModel model;

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

        String forwardUri = "index.html";
        String userName = "jbrains";

        try {
            if (requestActionIs(request, "browseCatalog")) {
                CoffeeCatalog catalog = model.getCatalog();

                CatalogView view = new CatalogView(request);
                view.setCatalog(catalog);

                forwardUri = view.getUri();
            } else if (requestActionIsAddToShopcart(request)) {
                AddToShopcartCommand command = makeAddToShopcartCommand(request);
                executeCommand(command);
            } else {
                log("Couldn't find action in " + ServletUtil
                        .parameterMapToString(request));
            }
        } catch (Exception wrapped) {
            throw new ServletException(wrapped);
        }

        request.getRequestDispatcher(forwardUri)
                .forward(request, response);
    }

    public void executeCommand(AddToShopcartCommand command) {
        command.execute();
    }

    private boolean requestActionIsAddToShopcart(
            HttpServletRequest request) {
        for (Enumeration e = request.getParameterNames(); e
                .hasMoreElements(); ) {

            String eachParameterName = (String) e.nextElement();
            if (eachParameterName.startsWith("addToShopcart")) {
                return true;
            }
        }
        return false;
    }

    private boolean requestActionIs(HttpServletRequest request,
                                    String candidateAction) {

        return request.getParameter(candidateAction) != null;
    }

    public void init() throws ServletException {
        try {
            model = new CoffeeShopModel();
        } catch (Exception e) {
            UnavailableException toThrow = new UnavailableException(
                    "Unable to initialize coffee catalog.");

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

        if (shopcartModel == null) {
            shopcartModel = new ShopcartModel();
        }

        return new AddToShopcartCommand(new CoffeeQuantity(
                amount, coffeeName), shopcartModel);
    }

    public CoffeeCatalog getCatalog() {
        return model.getCatalog();
    }
}