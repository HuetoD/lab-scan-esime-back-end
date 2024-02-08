package ipn.esimecu.labscan.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import ipn.esimecu.labscan.dto.response.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.util.IO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

@RestControllerAdvice
public class ExceptionHandling {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static final String METHOD_ARGUMENT_NOT_VALID = "Error de validación";

    public static final String INTERNAL_SERVER_ERROR = "Ha ocurrido un error en el servidor";
    public static final String BAD_CREDENTIALS = "Correo o contraseña incorrecta";
    public static final String BAD_CREDENTIALS_DETAILS = "Verifique que su correo o contraseña esté bien escrito.";
    public static final String UNKNOWN = "Desconocido";

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handleThrowable(Throwable throwable) {
        throwable.printStackTrace(System.err);
        return ResponseEntity.internalServerError()
                            .body(buildInternalServerError(throwable));
    }

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

    @ExceptionHandler({AuthenticationException.class, UserNotFoundException.class})
    public ResponseEntity<ErrorResponse> authenticationExceptionHandler() {
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
        exception.printStackTrace(System.err);
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
        exception.printStackTrace(System.err);
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

    @ExceptionHandler(UnsentMailException.class)
    public ResponseEntity<ErrorResponse> unsentMailExceptionHandler(UnsentMailException exception) {
        return ResponseEntity.internalServerError()
                .body(ErrorResponse.builder()
                        .message(INTERNAL_SERVER_ERROR)
                        .details(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<ErrorResponse> emailExistsExceptionHandler(EmailExistsException exception) {
        return ResponseEntity.badRequest()
                .body(ErrorResponse.builder()
                        .message("Correo duplicado")
                        .details(exception.getMessage())
                        .build());
    }

    public static void handle(HttpServletResponse response, ErrorResponse error, HttpStatus status) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status.value());
        OutputStream outputStream = response.getOutputStream();
        objectMapper.writeValue(outputStream, error);
        outputStream.flush();
    }

    public static void handleInternalServerError(HttpServletResponse response, Exception exception) throws IOException {
        handle(response, buildInternalServerError(exception), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static void handleUnauthorized(HttpServletResponse response) throws IOException {
        handle(response, ErrorResponse.builder()
                .message("Debes iniciar sesión")
                .details("No estás autorizado para acceder a este recurso")
                .build(), HttpStatus.UNAUTHORIZED);
    }

    public static ErrorResponse buildInternalServerError(Throwable throwable) {
        return ErrorResponse.builder().message(INTERNAL_SERVER_ERROR).details("Error de tipo: " + throwable.getClass().getSimpleName()).build();
    }

}
