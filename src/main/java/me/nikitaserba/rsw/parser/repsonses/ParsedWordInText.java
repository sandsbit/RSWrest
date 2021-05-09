package me.nikitaserba.rsw.parser.repsonses;

import me.nikitaserba.rsw.parser.ParserSettings;

import java.util.Objects;
import java.util.Set;

/**
 * Class that represents a word, found while parsing
 * text using HomographParser.
 */
public class ParsedWordInText extends WordParsingResult {

    // position of the first character of the word in parsed text
    private final int beginPosition;
    private final int endPosition;

    /**
     * Create result of parsing a word in a text.
     *
     * @param parsedWord - word that was parsed.
     * @param possibleHomoforms - possible homoforms of the word. must be null if `hasHomoforms` is false.
     * @param usedSettings - settings used while parsing.
     * @param beginPosition - position of the first character of the word in parsed text.
     * @param endPosition - position of the last character of the word in parsed text.
     */
    public ParsedWordInText(String parsedWord, Set<String> possibleHomoforms,
                            ParserSettings usedSettings, int beginPosition, int endPosition) {
        super(parsedWord, true, possibleHomoforms, usedSettings);
        this.beginPosition = beginPosition;
        this.endPosition = endPosition;
    }

    /**
     * Create result of parsing a word in a text.
     *
     * @param parent - WordParsingResult class instance, all values of inherited fields will be copied.
     * @param beginPosition - position of the first character of the word in parsed text.
     * @param endPosition - position of the last character of the word in parsed text.
     */
    public ParsedWordInText(WordParsingResult parent, int beginPosition, int endPosition) {
        super(parent.getParsedWord(), parent.hasHomoforms(), parent.getPossibleHomoforms(), parent.getUsedSettings());
        assert parent.hasHomoforms();
        this.beginPosition = beginPosition;
        this.endPosition = endPosition;
    }

    public int getBeginPosition() {
        return beginPosition;
    }

    public int getEndPosition() {
        return endPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ParsedWordInText that = (ParsedWordInText) o;
        return getBeginPosition() == that.getBeginPosition() && getEndPosition() == that.getEndPosition();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getBeginPosition(), getEndPosition());
    }
}
