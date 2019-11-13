package junit.cookbook.coffee.model.logic;

import junit.cookbook.coffee.service.MailService;

public class ProcessOrderSubmissionAction {
    public void processOrder(
            MailService mailService,
            String customerEmailAddress) {

        mailService.sendMessage(
                "ordering@coffeeShop.com",
                customerEmailAddress,
                "We received your order",
                "Hello, there! Just to let you know we received your order.");
    }
}
