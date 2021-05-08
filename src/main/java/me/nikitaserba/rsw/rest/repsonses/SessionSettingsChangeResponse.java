package me.nikitaserba.rsw.rest.repsonses;

import me.nikitaserba.rsw.parser.ParserSettings;

public final class SessionSettingsChangeResponse {

    private final int resultCode;
    private final String id;
    private final ParserSettings newSettings;

    public SessionSettingsChangeResponse(int resultCode, String id, ParserSettings newSettings) {
        this.resultCode = resultCode;
        this.id = id;
        this.newSettings = newSettings;
    }
}
