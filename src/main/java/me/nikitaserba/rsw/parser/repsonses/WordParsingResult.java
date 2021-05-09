package me.nikitaserba.rsw.parser.repsonses;

import me.nikitaserba.rsw.parser.ParserSettings;

import java.util.Objects;
import java.util.Set;

/**
 * Class that stores result of parsing word by `HomographParser`.
 */
public class WordParsingResult extends ParsingResult {

    private final String parsedWord;  // original word that was checked
    private final boolean hasHomoforms;
    private final Set<String> possibleHomoforms;

    /**
     * Create result of parsing a word.
     *
     * @param parsedWord - word that was parsed.
     * @param hasHomoforms - if the form have any homoforms depending on used setting.
     * @param possibleHomoforms - possible homoforms of the word. must be null if `hasHomoforms` is false.
     * @param usedSettings - settings used while parsing.
     */
    public WordParsingResult(String parsedWord, boolean hasHomoforms, Set<String> possibleHomoforms, ParserSettings usedSettings) {
        super(usedSettings);
        this.parsedWord = parsedWord;
        this.hasHomoforms = hasHomoforms;
        this.possibleHomoforms = possibleHomoforms;
    }

    public String getParsedWord() {
        return parsedWord;
    }

    public boolean hasHomoforms() {
        return hasHomoforms;
    }

    public Set<String> getPossibleHomoforms() {
        return possibleHomoforms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WordParsingResult)) return false;
        if (!super.equals(o)) return false;
        WordParsingResult that = (WordParsingResult) o;
        return hasHomoforms == that.hasHomoforms && getParsedWord().equals(that.getParsedWord()) && Objects.equals(getPossibleHomoforms(), that.getPossibleHomoforms());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getParsedWord(), hasHomoforms, getPossibleHomoforms());
    }
}
