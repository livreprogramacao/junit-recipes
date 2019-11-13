import org.apache.cactus.ServletTestCase;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;

public class JBossJmsLearningTest
        extends ServletTestCase
        implements MessageListener {

    private boolean received;

    protected void setUp() throws Exception {
        received = false;
    }

    public void testMessageListenerThrowsException() throws Exception {
        String queueConnectionFactoryJndiName = "ConnectionFactory";

        Context context = new InitialContext();
        QueueConnectionFactory queueConnectionFactory =
                (QueueConnectionFactory) context.lookup(
                        queueConnectionFactoryJndiName);

        QueueConnection connection =
                queueConnectionFactory.createQueueConnection();

        Queue queue = (Queue) context.lookup("queue/Test");

        QueueSession session =
                connection.createQueueSession(
                        false,
                        QueueSession.AUTO_ACKNOWLEDGE);

        connection.start();

        QueueReceiver receiver = session.createReceiver(queue);
        receiver.setMessageListener(this);
        connection.start();

        Thread.sleep(1000);

        TextMessage textMessage = session.createTextMessage();
        textMessage.setText("Come here, Watson; I want you.");

        QueueSender sender = session.createSender(queue);
        sender.send(textMessage);

        connection.stop();
        session.close();

        connection.close();

        assertTrue("Message not received", received);
    }

    public void onMessage(Message message) {
        received = true;
        throw new RuntimeException("Intentional exception");
        // Causes an exception to be written to the JBoss log.
        // That's all.
    }

}
