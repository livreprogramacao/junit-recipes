package junit.cookbook.coffee.jdbc.test;

import com.diasparsoftware.java.util.CollectionUtil;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CoffeeShopDatabaseSchemaTest
        extends CoffeeShopDatabaseFixture {

    protected void tearDown() throws Exception {
        Statement statement = getConnection().createStatement();
        registerStatement(statement);

        statement.executeUpdate("delete from catalog.beans");

        super.tearDown();
    }

    public void testTablesAndColumnsExist() throws Exception {
        DatabaseMetaData databaseMetaData =
                getConnection().getMetaData();
        ResultSet schemasResultSet = databaseMetaData.getSchemas();
        registerResultSet(schemasResultSet);

        List schemaNames = new LinkedList();
        while (schemasResultSet.next()) {
            schemaNames.add(schemasResultSet.getString("TABLE_SCHEM"));
        }

        assertTrue(
                CollectionUtil.stringCollectionContainsIgnoreCase(
                        schemaNames,
                        "catalog"));

        schemasResultSet.close();
    }

    public void testCoffeeNameUniquenessConstraint() throws Exception {
        Statement statement = getConnection().createStatement();
        registerStatement(statement);

        statement.executeUpdate("delete from catalog.beans");

        PreparedStatement createBeanProductStatement =
                getConnection().prepareStatement(
                        "insert into catalog.beans "
                                + "(productId, coffeeName, unitPrice) "
                                + "values (?, ?, ?)");

        registerStatement(createBeanProductStatement);

        createBeanProductStatement.clearParameters();
        createBeanProductStatement.setString(1, "000");
        createBeanProductStatement.setString(2, "Sumatra");
        createBeanProductStatement.setInt(3, 725);

        assertEquals(1, createBeanProductStatement.executeUpdate());

        try {
            createBeanProductStatement.executeUpdate();
            fail("Added two coffee products with the same name?!");
        } catch (SQLException expected) {
            assertEquals(String.valueOf(23000), expected.getSQLState());
        }
    }

}
