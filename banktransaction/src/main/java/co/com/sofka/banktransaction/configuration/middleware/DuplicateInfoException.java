package co.com.sofka.banktransaction.configuration.middleware;

public class DuplicateInfoException extends RuntimeException{

    public DuplicateInfoException(String message) {
        super(message);
    }
}
