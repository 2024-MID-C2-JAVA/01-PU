package co.com.sofka.banco.configuration.middleware;

public class DuplicateInfoException extends RuntimeException{

    public DuplicateInfoException(String message) {
        super(message);
    }
}
