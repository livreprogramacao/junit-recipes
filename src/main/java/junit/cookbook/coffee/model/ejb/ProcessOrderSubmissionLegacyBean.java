package junit.cookbook.coffee.model.ejb;

import junit.cookbook.coffee.service.MailService;

import javax.ejb.*;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ProcessOrderSubmissionLegacyBean implements
        MessageDrivenBean, MessageListener {

    public void ejbCreate() {
    }

    public void ejbRemove() throws EJBException {
    }

    public void setMessageDrivenContext(
            MessageDrivenContext context) throws EJBException {
    }

    public void onMessage(Message message) {
        try {
            MapMessage incomingMessage = (MapMessage) message;

            Context rootContext = new InitialContext();
            Object object = rootContext
                    .lookup("java:comp/env/service/Mail");

            MailService mailService = (MailService) object;

            String customerEmailAddress = incomingMessage
                    .getString("customer-email");

            mailService
                    .sendMessage("ordering@coffeeShop.com",
                            customerEmailAddress,
                            "We received your order",
                            "Hello, there! We received your order.");
        } catch (NamingException logged) {
            logged.printStackTrace();
        } catch (JMSException logged) {
            logged.printStackTrace();
        }
    }

}