package junit.cookbook.coffee.model.ejb;

import junit.cookbook.coffee.model.CoffeeQuantity;

import javax.ejb.CreateException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import java.util.Iterator;
import java.util.Vector;

public class LegacyShopcartOperationsBean
        implements javax.ejb.SessionBean {

    private javax.ejb.SessionContext mySessionCtx;
    private LegacyShopcart shopcart;

    public javax.ejb.SessionContext getSessionContext() {
        return mySessionCtx;
    }

    public void setSessionContext(javax.ejb.SessionContext ctx) {
        mySessionCtx = ctx;
    }

    public void addToShopcart(Vector requestedCoffeeQuantities) {
        for (Iterator i = requestedCoffeeQuantities.iterator();
             i.hasNext();
        ) {

            CoffeeQuantity each = (CoffeeQuantity) i.next();

            String eachCoffeeName = each.getCoffeeName();

            int currentQuantity;
            if (shopcart.containsCoffeeNamed(eachCoffeeName)) {
                currentQuantity = shopcart.getQuantity(eachCoffeeName);
            } else {
                currentQuantity = 0;
            }

            shopcart.setQuantity(
                    eachCoffeeName,
                    currentQuantity + each.getAmountInKilograms());
        }
    }

    public void ejbCreate() throws javax.ejb.CreateException {
        try {
            Context context = new InitialContext();
            Object homeAsObject = context.lookup("ejb/legacy/Shopcart");

            LegacyShopcartHome home =
                    (LegacyShopcartHome) PortableRemoteObject.narrow(
                            homeAsObject,
                            LegacyShopcartHome.class);

            shopcart = home.findByUserName("jbrains");
        } catch (NamingException e) {
            throw new CreateException(
                    "Naming exception: " + e.getMessage());
        }
    }

    public void ejbActivate() {
    }

    public void ejbPassivate() {
    }

    public void ejbRemove() {
        shopcart = null;
    }
}
