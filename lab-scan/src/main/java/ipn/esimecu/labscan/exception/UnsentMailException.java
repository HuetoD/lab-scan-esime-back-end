package ipn.esimecu.labscan.exception;

import ipn.esimecu.labscan.dto.response.ErrorResponse;

public class UnsentMailException extends RuntimeException {

    private static final String UNKNOWN = "Desconocido";

    public final ErrorResponse error;

    public UnsentMailException(ErrorResponse error) {
        this.error = error;
    }

    public UnsentMailException() {
        this(ErrorResponse.builder().build());
    }

    public UnsentMailException(String message) {
        this(ErrorResponse.builder().message(message).details(UNKNOWN).build());
    }

    public UnsentMailException(String message, Throwable cause) {
        this(ErrorResponse.builder().build());
    }

    public UnsentMailException(Throwable cause) {
        this(ErrorResponse.builder().build());
    }
}
