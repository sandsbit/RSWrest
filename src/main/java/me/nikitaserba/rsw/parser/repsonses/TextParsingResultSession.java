package me.nikitaserba.rsw.parser.repsonses;

import me.nikitaserba.rsw.parser.HomographParser;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TextParsingResultSession)) return false;
        if (!super.equals(o)) return false;
        TextParsingResultSession that = (TextParsingResultSession) o;
        return isChanged() == that.isChanged();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isChanged());
    }
}
