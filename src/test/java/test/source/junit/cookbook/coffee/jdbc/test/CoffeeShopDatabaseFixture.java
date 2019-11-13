package junit.cookbook.coffee.jdbc.test;

import com.diasparsoftware.jdbc.JdbcResourceRegistry;
import com.mimer.jdbc.MimerDataSource;
import junit.framework.TestCase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public abstract class CoffeeShopDatabaseFixture extends TestCase {
    private Connection connection;
    private MimerDataSource dataSource;
    private JdbcResourceRegistry jdbcResourceRegistry;

    public static MimerDataSource makeDataSource() {
        MimerDataSource dataSource = new MimerDataSource();
        dataSource.setDatabaseName("coffeeShopData");
        dataSource.setUser("admin");
        dataSource.setPassword("adm1n");
        return dataSource;
    }

    protected void setUp() throws Exception {
        dataSource = makeDataSource();

        jdbcResourceRegistry = new JdbcResourceRegistry();

        connection = dataSource.getConnection();
        getJdbcResourceRegistry().registerConnection(connection);
    }

    protected void tearDown() throws Exception {
        getJdbcResourceRegistry().cleanUp();
    }

    public MimerDataSource getDataSource() {
        return dataSource;
    }

    protected Connection getConnection() {
        return connection;
    }

    protected JdbcResourceRegistry getJdbcResourceRegistry() {
        return jdbcResourceRegistry;
    }

    protected void registerConnection(Connection connection) {
        jdbcResourceRegistry.registerConnection(connection);
    }

    protected void registerStatement(Statement statement) {
        jdbcResourceRegistry.registerStatement(statement);
    }

    protected void registerResultSet(ResultSet resultSet) {
        jdbcResourceRegistry.registerResultSet(resultSet);
    }
}
