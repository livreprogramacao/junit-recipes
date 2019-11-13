package junit.cookbook.coffee;

import com.diasparsoftware.java.util.CollectionUtil;
import org.apache.commons.collections.Predicate;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.regex.Pattern;

public class HttpServletRequestToActionMapper {
    private static final Pattern ADD_TO_SHOPCART_PARAMETER_NAME_REGEX =
            Pattern.compile("addToShopcart-\\d+");

    public String getActionName(HttpServletRequest request) {
        String actionName = null;

        Map parameterMap = request.getParameterMap();
        if (parameterMap.containsKey("browseCatalog")) {
            actionName = "Browse Catalog";
        } else if (
                containsKeyMatchingRegex(
                        parameterMap,
                        ADD_TO_SHOPCART_PARAMETER_NAME_REGEX)) {
            actionName = "Add to Shopcart";
        }

        if (actionName == null)
            throw new IllegalStateException(
                    "No action maps to request: " + request);

        return actionName;
    }

    private boolean containsKeyMatchingRegex(
            Map parameterMap,
            final Pattern regex) {

        return CollectionUtil
                .detect(parameterMap.entrySet(), new Predicate() {

                    public boolean evaluate(Object input) {
                        Map.Entry mapEntry = (Map.Entry) input;
                        String key = (String) mapEntry.getKey();
                        return regex.matcher(key).matches();
                    }
                });
    }
}
