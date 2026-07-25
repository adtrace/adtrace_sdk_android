package io.adtrace.examples;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import io.adtrace.sdk.AdTrace;
import io.adtrace.sdk.AdTraceEvent;

/**
 * Demo: track an AdTrace event from a background Service.
 *
 * In production, prefer WorkManager or a HandlerThread instead of raw threads.
 */
public class ServiceExample extends Service {

    private static final String LOG_TAG = "AdTraceExample";
    private static boolean sdkEnabledToggle = true;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "Service started — toggling SDK enabled state for demo");
        AdTrace.setEnabled(!sdkEnabledToggle);
        sdkEnabledToggle = !sdkEnabledToggle;

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(3000);
                AdTraceEvent event = new AdTraceEvent(AdTraceConstants.EVENT_TOKEN_BACKGROUND);
                AdTrace.trackEvent(event);
                Log.d(LOG_TAG, "Background event tracked");
            }
        }).start();

        return START_NOT_STICKY;
    }
}
