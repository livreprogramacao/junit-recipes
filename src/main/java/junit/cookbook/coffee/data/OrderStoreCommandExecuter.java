package junit.cookbook.coffee.data;

public interface OrderStoreCommandExecuter {
    void execute(
            junit.cookbook.coffee.data.OrderStore orderStore,
            junit.cookbook.coffee.data.OrderStoreCommand orderStoreCommand,
            String exceptionMessage);
}
