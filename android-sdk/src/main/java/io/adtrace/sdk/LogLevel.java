package io.adtrace.sdk;

import android.util.Log;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (github.com/namini40) on April 2022.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2022.
 */

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
