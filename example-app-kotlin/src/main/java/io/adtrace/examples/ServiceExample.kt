package io.adtrace.examples

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import io.adtrace.sdk.AdTrace
import io.adtrace.sdk.AdTraceEvent

/**
 * Demo: track an AdTrace event from a background Service.
 *
 * In production, prefer WorkManager or coroutines instead of raw threads.
 */
class ServiceExample : Service() {

    override fun onBind(intent: Intent): IBinder? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d(LOG_TAG, "Service started — toggling SDK enabled state for demo")
        AdTrace.setEnabled(!sdkEnabledToggle)
        sdkEnabledToggle = !sdkEnabledToggle

        Thread {
            SystemClock.sleep(3000)
            val event = AdTraceEvent(AdTraceConstants.EVENT_TOKEN_BACKGROUND)
            AdTrace.trackEvent(event)
            Log.d(LOG_TAG, "Background event tracked")
        }.start()

        return START_NOT_STICKY
    }

    companion object {
        private const val LOG_TAG = "AdTraceExample"
        private var sdkEnabledToggle = true
    }
}
