package me.nikitaserba.rsw.parser.repsonses;

import me.nikitaserba.rsw.parser.ParserSettings;

import java.util.Objects;

public abstract class ParsingResult {

    // settings used while parsing
    private final ParserSettings usedSettings;

    public ParsingResult(ParserSettings usedSettings) {
        this.usedSettings = usedSettings;
    }

    public ParserSettings getUsedSettings() {
        return usedSettings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParsingResult that = (ParsingResult) o;
        return Objects.equals(getUsedSettings(), that.getUsedSettings());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsedSettings());
    }
}
