package junit.cookbook.coffee.data.jdbc;

import com.diasparsoftware.jdbc.JdbcMapper;
import com.diasparsoftware.store.DataMakesNoSenseException;
import junit.cookbook.coffee.data.Discount;
import junit.cookbook.coffee.data.DiscountDefinition;
import junit.cookbook.coffee.data.PercentageOffSubtotalDiscountDefintion;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DiscountStoreJdbcMapper extends JdbcMapper {
    public Discount makeDiscount(ResultSet discountResultSet)
            throws SQLException {

        Discount discount = new Discount();

        discount.fromDate = getDate(discountResultSet, "fromDate");
        discount.toDate = getDate(discountResultSet, "toDate");

        discount.discountDefinition =
                makeDiscountDefinition(discountResultSet);

        return discount;
    }

    public DiscountDefinition makeDiscountDefinition(ResultSet resultSet)
            throws SQLException {

        String discountClassName = resultSet.getString("typeName");
        if (PercentageOffSubtotalDiscountDefintion
                .class
                .getName()
                .equals(discountClassName)) {

            PercentageOffSubtotalDiscountDefintion discountDefinition =
                    new PercentageOffSubtotalDiscountDefintion();
            discountDefinition.percentageOffSubtotal =
                    resultSet.getInt("percentageOffSubtotal");
            return discountDefinition;
        } else
            throw new DataMakesNoSenseException(
                    "Bad discount definition type name: '"
                            + discountClassName
                            + "'");
    }
}
