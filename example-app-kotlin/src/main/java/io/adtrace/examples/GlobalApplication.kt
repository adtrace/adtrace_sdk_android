package io.adtrace.examples

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import io.adtrace.sdk.AdTrace
import io.adtrace.sdk.AdTraceConfig
import io.adtrace.sdk.LogLevel

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Configure adtrace SDK.
        val appToken = "xyz123abc456"
        val environment = AdTraceConfig.ENVIRONMENT_SANDBOX

        val config = AdTraceConfig(this, appToken, environment)

        // Change the log level.
        config.setLogLevel(LogLevel.VERBOSE)

        // Set attribution delegate.
        config.setOnAttributionChangedListener { attribution ->
            Log.d("example", "Attribution callback called!")
            Log.d("example", "Attribution: $attribution")
        }

        // Set event success tracking delegate.
        config.setOnEventTrackingSucceededListener { eventSuccessResponseData ->
            Log.d("example", "Event success callback called!")
            Log.d("example", "Event success data: $eventSuccessResponseData")
        }

        // Set event failure tracking delegate.
        config.setOnEventTrackingFailedListener { eventFailureResponseData ->
            Log.d("example", "Event failure callback called!")
            Log.d("example", "Event failure data: $eventFailureResponseData")
        }

        // Set session success tracking delegate.
        config.setOnSessionTrackingSucceededListener { sessionSuccessResponseData ->
            Log.d("example", "Session success callback called!")
            Log.d("example", "Session success data: $sessionSuccessResponseData")
        }

        // Set session failure tracking delegate.
        config.setOnSessionTrackingFailedListener { sessionFailureResponseData ->
            Log.d("example", "Session failure callback called!")
            Log.d("example", "Session failure data: $sessionFailureResponseData")
        }

        // Evaluate deferred deep link to be launched.
        config.setOnDeeplinkResponseListener { deeplink ->
            Log.d("example", "Deferred deep link callback called!")
            Log.d("example", "Deep link URL: $deeplink")

            true
        }

        // Set default tracker.
        // config.setDefaultTracker("{YourDefaultTracker}");

        // Set process name.
        // config.setProcessName("io.adtrace.sample");

        // Allow to send in the background.
        config.setSendInBackground(true)

        // Enable event buffering.
        // config.setEventBufferingEnabled(true);

        // Delay first session.
        // config.setDelayStart(7);

        // Allow tracking preinstall
        // config.setPreinstallTrackingEnabled(true);

        // Add session callback parameters.
        AdTrace.addSessionCallbackParameter("sc_foo", "sc_bar")
        AdTrace.addSessionCallbackParameter("sc_key", "sc_value")

        // Add session partner parameters.
        AdTrace.addSessionPartnerParameter("sp_foo", "sp_bar")
        AdTrace.addSessionPartnerParameter("sp_key", "sp_value")

        // Remove session callback parameters.
        AdTrace.removeSessionCallbackParameter("sc_foo")

        // Remove session partner parameters.
        AdTrace.removeSessionPartnerParameter("sp_key")

        // Remove all session callback parameters.
        AdTrace.resetSessionCallbackParameters()

        // Remove all session partner parameters.
        AdTrace.resetSessionPartnerParameters()

        // Enable IMEI reading ONLY IF:
        // - IMEI plugin is added to your app.
        // - Your app is NOT distributed in Google Play Store.
        // AdTraceImei.readImei()

        // Enable OAID reading ONLY IF:
        // - OAID plugin is added to your app.
        // - Your app is NOT distributed in Google Play Store & supports OAID.
        // AdTraceOaid.readOaid()

        // Enable Google play kids and COPPA complaint
        // config.setPlayStoreKidsAppEnabled(true)
        // config.setCoppaCompliantEnabled(true)

        // Initialise the adtrace SDK.
        AdTrace.onCreate(config)

        // Abort delay for the first session introduced with setDelayStart method.
        // AdTrace.sendFirstPackages();

        // Register onResume and onPause events of all activities
        // for applications with minSdkVersion >= 14.
        registerActivityLifecycleCallbacks(AdTraceLifecycleCallbacks())

        // Put the SDK in offline mode.
        // AdTrace.setOfflineMode(true);

        // Disable the SDK
        // AdTrace.setEnabled(false);

        // Send push notification token.
        // AdTrace.setPushToken("token");

    }

    // You can use this class if your app is for Android 4.0 or higher
    private class AdTraceLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
        override fun onActivityResumed(activity: Activity) {
            AdTrace.onResume()
        }

        override fun onActivityPaused(activity: Activity) {
            AdTrace.onPause()
        }

        override fun onActivityStopped(activity: Activity) {}

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

        override fun onActivityDestroyed(activity: Activity) {}

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

        override fun onActivityStarted(activity: Activity) {}
    }
}