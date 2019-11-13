package junit.cookbook.coffee.jdbc.test;

import com.diasparsoftware.java.util.Money;
import junit.cookbook.coffee.data.Product;
import junit.cookbook.coffee.data.jdbc.CatalogStoreJdbcMapper;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AddProductStoredProcedureTest
        extends CoffeeShopDatabaseFixture {

    public void setUp() throws Exception {
        super.setUp();

        Statement statement = getConnection().createStatement();
        registerStatement(statement);

        statement.executeUpdate("delete from catalog.beans");
    }

    public void testWithEmptyTable() throws Exception {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        registerStatement(statement);

        Product specialBlendProduct =
                new Product("762", "Special Blend", Money.dollars(8, 50));

        CallableStatement addProductStatement =
                connection.prepareCall("call catalog.addProduct(?, ?, ?)");
        addProductStatement.setObject(1, specialBlendProduct.productId);
        addProductStatement.setObject(
                2,
                specialBlendProduct.coffeeName);
        addProductStatement.setObject(
                3,
                new Integer(specialBlendProduct.unitPrice.inCents()));
        addProductStatement.executeUpdate();

        ResultSet resultSet =
                statement.executeQuery("select * from catalog.beans");
        registerResultSet(resultSet);

        assertTrue(resultSet.next());
        Product onlyProduct =
                new CatalogStoreJdbcMapper().makeProduct(resultSet);

        assertEquals(specialBlendProduct, onlyProduct);

        assertFalse(resultSet.next());
    }

    protected void tearDown() throws Exception {
        Statement statement = getConnection().createStatement();
        registerStatement(statement);
        statement.executeUpdate("delete from catalog.beans");

        super.tearDown();
    }
}
