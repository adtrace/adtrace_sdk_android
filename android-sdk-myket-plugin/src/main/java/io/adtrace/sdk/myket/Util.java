package io.adtrace.sdk.myket;

import android.content.Context;

import io.adtrace.sdk.ILogger;
import io.adtrace.sdk.ReferrerDetails;

public class Util {
    public synchronized static ReferrerDetails getMyketInstallReferrerDetails(Context context, ILogger logger) {
        if (!AdTraceMyketReferrer.shouldReadMyketReferrer) {
            return null;
        }

        ir.myket.referrersdk.api.ReferrerDetails referrerDetails =
                MyketReferrerClientHelper.getReferrer(context, logger, 3000);
        if (referrerDetails == null) {
            return null;
        }

        return new ReferrerDetails(
                referrerDetails.getInstallReferrer(),
                referrerDetails.getReferrerClickTimestampSeconds(),
                referrerDetails.getInstallBeginTimestampSeconds()
        );
    }
}
