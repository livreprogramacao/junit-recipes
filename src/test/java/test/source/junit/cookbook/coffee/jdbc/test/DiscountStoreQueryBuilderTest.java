package junit.cookbook.coffee.jdbc.test;

import com.diasparsoftware.java.util.DateUtil;
import com.diasparsoftware.jdbc.JdbcUtil;
import junit.cookbook.coffee.data.Discount;
import junit.cookbook.coffee.data.PercentageOffSubtotalDiscountDefintion;
import junit.cookbook.coffee.data.jdbc.DiscountStoreJdbcQueryBuilder;
import junit.framework.TestCase;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DiscountStoreQueryBuilderTest extends TestCase {
    public void testParametersForRemoveExpiredDiscountAsOf()
            throws Exception {

        Date expiryDate = DateUtil.makeDate(2002, 1, 1);

        DiscountStoreJdbcQueryBuilder discountStoreJdbcQueryBuilder =
                new DiscountStoreJdbcQueryBuilder();

        List removeExpiredDiscountAsOfParameters =
                discountStoreJdbcQueryBuilder
                        .createPreparedStatementParameters(
                                "removeExpiredDiscountAsOf",
                                Collections.singletonList(expiryDate));

        List expectedParameters =
                Collections.singletonList(
                        JdbcUtil.makeTimestamp(2002, 1, 1));

        assertEquals(
                expectedParameters,
                removeExpiredDiscountAsOfParameters);
    }

    private Discount makeDiscount() {
        Discount discount = new Discount();
        discount.fromDate = DateUtil.makeDate(2002, 1, 1);
        discount.toDate = DateUtil.makeDate(2002, 12, 31);

        PercentageOffSubtotalDiscountDefintion discountDefinition =
                new PercentageOffSubtotalDiscountDefintion();
        discountDefinition.percentageOffSubtotal = 10;

        discount.discountDefinition = discountDefinition;
        return discount;
    }
}
