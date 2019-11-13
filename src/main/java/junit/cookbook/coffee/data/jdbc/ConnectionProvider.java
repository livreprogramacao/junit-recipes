package junit.cookbook.coffee.data.jdbc;

import java.sql.Connection;


public interface ConnectionProvider {
    Connection getConnection();
}
