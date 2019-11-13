package junit.cookbook.coffee.model.jms.test;

import com.sun.jms.MapMessageImpl;
import junit.cookbook.coffee.model.jms.ProcessOrderSubmissionMessageListener;
import junit.cookbook.coffee.model.logic.ProcessOrderSubmissionAction;
import junit.cookbook.coffee.service.MailService;
import junit.framework.TestCase;
import org.mockejb.jndi.MockContextFactory;

import javax.jms.MapMessage;
import javax.naming.InitialContext;

public class ProcessOrderSubmissionMessageListenerTest extends
        TestCase implements MailService {

    private boolean invoked;

    protected void setUp() throws Exception {
        invoked = false;

        MockContextFactory.setAsInitial();
        new InitialContext().bind("java:comp/env/service/Mail",
                this);
    }

    public void testHappyPath() throws Exception {
        ProcessOrderSubmissionAction spyAction = new ProcessOrderSubmissionAction() {
            public void processOrder(MailService mailService,
                                     String customerEmailAddress) {

                invoked = true;
                assertEquals("jbr@diasparsoftware.com",
                        customerEmailAddress);
            }
        };

        ProcessOrderSubmissionMessageListener consumer = new ProcessOrderSubmissionMessageListener(
                spyAction);

        MapMessage message = new MapMessageImpl();
        message
                .setString(
                        ProcessOrderSubmissionMessageListener.CUSTOMER_EMAIL_PARAMETER_NAME,
                        "jbr@diasparsoftware.com");

        consumer.onMessage(message);
        assertTrue("Did not invoke the processing action.",
                invoked);
    }

    public void sendMessage(String fromAddress,
                            String toAddress, String subject, String bodyText) {

        fail("No-one should ever invoke me.");
    }
}