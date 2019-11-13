package junit.cookbook.coffee.data.jdbc;

import com.diasparsoftware.java.sql.PreparedStatementData;
import com.diasparsoftware.store.*;
import com.diasparsoftware.util.jboss.testing.*;
import junit.cookbook.coffee.data.CatalogStore;
import junit.cookbook.coffee.data.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CatalogStoreJdbcImpl implements CatalogStore {
    private Connection connection;

    private JdbcQueryExecuter queryExecuter;

    private CatalogStoreJdbcQueryBuilder builder =
            new CatalogStoreJdbcQueryBuilder();

    private CatalogStoreJdbcMapper mapper =
            new CatalogStoreJdbcMapper();

    public CatalogStoreJdbcImpl(Connection connection) {
        this.connection = connection;
        queryExecuter = new JdbcQueryExecuter(connection);
    }

    public Set findBeansByName(String name) {
        PreparedStatementData findByNamePreparedStatementData =
                builder.getPreparedStatementData(
                        "findByName",
                        Collections.singletonList(name));

        return new HashSet(
                queryExecuter
                        .executeSelectStatement(
                                findByNamePreparedStatementData,
                                new JdbcRowMapper() {

                                    public Object makeDomainObject(ResultSet row)
                                            throws SQLException {

                                        return mapper.makeProduct(row);
                                    }
                                }));
    }

    public void addProduct(Product toAdd) {
        PreparedStatement insertStatement = null;

        try {
            insertStatement =
                    connection.prepareStatement(
                            "insert into catalog.beans "
                                    + "(productId, coffeeName, unitPrice) values "
                                    + "(?, ?, ?)");

            insertStatement.clearParameters();
            insertStatement.setString(1, toAdd.productId);
            insertStatement.setString(2, toAdd.coffeeName);
            insertStatement.setInt(3, toAdd.unitPrice.inCents());
            if (insertStatement.executeUpdate() != 1)
                throw new DataMakesNoSenseException("Inserted more than 1 row into catalog.beans!");
        } catch (SQLException e) {
            throw new DataStoreException(e);
        } finally {
            try {
                if (insertStatement != null)
                    insertStatement.close();
            } catch (SQLException ignored) {
            }
        }
    }

    public Set findAllProducts() {
        PreparedStatementData findAllProductsStatementData =
                builder.getPreparedStatementData(
                        "findAllProducts",
                        Collections.EMPTY_LIST);

        return new HashSet(
                queryExecuter
                        .executeSelectStatement(
                                findAllProductsStatementData,
                                new JdbcRowMapper() {

                                    public Object makeDomainObject(ResultSet row)
                                            throws SQLException {

                                        return mapper.makeProduct(row);
                                    }
                                }));
    }
}