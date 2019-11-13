package junit.cookbook.coffee.model.ejb;

import com.diasparsoftware.java.util.Money;

import javax.ejb.*;
import java.rmi.RemoteException;

public class PricingOperationsBean implements SessionBean {

    public void ejbCreate() throws RemoteException {
    }

    public void setPrice(String productId, Money newPrice) throws RemoteException {
        /* Intentionally do nothing */
    }

    public void setSessionContext(SessionContext context)
            throws EJBException, RemoteException {
    }

    public void ejbRemove() throws EJBException, RemoteException {
    }

    public void ejbActivate() throws EJBException, RemoteException {
    }

    public void ejbPassivate() throws EJBException, RemoteException {
    }
}
