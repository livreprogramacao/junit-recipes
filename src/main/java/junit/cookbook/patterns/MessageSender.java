package junit.cookbook.patterns;

public interface MessageSender {
    void sendMessage(
            String messageContent,
            String messageDestinationName);
}
