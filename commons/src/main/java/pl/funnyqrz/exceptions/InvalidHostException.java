package pl.funnyqrz.exceptions;

public class InvalidHostException extends RuntimeException {

    public InvalidHostException(String message) {
        super(message);
    }
}
