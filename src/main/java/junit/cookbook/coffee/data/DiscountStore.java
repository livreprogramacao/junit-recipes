package junit.cookbook.coffee.data;

import java.util.Date;
import java.util.Set;

public interface DiscountStore {
    public abstract Set findExpiresNoLaterThan(Date expiryDate);

    public abstract void removeExpiredDiscountAsOf(Date expiryDate);
}