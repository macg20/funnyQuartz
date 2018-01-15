package pl.funnyqrz.exceptions;

public class UserAlreadyRegisterException extends RuntimeException {

    public UserAlreadyRegisterException() {
    }

    public UserAlreadyRegisterException(String message) {
        super(message);
    }

    public UserAlreadyRegisterException(String message, Throwable cause) {
        super(message, cause);
    }
}
