package io.adtrace.sdk.myket;

import android.content.Context;

public class AdTraceMyketReferrer {

    static boolean shouldReadMyketReferrer = true;

    public static void readMyketReferrer(Context context) {
        shouldReadMyketReferrer = true;
    }

    public static void doNotReadMyketReferrer() {
        shouldReadMyketReferrer = false;
    }
}
