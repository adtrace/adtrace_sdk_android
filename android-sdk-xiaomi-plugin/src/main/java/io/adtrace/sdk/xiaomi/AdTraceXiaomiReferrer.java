package io.adtrace.sdk.xiaomi;

import android.content.Context;

public class AdTraceXiaomiReferrer {

   static boolean shouldReadXiaomiReferrer = true;

   public static void readXiaomiReferrer(Context context) {
      shouldReadXiaomiReferrer = true;
   }

   public static void doNotReadXiaomiReferrer() {
      shouldReadXiaomiReferrer = false;
   }
}
