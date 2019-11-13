package junit.cookbook.coffee.model.ejb;

import junit.cookbook.coffee.data.OrderRow;
import junit.cookbook.coffee.data.OrderStore;
import junit.cookbook.coffee.data.OrderStoreCommand;
import junit.cookbook.coffee.data.OrderStoreCommandExecuter;

import javax.ejb.*;

public class OrderBmpBeanLogic {
    private OrderStore orderStore;
    private OrderStoreCommandExecuter executer;
    private OrderRow orderRow;

    public OrderBmpBeanLogic(
            OrderStore orderStore,
            OrderStoreCommandExecuter executer) {

        this.orderStore = orderStore;
        this.executer = executer;
    }

    public Integer ejbFindByPrimaryKey(final Integer orderId)
            throws ObjectNotFoundException {

        OrderStoreCommand orderStoreLogic = new OrderStoreCommand() {
            private boolean orderExists;

            public void execute(OrderStore orderStore) {
                orderExists = orderStore.exists(orderId);
            }

            public Object getReturnValue() {
                return new Boolean(orderExists);
            }
        };

        String failureMessage =
                "Unable to find order with ID <" + orderId + ">";

        executer.execute(orderStore, orderStoreLogic, failureMessage);

        boolean orderExists =
                ((Boolean) orderStoreLogic.getReturnValue())
                        .booleanValue();

        if (orderExists)
            return orderId;
        else
            throw new ObjectNotFoundException(failureMessage);
    }

    public Integer ejbCreate(Integer orderId, String customerId) {
        orderRow = new OrderRow(orderId, customerId);

        OrderStoreCommand command = new OrderStoreCommand() {
            public void execute(OrderStore orderStore) {
                orderStore.create(orderRow);
            }
        };

        String failureMessage =
                "Unable to create order with ID <"
                        + orderRow.orderId
                        + ">";

        executer.execute(orderStore, command, failureMessage);

        return orderId;
    }

    public void ejbLoad(final Integer orderId) {
        OrderStoreCommand command = new OrderStoreCommand() {
            private OrderRow orderRow;

            public void execute(OrderStore orderStore) {
                orderRow = orderStore.findByOrderId(orderId);
            }

            public Object getReturnValue() {
                return orderRow;
            }
        };

        String failureMessage =
                "Unable to load order with ID <" + orderId + ">";

        executer.execute(orderStore, command, failureMessage);

        orderRow = (OrderRow) command.getReturnValue();
    }

    public void ejbStore() {
        OrderStoreCommand command = new OrderStoreCommand() {
            public void execute(OrderStore orderStore) {
                orderStore.update(orderRow);
            }
        };

        String failureMessage =
                "Unable to store order with ID <"
                        + orderRow.orderId
                        + ">";

        executer.execute(orderStore, command, failureMessage);
    }

    public void ejbRemove(final Integer orderId) {
        OrderStoreCommand command = new OrderStoreCommand() {
            public void execute(OrderStore orderStore) {
                orderStore.remove(orderId);
            }
        };

        String failureMessage =
                "Unable to remove order with ID <" + orderId + ">";

        executer.execute(orderStore, command, failureMessage);
    }

    public String getCustomerId() {
        return orderRow.customerId;
    }

    public void setCustomerId(String customerId) {
        orderRow.customerId = customerId;
    }

    public OrderRow getOrderRow() {
        return orderRow;
    }
}
