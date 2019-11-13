package junit.cookbook.coffee.model.ejb;

import javax.ejb.*;
import java.rmi.RemoteException;

public interface OrderHome extends EJBHome {
    OrderRemote create(Integer orderId, String customerId)
            throws CreateException, RemoteException;

    OrderRemote findByPrimaryKey(Integer orderId)
            throws FinderException, RemoteException;
}
