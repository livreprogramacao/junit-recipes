package junit.cookbook.coffee.model.jms;

import com.diasparsoftware.java.lang.MissingParameterException;
import com.diasparsoftware.javax.jms.MessagingException;
import junit.cookbook.coffee.model.logic.ProcessOrderSubmissionAction;
import junit.cookbook.coffee.service.MailService;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ProcessOrderSubmissionMessageListener
        implements MessageListener {

    public static final String CUSTOMER_EMAIL_PARAMETER_NAME =
            "customer-email";

    private ProcessOrderSubmissionAction action;

    public ProcessOrderSubmissionMessageListener() {
        this(new ProcessOrderSubmissionAction());
    }

    public ProcessOrderSubmissionMessageListener(ProcessOrderSubmissionAction action) {
        this.action = action;
    }

    public void onMessage(Message message) {
        try {
            MapMessage incomingMessage = (MapMessage) message;
            String customerEmailAddress =
                    incomingMessage.getString(
                            CUSTOMER_EMAIL_PARAMETER_NAME);

            if (customerEmailAddress == null)
                throw new MessagingException(
                        "Unable to read message " + incomingMessage,
                        new MissingParameterException(CUSTOMER_EMAIL_PARAMETER_NAME));

            Context rootContext = new InitialContext();
            Object object =
                    rootContext.lookup("java:comp/env/service/Mail");

            MailService mailService = (MailService) object;

            action.processOrder(mailService, customerEmailAddress);
        } catch (NamingException logged) {
            logged.printStackTrace();
        } catch (JMSException logged) {
            logged.printStackTrace();
        }
    }
}
