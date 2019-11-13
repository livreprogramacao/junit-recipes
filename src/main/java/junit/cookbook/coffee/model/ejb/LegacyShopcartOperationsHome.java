package junit.cookbook.coffee.model.ejb;

public interface LegacyShopcartOperationsHome
        extends javax.ejb.EJBHome {

    public LegacyShopcartOperations create()
            throws javax.ejb.CreateException, java.rmi.RemoteException;
}
