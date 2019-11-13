package junit.cookbook.coffee.model.ejb.test;

import junit.cookbook.coffee.model.ejb.LegacyShopcart;

import javax.ejb.*;
import java.util.Set;

public class MockLegacyShopcart implements LegacyShopcart {
    public void verify() {
    }

    public Set getItems() {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean containsCoffeeNamed(String eachCoffeeName) {
        // TODO Auto-generated method stub
        return false;
    }

    public int getQuantity(String eachCoffeeName) {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setQuantity(String eachCoffeeName, int i) {
        // TODO Auto-generated method stub

    }

    public EJBLocalHome getEJBLocalHome() throws EJBException {
        // TODO Auto-generated method stub
        return null;
    }

    public Object getPrimaryKey() throws EJBException {
        // TODO Auto-generated method stub
        return null;
    }

    public void remove() throws RemoveException, EJBException {
        // TODO Auto-generated method stub

    }

    public boolean isIdentical(EJBLocalObject arg0) throws EJBException {
        // TODO Auto-generated method stub
        return false;
    }
}
