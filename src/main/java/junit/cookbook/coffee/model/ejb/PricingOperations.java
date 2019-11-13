package junit.cookbook.coffee.model.ejb;

import com.diasparsoftware.java.util.Money;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

public interface PricingOperations extends EJBObject {
    void setPrice(String productId, Money newPrice)
            throws RemoteException;
}
