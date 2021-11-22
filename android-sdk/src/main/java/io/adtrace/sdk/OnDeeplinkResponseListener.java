package io.adtrace.sdk;

import android.net.Uri;


/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (namini40@gmail.com) on August 2021.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2021.
 */


public interface OnDeeplinkResponseListener {
    boolean launchReceivedDeeplink(Uri deeplink);
}
