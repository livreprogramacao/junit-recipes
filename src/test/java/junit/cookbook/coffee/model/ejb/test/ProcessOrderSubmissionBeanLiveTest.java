package junit.cookbook.coffee.model.ejb.test;

import com.diasparsoftware.javax.jms.JBossMapMessageSender;
import com.diasparsoftware.javax.jms.MapMessageSender;
import com.dumbster.smtp.SimpleSmtpServer;
import org.apache.cactus.ServletTestCase;
import org.apache.xerces.parsers.DOMParser;
import org.jboss.jmx.adaptor.rmi.RMIAdaptor;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.management.Attribute;
import javax.management.ObjectName;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.StringReader;
import java.util.Collections;
import java.util.Map;

public class ProcessOrderSubmissionBeanLiveTest
        extends ServletTestCase {

    private MapMessageSender messageSender;
    private SimpleSmtpServer smtpServer;
    private Element currentJbossMailServiceConfiguration;

    protected void setUp() throws Exception {
        currentJbossMailServiceConfiguration =
                reconfigureAndRestartJbossMailService(
                        parseDumbsterEmailConfiguration());

        messageSender = new JBossMapMessageSender();
        smtpServer = SimpleSmtpServer.start();
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        smtpServer.stop();
        reconfigureAndRestartJbossMailService(currentJbossMailServiceConfiguration);
    }

    private Element reconfigureAndRestartJbossMailService(Element dumbsterConfigurationElement)
            throws Exception {

        Context context = new InitialContext();
        RMIAdaptor rmiAdaptor =
                (RMIAdaptor) context.lookup("jmx/invoker/RMIAdaptor");

        ObjectName mailServiceObjectName =
                new ObjectName("jboss:service=Mail");

        Element currentJbossMailServiceConfiguration =
                (Element) rmiAdaptor.getAttribute(
                        mailServiceObjectName,
                        "Configuration");

        rmiAdaptor.setAttribute(
                mailServiceObjectName,
                new Attribute(
                        "Configuration",
                        dumbsterConfigurationElement));

        rmiAdaptor.invoke(
                mailServiceObjectName,
                "stop",
                new Object[0],
                new String[0]);

        rmiAdaptor.invoke(
                mailServiceObjectName,
                "start",
                new Object[0],
                new String[0]);

        return currentJbossMailServiceConfiguration;
    }

    private Element parseDumbsterEmailConfiguration()
            throws Exception {

        String configurationXmlAsString =
                "<configuration>"
                        + "    <property name=\"mail.store.protocol\" value=\"pop3\"/>"
                        + "    <property name=\"mail.transport.protocol\" value=\"smtp\"/>"
                        + "    <property name=\"mail.user\" value=\"jbrains\"/>"
                        + "    <property name=\"mail.pop3.host\" value=\"localhost\"/>"
                        + "    <property name=\"mail.smtp.host\" value=\"localhost\"/>"
                        + "    <property name=\"mail.from\" value=\"in-container-tests@localhost\"/>"
                        + "    <property name=\"mail.debug\" value=\"false\"/>"
                        + "</configuration>";

        DOMParser parser = new DOMParser();
        parser.parse(
                new InputSource(
                        new StringReader(configurationXmlAsString)));

        return parser.getDocument().getDocumentElement();
    }

    public void testEndToEnd() throws Exception {
        String orderQueueJndiName = "queue/Orders";

        Map messageContent =
                Collections.singletonMap(
                        "customer-email",
                        "jbr@diasparsoftware.com");

        messageSender.sendMapMessage(
                orderQueueJndiName,
                messageContent);

        Thread.sleep(500);

        assertEquals(1, smtpServer.getReceievedEmailSize());
    }
}
