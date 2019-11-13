package junit.cookbook.coffee.model.logic.test;

import junit.cookbook.coffee.data.OrderRow;
import junit.cookbook.coffee.data.OrderStore;
import junit.cookbook.coffee.model.CoffeeQuantity;
import junit.cookbook.coffee.model.Customer;
import junit.cookbook.coffee.model.Order;
import junit.cookbook.coffee.model.logic.ProcessOrderSubmissionCommand;
import junit.cookbook.coffee.service.MailService;
import junit.framework.TestCase;
import org.easymock.MockControl;

import java.util.HashSet;
import java.util.Set;

public class ProcessOrderSubmissionTest extends TestCase {
    private MockControl orderStoreControl;
    private OrderStore orderStore;
    private MockControl mailServiceControl;
    private MailService mailService;

    protected void setUp() throws Exception {
        orderStoreControl = MockControl.createControl(OrderStore.class);
        orderStore = (OrderStore) orderStoreControl.getMock();

        mailServiceControl =
                MockControl.createControl(MailService.class);

        mailService = (MailService) mailServiceControl.getMock();
    }

    public void testStoreOrder() throws Exception {
        Customer jbrains = new Customer("jbrains");
        jbrains.emailAddress = "jbr@diasparsoftware.com";

        Set orderItems = new HashSet();
        orderItems.add(new CoffeeQuantity(5, "Sumatra"));

        Order order = new Order(new Integer(762), jbrains, orderItems);

        ProcessOrderSubmissionCommand command =
                new ProcessOrderSubmissionCommand();
        command.setOrder(order);

        orderStore.create(new OrderRow(order.id, jbrains.id));

        mailService.sendMessage(
                ProcessOrderSubmissionCommand.CUSTOMER_SERVICE_ADDRESS,
                jbrains.emailAddress,
                ProcessOrderSubmissionCommand.SUBJECT,
                "We will inform you if there is any problem processing your order");
        mailServiceControl.setVoidCallable();

        orderStoreControl.replay();
        mailServiceControl.replay();

        command.execute(orderStore, mailService);

        orderStoreControl.verify();
        mailServiceControl.verify();
    }
}
