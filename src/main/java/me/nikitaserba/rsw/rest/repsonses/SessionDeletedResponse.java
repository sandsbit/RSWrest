package me.nikitaserba.rsw.rest.repsonses;

public final class SessionDeletedResponse {

    private final int resultCode;
    private final String id;

    public SessionDeletedResponse(int resultCode, String id) {
        this.resultCode = resultCode;
        this.id = id;
    }
}
