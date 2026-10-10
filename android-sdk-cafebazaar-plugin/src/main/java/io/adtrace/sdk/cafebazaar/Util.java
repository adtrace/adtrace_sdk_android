package io.adtrace.sdk.cafebazaar;

import android.content.Context;

import io.adtrace.sdk.ILogger;
import io.adtrace.sdk.ReferrerDetails;

public class Util {
    public synchronized static ReferrerDetails getCafeBazaarInstallReferrerDetails(
            Context context, ILogger logger) {
        if (!AdTraceCafeBazaarReferrer.shouldReadCafeBazaarReferrer) {
            return null;
        }

        com.cafebazaar.referrersdk.model.ReferrerDetails referrerDetails =
                CafeBazaarReferrerClientHelper.getReferrer(context, logger, 3000);
        if (referrerDetails == null) {
            return null;
        }

        // Cafe Bazaar timestamps are milliseconds; AdTrace expects seconds.
        long clickTimeSeconds = referrerDetails.getReferrerClickTimestampMilliseconds() / 1000L;
        long installBeginSeconds = referrerDetails.getInstallBeginTimestampMilliseconds() / 1000L;

        return new ReferrerDetails(
                referrerDetails.getReferrer(),
                clickTimeSeconds,
                installBeginSeconds,
                -1,
                -1,
                referrerDetails.getAppVersion(),
                null,
                null
        );
    }
}
