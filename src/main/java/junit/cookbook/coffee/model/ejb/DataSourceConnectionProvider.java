package junit.cookbook.coffee.model.ejb;

import junit.cookbook.coffee.data.jdbc.ConnectionProvider;

import javax.ejb.EJBException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceConnectionProvider
        implements ConnectionProvider {

    private DataSource dataSource;

    public DataSourceConnectionProvider(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // TODO  Is it reasonable to throw an EJBException from here?
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException wrapped) {
            throw new EJBException(
                    "Unable to connect to data source",
                    wrapped);
        }

    }
}