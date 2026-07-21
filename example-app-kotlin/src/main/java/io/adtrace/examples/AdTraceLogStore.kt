package io.adtrace.examples

import android.os.Handler
import android.os.Looper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * In-memory log buffer for AdTrace SDK output shown in the example app UI.
 *
 * For production apps, use Logcat instead — this is a demo helper only.
 */
object AdTraceLogStore {

    private const val MAX_LINES = 300

    private val mainHandler = Handler(Looper.getMainLooper())
    private val timestampFormat = SimpleDateFormat("HH:mm:ss.SSS", Locale.US)
    private val lines = ArrayDeque<String>(MAX_LINES)
    private val listeners = mutableSetOf<(String) -> Unit>()
    private val lock = Any()

    fun append(message: String) {
        val entry = "${timestampFormat.format(Date())}  $message"
        val snapshot = synchronized(lock) {
            lines.addLast(entry)
            while (lines.size > MAX_LINES) {
                lines.removeFirst()
            }
            lines.joinToString("\n")
        }
        notifyListeners(snapshot)
    }

    fun clear() {
        val snapshot = synchronized(lock) {
            lines.clear()
            ""
        }
        notifyListeners(snapshot)
    }

    fun snapshot(): String = synchronized(lock) {
        lines.joinToString("\n")
    }

    fun addListener(listener: (String) -> Unit) {
        synchronized(lock) {
            listeners.add(listener)
        }
        listener(snapshot())
    }

    fun removeListener(listener: (String) -> Unit) {
        synchronized(lock) {
            listeners.remove(listener)
        }
    }

    private fun notifyListeners(text: String) {
        mainHandler.post {
            synchronized(lock) {
                listeners.forEach { it(text) }
            }
        }
    }
}
