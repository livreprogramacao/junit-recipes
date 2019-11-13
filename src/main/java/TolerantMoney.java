public class TolerantMoney {
    private double amount;

    public TolerantMoney(double amount) {
        this.amount = amount;
    }

    public boolean equals(Object other) {
        if (other instanceof TolerantMoney) {
            TolerantMoney that = (TolerantMoney) other;
            return Math.abs(this.amount - that.amount) <= 0.005d;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        return String.valueOf(amount);
    }
}
