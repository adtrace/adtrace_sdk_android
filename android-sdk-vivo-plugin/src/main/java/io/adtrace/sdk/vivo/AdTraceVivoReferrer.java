package io.adtrace.sdk.vivo;

import android.content.Context;

public class AdTraceVivoReferrer {

   static boolean shouldReadVivoReferrer = true;

   public static void readVivoReferrer(Context context) {
      shouldReadVivoReferrer = true;
   }

   public static void doNotReadVivoReferrer() {
      shouldReadVivoReferrer = false;
   }
}
