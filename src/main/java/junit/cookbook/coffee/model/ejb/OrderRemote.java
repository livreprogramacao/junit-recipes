package junit.cookbook.coffee.model.ejb;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

public interface OrderRemote extends EJBObject {
    String getCustomerId() throws RemoteException;

    void setCustomerId(String customerId) throws RemoteException;
}
