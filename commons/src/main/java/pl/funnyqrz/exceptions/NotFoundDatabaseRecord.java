package pl.funnyqrz.exceptions;


public class NotFoundDatabaseRecord extends RuntimeException {

    public NotFoundDatabaseRecord(String message) {
        super(message);
    }


    public NotFoundDatabaseRecord(String message, Throwable cause) {
        super(message, cause);
    }

}
