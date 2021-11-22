package io.adtrace.sdk;

import static io.adtrace.sdk.Constants.EXTRA_SYSTEM_INSTALLER_REFERRER;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (namini40@gmail.com) on August 2021.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2021.
 */


public class AdTracePreinstallReferrerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }

        String referrer = intent.getStringExtra(EXTRA_SYSTEM_INSTALLER_REFERRER);
        if (referrer == null) {
            return;
        }

        AdTrace.getDefaultInstance().sendPreinstallReferrer(referrer, context);
    }
}
