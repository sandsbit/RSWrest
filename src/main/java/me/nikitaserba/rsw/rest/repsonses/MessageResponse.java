package me.nikitaserba.rsw.rest.repsonses;

import com.google.gson.annotations.SerializedName;

public final class MessageResponse {

    @SerializedName("response_code")
    private final int responseCode;
    private final String message;

    public MessageResponse(int responseCode, String message) {
        this.responseCode = responseCode;
        this.message = message;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getMessage() {
        return message;
    }
}
