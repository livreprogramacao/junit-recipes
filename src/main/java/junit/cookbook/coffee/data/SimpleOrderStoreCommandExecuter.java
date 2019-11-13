package junit.cookbook.coffee.data;

public class SimpleOrderStoreCommandExecuter
        implements OrderStoreCommandExecuter {

    public void execute(
            OrderStore orderStore,
            OrderStoreCommand orderStoreCommand,
            String exceptionMessage) {

        orderStoreCommand.execute(orderStore);
    }
}
