package me.nikitaserba.rsw.parser.repsonses;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

public final class Session {

    // STATIC PART (Storing sessions)

    private static final Duration SESSION_EXPIRE = Duration.ofMinutes(5);
    private static final Duration RECHECK_EXPIRE_EVERY = Duration.ofMinutes(5);

    private static ConcurrentMap<String, Session> sessions = new ConcurrentHashMap<>();
    private static ConcurrentMap<String, LocalDateTime> sessionLastAccessed = new ConcurrentHashMap<>();

    private static ScheduledExecutorService expiredSessionsCleaner;

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

    public static void stopExpiredSessionsCleaner() throws InterruptedException {
        if (expiredSessionsCleaner == null)
            throw new IllegalStateException("Expire sessions cleaner isn't running");

        expiredSessionsCleaner.shutdown();
        if (!expiredSessionsCleaner.awaitTermination(5, TimeUnit.SECONDS))
            expiredSessionsCleaner.shutdownNow();
    }

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

    public static boolean delete(String token) {
        sessionLastAccessed.remove(token);
        return sessions.remove(token) == null;
    }

    public static Session getByToken(String token) {
        sessionLastAccessed.put(token, LocalDateTime.now());
        return sessions.get(token);
    }

    // GENERAL PART (Session itself)

    private final String token;

    private String text;
    private TextParsingResult textParsingResultCache;

    private String word;
    private Map<String, WordParsingResult> wordParsingResultCache;

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
}
