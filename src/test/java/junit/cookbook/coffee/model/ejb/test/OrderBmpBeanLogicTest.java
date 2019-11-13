package junit.cookbook.coffee.model.ejb.test;

import com.diasparsoftware.store.DataStoreException;
import junit.cookbook.coffee.data.OrderRow;
import junit.cookbook.coffee.data.OrderStore;
import junit.cookbook.coffee.data.OrderStoreCommand;
import junit.cookbook.coffee.data.OrderStoreCommandExecuter;
import junit.cookbook.coffee.model.ejb.OrderBmpBeanLogic;
import junit.framework.TestCase;
import org.easymock.MockControl;

import javax.ejb.*;
import java.sql.SQLException;

public class OrderBmpBeanLogicTest extends TestCase {
    private OrderBmpBeanLogic logic;
    private MockControl orderStoreControl;
    private OrderStore mockOrderStore;

    protected void setUp() throws Exception {
        orderStoreControl = MockControl
                .createNiceControl(OrderStore.class);

        mockOrderStore = (OrderStore) orderStoreControl
                .getMock();

        OrderStoreCommandExecuter simpleExecuter = new OrderStoreCommandExecuter() {
            public void execute(OrderStore orderStore,
                                OrderStoreCommand orderStoreCommand,
                                String exceptionMessage) {

                orderStoreCommand.execute(orderStore);
            }
        };

        logic = new OrderBmpBeanLogic(mockOrderStore,
                simpleExecuter);
    }

    public void testFindByPrimaryKey_Found() throws Exception {
        mockOrderStore.exists(new Integer(762));
        orderStoreControl.setReturnValue(true);

        orderStoreControl.replay();

        assertEquals(new Integer(762), logic
                .ejbFindByPrimaryKey(new Integer(762)));

        orderStoreControl.verify();
    }

    public void testFindByPrimaryKey_NotFound()
            throws Exception {
        mockOrderStore.exists(new Integer(762));
        orderStoreControl.setReturnValue(false);

        orderStoreControl.replay();

        try {
            logic.ejbFindByPrimaryKey(new Integer(762));
            fail("Found object?");
        } catch (ObjectNotFoundException expected) {
        }

        orderStoreControl.verify();
    }

    public void testLoad() throws Exception {
        Integer orderId = new Integer(762);
        OrderRow orderRow = new OrderRow(orderId, "jbrains");

        mockOrderStore.findByOrderId(orderId);
        orderStoreControl.setReturnValue(orderRow);

        orderStoreControl.replay();

        logic.ejbLoad(orderId);
        assertEquals(orderRow, logic.getOrderRow());

        orderStoreControl.verify();
    }

    public void testLoad_DataStoreException() throws Exception {
        Integer orderId = new Integer(762);
        OrderRow orderRow = new OrderRow(orderId, "jbrains");

        mockOrderStore.findByOrderId(orderId);
        DataStoreException exception = new DataStoreException(
                "Unable to find order", new SQLException());

        orderStoreControl.setThrowable(exception);

        orderStoreControl.replay();

        try {
            logic.ejbLoad(orderId);
            fail("Should have thrown an exception");
        } catch (DataStoreException expected) {
            assertSame(exception, expected);
        }

        orderStoreControl.verify();
    }
}