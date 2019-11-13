package junit.cookbook.coffee.model.ejb;

import java.util.Vector;

public interface LegacyShopcartOperations extends javax.ejb.EJBObject {
    public void addToShopcart(Vector requestedCoffeeQuantities)
            throws java.rmi.RemoteException;

    public Vector getShopcartItems() throws java.rmi.RemoteException;
}
