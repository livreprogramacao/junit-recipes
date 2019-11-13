package junit.cookbook.coffee.jdbc.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectCoffeeBeansTest extends CoffeeShopDatabaseFixture {
    protected void setUp() throws Exception {
        super.setUp();

        Connection connection = getConnection();

        // Use the "don't commit" trick to avoid cleaning up data
        connection.setAutoCommit(false);

        PreparedStatement insertStatement =
                connection.prepareStatement(
                        "insert into catalog.beans "
                                + "(productId, coffeeName, unitPrice) values "
                                + "(?, ?, ?)");

        registerStatement(insertStatement);

        insertCoffee(insertStatement, "001", "Sumatra", 750);
        insertCoffee(insertStatement, "002", "Special Blend", 825);
        insertCoffee(insertStatement, "003", "Colombiano", 810);
    }

    protected void tearDown() throws Exception {
        getConnection().rollback();
        super.tearDown();
    }

    public void testFindExpensiveCoffee() throws Exception {
        Connection connection = getConnection();
        PreparedStatement findExpensiveCoffeeStatement =
                connection.prepareStatement(
                        "select * from catalog.beans where unitPrice > 2000");

        registerStatement(findExpensiveCoffeeStatement);

        ResultSet expensiveCoffeeResults =
                findExpensiveCoffeeStatement.executeQuery();
        registerResultSet(expensiveCoffeeResults);

        assertFalse(expensiveCoffeeResults.next());
    }

    public void testFindAllCoffee() throws Exception {
        Connection connection = getConnection();
        PreparedStatement findAllCoffeeStatement =
                connection.prepareStatement("select * from catalog.beans");

        registerStatement(findAllCoffeeStatement);

        ResultSet allCoffeeResults =
                findAllCoffeeStatement.executeQuery();
        registerResultSet(allCoffeeResults);

        int rowCount = 0;
        while (allCoffeeResults.next())
            rowCount++;

        assertEquals(3, rowCount);

    }

    private void insertCoffee(
            PreparedStatement insertStatement,
            String productId,
            String coffeeName,
            int unitPrice)
            throws SQLException {

        insertStatement.clearParameters();
        insertStatement.setObject(1, productId);
        insertStatement.setObject(2, coffeeName);
        insertStatement.setObject(3, new Integer(unitPrice));
        insertStatement.executeUpdate();
    }
}
