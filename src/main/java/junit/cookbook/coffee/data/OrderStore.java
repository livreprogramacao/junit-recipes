package junit.cookbook.coffee.data;

public interface OrderStore {
    boolean exists(Integer orderId);

    void create(OrderRow orderRow);

    OrderRow findByOrderId(Integer orderId);

    void remove(Integer orderId);

    void update(final OrderRow orderRow);
}
