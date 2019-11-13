package junit.cookbook.coffee.data.jdbc;

import com.diasparsoftware.java.sql.PreparedStatementData;
import com.diasparsoftware.jdbc.JdbcQueryExecuter;
import com.diasparsoftware.jdbc.JdbcRowMapper;
import junit.cookbook.coffee.data.OrderRow;
import junit.cookbook.coffee.data.OrderStore;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderStoreJdbcImpl implements OrderStore {
    private JdbcQueryExecuter executer;

    public void open(Connection connection) {
        this.setExecuter(new JdbcQueryExecuter(connection));
    }

    public void close() {
        this.executer = null;
    }

    private JdbcQueryExecuter getExecuter() {
        if (executer == null) {
            throw new IllegalStateException(
                    "Please provide me with a database connection first.");
        }
        return executer;
    }

    private void setExecuter(JdbcQueryExecuter executer) {
        this.executer = executer;
    }

    public boolean exists(Integer orderId) {
        PreparedStatementData countOrdersByIdStatementData = new PreparedStatementData(
                "select count(orderId) from orders.orders " + "where orderId = ?",
                Collections.singletonList(orderId));

        int rowCount = getExecuter().executeCountStatement(
                countOrdersByIdStatementData);

        return (rowCount > 0);
    }

    public void create(final OrderRow orderRow) {

        List parameters = new ArrayList() {
            {
                add(orderRow.orderId);
                add(orderRow.customerId);
            }
        };

        PreparedStatementData insertStatementData = new PreparedStatementData(
                "insert into orders.orders (orderId, customerId) " + "values (?, ?)",
                parameters);

        getExecuter().executeInsertStatement(
                insertStatementData);
    }

    public OrderRow findByOrderId(final Integer orderId) {
        PreparedStatementData selectStatementData = new PreparedStatementData(
                "select * from orders.orders where orderId = ?",
                Collections.singletonList(orderId));

        List orders = getExecuter().executeSelectStatement(
                selectStatementData, new OrderRowMapper());

        return (OrderRow) orders.get(0);
    }

    public void remove(Integer orderId) {
        PreparedStatementData deleteStatementData = new PreparedStatementData(
                "delete from orders.orders where orderId = ?",
                Collections.singletonList(orderId));

        getExecuter().executeDeleteStatement(
                deleteStatementData);
    }

    public void update(final OrderRow orderRow) {
        List parameters = new ArrayList() {
            {
                add(orderRow.customerId);
                add(orderRow.orderId);
            }
        };

        PreparedStatementData updateStatementData = new PreparedStatementData(
                "update orders.orders set customerId = ? " + "where orderId = ?",
                parameters);

        getExecuter().executeUpdateStatement(
                updateStatementData);
    }

    public static class OrderRowMapper extends JdbcRowMapper {
        public Object makeDomainObject(ResultSet row)
                throws SQLException {

            Integer orderId = (Integer) row
                    .getObject("orderId");
            String customerId = row.getString("customerId");

            return new OrderRow(orderId, customerId);
        }
    }
}