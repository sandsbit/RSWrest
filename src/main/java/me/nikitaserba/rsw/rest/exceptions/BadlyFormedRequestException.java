package me.nikitaserba.rsw.rest.exceptions;

public class BadlyFormedRequestException extends ServerException {
    public BadlyFormedRequestException(String message) {
        super(message, "INVALID_PARSING_REQUEST");
    }
}
