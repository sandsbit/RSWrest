package me.nikitaserba.rsw.rest.repsonses;

public final class SessionCreatedResponse {

    private final int resultCode;
    private final String id;
    private final long expireTimeSeconds;

    public SessionCreatedResponse(int resultCode, String id, long expireTimeSeconds) {
        this.resultCode = resultCode;
        this.id = id;
        this.expireTimeSeconds = expireTimeSeconds;
    }
}
