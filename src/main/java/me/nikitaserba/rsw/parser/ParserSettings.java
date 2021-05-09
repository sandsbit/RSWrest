package me.nikitaserba.rsw.parser;

import java.util.Objects;

public final class ParserSettings implements Cloneable {

    // ALL fields MUST be classes (not primitives), so they can be NULL
    private Boolean ignoreDiacritic;
    private String languageCode;

    public ParserSettings() {}

    public ParserSettings(Boolean ignoreDiacritic, String languageCode) {
        this.ignoreDiacritic = ignoreDiacritic;
        this.languageCode = languageCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParserSettings that = (ParserSettings) o;
        return ignoreDiacritic == that.ignoreDiacritic && languageCode.equals(that.languageCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ignoreDiacritic, languageCode);
    }

    @Override
    protected ParserSettings clone() {
        try {
            return (ParserSettings) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }

    public Boolean isIgnoringDiacritic() {
        return ignoreDiacritic;
    }

    public void setIgnoreDiacritic(boolean ignoreDiacritic) {
        this.ignoreDiacritic = ignoreDiacritic;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }
}