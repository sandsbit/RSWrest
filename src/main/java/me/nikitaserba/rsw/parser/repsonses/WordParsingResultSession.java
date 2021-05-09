package me.nikitaserba.rsw.parser.repsonses;

import me.nikitaserba.rsw.parser.HomographParser;

public class WordParsingResultSession extends WordParsingResult implements SessionParsingResult<WordParsingResult> {

    private final boolean changed;

    /**
     * Create result of parsing a word.
     *
     * @param result - WordParsingResult to copy parsing result from.
     * @param changed - states if parsing result has changed since last request.
     */
    public WordParsingResultSession(WordParsingResult result, HomographParser.ChangeState changed) {
        super(result.getParsedWord(), result.hasHomoforms(), result.getPossibleHomoforms(), result.getUsedSettings());
        this.changed = changed == HomographParser.ChangeState.CHANGED;
    }

    @Override
    public boolean isChanged() {
        return changed;
    }


}
