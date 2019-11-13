package junit.cookbook.coffee.deployment.test;

import junit.cookbook.coffee.model.ejb.ShopcartOperationsHome;
import org.apache.cactus.ServletTestCase;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.sql.DataSource;

public class JndiDirectoryContentsTest extends ServletTestCase {
    public void testBusinessDataSource() throws Exception {
        doTestObjectDeployed(
                "business data source",
                "java:/jdbc/mimer/CoffeeShopData",
                DataSource.class);
    }

    public void testShopcartOperationsEjb() throws Exception {
        doTestObjectDeployed(
                "shopcart operations EJB",
                "ejb/ShopcartOperations",
                ShopcartOperationsHome.class);
    }

    public void doTestObjectDeployed(
            String jndiObjectDescription,
            String jndiName,
            Class expectedClass)
            throws Exception {

        Context context = new InitialContext();
        Object jndiObject = context.lookup(jndiName);

        String failureMessage =
                "Unable to find "
                        + jndiObjectDescription
                        + " at "
                        + jndiName;

        assertNotNull(failureMessage, jndiObject);

        Object narrowedObject =
                PortableRemoteObject.narrow(jndiObject, expectedClass);

        assertTrue(expectedClass.isInstance(narrowedObject));
    }
}
