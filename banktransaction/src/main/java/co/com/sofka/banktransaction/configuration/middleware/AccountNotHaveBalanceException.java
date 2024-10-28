package co.com.sofka.banktransaction.configuration.middleware;

public class AccountNotHaveBalanceException extends RuntimeException{

    public AccountNotHaveBalanceException(String message) {
        super(message);
    }
}
