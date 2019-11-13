package junit.cookbook.coffee.data;

import java.util.Set;


public interface CatalogStore {
    Set findBeansByName(String name);

    void addProduct(Product toAdd);

    Set findAllProducts();
}
