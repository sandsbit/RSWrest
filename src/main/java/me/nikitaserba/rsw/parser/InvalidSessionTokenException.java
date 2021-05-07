package me.nikitaserba.rsw.parser;

public class InvalidSessionTokenException extends Exception {
    public InvalidSessionTokenException() {
        super();
    }

    public InvalidSessionTokenException(String message) {
        super(message);
    }

    public InvalidSessionTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidSessionTokenException(Throwable cause) {
        super(cause);
    }

    protected InvalidSessionTokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
