package junit.cookbook.coffee.data.jdbc;

import com.diasparsoftware.java.sql.PreparedStatementData;
import com.diasparsoftware.jdbc.JdbcQueryExecuter;
import com.diasparsoftware.jdbc.JdbcRowMapper;
import junit.cookbook.coffee.data.DiscountStore;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class DiscountStoreJdbcImpl implements DiscountStore {
    private DiscountStoreJdbcQueryBuilder queryBuilder =
            new DiscountStoreJdbcQueryBuilder();
    private DiscountStoreJdbcMapper mapper =
            new DiscountStoreJdbcMapper();

    private JdbcQueryExecuter queryExecuter;
    private Connection connection;

    public DiscountStoreJdbcImpl(Connection connection) {
        this.connection = connection;
        queryExecuter = new JdbcQueryExecuter(connection);
    }

    public Set findExpiresNoLaterThan(Date expiryDate) {
        PreparedStatementData selectStatementData =
                new PreparedStatementData(
                        "select * from "
                                + "catalog.discount, catalog.discountDefinition "
                                + "where ("
                                + "catalog.discount.discountDefinitionId = catalog.discountDefinition.discountDefinitionId "
                                + "AND toDate <= ?"
                                + ")",
                        Collections.singletonList(
                                new java.sql.Date(expiryDate.getTime())));

        return new HashSet(queryExecuter
                .executeSelectStatement(
                        selectStatementData,
                        new JdbcRowMapper() {

                            public Object makeDomainObject(ResultSet row)
                                    throws SQLException {

                                return mapper.makeDiscount(row);
                            }
                        }));
    }

    public void removeExpiredDiscountAsOf(Date expiryDate) {
        PreparedStatementData removeExpiredDiscountAsOfStatement =
                queryBuilder.getPreparedStatementData(
                        "removeExpiredDiscountAsOf",
                        Collections.singletonList(expiryDate));

        queryExecuter.executeDeleteStatement(
                removeExpiredDiscountAsOfStatement);
    }

    public void suspendAllDiscounts() {
        PreparedStatementData preparedStatementData =
                queryBuilder.getPreparedStatementData(
                        "suspendAllDiscounts",
                        Collections.EMPTY_LIST);

        queryExecuter.executeUpdateStatement(preparedStatementData);
    }

}
