package junit.cookbook.coffee.model.ejb;

import javax.ejb.*;
import java.rmi.RemoteException;

public abstract class CoffeeCatalogItemBean implements EntityBean {
    public abstract String getProductId();

    public abstract void setProductId(String productId);

    public abstract String getCoffeeName();

    public abstract void setCoffeeName(String coffeeName);

    public abstract int getUnitPrice();

    public abstract void setUnitPrice(int unitPriceInCents);

    public String ejbCreate(
            String productId,
            String coffeeName,
            int unitPriceInCents)
            throws CreateException {

        setProductId(productId);
        setCoffeeName(coffeeName);
        setUnitPrice(unitPriceInCents);

        return productId;
    }

    public void ejbPostCreate(
            String productId,
            String coffeeName,
            int unitPriceInCents) {

        // Intentionally do nothing
    }

    public void setEntityContext(EntityContext entityContext)
            throws EJBException, RemoteException {
        // TODO Auto-generated method stub

    }

    public void unsetEntityContext()
            throws EJBException, RemoteException {
        // TODO Auto-generated method stub

    }

    public void ejbRemove()
            throws RemoveException, EJBException, RemoteException {
        // TODO Auto-generated method stub

    }

    public void ejbActivate() throws EJBException, RemoteException {
        // TODO Auto-generated method stub

    }

    public void ejbPassivate() throws EJBException, RemoteException {
        // TODO Auto-generated method stub

    }

    public void ejbLoad() throws EJBException, RemoteException {
        // TODO Auto-generated method stub

    }

    public void ejbStore() throws EJBException, RemoteException {
        // TODO Auto-generated method stub

    }

}
