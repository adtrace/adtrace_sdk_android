package io.adtrace.sdk;

import android.content.Context;
import android.provider.Settings.Secure;

/**
 * Created by Morteza KhosraviNejad on 06/01/19.
 */
public class AndroidIdUtil {
    public static String getAndroidId(final Context context) {
        return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
    }
}
