package junit.cookbook.coffee.model.ejb.test;

import com.diasparsoftware.util.jboss.testing.EntityBeanMetaDataTest;

public class CoffeeCatalogItemEntityBeanMetaDataTest extends
        EntityBeanMetaDataTest {

    protected void setUp() throws Exception {
        setMetaDataFilename("../CoffeeShopEJB/ejbModule/META-INF/jbosscmp-jdbc.xml");
        setEntityBeanUnderTest("CoffeeCatalogItem");

        super.setUp();
    }

    public void testTableMapping() throws Exception {
        assertBeanMappedToTable("catalog.beans");
    }

    public void testFieldMapping() throws Exception {
        assertFieldMappedToColumn("productId", "productId");
        assertFieldMappedToColumn("coffeeName", "coffeeName");
        assertFieldMappedToColumn("unitPrice", "unitPrice");
    }

    public void testDataSource() throws Exception {
        assertDefaultDataSource("java:/jdbc/mimer/CoffeeShopData");
    }
}