package junit.cookbook.coffee.jdbc.test;

import com.diasparsoftware.java.util.Money;
import com.mockobjects.sql.*;
import junit.cookbook.coffee.data.CatalogStore;
import junit.cookbook.coffee.data.Product;
import junit.cookbook.coffee.data.jdbc.CatalogStoreJdbcImpl;
import junit.framework.Assert;
import junit.framework.TestCase;

import java.sql.CallableStatement;
import java.sql.SQLException;

public class AddProductTest extends TestCase {
    public void testHappyPathWithPreparedStatement() {
        Product toAdd =
                new Product("999", "Colombiano", Money.dollars(9, 0));

        final MockPreparedStatement addProductStatement =
                new MockPreparedStatement();
        addProductStatement.addUpdateCount(1);
        addProductStatement.setExpectedCloseCalls(1);

        MockConnection2 connection = new MockConnection2();
        connection.setupAddPreparedStatement(
                "insert into catalog.beans "
                        + "(productId, coffeeName, unitPrice) values "
                        + "(?, ?, ?)",
                addProductStatement);

        CatalogStore store = new CatalogStoreJdbcImpl(connection);
        store.addProduct(toAdd);

        addProductStatement.verify();
        connection.verify();
    }

    public void testHappyPathWithStoredProcedure() {
        Product toAdd =
                new Product("999", "Colombiano", Money.dollars(9, 0));

        final MockCallableStatement addProductStatement =
                new MockCallableStatement();
        addProductStatement.setExpectedClearParametersCalls(1);
        addProductStatement.setExpectedCloseCalls(1);
        addProductStatement.addExpectedSetParameters(
                new Object[]{"999", "Colombiano", new Integer(900)});
        addProductStatement.addUpdateCount(1);

        MockConnection2 connection = new MockConnection2() {
            public CallableStatement prepareCall(String sql)
                    throws SQLException {

                Assert.assertEquals("call addProduct(?, ?, ?)", sql);
                return addProductStatement;
            }
        };

        CatalogStore store =
                new CatalogStoreStoredProcedureImpl(connection);
        store.addProduct(toAdd);

        addProductStatement.verify();
        connection.verify();
    }
}
