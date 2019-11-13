package junit.cookbook.coffee.model.ejb.test;

import org.apache.cactus.ServletTestCase;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;

public abstract class JmsReplyBasedTest
        extends ServletTestCase
        implements MessageListener {

    private boolean receivedReply = false;
    private String replyText;

    protected void doTest_TextMessages(
            String queueConnectionFactoryJndiName,
            String requestQueueJndiName,
            String requestText,
            String responseQueueJndiName,
            String expectedResponseText,
            String correlationId)
            throws Exception {

        Context context = new InitialContext();
        QueueConnectionFactory queueConnectionFactory =
                (QueueConnectionFactory) context.lookup(
                        queueConnectionFactoryJndiName);

        QueueConnection connection =
                queueConnectionFactory.createQueueConnection();

        Queue submitOrderQueue =
                (Queue) context.lookup(requestQueueJndiName);
        Queue orderReceivedQueue =
                (Queue) context.lookup(responseQueueJndiName);
        QueueSession session =
                connection.createQueueSession(
                        false,
                        QueueSession.AUTO_ACKNOWLEDGE);

        connection.start();

        TextMessage textMessage = session.createTextMessage();
        textMessage.setText(requestText);
        textMessage.setJMSReplyTo(orderReceivedQueue);
        textMessage.setJMSCorrelationID(correlationId);

        QueueSender sender = session.createSender(submitOrderQueue);
        sender.send(textMessage);

        connection.stop();
        session.close();

        QueueSession replySession =
                connection.createQueueSession(
                        false,
                        QueueSession.AUTO_ACKNOWLEDGE);

        QueueReceiver receiver =
                replySession.createReceiver(orderReceivedQueue);
        receiver.setMessageListener(this);
        connection.start();

        Thread.sleep(1000);

        connection.stop();
        session.close();
        connection.close();

        assertTrue("No reply", receivedReply);
        assertEquals(expectedResponseText, replyText);
    }

    public void onMessage(Message message) {
        receivedReply = true;

        assertTrue(
                "Reply not a text message",
                message instanceof TextMessage);

        TextMessage replyAsTextMessage = (TextMessage) message;
        try {
            replyText = replyAsTextMessage.getText();
        } catch (JMSException e) {
            replyText = e.toString();
        }
    }
}
