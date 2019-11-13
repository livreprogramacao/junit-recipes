package junit.cookbook.coffee.model.ejb;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Set;

public abstract class LegacyShopcartBean implements EntityBean {
    public abstract Set getItems();

    public abstract void setItems(Set items);

    public void setEntityContext(EntityContext context)
            throws EJBException, RemoteException {
        // STUB Auto-generated method stub

    }

    public void unsetEntityContext()
            throws EJBException, RemoteException {
        // STUB Auto-generated method stub

    }

    public void ejbRemove()
            throws RemoveException, EJBException, RemoteException {
        // STUB Auto-generated method stub

    }

    public void ejbActivate() throws EJBException, RemoteException {
        // STUB Auto-generated method stub

    }

    public void ejbPassivate() throws EJBException, RemoteException {
        // STUB Auto-generated method stub

    }

    public void ejbLoad() throws EJBException, RemoteException {
        // STUB Auto-generated method stub

    }

    public void ejbStore() throws EJBException, RemoteException {
        // STUB Auto-generated method stub

    }

}
