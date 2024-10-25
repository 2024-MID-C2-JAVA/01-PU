package co.com.sofka.banco.configuration.middleware;

public class AccountNotExistException extends RuntimeException{

    public AccountNotExistException(String message) {
        super(message);
    }
}
