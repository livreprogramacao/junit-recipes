package junit.cookbook.coffee.model.logic;

import junit.cookbook.coffee.data.OrderRow;
import junit.cookbook.coffee.data.OrderStore;
import junit.cookbook.coffee.model.Order;
import junit.cookbook.coffee.service.MailService;

public class ProcessOrderSubmissionCommand {
    public static final String CUSTOMER_SERVICE_ADDRESS =
            "customer-service@coffeeShop.com";
    public static final String SUBJECT = "We have received your order";

    private Order order;

    public void setOrder(Order order) {
        this.order = order;
    }

    public void execute(
            OrderStore orderStore,
            MailService mailService) {

        orderStore.create(new OrderRow(order.id, order.customer.id));

        mailService.sendMessage(
                CUSTOMER_SERVICE_ADDRESS,
                order.getCustomerEmailAddress(),
                SUBJECT,
                "We will inform you if there is any problem processing your order");
    }
}
