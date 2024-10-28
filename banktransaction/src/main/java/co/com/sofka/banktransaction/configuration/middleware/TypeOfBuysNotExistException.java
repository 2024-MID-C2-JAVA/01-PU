package co.com.sofka.banktransaction.configuration.middleware;

public class TypeOfBuysNotExistException extends RuntimeException{

    public TypeOfBuysNotExistException(String message) {
        super(message);
    }
}
