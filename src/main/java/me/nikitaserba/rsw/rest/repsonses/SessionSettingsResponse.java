package me.nikitaserba.rsw.rest.repsonses;

import me.nikitaserba.rsw.parser.ParserSettings;

public final class SessionSettingsResponse {

    private final int resultCode;
    private final String id;
    private final ParserSettings settings;

    public SessionSettingsResponse(int resultCode, String id, ParserSettings settings) {
        this.resultCode = resultCode;
        this.id = id;
        this.settings = settings;
    }
}
