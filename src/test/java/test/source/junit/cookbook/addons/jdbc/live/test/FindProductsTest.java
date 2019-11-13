package junit.cookbook.addons.jdbc.live.test;

import com.diasparsoftware.jdbc.JdbcResourceRegistry;
import junit.cookbook.coffee.data.CatalogStore;
import junit.cookbook.coffee.data.jdbc.CatalogStoreJdbcImpl;
import junit.framework.Test;
import junit.framework.TestSuite;
import junitx.util.ResourceManager;
import org.dbunit.DatabaseTestCase;
import org.dbunit.database.*;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

public class FindProductsTest extends DatabaseTestCase {
    private JdbcResourceRegistry jdbcResourceRegistry;

    public FindProductsTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new ResourceManagerTestSetup(
                new TestSuite(FindProductsTest.class));
    }

    protected void setUp() throws Exception {
        System.setProperty("dbunit.qualified.table.names", "true");
        jdbcResourceRegistry = new JdbcResourceRegistry();
        super.setUp();
    }

    protected void tearDown() throws Exception {
        jdbcResourceRegistry.cleanUp();
        super.tearDown();
    }

    public void testFindAll() throws Exception {
        Connection connection = makeJdbcConnection();
        CatalogStore store = new CatalogStoreJdbcImpl(connection);
        Set allProducts = store.findAllProducts();
        assertEquals(3, allProducts.size());
    }

    public void testFindByName() throws Exception {
        Connection connection = makeJdbcConnection();
        CatalogStore store = new CatalogStoreJdbcImpl(connection);
        Set allProducts = store.findBeansByName("Sumatra");
        assertEquals(1, allProducts.size());
    }

    private DataSource getDataSource() {
        return (DataSource) ResourceManager.getResource("dataSource");
    }

    private Connection makeJdbcConnection() throws SQLException {
        Connection connection = getDataSource().getConnection();
        jdbcResourceRegistry.registerConnection(connection);
        return connection;
    }

    protected IDatabaseConnection getConnection() throws Exception {
        Connection connection = makeJdbcConnection();
        return new DatabaseConnection(connection);
    }

    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSet(
                new File("test/data/datasets/findProductsTest.xml"));
    }
}
