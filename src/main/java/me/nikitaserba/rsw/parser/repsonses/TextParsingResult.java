package me.nikitaserba.rsw.parser.repsonses;

import me.nikitaserba.rsw.parser.ParserSettings;

import java.util.List;
import java.util.Objects;

/**
 * Class that stores result of parsing text by `HomographParser`.
 */
public final class TextParsingResult extends ParsingResult {

    private final String text;  // original text that was checked

    private final long totalNumberOfParsedWords;  // size of 'wordsThatHaveHomoforms'
    private final boolean foundAnyWordsThatHaveHomoforms;  // True if 'wordsThatHaveHomoforms' is not empty
    private final List<ParsedWordInText> wordsThatHaveHomoforms;

    /**
     * Create new result of parsing text.
     *
     * @param text - text that was parsed.
     * @param totalNumberOfParsedWords - size of `wordsThatHaveHomoforms`
     * @param wordsThatHaveHomoforms - array of `ParsedWordInText` instances, that represent words in text
     *                                 that have homoforms
     * @param usedSettings - settings used while parsing
     */
    public TextParsingResult(String text, long totalNumberOfParsedWords, List<ParsedWordInText> wordsThatHaveHomoforms, ParserSettings usedSettings) {
        super(usedSettings);
        this.text = text;
        this.totalNumberOfParsedWords = totalNumberOfParsedWords;
        this.foundAnyWordsThatHaveHomoforms = (totalNumberOfParsedWords != 0);
        this.wordsThatHaveHomoforms = foundAnyWordsThatHaveHomoforms ? wordsThatHaveHomoforms : null;
    }

    /**
     * Create new result of parsing text.
     *
     * Same as another constructor, but `totalNumberOfParsedWords` will be calculated as `wordsThatHaveHomoforms.size()`.
     *
     * @param text - text that was parsed.
     * @param wordsThatHaveHomoforms - array of `ParsedWordInText` instances, that represent words in text
     *                                 that have homoforms
     * @param usedSettings - settings used while parsing will
     */
    public TextParsingResult(String text, List<ParsedWordInText> wordsThatHaveHomoforms, ParserSettings usedSettings) {
        super(usedSettings);
        this.text = text;
        this.totalNumberOfParsedWords = wordsThatHaveHomoforms.size();
        this.foundAnyWordsThatHaveHomoforms = (totalNumberOfParsedWords != 0);
        this.wordsThatHaveHomoforms = foundAnyWordsThatHaveHomoforms ? wordsThatHaveHomoforms : null;
    }

    public String getText() {
        return text;
    }

    public long getTotalNumberOfParsedWords() {
        return totalNumberOfParsedWords;
    }

    public boolean haveFoundAnyWordsThatHaveHomoforms() {
        return foundAnyWordsThatHaveHomoforms;
    }

    public List<ParsedWordInText> getWordsThatHaveHomoforms() {
        return wordsThatHaveHomoforms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextParsingResult result = (TextParsingResult) o;
        return getTotalNumberOfParsedWords() == result.getTotalNumberOfParsedWords() && foundAnyWordsThatHaveHomoforms == result.foundAnyWordsThatHaveHomoforms && getText().equals(result.getText()) && Objects.equals(getWordsThatHaveHomoforms(), result.getWordsThatHaveHomoforms());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getText(), getTotalNumberOfParsedWords(), foundAnyWordsThatHaveHomoforms, getWordsThatHaveHomoforms());
    }
}
