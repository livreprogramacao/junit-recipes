package junit.cookbook.junitx.example;

public class LegacyMoney {
    private int dollars;

    private LegacyMoney(int dollars) {
        this.dollars = dollars;
    }

    public static LegacyMoney dollars(int dollars) {
        return new LegacyMoney(dollars);
    }

    public void add(int dollars) {
        this.dollars += dollars;
    }

    public void subtract(int dollars) {
        this.dollars -= dollars;
    }

    protected void reset() {
        this.dollars = 0;
    }
}
