package io.adtrace.sdk;

import android.net.Uri;

/**
 * Created by Morteza KhosraviNejad on 06/01/19.
 */
public interface OnDeeplinkResponseListener {
    boolean launchReceivedDeeplink(Uri deeplink);
}
