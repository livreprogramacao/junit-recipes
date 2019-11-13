package junit.cookbook.coffee.test;

import junit.framework.TestCase;

import java.io.File;

public abstract class CoffeeShopFixture extends TestCase {
    public static final String webApplicationRoot =
            "../CoffeeShopWeb/Web Content";

    public static String getWebContentPath(String relativePath) {
        return new File(webApplicationRoot, relativePath).getAbsolutePath();
    }

    public static String getWebDeploymentDescriptorPath() {
        return ("../CoffeeShopWeb/Web Content/WEB-INF/web.xml");
    }

    protected String getCoffeeShopUrlString(String uri) throws Exception {
        return "http://localhost/coffeeShop" + uri;
    }
}
