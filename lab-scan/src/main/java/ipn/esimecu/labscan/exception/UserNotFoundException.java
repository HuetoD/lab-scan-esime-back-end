package ipn.esimecu.labscan.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {

    }

    public UserNotFoundException(String cause) {
        super(cause);
    }

}
