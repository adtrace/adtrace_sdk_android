package io.adtrace.sdk;

import android.net.Uri;


/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (github.com/namini40) on April 2022.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2022.
 */


public interface OnDeeplinkResponseListener {
    boolean launchReceivedDeeplink(Uri deeplink);
}
