package junit.cookbook.patterns.test;

import junit.cookbook.patterns.AbcEvent;
import junit.cookbook.patterns.MessageSender;
import junit.cookbook.patterns.MessageSendingObserver;
import junit.framework.TestCase;
import org.easymock.MockControl;

public class MessageSendingObserverTest extends TestCase {
    private MessageSendingObserver observer;
    private MessageSender messageSender;
    private MockControl messageSenderControl;

    protected void setUp() throws Exception {
        messageSenderControl =
                MockControl.createControl(MessageSender.class);
        messageSender = (MessageSender) messageSenderControl.getMock();
        observer = new MessageSendingObserver(messageSender);
    }

    public void testAbcEvent() throws Exception {
        messageSender.sendMessage(
                "ABC-related content",
                "ABC destination");

        messageSenderControl.replay();

        observer.handle(new AbcEvent());

        messageSenderControl.verify();
    }
}
