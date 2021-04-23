package me.nikitaserba.rsw.parser.repsonses;

import me.nikitaserba.rsw.parser.HomographParser;

import java.util.List;
import java.util.Map;

public class TextParsingResult {

    private final String text;

    private final long totalNumberOfParsedWords;
    private final boolean foundAnyWordsThatHaveHomoforms;
    private final List<Map<String, Long>> wordsThatHaveHomoforms;

    private final HomographParser.ParserSettings usedSettings;

    public TextParsingResult(String text, long totalNumberOfParsedWords, List<Map<String, Long>> wordsThatHaveHomoforms, HomographParser.ParserSettings usedSettings) {
        this.text = text;
        this.totalNumberOfParsedWords = totalNumberOfParsedWords;
        this.foundAnyWordsThatHaveHomoforms = (totalNumberOfParsedWords != 0);
        this.wordsThatHaveHomoforms = wordsThatHaveHomoforms;
        this.usedSettings = usedSettings;
    }

    public TextParsingResult(String text, List<Map<String, Long>> wordsThatHaveHomoforms, HomographParser.ParserSettings usedSettings) {
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

    public List<Map<String, Long>> getWordsThatHaveHomoforms() {
        return wordsThatHaveHomoforms;
    }

    public HomographParser.ParserSettings getUsedSettings() {
        return usedSettings;
    }
}
