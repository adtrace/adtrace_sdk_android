package io.adtrace.examples;

import io.adtrace.sdk.ILogger;
import io.adtrace.sdk.LogLevel;
import io.adtrace.sdk.Logger;
import io.adtrace.sdk.Util;

/**
 * Forwards AdTrace SDK logs to Logcat and mirrors them in {@link AdTraceLogStore}
 * so developers can inspect sent requests inside the example app.
 *
 * Install before creating {@link io.adtrace.sdk.AdTraceConfig}:
 * <pre>
 * AdTraceFactory.setLogger(new InAppLogger());
 * </pre>
 */
public class InAppLogger implements ILogger {

    private final Logger delegate = new Logger();

    @Override
    public void setLogLevel(LogLevel logLevel, boolean isProductionEnvironment) {
        delegate.setLogLevel(logLevel, isProductionEnvironment);
    }

    @Override
    public void setLogLevelString(String logLevelString, boolean isProductionEnvironment) {
        delegate.setLogLevelString(logLevelString, isProductionEnvironment);
    }

    @Override
    public void verbose(String message, Object... parameters) {
        delegate.verbose(message, parameters);
        mirror("V", message, parameters);
    }

    @Override
    public void debug(String message, Object... parameters) {
        delegate.debug(message, parameters);
        mirror("D", message, parameters);
    }

    @Override
    public void info(String message, Object... parameters) {
        delegate.info(message, parameters);
        mirror("I", message, parameters);
    }

    @Override
    public void warn(String message, Object... parameters) {
        delegate.warn(message, parameters);
        mirror("W", message, parameters);
    }

    @Override
    public void warnInProduction(String message, Object... parameters) {
        delegate.warnInProduction(message, parameters);
        mirror("W", message, parameters);
    }

    @Override
    public void error(String message, Object... parameters) {
        delegate.error(message, parameters);
        mirror("E", message, parameters);
    }

    @Override
    public void Assert(String message, Object... parameters) {
        delegate.Assert(message, parameters);
        mirror("A", message, parameters);
    }

    @Override
    public void lockLogLevel() {
        delegate.lockLogLevel();
    }

    private void mirror(String level, String message, Object... parameters) {
        if (message == null) {
            return;
        }
        String formatted;
        try {
            formatted = Util.formatString(message, parameters);
        } catch (Exception e) {
            formatted = message;
        }
        AdTraceLogStore.append("[" + level + "] " + formatted);
    }
}
