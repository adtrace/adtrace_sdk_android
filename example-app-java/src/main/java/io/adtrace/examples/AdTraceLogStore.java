package io.adtrace.examples;

import android.os.Handler;
import android.os.Looper;

import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * In-memory log buffer for AdTrace SDK output shown in the example app UI.
 *
 * For production apps, use Logcat instead — this is a demo helper only.
 */
public final class AdTraceLogStore {

    public interface Listener {
        void onLogsUpdated(String text);
    }

    private static final int MAX_LINES = 300;

    private static final Handler MAIN_HANDLER = new Handler(Looper.getMainLooper());
    private static final SimpleDateFormat TIMESTAMP_FORMAT =
            new SimpleDateFormat("HH:mm:ss.SSS", Locale.US);
    private static final ArrayDeque<String> LINES = new ArrayDeque<>(MAX_LINES);
    private static final List<Listener> LISTENERS = new ArrayList<>();
    private static final Object LOCK = new Object();

    private AdTraceLogStore() {
    }

    public static void append(String message) {
        String entry = TIMESTAMP_FORMAT.format(new Date()) + "  " + message;
        String snapshot;
        synchronized (LOCK) {
            LINES.addLast(entry);
            while (LINES.size() > MAX_LINES) {
                LINES.removeFirst();
            }
            snapshot = joinLines();
        }
        notifyListeners(snapshot);
    }

    public static void clear() {
        String snapshot;
        synchronized (LOCK) {
            LINES.clear();
            snapshot = "";
        }
        notifyListeners(snapshot);
    }

    public static String snapshot() {
        synchronized (LOCK) {
            return joinLines();
        }
    }

    public static void addListener(Listener listener) {
        synchronized (LOCK) {
            LISTENERS.add(listener);
        }
        listener.onLogsUpdated(snapshot());
    }

    public static void removeListener(Listener listener) {
        synchronized (LOCK) {
            LISTENERS.remove(listener);
        }
    }

    private static String joinLines() {
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (String line : LINES) {
            if (!first) {
                builder.append('\n');
            }
            builder.append(line);
            first = false;
        }
        return builder.toString();
    }

    private static void notifyListeners(final String text) {
        MAIN_HANDLER.post(new Runnable() {
            @Override
            public void run() {
                List<Listener> copy;
                synchronized (LOCK) {
                    copy = new ArrayList<>(LISTENERS);
                }
                for (Listener listener : copy) {
                    listener.onLogsUpdated(text);
                }
            }
        });
    }
}
