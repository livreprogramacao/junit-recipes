package junit.cookbook.coffee.model.ejb.test;

import junit.cookbook.coffee.model.ejb.CoffeeCatalogItemHome;
import org.apache.cactus.ServletTestCase;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

public class CoffeeCatalogItemRoundTripTest extends ServletTestCase {
    public void testFindByCoffeeName() throws Exception {
        Context rootContext = new InitialContext();

        Object homeAsObject =
                rootContext.lookup("CoffeeCatalogItem");

        assertNotNull("JNDI returned null!", homeAsObject);

        CoffeeCatalogItemHome home =
                (CoffeeCatalogItemHome) PortableRemoteObject.narrow(
                        homeAsObject,
                        CoffeeCatalogItemHome.class);

        assertNotNull("Narrow returned null!", home);

        CoffeeCatalogItem item = home.findByCoffeeName("Sumatra");
        assertEquals("Sumatra", item.getCoffeeName());
        assertEquals("000", item.getProductId());
        assertEquals(750, item.getUnitPrice());
    }
}
