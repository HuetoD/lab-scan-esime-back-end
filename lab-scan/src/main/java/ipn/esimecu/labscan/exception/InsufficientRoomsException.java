package ipn.esimecu.labscan.exception;

public class InsufficientRoomsException extends RuntimeException {

    public InsufficientRoomsException() {
    }

    public InsufficientRoomsException(String message) {
        super(message);
    }

    public InsufficientRoomsException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsufficientRoomsException(Throwable cause) {
        super(cause);
    }

    public InsufficientRoomsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
