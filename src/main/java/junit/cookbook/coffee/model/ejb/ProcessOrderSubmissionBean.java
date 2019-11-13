package junit.cookbook.coffee.model.ejb;

import junit.cookbook.coffee.service.MailService;

import javax.ejb.*;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ProcessOrderSubmissionBean
        implements MessageDrivenBean, MessageListener {

    private ProcessOrderSubmissionMessageHandler messageHandler;

    public void ejbCreate() {
        messageHandler = new ProcessOrderSubmissionMessageHandler();
    }

    public void ejbRemove() throws EJBException {
        messageHandler = null;
    }

    public void setMessageDrivenContext(MessageDrivenContext context)
            throws EJBException {
    }

    public void onMessage(Message message) {
        try {
            Context rootContext = new InitialContext();
            Object object =
                    rootContext.lookup("java:comp/env/service/Mail");
            MailService mailService = (MailService) object;

            messageHandler.handleMessage(message, mailService);
        } catch (NamingException logged) {
            logged.printStackTrace();
        } catch (JMSException logged) {
            logged.printStackTrace();
        }
    }
}
