package me.nikitaserba.rsw.utils.json;

import com.google.gson.JsonParseException;

public class InvalidClassException extends JsonParseException {

    public InvalidClassException(String msg) {
        super(msg);
    }

    public InvalidClassException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public InvalidClassException(Throwable cause) {
        super(cause);
    }
}
