package junit.cookbook.util;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Money implements Cloneable, Serializable {
    private int cents;

    public Money(String moneyAsString) throws ParseException {
        this(parseToCents(moneyAsString));
    }

    public Money() {
        this.cents = 0;
    }

    public Money(int cents) {
        this.cents = cents;
    }

    public Money(int dollars, int cents) {
        this(100 * dollars + cents);
    }

    public static Money parse(String moneyAsString) {
        try {
            return new Money(parseToCents(moneyAsString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static int parseToCents(String moneyAsString)
            throws ParseException {

        double amountInDollars = NumberFormat
                .getCurrencyInstance().parse(moneyAsString)
                .doubleValue();

        return (int) (amountInDollars * 100);
    }

    public static Money dollars(int dollars) {
        return new Money(dollars, 0);
    }

    public int inCents() {
        return cents;
    }

    public boolean valueInCentsIs(int expected) {
        return this.cents == expected;
    }

    public boolean isValid() {
        return true;
    }

    public Object clone() {
        return new Money(cents);
    }

    public boolean equals(Object other) {
        if (other != null && getClass() == other.getClass()) {
            Money that = (Money) other;
            return this.cents == that.cents;
        }
        return false;
    }

    public int hashCode() {
        return cents;
    }

    public Money add(Money augend) {
        return new Money(this.cents + augend.cents);
    }

    public Money negate() {
        return new Money(-cents);
    }

    public Money roundToNearestDollar() {
        int justCents = cents % 100;
        int roundedDollars = (justCents < 50) ? (cents / 100)
                : (cents / 100 + 1);
        return new Money(roundedDollars, 0);
    }

    public List split(int nWays) {
        List result = new ArrayList();
        int baseSplitInCents = inCents() / nWays;
        int centsLeftOver = inCents() - baseSplitInCents * nWays;

        for (int i = 0; i < nWays; i++) {
            int eachSplitInCents;
            if (i < centsLeftOver) {
                eachSplitInCents = baseSplitInCents + 1;
            } else {
                eachSplitInCents = baseSplitInCents;
            }

            result.add(new Money(eachSplitInCents));
        }

        return result;
    }

    public String toString() {
        return NumberFormat.getCurrencyInstance().format(
                inDollars());
    }

    private double inDollars() {
        return ((double) inCents()) / 100.0d;
    }
}