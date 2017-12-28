package pl.funnyqrz.exceptions;

import java.util.function.Supplier;

public class ApplicationException extends RuntimeException{

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
