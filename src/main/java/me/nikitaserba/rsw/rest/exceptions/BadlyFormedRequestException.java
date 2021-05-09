package me.nikitaserba.rsw.rest.exceptions;

public class BadlyFormedRequestException extends ServerException{
    public BadlyFormedRequestException(String message, String id) {
        super(message, id);
    }
}
