package me.nikitaserba.rsw.parser.exceptions;

public class InvalidSessionTokenException extends Exception {

    private final String id;

    public InvalidSessionTokenException(String id) {
        this.id = id;
    }

    public InvalidSessionTokenException(String message, String id) {
        super(message);
        this.id = id;
    }

    public InvalidSessionTokenException(String message, Throwable cause, String id) {
        super(message, cause);
        this.id = id;
    }

    public InvalidSessionTokenException(Throwable cause, String id) {
        super(cause);
        this.id = id;
    }

    public InvalidSessionTokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String id) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
