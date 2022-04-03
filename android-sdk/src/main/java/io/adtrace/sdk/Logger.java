

package io.adtrace.sdk;

import static io.adtrace.sdk.Constants.LOGTAG;

import android.util.Log;

import java.util.Arrays;
import java.util.Locale;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (namini40@gmail.com) on August 2021.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2021.
 */


public class Logger implements ILogger {

    private LogLevel logLevel;
    private boolean logLevelLocked;
    private boolean isProductionEnvironment;
    private static String formatErrorMessage = "Error formating log message: %s, with params: %s";

    public Logger() {
        isProductionEnvironment = false;
        logLevelLocked = false;
        setLogLevel(LogLevel.INFO, isProductionEnvironment);
    }

    @Override
    public void setLogLevel(LogLevel logLevel, boolean isProductionEnvironment) {
        if (logLevelLocked) {
            return;
        }
        this.logLevel = logLevel;
        this.isProductionEnvironment = isProductionEnvironment;
    }

    @Override
    public void setLogLevelString(String logLevelString, boolean isProductionEnvironment) {
        if (null != logLevelString) {
            try {
                setLogLevel(LogLevel.valueOf(logLevelString.toUpperCase(Locale.US)), isProductionEnvironment);
            } catch (IllegalArgumentException iae) {
                error("Malformed logLevel '%s', falling back to 'info'", logLevelString);
            }
        }
    }

    @Override
    public void verbose(String message, Object... parameters) {
        if (isProductionEnvironment) {
            return;
        }
        if (logLevel.androidLogLevel <= Log.VERBOSE) {
            try {
                Log.v(LOGTAG, Util.formatString(message, parameters));
            } catch (Exception e) {
                Log.e(LOGTAG, Util.formatString(formatErrorMessage, message, Arrays.toString(parameters)));
            }
        }
    }

    @Override
    public void debug(String message, Object... parameters) {
        if (isProductionEnvironment) {
            return;
        }
        if (logLevel.androidLogLevel <= Log.DEBUG) {
            try {
                Log.d(LOGTAG, Util.formatString(message, parameters));
            } catch (Exception e) {
                Log.e(LOGTAG, Util.formatString(formatErrorMessage, message, Arrays.toString(parameters)));
            }
        }
    }

    @Override
    public void info(String message, Object... parameters) {
        if (isProductionEnvironment) {
            return;
        }
        if (logLevel.androidLogLevel <= Log.INFO) {
            try {
                Log.i(LOGTAG, Util.formatString(message, parameters));
            } catch (Exception e) {
                Log.e(LOGTAG, Util.formatString(formatErrorMessage, message, Arrays.toString(parameters)));
            }
        }
    }

    @Override
    public void warn(String message, Object... parameters) {
        if (isProductionEnvironment) {
            return;
        }
        if (logLevel.androidLogLevel <= Log.WARN) {
            try {
                Log.w(LOGTAG, Util.formatString(message, parameters));
            } catch (Exception e) {
                Log.e(LOGTAG, Util.formatString(formatErrorMessage, message, Arrays.toString(parameters)));
            }
        }
    }

    @Override
    public void warnInProduction(String message, Object... parameters) {
        if (logLevel.androidLogLevel <= Log.WARN) {
            try {
                Log.w(LOGTAG, Util.formatString(message, parameters));
            } catch (Exception e) {
                Log.e(LOGTAG, Util.formatString(formatErrorMessage, message, Arrays.toString(parameters)));
            }
        }
    }


    @Override
    public void error(String message, Object... parameters) {
        if (isProductionEnvironment) {
            return;
        }
        if (logLevel.androidLogLevel <= Log.ERROR) {
            try {
                Log.e(LOGTAG, Util.formatString(message, parameters));
            } catch (Exception e) {
                Log.e(LOGTAG, Util.formatString(formatErrorMessage, message, Arrays.toString(parameters)));
            }
        }
    }

    @Override
    public void Assert(String message, Object... parameters) {
        if (isProductionEnvironment) {
            return;
        }
        if(logLevel.androidLogLevel <= Log.ASSERT) {
            try {
                Log.println(Log.ASSERT, LOGTAG, Util.formatString(message, parameters));
            } catch (Exception e) {
                Log.e(LOGTAG, Util.formatString(formatErrorMessage, message, Arrays.toString(parameters)));
            }
        }
    }

    @Override
    public void lockLogLevel() {
        logLevelLocked = true;
    }
}
