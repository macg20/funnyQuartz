package pl.funnyqrz.exceptions;

public class NotFoundDatabaseRecord extends Exception {

    public NotFoundDatabaseRecord(String message) {
        super(message);
    }
}
