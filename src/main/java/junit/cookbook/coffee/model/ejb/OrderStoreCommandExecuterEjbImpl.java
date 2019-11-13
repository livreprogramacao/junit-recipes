package junit.cookbook.coffee.model.ejb;

import com.diasparsoftware.store.DataStoreException;
import junit.cookbook.coffee.data.OrderStore;
import junit.cookbook.coffee.data.OrderStoreCommand;
import junit.cookbook.coffee.data.OrderStoreCommandExecuter;

import javax.ejb.EJBException;

public class OrderStoreCommandExecuterEjbImpl
        implements OrderStoreCommandExecuter {

    private OrderStoreCommandExecuter executer;

    public OrderStoreCommandExecuterEjbImpl(OrderStoreCommandExecuter executer) {
        this.executer = executer;
    }

    public void execute(
            OrderStore orderStore,
            OrderStoreCommand orderStoreCommand,
            String exceptionMessage) {

        try {
            executer.execute(
                    orderStore,
                    orderStoreCommand,
                    exceptionMessage);
        } catch (DataStoreException wrap) {
            throw new EJBException(exceptionMessage, wrap);
        }
    }
}
