package me.nikitaserba.rsw.rest.repsonses;

public class ErrorResponseWithId extends ErrorResponse {

    private final String id;

    public ErrorResponseWithId(int errorCode, String errorId, String message, String id) {
        super(errorCode, errorId, message);
        this.id = id;
    }
}
