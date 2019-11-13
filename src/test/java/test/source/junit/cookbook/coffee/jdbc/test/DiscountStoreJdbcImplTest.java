package junit.cookbook.coffee.jdbc.test;

import com.diasparsoftware.java.util.DateUtil;
import com.diasparsoftware.jdbc.JdbcUtil;
import junit.cookbook.coffee.data.Discount;
import junit.cookbook.coffee.data.DiscountStore;
import junit.cookbook.coffee.data.PercentageOffSubtotalDiscountDefintion;
import junit.cookbook.coffee.data.jdbc.DiscountStoreJdbcImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

public class DiscountStoreJdbcImplTest
        extends CoffeeShopDatabaseFixture {

    protected void setUp() throws Exception {
        super.setUp();

        cleanTables();

        Connection connection = null;
        PreparedStatement insertPercentageOffSubtotalOffer = null;
        PreparedStatement insertDiscount = null;

        try {
            connection = getDataSource().getConnection();

            insertPercentageOffSubtotalOffer =
                    connection.prepareStatement(
                            "insert into catalog.discountDefinition (discountDefinitionId, typeName, percentageOffSubtotal) values (?, ?, ?)");

            int[] percentageOffSubtotalOfferData =
                    new int[]{10, 20, 30, 40, 50};

            doInsertDiscountDefinitions(
                    insertPercentageOffSubtotalOffer,
                    percentageOffSubtotalOfferData);

            insertDiscount =
                    connection.prepareStatement(
                            "insert into catalog.discount (discountId, fromDate, toDate, discountDefinitionId) values (?, ?, ?, ?)");

            insertDiscount.clearParameters();
            insertDiscount.setInt(1, 0);
            insertDiscount.setTimestamp(
                    2,
                    JdbcUtil.makeTimestamp(2002, 10, 15));
            insertDiscount.setTimestamp(
                    3,
                    JdbcUtil.makeTimestamp(2002, 11, 30));
            insertDiscount.setInt(4, 4); // 50% off    

            insertDiscount.executeUpdate();
        } catch (Exception rethrow) {
            throw rethrow;
        } finally {
            if (insertDiscount != null)
                insertDiscount.close();

            if (insertPercentageOffSubtotalOffer != null)
                insertPercentageOffSubtotalOffer.close();

            if (connection != null)
                connection.close();
        }
    }

    private void doInsertDiscountDefinitions(
            PreparedStatement insertPercentageOffSubtotalOffer,
            int[] percentageOffSubtotalOfferData)
            throws SQLException {

        for (int discountDefinitionId = 0;
             discountDefinitionId
                     < percentageOffSubtotalOfferData.length;
             discountDefinitionId++) {

            insertPercentageOffSubtotalOffer.clearParameters();

            insertPercentageOffSubtotalOffer.setInt(
                    1,
                    discountDefinitionId);

            insertPercentageOffSubtotalOffer.setString(
                    2,
                    PercentageOffSubtotalDiscountDefintion.class.getName());

            insertPercentageOffSubtotalOffer.setInt(
                    3,
                    percentageOffSubtotalOfferData[discountDefinitionId]);

            assertEquals(
                    1,
                    insertPercentageOffSubtotalOffer.executeUpdate());
        }
    }

    protected void tearDown() throws Exception {
        cleanTables();

        super.tearDown();
    }

    private void cleanTables() throws Exception {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = getDataSource().getConnection();
            statement = connection.createStatement();

            // The order of the tables matters
            statement.executeUpdate("delete from catalog.discount");

            statement.executeUpdate(
                    "delete from catalog.discountDefinition");
        } catch (Exception rethrow) {
            throw rethrow;
        } finally {
            try {
                if (statement != null)
                    statement.close();

                if (connection != null)
                    connection.close();
            } catch (SQLException ignored) {
            }
        }
    }

    public void testDeleteExpiredDiscounts() throws Exception {
        Connection connection = getDataSource().getConnection();
        registerConnection(connection);

        DiscountStore discountStore =
                new DiscountStoreJdbcImpl(connection);

        Date expiryDate = DateUtil.makeDate(2003, 1, 1);

        discountStore.removeExpiredDiscountAsOf(expiryDate);

        assertTrue(
                discountStore.findExpiresNoLaterThan(expiryDate).isEmpty());
    }

    public void testFindExpiredDiscounts() throws Exception {
        Connection connection = getDataSource().getConnection();
        registerConnection(connection);

        DiscountStore discountStore =
                new DiscountStoreJdbcImpl(connection);

        Date expiryDate = DateUtil.makeDate(2003, 1, 1);

        discountStore.findExpiresNoLaterThan(expiryDate);

        PercentageOffSubtotalDiscountDefintion discountDefinition =
                new PercentageOffSubtotalDiscountDefintion();

        discountDefinition.percentageOffSubtotal = 50;

        Discount discount = new Discount();
        discount.fromDate = DateUtil.makeDate(2002, 10, 15);
        discount.toDate = DateUtil.makeDate(2002, 11, 30);
        discount.discountDefinition = discountDefinition;

        Set expectedDiscounts = Collections.singleton(discount);

        assertEquals(
                expectedDiscounts,
                discountStore.findExpiresNoLaterThan(expiryDate));
    }
}
