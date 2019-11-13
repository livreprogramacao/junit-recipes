package junit.cookbook.coffee.jdbc.test;

import com.diasparsoftware.java.util.Money;
import junit.cookbook.coffee.data.CatalogStore;
import junit.cookbook.coffee.data.Product;
import junit.cookbook.coffee.data.jdbc.CatalogStoreJdbcImpl;
import junit.framework.TestCase;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Set;

public class BrowseCatalogIsolationTest
        extends CoffeeShopDatabaseFixture {

    protected int expectedNumberOfProducts = 1000;
    private int actualNumberOfProducts1;
    private int actualNumberOfProducts2;

    protected void setUp() throws Exception {
        super.setUp();
        cleanTables();
        addProductsToDatabase(expectedNumberOfProducts);
    }

    private void addProductsToDatabase(int numberOfProductsToAdd)
            throws Exception {
        getConnection().setAutoCommit(false);

        CatalogStoreJdbcImpl catalogStore =
                new CatalogStoreJdbcImpl(getConnection());

        for (int i = 0; i < numberOfProductsToAdd; i++)
            catalogStore.addProduct(
                    new Product(
                            String.valueOf(i),
                            "Product " + i,
                            Money.dollars(6, 0)));

        getConnection().commit();
    }

    public void testBrowseDuringAdd() throws Exception {
        final Product newProduct =
                new Product("T-762", "New Coffee", Money.dollars(5, 50));

        Runnable addProductRunnable = new Runnable() {
            public void run() {
                try {
                    addProduct(newProduct);
                } catch (Exception e) {
                    TestCase.fail(e.toString());
                }
            }
        };

        Runnable browseProductsExpectingRunnable = new Runnable() {
            public void run() {
                try {
                    setActualNumberOfProducts1(countProducts());
                    Thread.sleep((long) (1000 * Math.random()));
                    setActualNumberOfProducts2(countProducts());
                } catch (Exception e) {
                    TestCase.fail(e.toString());
                }
            }
        };

        Thread productManagerThread =
                new Thread(addProductRunnable, "Product Manager Thread");

        Thread shopperThread =
                new Thread(
                        browseProductsExpectingRunnable,
                        "Shopper Thread");

        for (int i = 0; i < 100; i++) {
            productManagerThread.start();
            shopperThread.start();

            productManagerThread.join();
            shopperThread.join();

            TestCase.assertEquals(
                    "Different results for each query",
                    actualNumberOfProducts1,
                    actualNumberOfProducts2);

            TestCase.assertEquals(
                    expectedNumberOfProducts,
                    actualNumberOfProducts1);
        }
    }

    private int countProducts() throws Exception {

        Connection connection = getDataSource().getConnection();
        registerConnection(connection);

        connection.setAutoCommit(false);
        connection.setTransactionIsolation(
                Connection.TRANSACTION_READ_UNCOMMITTED);

        Thread.sleep((long) (1000 * Math.random()));

        Set products =
                new CatalogStoreJdbcImpl(connection).findAllProducts();

        connection.commit();

        return products.size();
    }

    private void addProduct(Product newProduct) throws Exception {
        Connection connection = getDataSource().getConnection();
        registerConnection(connection);

        connection.setAutoCommit(false);
        connection.setTransactionIsolation(
                Connection.TRANSACTION_READ_UNCOMMITTED);

        CatalogStore store = new CatalogStoreJdbcImpl(connection);
        store.addProduct(newProduct);

        Thread.sleep(1000);

        connection.rollback();
    }

    protected void tearDown() throws Exception {
        cleanTables();
        super.tearDown();
    }

    private void cleanTables() throws Exception {
        Statement statement = getConnection().createStatement();
        registerStatement(statement);

        statement.executeUpdate("delete from catalog.beans");
    }

    protected void setActualNumberOfProducts1(int actualNumberOfProducts1) {
        this.actualNumberOfProducts1 = actualNumberOfProducts1;
    }

    protected void setActualNumberOfProducts2(int actualNumberOfProducts2) {
        this.actualNumberOfProducts2 = actualNumberOfProducts2;
    }

}
