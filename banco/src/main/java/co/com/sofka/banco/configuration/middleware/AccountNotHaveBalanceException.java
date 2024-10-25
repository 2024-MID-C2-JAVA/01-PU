package co.com.sofka.banco.configuration.middleware;

public class AccountNotHaveBalanceException extends RuntimeException{

    public AccountNotHaveBalanceException(String message) {
        super(message);
    }
}
