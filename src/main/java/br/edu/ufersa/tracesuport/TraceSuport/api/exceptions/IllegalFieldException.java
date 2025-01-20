package br.edu.ufersa.tracesuport.TraceSuport.api.exceptions;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class IllegalFieldException extends RuntimeException {
    String field;

    public IllegalFieldException(String message, String field) {
        super(message);
        this.setField(field);
    }
}
