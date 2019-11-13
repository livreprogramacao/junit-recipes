package junit.cookbook.coffee.data;

public final class OrderRow {
    public Integer orderId;
    public String customerId;

    public OrderRow() {
    }

    public OrderRow(Integer orderId, String customerId) {
        this.orderId = orderId;
        this.customerId = customerId;
    }

    public boolean equals(Object other) {
        if (other instanceof OrderRow) {
            OrderRow that = (OrderRow) other;
            return this.orderId.equals(that.orderId)
                    && this.customerId.equals(that.customerId);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return 762;
    }

    public String toString() {
        return "an OrderRow (" + orderId + ") [" + customerId + "]";
    }
}
