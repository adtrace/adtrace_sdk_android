package io.adtrace.sdk.imei;

public class AdTraceImei {
    static boolean isImeiToBeRead = false;

    public static void readImei() {
        AdTraceImei.isImeiToBeRead = true;
    }

    public static void doNotReadImei() {
        AdTraceImei.isImeiToBeRead = false;
    }
}
