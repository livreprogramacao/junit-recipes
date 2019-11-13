package junit.cookbook.addons.jdbc.live.test;

import junit.cookbook.coffee.jdbc.test.CoffeeShopDatabaseFixture;
import junit.extensions.TestSetup;
import junit.framework.Test;
import junitx.util.ResourceManager;

public class ResourceManagerTestSetup extends TestSetup {
    public ResourceManagerTestSetup(Test test) {
        super(test);
    }

    protected void setUp() throws Exception {
        ResourceManager.addResource(
                "dataSource",
                CoffeeShopDatabaseFixture.makeDataSource());
    }

    protected void tearDown() throws Exception {
        ResourceManager.removeResource("dataSource");
    }
}
