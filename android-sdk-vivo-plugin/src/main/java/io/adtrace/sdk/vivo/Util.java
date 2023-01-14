package io.adtrace.sdk.vivo;

import android.content.Context;

import io.adtrace.sdk.ILogger;
import io.adtrace.sdk.ReferrerDetails;

public class Util {
   public synchronized static ReferrerDetails getVivoInstallReferrerDetails(Context context, ILogger logger) {
      if (!AdTraceVivoReferrer.shouldReadVivoReferrer) {
         return null;
      }

      return VivoReferrerClient.getReferrer(context, logger);
   }
}
