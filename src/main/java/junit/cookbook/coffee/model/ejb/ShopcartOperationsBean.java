package junit.cookbook.coffee.model.ejb;

import junit.cookbook.coffee.model.ShopcartLogic;

import java.util.Vector;


public class ShopcartOperationsBean
        implements javax.ejb.SessionBean {

    private javax.ejb.SessionContext mySessionCtx;
    private ShopcartLogic shopcart;

    public javax.ejb.SessionContext getSessionContext() {
        return mySessionCtx;
    }

    public void setSessionContext(
            javax.ejb.SessionContext ctx) {
        mySessionCtx = ctx;
    }

    public void ejbCreate()
            throws javax.ejb.CreateException {

        shopcart = new ShopcartLogic();
    }

    public void ejbActivate() {
    }

    public void ejbPassivate() {
    }

    public void ejbRemove() {
    }

    public void addToShopcart(Vector requestedCoffeeQuantities) {
        shopcart.addToShopcart(requestedCoffeeQuantities);
    }

    public Vector getShopcartItems() {
        return shopcart.getShopcartItems();
    }
}
