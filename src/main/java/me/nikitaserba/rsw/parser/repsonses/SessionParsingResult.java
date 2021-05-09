package me.nikitaserba.rsw.parser.repsonses;

import me.nikitaserba.rsw.parser.HomographParser;

/**
 * This interface must be implemented by all session parsing results.
 *
 * Class that implements this MUST be inherited from T.
 *
 * @param <T> - non-session version of parsing result.
 */
public interface SessionParsingResult<T> {

    boolean isChanged();

    /**
     * Returns version of result without `changed` field.
     *
     * @deprecated
     * This method was created to left compatibility with Pair<T, Boolean> class. Use cast to T instead. WILL be removed
     * in future versions.
     * @return version of result without `changed` field.
     */
    @Deprecated
    default T getFirst() {
        return (T) this;
    }

    /**
     * Tells if parsing result has changed since last request.
     *
     * @deprecated
     * This method was created to left compatibility with Pair<T, Boolean> class. Use isChanged() instead. WILL be
     * removed in future versions.
     * @return CHANGED if parsing result has changed sine last request, otherwise NOT_CHANGED.
     */
    @Deprecated
    default HomographParser.ChangeState getSecond() {
        return isChanged() ? HomographParser.ChangeState.CHANGED : HomographParser.ChangeState.NOT_CHANGED;
    }

}
