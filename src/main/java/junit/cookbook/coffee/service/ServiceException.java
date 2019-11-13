package junit.cookbook.coffee.service;

public class ServiceException extends RuntimeException {
    public ServiceException(String message, Exception cause) {
        super(message, cause);
    }
}
