package io.adtrace.sdk;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (namini40@gmail.com) on August 2021.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2021.
 */


public interface ILogger {
    void setLogLevel(LogLevel logLevel, boolean isProductionEnvironment);

    void setLogLevelString(String logLevelString, boolean isProductionEnvironment);

    void verbose(String message, Object... parameters);

    void debug(String message, Object... parameters);

    void info(String message, Object... parameters);

    void warn(String message, Object... parameters);
    void warnInProduction(String message, Object... parameters);

    void error(String message, Object... parameters);

    void Assert(String message, Object... parameters);

    void lockLogLevel();
}
