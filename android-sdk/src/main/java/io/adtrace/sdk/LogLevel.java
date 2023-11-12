package io.adtrace.sdk;

import android.util.Log;

public enum LogLevel {
    VERBOSE(Log.VERBOSE), DEBUG(Log.DEBUG), INFO(Log.INFO), WARN(Log.WARN), ERROR(Log.ERROR), ASSERT(Log.ASSERT), SUPRESS(8);
    final int androidLogLevel;

    LogLevel(final int androidLogLevel) {
        this.androidLogLevel = androidLogLevel;
    }

    public int getAndroidLogLevel() {
        return androidLogLevel;
    }
}
