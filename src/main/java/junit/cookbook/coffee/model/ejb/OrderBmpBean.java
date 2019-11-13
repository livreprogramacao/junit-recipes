package junit.cookbook.coffee.model.ejb;

import junit.cookbook.coffee.data.SimpleOrderStoreCommandExecuter;
import junit.cookbook.coffee.data.jdbc.OrderStoreJdbcImpl;

import javax.ejb.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.rmi.RemoteException;

public class OrderBmpBean implements EntityBean {
    private EntityContext context;
    private OrderBmpBeanLogic logic;

    public static DataSource lookupDataSource() {
        try {
            Context rootContext = new InitialContext();
            Object object =
                    rootContext.lookup("java:comp/env/jdbc/OrderData");
            return (DataSource) object;
        } catch (NamingException wrap) {
            throw new EJBException(
                    "Unable to retrieve data source",
                    wrap);
        }
    }

    public String getCustomerId() {
        return logic.getCustomerId();
    }

    public void setCustomerId(String customerId) {
        logic.setCustomerId(customerId);
    }

    public void setEntityContext(EntityContext context)
            throws EJBException, RemoteException {

        this.context = context;
        makeBeanLogicObject();
    }

    protected void makeBeanLogicObject() {
        DataSourceConnectionProvider connectionProvider =
                new DataSourceConnectionProvider(lookupDataSource());

        OrderStoreCommandExecuterJdbcImpl orderStoreCommandExecuter =
                new OrderStoreCommandExecuterJdbcImpl(
                        new OrderStoreCommandExecuterEjbImpl(
                                new SimpleOrderStoreCommandExecuter()),
                        connectionProvider);

        this.logic =
                new OrderBmpBeanLogic(
                        new OrderStoreJdbcImpl(),
                        orderStoreCommandExecuter);
    }

    public void unsetEntityContext()
            throws EJBException, RemoteException {

        this.context = null;
    }

    public Integer ejbFindByPrimaryKey(Integer orderId)
            throws FinderException, RemoteException {

        return logic.ejbFindByPrimaryKey(orderId);
    }

    public Integer ejbCreate(Integer orderId, String customerId)
            throws CreateException, RemoteException {

        return logic.ejbCreate(orderId, customerId);
    }

    public void ejbPostCreate(Integer orderId, String customerId) {
    }

    public void ejbLoad() throws EJBException, RemoteException {
        logic.ejbLoad(getOrderId());
    }

    public void ejbRemove()
            throws RemoveException, EJBException, RemoteException {

        logic.ejbRemove(getOrderId());
    }

    public void ejbStore() throws EJBException, RemoteException {
        logic.ejbStore();
    }

    public void ejbActivate() throws EJBException, RemoteException {
    }

    public void ejbPassivate() throws EJBException, RemoteException {
    }

    public Integer getOrderId() {
        return (Integer) context.getPrimaryKey();
    }
}
