package ipn.esimecu.labscan.exception;

import ipn.esimecu.labscan.dto.response.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@RestControllerAdvice
public class ExceptionHandling {

    private static final String METHOD_ARGUMENT_NOT_VALID = "Error de validación";

    private static final String INTERNAL_SERVER_ERROR = "Ha ocurrido un error en el servidor";
    private static final String BAD_CREDENTIALS = "Correo o contraseña incorrecta";
    private static final String BAD_CREDENTIALS_DETAILS = "Verifique que su correo o contraseña esté bien escrito.";
    private static final String UNKNOWN = "Desconocido";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidHandler(MethodArgumentNotValidException exception) {
        exception.printStackTrace(System.err);
        return ResponseEntity.badRequest()
                             .body(ErrorResponse.builder()
                                                .message(METHOD_ARGUMENT_NOT_VALID)
                                                .details(Optional.ofNullable(exception.getBindingResult().getFieldError())
                                                                 .map(FieldError::getDefaultMessage)
                                                                 .orElse(UNKNOWN))
                                                .build());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> authenticationExceptionHandler(AuthenticationException exception) {
        return ResponseEntity.badRequest()
                             .body(
                                ErrorResponse.builder()
                                             .message(BAD_CREDENTIALS)
                                             .details(BAD_CREDENTIALS_DETAILS)
                                             .build()
                            );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> illegalArgumentExceptionHandler(IllegalArgumentException exception) {
        return ResponseEntity.internalServerError()
                             .body(
                                ErrorResponse.builder()
                                             .message(INTERNAL_SERVER_ERROR)
                                             .details(exception.getMessage())
                                             .build()
                            );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> entityNotFoundExceptionHandler(EntityNotFoundException exception) {
        return ResponseEntity.unprocessableEntity()
                             .body(
                                     ErrorResponse.builder()
                                             .message("Ha ocurrido un error con la entidad a guardar")
                                             .details(exception.getMessage())
                                             .build()
                             );
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ErrorResponse> studentNotFoundExceptionHandler(StudentNotFoundException exception) {
        return ResponseEntity.badRequest()
                            .body(ErrorResponse.builder()
                                            .message("Error al buscar informacion del estudiante")
                                            .details(exception.getMessage())
                                            .build()
                            );
    }

    @ExceptionHandler(DaeServiceException.class)
    public ResponseEntity<ErrorResponse> daeServiceExceptionHandler(DaeServiceException exception) {
        return exception.isInternal ? ResponseEntity.internalServerError()
                .body(ErrorResponse.builder()
                                    .message(INTERNAL_SERVER_ERROR)
                                    .details(exception.getMessage())
                                    .build())

                : ResponseEntity.badRequest()
                                .body(ErrorResponse.builder()
                                                    .message("Verifique su credencial escaneada")
                                                    .details(exception.getMessage())
                                                    .build());
    }


}
