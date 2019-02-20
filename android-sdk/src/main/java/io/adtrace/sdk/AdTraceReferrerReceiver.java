package io.adtrace.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static io.adtrace.sdk.Constants.REFERRER;

/**
 * Created by Morteza KhosraviNejad on 06/01/19.
 */
// support multiple BroadcastReceivers for the INSTALL_REFERRER:
// https://appington.wordpress.com/2012/08/01/giving-credit-for-android-app-installs/

public class AdTraceReferrerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String rawReferrer = intent.getStringExtra(REFERRER);

        if (null == rawReferrer) {
            return;
        }

        AdTrace.getDefaultInstance().sendReferrer(rawReferrer, context);
    }
}
