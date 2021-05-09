package me.nikitaserba.rsw.rest.requests;

import me.nikitaserba.rsw.parser.ParserSettings;

public final class ParsingRequest {

    private final String sessionId;
    private final ParserSettings settings;
    private final String word;
    private final String text;

    private ParsingRequest() {
        this.sessionId = null;
        this.settings = null;
        this.word = null;
        this.text = null;
    }

    public ParsingRequest(String sessionId, ParserSettings settings, String word, String text) {
        this.sessionId = sessionId;
        this.settings = settings;
        this.word = word;
        this.text = text;
    }

    public String getSessionId() {
        return sessionId;
    }

    public ParserSettings getSettings() {
        return settings;
    }

    public String getWord() {
        return word;
    }

    public String getText() {
        return text;
    }
}
