package co.com.sofka.banktransaction.configuration.middleware;


import co.com.sofka.banktransaction.controller.dto.Response;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadRequestException extends RuntimeException {

    private Response bodyOut;

    public BadRequestException() {
        super();
    }

    public BadRequestException(final String message) {
        super(message);
    }

    public BadRequestException(Response bodyOut) {
        this.bodyOut = bodyOut;
    }
}
