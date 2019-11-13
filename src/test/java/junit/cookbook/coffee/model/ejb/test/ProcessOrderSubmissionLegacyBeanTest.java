package junit.cookbook.coffee.model.ejb.test;

import com.sun.jms.MapMessageImpl;
import junit.cookbook.coffee.model.ejb.ProcessOrderSubmissionLegacyBean;
import junit.cookbook.coffee.service.MailService;
import junit.framework.TestCase;
import org.mockejb.*;
import org.mockejb.jndi.MockContextFactory;

import javax.jms.*;
import javax.naming.InitialContext;

public class ProcessOrderSubmissionLegacyBeanTest extends
        TestCase implements MailService {

    private MessageListener processOrderSubmissionBean;
    private boolean invoked;

    protected void setUp() throws Exception {
        invoked = false;

        MockContextFactory.setAsInitial();
        InitialContext rootContext = new InitialContext();
        rootContext.bind("java:comp/env/service/Mail", this);

        MockContainer mockContainer = new MockContainer(
                rootContext);
        MockEjbObject mockEjbObject = mockContainer
                .deployMessageBean(ProcessOrderSubmissionLegacyBean.class);

        processOrderSubmissionBean = mockContainer
                .createMessageBean(mockEjbObject);
    }

    public void testHappyPath() throws Exception {
        final MapMessage message = new MapMessageImpl();
        message.setString("customer-email",
                "jbr@diasparsoftware.com");

        processOrderSubmissionBean.onMessage(message);

        assertTrue("Did not invoke MailService", invoked);
    }

    public void sendMessage(String fromAddress,
                            String toAddress, String subject, String bodyText) {

        invoked = true;
        assertEquals("jbr@diasparsoftware.com", toAddress);
    }
}