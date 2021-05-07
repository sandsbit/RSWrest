package me.nikitaserba.rsw.rest.exceptions;

public class ServerException extends RuntimeException {

    private final String id;

    public ServerException(String message, String id) {
        super(message);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
