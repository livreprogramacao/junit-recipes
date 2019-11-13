package junit.cookbook.coffee.service;

public interface MailService {
    void sendMessage(
            String fromAddress,
            String toAddress,
            String subject,
            String bodyText);
}
