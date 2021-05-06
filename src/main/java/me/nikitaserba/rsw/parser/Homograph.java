package me.nikitaserba.rsw.parser;

import java.util.Objects;
import java.util.Set;

/**
 * Class that represents homograph parsed from dictionary.
 */
public final class Homograph {

    private final String word;
    private final Set<String> homoforms;
    private final HomographType type;
    private final boolean isIgnoringDiacritic;  // True if word have homoforms only ignoring diacritical marks

    public Homograph(String word, Set<String> homoforms, HomographType type, boolean isIgnoringDiacritic) {
        this.word = word;
        this.homoforms = homoforms;
        this.type = type;
        this.isIgnoringDiacritic = isIgnoringDiacritic;
    }

    public String getWord() {
        return word;
    }

    public Set<String> getHomoforms() {
        return homoforms;
    }

    public HomographType getType() {
        return type;
    }

    public boolean isIgnoringDiacritic() {
        return isIgnoringDiacritic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Homograph homograph = (Homograph) o;
        return isIgnoringDiacritic == homograph.isIgnoringDiacritic && word.equals(homograph.word) && homoforms.equals(homograph.homoforms) && type == homograph.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, homoforms, type, isIgnoringDiacritic);
    }
}
