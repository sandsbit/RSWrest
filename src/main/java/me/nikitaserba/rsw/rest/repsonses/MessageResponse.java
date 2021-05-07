package me.nikitaserba.rsw.rest.repsonses;

public final class MessageResponse {

    private final int responseCode;
    private final String message;

    public MessageResponse(int responseCode, String message) {
        this.responseCode = responseCode;
        this.message = message;
    }
}
