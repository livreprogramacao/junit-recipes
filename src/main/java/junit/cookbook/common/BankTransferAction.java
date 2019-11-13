package junit.cookbook.common;

import junit.cookbook.util.Bank;
import junit.cookbook.util.Money;

public class BankTransferAction {
    private String sourceAccountId;
    private String targetAccountId;
    private Money amount;

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public void setSourceAccountId(String sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public void setTargetAccountId(String targetAccountId) {
        this.targetAccountId = targetAccountId;
    }

    public void execute() {
        execute(Bank.getInstance());
    }

    public void execute(Bank bank) {
        bank.transfer(sourceAccountId, targetAccountId, amount);
    }
}