package ipn.esimecu.labscan.exception;

import ipn.esimecu.labscan.dto.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@RestControllerAdvice
public class ExceptionHandling {

    private static final String METHOD_ARGUMENT_NOT_VALID = "Error de validación";
    private static final String UNKNOWN = "Desconocido";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidHandler(MethodArgumentNotValidException exception) {
        return ResponseEntity.badRequest()
                             .body(ErrorResponse.builder()
                                                .message(METHOD_ARGUMENT_NOT_VALID)
                                                .details(Optional.ofNullable(exception.getBindingResult().getFieldError())
                                                                 .map(FieldError::getDefaultMessage)
                                                                 .orElse(UNKNOWN))
                                                .build());
    }


}
