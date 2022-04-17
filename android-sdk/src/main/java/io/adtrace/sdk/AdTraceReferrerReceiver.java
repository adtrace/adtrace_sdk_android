package io.adtrace.sdk;

import static io.adtrace.sdk.Constants.REFERRER;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (github.com/namini40) on April 2022.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2022.
 */


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
