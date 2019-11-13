package junit.cookbook.coffee.data;

public abstract class OrderStoreCommand {
    public abstract void execute(OrderStore orderStore);

    public Object getReturnValue() {
        return null;
    }
}
