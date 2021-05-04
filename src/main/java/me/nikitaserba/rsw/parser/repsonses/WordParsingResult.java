package me.nikitaserba.rsw.parser.repsonses;

import me.nikitaserba.rsw.parser.HomographParser;

import java.util.Set;

/**
 * Class that stores result of parsing word by `HomographParser`.
 */
public final class WordParsingResult {

    private final String parsedWord;  // original word that was checked
    private final boolean wordHasHomoforms;
    private final Set<String> possibleHomoforms;
    private final HomographParser.ParserSettings usedSettings;  // setting that used parser while parsing

    /**
     * Create result of parsing a word.
     *
     * @param parsedWord - word that was parsed.
     * @param wordHasHomoforms - if the form have any homoforms depending on used setting.
     * @param possibleHomoforms - possible homoforms of the word. must be null if `hasHomoforms` is false.
     * @param usedSettings - settings used while parsing.
     */
    public WordParsingResult(String parsedWord, boolean wordHasHomoforms, Set<String> possibleHomoforms, HomographParser.ParserSettings usedSettings) {
        this.parsedWord = parsedWord;
        this.wordHasHomoforms = wordHasHomoforms;
        this.possibleHomoforms = possibleHomoforms;
        this.usedSettings = usedSettings;
    }

    public String getParsedWord() {
        return parsedWord;
    }

    public boolean hasHomoforms() {
        return wordHasHomoforms;
    }

    public Set<String> getPossibleHomoforms() {
        return possibleHomoforms;
    }

    public HomographParser.ParserSettings getUsedSettings() {
        return usedSettings;
    }
}
