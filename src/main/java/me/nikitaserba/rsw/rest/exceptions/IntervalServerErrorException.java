package me.nikitaserba.rsw.rest.exceptions;

public class IntervalServerErrorException extends ServerException {
    public IntervalServerErrorException(String message, String id) {
        super(message, id);
    }
}
