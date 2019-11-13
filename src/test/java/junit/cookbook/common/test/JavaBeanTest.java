package junit.cookbook.common.test;

import junit.cookbook.common.BankTransferCommand;
import junit.cookbook.util.Money;
import junit.framework.TestCase;

public class JavaBeanTest extends TestCase {
    public void testBankTransferCommandIsValid() {
        BankTransferCommand command = new BankTransferCommand();
        command.setSourceAccountId("123-456A");
        command.setTargetAccountId("987-654B");
        command.setAmount(Money.dollars(1000));

        assertTrue(command.isReadyToExecute());
    }

    public void testNeedsAmount() {
        BankTransferCommand command = new BankTransferCommand();
        command.setSourceAccountId("123-456A");
        command.setTargetAccountId("987-654B");
        // Do not set amount

        assertFalse(command.isReadyToExecute());
    }
}
