package me.nikitaserba.rsw.parser.repsonses;

import me.nikitaserba.rsw.parser.HomographParser;

public class WordParsingResult {

    private final String parsedWord;
    private final boolean hasHomoforms;
    private final HomographParser.ParserSettings usedSettings;

    public WordParsingResult(String parsedWord, boolean hasHomoforms, HomographParser.ParserSettings usedSettings) {
        this.parsedWord = parsedWord;
        this.hasHomoforms = hasHomoforms;
        this.usedSettings = usedSettings;
    }

    public String getParsedWord() {
        return parsedWord;
    }

    public boolean isHavingHomoforms() {
        return hasHomoforms;
    }

    public HomographParser.ParserSettings getUsedSettings() {
        return usedSettings;
    }
}
