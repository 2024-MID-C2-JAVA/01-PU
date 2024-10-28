package co.com.sofka.banco.configuration.middleware;

public class TypeOfBuysNotExistException extends RuntimeException{

    public TypeOfBuysNotExistException(String message) {
        super(message);
    }
}
