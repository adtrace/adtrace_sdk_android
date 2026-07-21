package io.adtrace.examples

import android.util.Log
import io.adtrace.sdk.AdTraceConfig
import io.adtrace.sdk.LogLevel

/**
 * Optional AdTrace SDK configuration — not required for basic integration.
 *
 * Called from [GlobalApplication] before [io.adtrace.sdk.AdTrace.onCreate].
 * Remove or trim this file if you only need the minimal setup.
 */
object AdTraceAdvancedConfig {

    private const val LOG_TAG = "AdTraceExample"

    fun apply(config: AdTraceConfig) {
        config.setLogLevel(LogLevel.VERBOSE)

        config.setOnAttributionChangedListener { attribution ->
            Log.d(LOG_TAG, "Attribution: $attribution")
        }

        config.setOnEventTrackingSucceededListener { data ->
            Log.d(LOG_TAG, "Event success: $data")
        }

        config.setOnEventTrackingFailedListener { data ->
            Log.d(LOG_TAG, "Event failure: $data")
        }

        config.setOnSessionTrackingSucceededListener { data ->
            Log.d(LOG_TAG, "Session success: $data")
        }

        config.setOnSessionTrackingFailedListener { data ->
            Log.d(LOG_TAG, "Session failure: $data")
        }

        config.setOnDeeplinkResponseListener { deeplink ->
            Log.d(LOG_TAG, "Deferred deep link: $deeplink")
            true // return true to let the SDK open the deep link
        }

        config.setSendInBackground(true)

        // Uncomment any of the following as needed:
        // config.setDefaultTracker("{YourDefaultTracker}")
        // config.setEventBufferingEnabled(true)
        // config.setDelayStart(7)
        // config.setPreinstallTrackingEnabled(true)
        // config.setPlayStoreKidsAppEnabled(true)
        // config.setCoppaCompliantEnabled(true)
    }
}
