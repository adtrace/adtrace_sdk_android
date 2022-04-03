package io.adtrace.sdk;

import android.content.Context;
import android.provider.Settings.Secure;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (namini40@gmail.com) on August 2021.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2021.
 */


public class AndroidIdUtil {
    public static String getAndroidId(final Context context) {
        return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
    }
}
