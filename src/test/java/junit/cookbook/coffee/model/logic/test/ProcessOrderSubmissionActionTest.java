package junit.cookbook.coffee.model.logic.test;

import junit.cookbook.coffee.model.logic.ProcessOrderSubmissionAction;
import junit.cookbook.coffee.service.MailService;
import junit.framework.TestCase;

public class ProcessOrderSubmissionActionTest extends TestCase {
    private boolean spyMailServiceInvoked = false;

    public void testToAddress() throws Exception {
        ProcessOrderSubmissionAction action =
                new ProcessOrderSubmissionAction();

        MailService spyMailService = new MailService() {
            public void sendMessage(
                    String fromAddress,
                    String toAddress,
                    String subject,
                    String bodyText) {

                assertEquals("jbr@diasparsoftware.com", toAddress);
                spyMailServiceInvoked = true;
            }
        };

        action.processOrder(spyMailService, "jbr@diasparsoftware.com");
        assertTrue(spyMailServiceInvoked);
    }
}
