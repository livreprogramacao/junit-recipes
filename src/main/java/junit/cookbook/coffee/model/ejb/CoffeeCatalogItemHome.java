package junit.cookbook.coffee.model.ejb;

import junit.cookbook.coffee.model.CoffeeCatalogItem;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface CoffeeCatalogItemHome extends EJBHome {
    CoffeeCatalogItem create(
            String productId,
            String coffeeName,
            int unitPriceInCents)
            throws RemoteException, CreateException;

    CoffeeCatalogItem findByPrimaryKey(String productId)
            throws RemoteException, FinderException;

    CoffeeCatalogItem findByCoffeeName(String coffeeName)
            throws RemoteException, FinderException;
}
