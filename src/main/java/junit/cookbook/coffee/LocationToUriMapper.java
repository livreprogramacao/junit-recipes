package junit.cookbook.coffee;

import java.util.HashMap;
import java.util.Map;

public class LocationToUriMapper {
    private Map mapping = new HashMap();

    {
        mapping.put("Catalog", "/catalog.jsp");
        mapping.put("Welcome", "/index.html");
        mapping.put("Shopcart", "/shopcart.jsp");
    }

    public String getUri(String nextLocationName) {
        if (mapping.containsKey(nextLocationName) == false)
            throw new NoSuchMappingException(nextLocationName);

        return (String) mapping.get(nextLocationName);
    }
}
