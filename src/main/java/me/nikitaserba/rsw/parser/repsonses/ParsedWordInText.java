package me.nikitaserba.rsw.parser.repsonses;

import me.nikitaserba.rsw.parser.HomographParser;

import java.util.Set;

/**
 * Class that represents a word, found while parsing
 * text using HomographParser.
 */
public class ParsedWordInText extends WordParsingResult {

    // position of the first character of the word in parsed text
    private final int position;

    /**
     * Create result of parsing a word in a text.
     *
     * @param parsedWord - word that was parsed.
     * @param wordHasHomoforms - if the form have any homoforms depending on used setting.
     * @param possibleHomoforms - possible homoforms of the word. must be null if `hasHomoforms` is false.
     * @param usedSettings - settings used while parsing.
     * @param position - position of the first character of the word in parsed text.
     */
    public ParsedWordInText(String parsedWord, boolean wordHasHomoforms, Set<String> possibleHomoforms,
                            HomographParser.ParserSettings usedSettings, int position) {
        super(parsedWord, wordHasHomoforms, possibleHomoforms, usedSettings);
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
