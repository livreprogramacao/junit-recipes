package junit.cookbook.coffee.model.logic;

import com.diasparsoftware.javax.jms.MapMessageSender;
import com.diasparsoftware.javax.jms.MessagingException;
import junit.cookbook.coffee.model.Order;

import java.util.Collections;

public class SubmitOrderCommand {
    private Order order;

    public void setOrder(Order order) {
        this.order = order;
    }

    public void execute(MapMessageSender mapMessageSender) {
        try {
            mapMessageSender.sendMapMessage(
                    "queue/Orders",
                    Collections.singletonMap(
                            "customer-email",
                            order.customer.emailAddress));
        } catch (MessagingException e) {
            throw new CommandException(
                    "Unable to submit order " + order,
                    e);
        }
    }
}