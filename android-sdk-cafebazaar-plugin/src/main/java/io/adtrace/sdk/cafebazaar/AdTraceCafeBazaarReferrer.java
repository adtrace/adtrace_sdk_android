package io.adtrace.sdk.cafebazaar;

import android.content.Context;

public class AdTraceCafeBazaarReferrer {

    static boolean shouldReadCafeBazaarReferrer = true;

    public static void readCafeBazaarReferrer(Context context) {
        shouldReadCafeBazaarReferrer = true;
    }

    public static void doNotReadCafeBazaarReferrer() {
        shouldReadCafeBazaarReferrer = false;
    }
}
