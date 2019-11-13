package junit.cookbook.coffee.model.logic.test;

import com.diasparsoftware.javax.jms.MapMessageSender;
import com.diasparsoftware.javax.jms.MessagingException;
import junit.cookbook.coffee.model.CoffeeQuantity;
import junit.cookbook.coffee.model.Customer;
import junit.cookbook.coffee.model.Order;
import junit.cookbook.coffee.model.logic.CommandException;
import junit.cookbook.coffee.model.logic.SubmitOrderCommand;
import junit.framework.TestCase;
import org.easymock.MockControl;

import javax.jms.JMSException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class SubmitOrderTest extends TestCase {
    private MockControl mapMessageSenderControl;
    private MapMessageSender mapMessageSender;
    private Customer jbrains;
    private Order order;

    protected void setUp() throws Exception {
        mapMessageSenderControl =
                MockControl.createControl(MapMessageSender.class);

        mapMessageSender =
                (MapMessageSender) mapMessageSenderControl.getMock();

        jbrains = new Customer("jbrains");
        jbrains.emailAddress = "jbr@diasparsoftware.com";

        Set orderItems =
                Collections.singleton(
                        new CoffeeQuantity(3, "Special Blend"));

        order = new Order(new Integer(762), jbrains, orderItems);

    }

    public void testHappyPath() throws Exception {
        Map expectedMessageContent =
                Collections.singletonMap(
                        "customer-email",
                        jbrains.emailAddress);

        mapMessageSender.sendMapMessage(
                "queue/Orders",
                expectedMessageContent);
        mapMessageSenderControl.setVoidCallable();

        mapMessageSenderControl.replay();

        SubmitOrderCommand command = new SubmitOrderCommand();
        command.setOrder(order);
        command.execute(mapMessageSender);

        mapMessageSenderControl.verify();
    }

    public void testQueueDoesNotExist() throws Exception {
        Map expectedMessageContent =
                Collections.singletonMap(
                        "customer-email",
                        jbrains.emailAddress);

        mapMessageSender.sendMapMessage(
                "queue/Orders",
                expectedMessageContent);

        MessagingException destinationNotExistException =
                new MessagingException(
                        "Unable to send message",
                        new JMSException("Destination does not exist"));

        mapMessageSenderControl.setThrowable(
                destinationNotExistException);

        mapMessageSenderControl.replay();

        try {
            SubmitOrderCommand command = new SubmitOrderCommand();
            command.setOrder(order);
            command.execute(mapMessageSender);
            fail("Did not throw exception?");
        } catch (CommandException expected) {
            assertEquals(
                    "Unable to submit order " + order,
                    expected.getMessage());

            assertSame(
                    destinationNotExistException,
                    expected.getCause());
        }

        mapMessageSenderControl.verify();
    }
}
