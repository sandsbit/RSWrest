package me.nikitaserba.rsw.parser.repsonses;

import me.nikitaserba.rsw.parser.HomographParser;

public class TextParsingResultSession extends TextParsingResult implements SessionParsingResult<TextParsingResult> {

    private final boolean changed;

    /**
     * Create new result of parsing text.
     *
     * @param result - TextParsingResult to copy parsing result from.
     * @param changed - states if parsing result has changed since last request.
     */
    public TextParsingResultSession(TextParsingResult result, HomographParser.ChangeState changed) {
        super(result.getText(), result.getTotalNumberOfParsedWords(), result.getWordsThatHaveHomoforms(), result.getUsedSettings());
        this.changed = changed == HomographParser.ChangeState.CHANGED;
    }

    @Override
    public boolean isChanged() {
        return changed;
    }
}
