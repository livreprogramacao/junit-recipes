package junit.cookbook.common.test;

import junit.cookbook.common.BankTransferAction;
import junit.cookbook.util.Bank;
import junit.cookbook.util.Money;
import junit.framework.TestCase;

public class BankTransferActionTest extends TestCase {
    public void testSettingInputParameters() {
        BankTransferAction action = new BankTransferAction();
        action.setSourceAccountId("source");
        action.setTargetAccountId("target");
        action.setAmount(Money.dollars(100));

        action.execute(new Bank() {
            public void transfer(String sourceAccountId, String targetAccountId, Money amount) {
                assertEquals("source", sourceAccountId);
                assertEquals("target", targetAccountId);
                assertEquals(Money.dollars(100), amount);
            }
        });
    }
}
