package junit.cookbook.patterns;

public class MessageSendingObserver {
    private MessageSender messageSender;

    public MessageSendingObserver(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void handle(AbcEvent event) {
        messageSender.sendMessage(
                "ABC-related content",
                "ABC destination");
    }
}
