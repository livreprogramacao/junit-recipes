package junit.cookbook.coffee.model.logic;

public class CommandException extends RuntimeException {
    public CommandException(String message, Exception cause) {
        super(message, cause);
    }
}
