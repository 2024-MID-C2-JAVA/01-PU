package co.com.sofka.banktransaction.configuration.middleware;

public class AccountNotExistException extends RuntimeException{

    public AccountNotExistException(String message) {
        super(message);
    }
}
