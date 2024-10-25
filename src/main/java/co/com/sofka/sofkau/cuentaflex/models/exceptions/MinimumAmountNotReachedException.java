package co.com.sofka.sofkau.cuentaflex.models.exceptions;

public class MinimumAmountNotReachedException extends RuntimeException {
    public MinimumAmountNotReachedException(String message) {
        super(message);
    }
}
