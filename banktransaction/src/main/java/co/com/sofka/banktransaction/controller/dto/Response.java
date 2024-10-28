package co.com.sofka.banktransaction.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Response<T> {

    private String message;
    /**
     * Code to response
     */
    private Integer code;

    private T bodyOut;

}
