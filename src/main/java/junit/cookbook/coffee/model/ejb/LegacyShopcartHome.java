package junit.cookbook.coffee.model.ejb;

import javax.ejb.EJBLocalHome;


public interface LegacyShopcartHome extends EJBLocalHome {
    LegacyShopcart findByUserName(String string);
}
