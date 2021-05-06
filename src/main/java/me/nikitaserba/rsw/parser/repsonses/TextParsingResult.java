package me.nikitaserba.rsw.parser.repsonses;

import me.nikitaserba.rsw.parser.HomographParser;

import java.util.List;

/**
 * Class that stores result of parsing text by `HomographParser`.
 */
public final class TextParsingResult {

    private final String text;  // original text that was checked

    private final long totalNumberOfParsedWords;  // size of 'wordsThatHaveHomoforms'
    private final boolean foundAnyWordsThatHaveHomoforms;  // True if 'wordsThatHaveHomoforms' is not empty
    private final List<ParsedWordInText> wordsThatHaveHomoforms;

    private final HomographParser.ParserSettings usedSettings;  // setting that used parser while parsing

    /**
     * Create new result of parsing text.
     *
     * @param text - text that was parsed.
     * @param totalNumberOfParsedWords - size of `wordsThatHaveHomoforms`
     * @param wordsThatHaveHomoforms - array of `ParsedWordInText` instances, that represent words in text
     *                                 that have homoforms
     * @param usedSettings - settings used while parsing
     */
    public TextParsingResult(String text, long totalNumberOfParsedWords, List<ParsedWordInText> wordsThatHaveHomoforms, HomographParser.ParserSettings usedSettings) {
        this.text = text;
        this.totalNumberOfParsedWords = totalNumberOfParsedWords;
        this.foundAnyWordsThatHaveHomoforms = (totalNumberOfParsedWords != 0);
        this.wordsThatHaveHomoforms = wordsThatHaveHomoforms;
        this.usedSettings = usedSettings;
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
    public TextParsingResult(String text, List<ParsedWordInText> wordsThatHaveHomoforms, HomographParser.ParserSettings usedSettings) {
        this.text = text;
        this.totalNumberOfParsedWords = wordsThatHaveHomoforms.size();
        this.foundAnyWordsThatHaveHomoforms = (totalNumberOfParsedWords != 0);
        this.wordsThatHaveHomoforms = wordsThatHaveHomoforms;
        this.usedSettings = usedSettings;
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

    public HomographParser.ParserSettings getUsedSettings() {
        return usedSettings;
    }
}
