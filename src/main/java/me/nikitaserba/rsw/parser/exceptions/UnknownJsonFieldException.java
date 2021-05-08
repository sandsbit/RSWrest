package me.nikitaserba.rsw.parser.exceptions;

import com.google.gson.JsonParseException;

/**
 * This exception should be thrown if while parsing json was found excess field.
 */
public class UnknownJsonFieldException extends JsonParseException {

    private static String formMessage(String field) {
        return "Unknown field: '" + field + "'";
    }

    public UnknownJsonFieldException(String field) {
        super(formMessage(field));
    }

    public UnknownJsonFieldException(String field, Throwable cause) {
        super(formMessage(field), cause);
    }
}
