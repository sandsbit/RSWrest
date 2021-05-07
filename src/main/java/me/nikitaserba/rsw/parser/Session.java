package me.nikitaserba.rsw.parser;

import me.nikitaserba.rsw.parser.repsonses.TextParsingResult;
import me.nikitaserba.rsw.parser.repsonses.WordParsingResult;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

public final class Session {

    // STATIC PART (Storing sessions)

    // Time that session exist while it is not accessed.
    // It is NOT guaranteed that session will be removed at that time, but it MIGHT.
    private static final Duration SESSION_EXPIRE = Duration.ofMinutes(5);
    // timeout for threads that removes expired sessions
    private static final Duration RECHECK_EXPIRE_EVERY = Duration.ofMinutes(5);

    private static ConcurrentMap<String, Session> sessions = new ConcurrentHashMap<>();
    private static ConcurrentMap<String, LocalDateTime> sessionLastAccessed = new ConcurrentHashMap<>();

    private static ScheduledExecutorService expiredSessionsCleaner;

    /**
     * Starts thread that removes sessions that have expired.
     *
     * That thread WILL NOT stop after closing the application, so you MUST call `stopExpiredSessionsCleaner()` before
     * finishing application.
     *
     * @throws IllegalStateException if cleaner is already started.
     */
    public static void startExpiredSessionsCleaner() {
        if (expiredSessionsCleaner != null)
            throw new IllegalStateException("Expired sessions cleaner is already started.");
        expiredSessionsCleaner = Executors.newSingleThreadScheduledExecutor();
        expiredSessionsCleaner.scheduleAtFixedRate(() -> {
            Iterator<Map.Entry<String, LocalDateTime>> iterator = sessionLastAccessed.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, LocalDateTime> values = iterator.next();
                if (Duration.between(values.getValue(), LocalDateTime.now()).compareTo(SESSION_EXPIRE) > 0) {
                    sessions.remove(values.getKey());
                    iterator.remove();
                }
            }
        }, 0, RECHECK_EXPIRE_EVERY.toSeconds(), TimeUnit.SECONDS);
    }

    /**
     * Stops thread that cleans expired sessions.
     *
     * Thread must be started with `startExpiredSessionsCleaner` first. In almost all cases this method must be called
     * before finishing program.
     *
     * @throws InterruptedException if interrupted while waiting.
     * @throws IllegalStateException if cleaner is not running.
     */
    public static void stopExpiredSessionsCleaner() throws InterruptedException {
        if (expiredSessionsCleaner == null)
            throw new IllegalStateException("Expire sessions cleaner isn't running");

        expiredSessionsCleaner.shutdown();
        if (!expiredSessionsCleaner.awaitTermination(5, TimeUnit.SECONDS))
            expiredSessionsCleaner.shutdownNow();
    }

    /**
     * Creates new session.
     *
     * Token of new Session is saved in Session class.
     *
     * @return new Instance of Session class.
     */
    public static Session create() {
        String token;
        do {
            token = UUID.randomUUID().toString();
        } while (sessions.containsKey(token));
        Session newSession = new Session(token);
        sessions.put(token, newSession);
        sessionLastAccessed.put(token, LocalDateTime.now());

        return newSession;
    }

    /**
     * Deletes session by its token.
     *
     * @param token - token of session that must be deleted.
     * @return true if it was deleted, false if there is no sessions associated with given token.
     */
    public static boolean delete(String token) {
        sessionLastAccessed.remove(token);
        return sessions.remove(token) == null;
    }

    /**
     * Returns Session class by token.
     *
     * @param token - token to search for.
     * @return Session class that has the given token or null if there's no sessions with that token.
     */
    public static Session getByToken(String token) {
        sessionLastAccessed.put(token, LocalDateTime.now());
        return sessions.get(token);
    }

    // GENERAL PART (Session itself)

    private final String token;  // it will be used by client to identify session

    private HomographParser.ParserSettings settings;

    private String text;  // last text that was parsed in that session
    private TextParsingResult textParsingResultCache; // result of parsing `text`

    private String word;  // last word that was parsed in that session
    private Map<String, WordParsingResult> wordParsingResultCache;  // cache of parsing words in that session

    // it is private so session can't be created not in `create` method
    private Session(String token) {
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

    public HomographParser.ParserSettings getSettings() {
        return settings;
    }

    public void setSettings(HomographParser.ParserSettings settings) {
        this.settings = settings;
    }
}
