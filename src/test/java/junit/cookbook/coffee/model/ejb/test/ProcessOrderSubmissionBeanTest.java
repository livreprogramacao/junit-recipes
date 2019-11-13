package junit.cookbook.coffee.model.ejb.test;

import com.diasparsoftware.javax.jms.JBossMapMessageSender;
import com.diasparsoftware.javax.jms.MapMessageSender;
import com.dumbster.smtp.SimpleSmtpServer;
import org.apache.cactus.ServletTestCase;
import org.jboss.jmx.adaptor.rmi.RMIAdaptor;

import javax.management.ObjectName;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import java.util.Collections;
import java.util.Map;

public class ProcessOrderSubmissionBeanTest extends ServletTestCase {
    private MapMessageSender messageSender;
    private SimpleSmtpServer smtpServer;

    protected void setUp() throws Exception {
        messageSender = new JBossMapMessageSender();
        smtpServer = SimpleSmtpServer.start();
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        smtpServer.stop();
    }

    public void testHappyPath() throws Exception {
        Context context = new InitialContext();
        Object object = context.lookup("jmx/invoker/RMIAdaptor");
        RMIAdaptor rmiAdaptor =
                (RMIAdaptor) PortableRemoteObject.narrow(
                        object,
                        RMIAdaptor.class);

        ObjectName mailServiceObjectName =
                new ObjectName("jboss:service=Mail");

        Object configurationAttribute =
                rmiAdaptor.getAttribute(
                        mailServiceObjectName,
                        "Configuration");

        assertNotNull(configurationAttribute);

        fail(configurationAttribute.getClass().getName());


        String orderQueueJndiName = "queue/Orders";

        Map messageContent =
                Collections.singletonMap(
                        "customer-email",
                        "jbr@diasparsoftware.com");

        messageSender.sendMapMessage(
                orderQueueJndiName,
                messageContent);

        Thread.sleep(500);

    }
}
