package me.nikitaserba.rsw.rest.repsonses;

public class ErrorResponse {

    private final int errorCode;
    private final String errorId;
    private final String message;

    public ErrorResponse(int errorCode, String errorId, String message) {
        this.errorCode = errorCode;
        this.errorId = errorId;
        this.message = message;
    }
}
