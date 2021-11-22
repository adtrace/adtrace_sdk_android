package io.adtrace.sdk;

import static io.adtrace.sdk.Constants.REFERRER;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (namini40@gmail.com) on August 2021.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2021.
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
