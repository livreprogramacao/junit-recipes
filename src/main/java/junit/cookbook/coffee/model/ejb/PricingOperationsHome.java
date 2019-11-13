package junit.cookbook.coffee.model.ejb;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface PricingOperationsHome extends EJBHome {
    PricingOperations create() throws RemoteException, CreateException;
}
