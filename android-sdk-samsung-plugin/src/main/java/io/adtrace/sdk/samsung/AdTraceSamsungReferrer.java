package io.adtrace.sdk.samsung;

import android.content.Context;

public class AdTraceSamsungReferrer {

   static boolean shouldReadSamsungReferrer = true;

   public static void readSamsungReferrer(Context context) {
      shouldReadSamsungReferrer = true;
   }

   public static void doNotReadSamsungReferrer() {
      shouldReadSamsungReferrer = false;
   }
}
