package junit.cookbook.coffee.jdbc.test;

import com.diasparsoftware.java.util.Money;
import com.mockobjects.sql.*;
import junit.cookbook.coffee.data.Product;
import junit.cookbook.coffee.data.jdbc.CatalogStoreJdbcImpl;
import junit.framework.TestCase;

import java.io.FileInputStream;
import java.util.Properties;

public class SqlStringTest extends TestCase {
    private Properties expectedSqlStrings;

    protected void setUp() throws Exception {
        expectedSqlStrings = new Properties();
        expectedSqlStrings.load(
                new FileInputStream("test/data/expectedSqlStrings.properties"));
    }

    public void testAddProductSqlString() throws Exception {
        String expectedSqlString =
                expectedSqlStrings.getProperty("catalog.addProduct");

        MockConnection2 connection = new MockConnection2();
        MockPreparedStatement expectedStatement =
                new MockPreparedStatement();
        expectedStatement.addUpdateCount(1);

        connection.setupAddPreparedStatement(
                expectedSqlString,
                expectedStatement);

        CatalogStoreJdbcImpl store =
                new CatalogStoreJdbcImpl(connection);

        Product product =
                new Product(
                        "762",
                        "Expensive New Coffee",
                        Money.dollars(10, 50));

        store.addProduct(product);

        expectedStatement.verify();
    }
}
