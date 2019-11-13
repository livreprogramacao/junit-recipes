package junit.cookbook.coffee.data.jdbc;

import junit.cookbook.coffee.data.OrderStore;
import junit.cookbook.coffee.data.OrderStoreCommand;
import junit.cookbook.coffee.data.OrderStoreCommandExecuter;

import java.sql.Connection;
import java.sql.SQLException;

public class OrderStoreCommandExecuterJdbcImpl
        implements OrderStoreCommandExecuter {

    private OrderStoreCommandExecuter executer;
    private ConnectionProvider connectionProvider;

    public OrderStoreCommandExecuterJdbcImpl(
            OrderStoreCommandExecuter executer,
            ConnectionProvider connectionProvider) {

        this.executer = executer;
        this.connectionProvider = connectionProvider;
    }

    public void execute(
            OrderStore orderStore,
            OrderStoreCommand orderStoreCommand,
            String exceptionMessage) {

        OrderStoreJdbcImpl orderStoreJdbcImpl =
                (OrderStoreJdbcImpl) orderStore;

        Connection connection = connectionProvider.getConnection();
        orderStoreJdbcImpl.open(connection);

        try {
            executer.execute(
                    orderStoreJdbcImpl,
                    orderStoreCommand,
                    exceptionMessage);
        } finally {
            try {
                orderStoreJdbcImpl.close();
                connection.close();
            } catch (SQLException ignored) {
            }
        }
    }
}
