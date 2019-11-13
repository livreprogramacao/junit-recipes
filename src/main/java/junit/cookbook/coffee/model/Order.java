package junit.cookbook.coffee.model;

import java.util.Set;


public class Order {
    public Customer customer;
    public Integer id;

    public Order(Integer id, Customer customer, Set orderItems) {
        this.id = id;
        this.customer = customer;
    }

    public String getCustomerEmailAddress() {
        return customer.emailAddress;
    }
}
