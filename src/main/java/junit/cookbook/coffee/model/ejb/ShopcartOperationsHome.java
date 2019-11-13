package junit.cookbook.coffee.model.ejb;

/**
 * Home interface for Enterprise Bean: ShopcartOperations
 */
public interface ShopcartOperationsHome
        extends javax.ejb.EJBHome {
    /**
     * Creates a default instance of Session Bean: ShopcartOperations
     */
    public junit
            .cookbook
            .coffee
            .model
            .ejb
            .ShopcartOperations create()
            throws javax.ejb.CreateException, java.rmi.RemoteException;
}
