package me.nikitaserba.rsw.parser.repsonses;

import me.nikitaserba.rsw.parser.ParserSettings;

public abstract class ParsingResult {

    // settings used while parsing
    private final ParserSettings usedSettings;

    public ParsingResult(ParserSettings usedSettings) {
        this.usedSettings = usedSettings;
    }

    public ParserSettings getUsedSettings() {
        return usedSettings;
    }
}
