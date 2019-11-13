package junit.cookbook.coffee.jdbc.test;

import com.diasparsoftware.java.sql.PreparedStatementData;
import com.diasparsoftware.java.util.Money;
import junit.cookbook.coffee.data.jdbc.CatalogStoreJdbcQueryBuilder;
import junit.framework.TestCase;

import java.util.Arrays;

public class InsertProductJdbcQueryTest extends TestCase {
    public void testParameters() {
        CatalogStoreJdbcQueryBuilder builder =
                new CatalogStoreJdbcQueryBuilder();

        PreparedStatementData expectedPreparedStatementData =
                new PreparedStatementData();
        expectedPreparedStatementData.sqlString =
                "insert into catalog.beans (productId, coffeeName, unitPrice) values (?, ?, ?)";
        expectedPreparedStatementData.parameters =
                Arrays.asList(
                        new Object[]{"001", "Sumatra", new Integer(750)});

        PreparedStatementData actualPreparedStatementData =
                builder.getPreparedStatementData(
                        "addProduct",
                        Arrays.asList(
                                new Object[]{
                                        "001",
                                        "Sumatra",
                                        Money.dollars(7, 50)}));

        assertEquals(
                expectedPreparedStatementData,
                actualPreparedStatementData);
    }
}
