package junit.cookbook.jdbc.live.test;

import com.diasparsoftware.jdbc.JdbcResourceRegistry;
import junit.cookbook.coffee.data.CatalogStore;
import junit.cookbook.coffee.data.jdbc.CatalogStoreJdbcImpl;
import junit.cookbook.coffee.jdbc.test.CoffeeShopDatabaseFixture;
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
    private DataSource dataSource;
    private JdbcResourceRegistry jdbcResourceRegistry;

    public FindProductsTest(String name) {
        super(name);
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

    private DataSource getDataSource() {
        if (dataSource == null)
            dataSource = CoffeeShopDatabaseFixture.makeDataSource();
        return dataSource;
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

    public void testFindAll() throws Exception {
        Connection connection = makeJdbcConnection();
        CatalogStore store = new CatalogStoreJdbcImpl(connection);
        Set allProducts = store.findAllProducts();
        assertEquals(3, allProducts.size());
    }
}
