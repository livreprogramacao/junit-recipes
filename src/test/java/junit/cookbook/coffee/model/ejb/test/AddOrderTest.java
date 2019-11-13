package junit.cookbook.coffee.model.ejb.test;

import com.mimer.jdbc.MimerDataSource;
import junit.cookbook.coffee.model.ejb.OrderHome;
import junit.cookbook.coffee.model.ejb.OrderRemote;
import junit.framework.Test;
import org.apache.cactus.ServletTestSuite;
import org.dbunit.DatabaseTestCase;
import org.dbunit.database.*;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

import javax.ejb.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.sql.DataSource;
import java.io.StringReader;
import java.rmi.RemoteException;

public class AddOrderTest extends DatabaseTestCase {
    private MimerDataSource dataSource;
    private OrderHome home;

    public AddOrderTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new ServletTestSuite(AddOrderTest.class);
    }

    protected IDatabaseConnection getConnection() throws Exception {
        return new DatabaseConnection(getDataSource().getConnection());
    }

    private DataSource getDataSource() {
        if (dataSource == null) {
            dataSource = new MimerDataSource();
            dataSource.setDatabaseName("coffeeShopData");
            dataSource.setUser("admin");
            dataSource.setPassword("adm1n");
        }

        return dataSource;
    }

    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSet(
                new StringReader(
                        "<?xml version=\"1.0\" ?>"
                                + "<dataset>"
                                + "<people.customer customerId=\"jbrains\" name=\"J. B. Rainsberger\" />"
                                + "<people.customer customerId=\"sstirling\" name=\"Scott Stirling\" />"
                                + "<orders.orders />"
                                + "</dataset>"));
    }

    protected void setUp() throws Exception {
        System.setProperty("dbunit.qualified.table.names", "true");

        Context rootContext = new InitialContext();
        Object homeAsObject = rootContext.lookup("ejb/Order");

        home =
                (OrderHome) PortableRemoteObject.narrow(
                        homeAsObject,
                        OrderHome.class);

        super.setUp();
    }

    public void testCreateEmptyOrder() throws Exception {
        home.create(new Integer(1), "jbrains");
        assertOrderExists(1, "jbrains");
    }

    public void testCreateTwoEmptyOrders() throws Exception {
        home.create(new Integer(1), "jbrains");
        assertOrderExists(1, "jbrains");

        home.create(new Integer(2), "jbrains");
        assertOrderExists(2, "jbrains");
    }

    public void testRemoveOrder() throws Exception {
        OrderRemote order =
                home.create(new Integer(1), "jbrains");
        order.remove();

        try {
            home.findByPrimaryKey(new Integer(1));
            fail("Found order?");
        } catch (ObjectNotFoundException expected) {
        }
    }

    public void testChangeOrder() throws Exception {
        OrderRemote order =
                home.create(new Integer(1), "jbrains");
        assertOrderExists(1, "jbrains");

        order.setCustomerId("sstirling");
        assertOrderExists(1, "sstirling");
    }

    private void assertOrderExists(int orderId, String customerId)
            throws FinderException, RemoteException {

        OrderRemote newOrder =
                home.findByPrimaryKey(new Integer(orderId));

        assertEquals(customerId, newOrder.getCustomerId());
    }
}
