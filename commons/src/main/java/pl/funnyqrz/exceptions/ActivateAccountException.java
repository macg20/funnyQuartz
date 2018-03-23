package pl.funnyqrz.exceptions;


public class ActivateAccountException extends RuntimeException {

    public ActivateAccountException() {
    }

    public ActivateAccountException(String message) {
        super(message);
    }

    public ActivateAccountException(String message, Throwable cause) {
        super(message, cause);
    }
}
