package io.adtrace.sdk.samsung;

import android.content.Context;

import io.adtrace.sdk.ILogger;
import io.adtrace.sdk.ReferrerDetails;


public class Util {
   public synchronized static ReferrerDetails getSamsungInstallReferrerDetails(Context context, ILogger logger) {
      if (!AdTraceSamsungReferrer.shouldReadSamsungReferrer) {
         return null;
      }

      com.sec.android.app.samsungapps.installreferrer.api.ReferrerDetails referrerDetails =
              SamsungReferrerClient.getReferrer(context, logger, 3000);
      if (referrerDetails == null) {
         return null;
      }

      return new ReferrerDetails(referrerDetails.getInstallReferrer(),
                                 referrerDetails.getReferrerClickTimestampSeconds(),
                                 referrerDetails.getInstallBeginTimestampSeconds());
   }
}
