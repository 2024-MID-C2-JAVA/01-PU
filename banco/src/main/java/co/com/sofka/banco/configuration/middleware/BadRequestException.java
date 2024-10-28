package co.com.sofka.banco.configuration.middleware;


import co.com.sofka.banco.controller.model.response.GenericResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadRequestException extends RuntimeException {

    private GenericResponse bodyOut;

    public BadRequestException() {
        super();
    }

    public BadRequestException(final String message) {
        super(message);
    }

    public BadRequestException(GenericResponse bodyOut) {
        this.bodyOut = bodyOut;
    }
}
