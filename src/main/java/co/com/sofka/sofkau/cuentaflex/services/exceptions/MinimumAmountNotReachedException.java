package co.com.sofka.sofkau.cuentaflex.services.exceptions;

public class MinimumAmountNotReachedException extends RuntimeException {
    public MinimumAmountNotReachedException(String message) {
        super(message);
    }
}
