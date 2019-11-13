package junit.cookbook.coffee;

import java.util.HashMap;
import java.util.Map;

public class NavigationEngine {
    private Map globalRules = new HashMap();
    private Map rules = new HashMap();

    {
        globalRules.put("Browse Catalog", "Catalog");
    }

    {
        rules.put("Add to Shopcart/OK", "Shopcart");
    }

    public String getNextLocation(String action, String result) {

        if (globalRules.containsKey(action)) {
            return (String) globalRules.get(action);
        } else {
            return (String) rules.get(action + "/" + result);
        }
    }
}
