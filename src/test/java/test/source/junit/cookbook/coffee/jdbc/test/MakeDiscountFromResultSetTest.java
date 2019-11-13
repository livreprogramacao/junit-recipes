package junit.cookbook.coffee.jdbc.test;

import com.diasparsoftware.java.util.DateUtil;
import com.diasparsoftware.jdbc.JdbcUtil;
import com.mockobjects.sql.MockSingleRowResultSet;
import junit.cookbook.coffee.data.Discount;
import junit.cookbook.coffee.data.PercentageOffSubtotalDiscountDefintion;
import junit.cookbook.coffee.data.jdbc.DiscountStoreJdbcMapper;
import junit.framework.TestCase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MakeDiscountFromResultSetTest extends TestCase {
    public void testDiscountJoinWithDiscountDefinition()
            throws Exception {

        PercentageOffSubtotalDiscountDefintion expectedDiscountDefinition =
                new PercentageOffSubtotalDiscountDefintion();
        expectedDiscountDefinition.percentageOffSubtotal = 25;

        Date expectedFromDate = DateUtil.makeDate(1974, 5, 4);
        Date expectedToDate = DateUtil.makeDate(2000, 5, 5);

        Discount expectedDiscount =
                new Discount(
                        expectedFromDate,
                        expectedToDate,
                        expectedDiscountDefinition);

        DiscountStoreJdbcMapper mapper = new DiscountStoreJdbcMapper();
        MockSingleRowResultSet resultSet = new MockSingleRowResultSet();

        Map rowData = new HashMap();

        rowData.put(
                "typeName",
                PercentageOffSubtotalDiscountDefintion.class.getName());

        rowData.put("fromDate", JdbcUtil.makeTimestamp(1974, 5, 4));
        rowData.put("toDate", JdbcUtil.makeTimestamp(2000, 5, 5));
        rowData.put("percentageOffSubtotal", new Integer(25));
        rowData.put("suspended", null);

        resultSet.addExpectedNamedValues(rowData);

        assertTrue(resultSet.next());

        assertEquals(expectedDiscount, mapper.makeDiscount(resultSet));
    }
}
