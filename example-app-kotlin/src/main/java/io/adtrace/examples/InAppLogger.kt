package io.adtrace.examples

import io.adtrace.sdk.ILogger
import io.adtrace.sdk.LogLevel
import io.adtrace.sdk.Logger
import io.adtrace.sdk.Util

/**
 * Forwards AdTrace SDK logs to Logcat and mirrors them in [AdTraceLogStore]
 * so developers can inspect sent requests inside the example app.
 *
 * Install before creating [io.adtrace.sdk.AdTraceConfig]:
 * ```
 * AdTraceFactory.setLogger(InAppLogger())
 * ```
 */
class InAppLogger : ILogger {

    private val delegate = Logger()

    override fun setLogLevel(logLevel: LogLevel, isProductionEnvironment: Boolean) {
        delegate.setLogLevel(logLevel, isProductionEnvironment)
    }

    override fun setLogLevelString(logLevelString: String?, isProductionEnvironment: Boolean) {
        delegate.setLogLevelString(logLevelString, isProductionEnvironment)
    }

    override fun verbose(message: String?, vararg parameters: Any?) {
        delegate.verbose(message, *parameters)
        mirror("V", message, parameters)
    }

    override fun debug(message: String?, vararg parameters: Any?) {
        delegate.debug(message, *parameters)
        mirror("D", message, parameters)
    }

    override fun info(message: String?, vararg parameters: Any?) {
        delegate.info(message, *parameters)
        mirror("I", message, parameters)
    }

    override fun warn(message: String?, vararg parameters: Any?) {
        delegate.warn(message, *parameters)
        mirror("W", message, parameters)
    }

    override fun warnInProduction(message: String?, vararg parameters: Any?) {
        delegate.warnInProduction(message, *parameters)
        mirror("W", message, parameters)
    }

    override fun error(message: String?, vararg parameters: Any?) {
        delegate.error(message, *parameters)
        mirror("E", message, parameters)
    }

    override fun Assert(message: String?, vararg parameters: Any?) {
        delegate.Assert(message, *parameters)
        mirror("A", message, parameters)
    }

    override fun lockLogLevel() {
        delegate.lockLogLevel()
    }

    private fun mirror(level: String, message: String?, parameters: Array<out Any?>) {
        if (message == null) {
            return
        }
        val formatted = try {
            Util.formatString(message, *parameters)
        } catch (e: Exception) {
            message
        }
        AdTraceLogStore.append("[$level] $formatted")
    }
}
