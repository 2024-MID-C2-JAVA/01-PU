package co.com.sofka.banco.controller.model.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GenericResponse<T> {

    private String message;
    /**
     * Code to response
     */
    private Integer code;

    private T bodyOut;

}
