package junit.cookbook.coffee.model.ejb;

import java.util.Vector;

/**
 * Remote interface for Enterprise Bean: ShopcartOperations
 */
public interface ShopcartOperations
        extends javax.ejb.EJBObject {
    public void addToShopcart(Vector requestedCoffeeQuantities)
            throws java.rmi.RemoteException;

    public Vector getShopcartItems()
            throws java.rmi.RemoteException;
}
