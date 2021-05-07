package me.nikitaserba.rsw.parser.repsonses;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class Session {

    // STATIC PART (Storing sessions)

    private static Map<String, Session> sessions;

    public static Session create() {
        String token;
        do {
            token = UUID.randomUUID().toString();
        } while (sessions.containsKey(token));
        Session newSession = new Session(token);
        sessions.put(token, newSession);

        return newSession;
    }

    public static boolean delete(String token) {
        return sessions.remove(token) == null;
    }

    public static Session getByToken(String token) {
        return sessions.get(token);
    }

    // GENERAL PART (Session itself)

    private final String token;

    private String text;
    private TextParsingResult textParsingResultCache;

    private String word;
    private Map<String, WordParsingResult> wordParsingResultCache;

    public Session(String token) {
        this.token = token;
        wordParsingResultCache = new HashMap<>();
    }

    public String getToken() {
        return token;
    }

    public String getText() {
        return text;
    }

    public TextParsingResult getTextParsingResultCache() {
        return textParsingResultCache;
    }

    public String getWord() {
        return word;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTextParsingResultCache(TextParsingResult textParsingResultCache) {
        this.textParsingResultCache = textParsingResultCache;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void addWordParsingResultToCache(String word, WordParsingResult result) {
        wordParsingResultCache.put(word, result);
    }

    public WordParsingResult getWordParsingResultFromCache(String word) {
        return wordParsingResultCache.get(word);
    }
}
