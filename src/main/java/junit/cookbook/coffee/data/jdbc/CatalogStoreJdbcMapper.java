package junit.cookbook.coffee.data.jdbc;

import com.diasparsoftware.java.util.Money;
import com.diasparsoftware.jdbc.JdbcMapper;
import junit.cookbook.coffee.data.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CatalogStoreJdbcMapper extends JdbcMapper {
    public Product makeProduct(ResultSet resultSet)
            throws SQLException {

        Product product = new Product();

        product.productId = resultSet.getString("productId");
        product.coffeeName = resultSet.getString("coffeeName");
        product.unitPrice = Money.cents(resultSet.getInt("unitPrice"));

        return product;
    }
}
