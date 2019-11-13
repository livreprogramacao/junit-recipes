package junit.cookbook.coffee.jdbc.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class StoredProcedurePrivilegesTest
        extends CoffeeShopDatabaseFixture {

    protected void setUp() throws Exception {
        super.setUp();

        Statement statement = getConnection().createStatement();
        registerStatement(statement);

        try {
            statement.executeUpdate("drop procedure admin.not_allowed");
        } catch (SQLException doesNotExist) {
            if ("42000".equals(doesNotExist.getSQLState()) == false) {
                throw doesNotExist;
            }
        }

        statement.executeUpdate(
                "create procedure admin.not_allowed() begin end");
        statement.executeUpdate(
                "grant execute on procedure admin.not_allowed to programmer");
        // User 'programmer' cannot execute NOT_ALLOWED
    }

    protected void tearDown() throws Exception {
        Statement statement = getConnection().createStatement();
        registerStatement(statement);
        statement.executeUpdate("drop procedure admin.not_allowed");
        super.tearDown();
    }

    public void testCanCall() throws Exception {
        Connection connection =
                getDataSource().getConnection("programmer", "pr0grammer");
        Statement statement = connection.createStatement();
        registerStatement(statement);

        try {
            statement.execute("call admin.not_allowed()");
        } catch (SQLException e) {
            if (isNoPrivilegesException(e))
                fail(
                        "User 'programmer' cannot call procedure "
                                + "admin.not_allowed");
            else
                throw e;
        } finally {
            connection.close();
        }
    }

    private boolean isNoPrivilegesException(SQLException e) {
        return (-12743 == e.getErrorCode())
                && ("42000".equals(e.getSQLState()));
    }
}
