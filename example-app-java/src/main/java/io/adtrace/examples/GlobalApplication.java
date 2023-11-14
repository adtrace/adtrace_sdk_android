package io.adtrace.examples;

import android.app.Activity;
import android.app.Application;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import io.adtrace.sdk.AdTrace;
import io.adtrace.sdk.AdTraceAttribution;
import io.adtrace.sdk.AdTraceConfig;
import io.adtrace.sdk.AdTraceEventFailure;
import io.adtrace.sdk.AdTraceEventSuccess;
import io.adtrace.sdk.AdTraceSessionFailure;
import io.adtrace.sdk.AdTraceSessionSuccess;
import io.adtrace.sdk.LogLevel;
import io.adtrace.sdk.OnAttributionChangedListener;
import io.adtrace.sdk.OnDeeplinkResponseListener;
import io.adtrace.sdk.OnEventTrackingFailedListener;
import io.adtrace.sdk.OnEventTrackingSucceededListener;
import io.adtrace.sdk.OnSessionTrackingFailedListener;
import io.adtrace.sdk.OnSessionTrackingSucceededListener;

public class GlobalApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Configure adtrace SDK.
        String appToken = "wzfjavr56krp";
        String environment = AdTraceConfig.ENVIRONMENT_SANDBOX;

        AdTraceConfig config = new AdTraceConfig(this, appToken, environment);

        // Change the log level.
        config.setLogLevel(LogLevel.VERBOSE);

        // Set attribution delegate.
        config.setOnAttributionChangedListener(new OnAttributionChangedListener() {
            @Override
            public void onAttributionChanged(AdTraceAttribution attribution) {
                Log.d("example", "Attribution callback called!");
                Log.d("example", "Attribution: " + attribution.toString());
            }
        });

        // Set event success tracking delegate.
        config.setOnEventTrackingSucceededListener(new OnEventTrackingSucceededListener() {
            @Override
            public void onFinishedEventTrackingSucceeded(AdTraceEventSuccess eventSuccessResponseData) {
                Log.d("example", "Event success callback called!");
                Log.d("example", "Event success data: " + eventSuccessResponseData.toString());
            }
        });

        // Set event failure tracking delegate.
        config.setOnEventTrackingFailedListener(new OnEventTrackingFailedListener() {
            @Override
            public void onFinishedEventTrackingFailed(AdTraceEventFailure eventFailureResponseData) {
                Log.d("example", "Event failure callback called!");
                Log.d("example", "Event failure data: " + eventFailureResponseData.toString());
            }
        });

        // Set session success tracking delegate.
        config.setOnSessionTrackingSucceededListener(new OnSessionTrackingSucceededListener() {
            @Override
            public void onFinishedSessionTrackingSucceeded(AdTraceSessionSuccess sessionSuccessResponseData) {
                Log.d("example", "Session success callback called!");
                Log.d("example", "Session success data: " + sessionSuccessResponseData.toString());
            }
        });

        // Set session failure tracking delegate.
        config.setOnSessionTrackingFailedListener(new OnSessionTrackingFailedListener() {
            @Override
            public void onFinishedSessionTrackingFailed(AdTraceSessionFailure sessionFailureResponseData) {
                Log.d("example", "Session failure callback called!");
                Log.d("example", "Session failure data: " + sessionFailureResponseData.toString());
            }
        });

        // Evaluate deferred deep link to be launched.
        config.setOnDeeplinkResponseListener(new OnDeeplinkResponseListener() {
            @Override
            public boolean launchReceivedDeeplink(Uri deeplink) {
                Log.d("example", "Deferred deep link callback called!");
                Log.d("example", "Deep link URL: " + deeplink);

                return true;
            }
        });

        // Set default tracker.
        // config.setDefaultTracker("{YourDefaultTracker}");

        // Set process name.
        // config.setProcessName("io.adtrace.sample");

        // Allow to send in the background.
        config.setSendInBackground(true);

        // Enable event buffering.
        // config.setEventBufferingEnabled(true);

        // Delay first session.
        // config.setDelayStart(7);

        // Allow tracking preinstall
        // config.setPreinstallTrackingEnabled(true);

        // Add session callback parameters.
        AdTrace.addSessionCallbackParameter("sc_foo", "sc_bar");
        AdTrace.addSessionCallbackParameter("sc_key", "sc_value");

        // Add session partner parameters.
        AdTrace.addSessionPartnerParameter("sp_foo", "sp_bar");
        AdTrace.addSessionPartnerParameter("sp_key", "sp_value");

        // Remove session callback parameters.
        AdTrace.removeSessionCallbackParameter("sc_foo");

        // Remove session partner parameters.
        AdTrace.removeSessionPartnerParameter("sp_key");

        // Remove all session callback parameters.
        AdTrace.resetSessionCallbackParameters();

        // Remove all session partner parameters.
        AdTrace.resetSessionPartnerParameters();

        // Enable IMEI reading ONLY IF:
        // - IMEI plugin is added to your app.
        // - Your app is NOT distributed in Google Play Store.
        // AdTraceImei.readImei();

        // Enable OAID reading ONLY IF:
        // - OAID plugin is added to your app.
        // - Your app is NOT distributed in Google Play Store & supports OAID.
//         AdTraceOaid.readOaid();

        // Enable Google play kids and COPPA complaint
        // config.setPlayStoreKidsAppEnabled(true);
        // config.setCoppaCompliantEnabled(true);

        // Initialise the adtrace SDK.
        AdTrace.onCreate(config);

        // Abort delay for the first session introduced with setDelayStart method.
        // AdTrace.sendFirstPackages();

        // Register onResume and onPause events of all activities
        // for applications with minSdkVersion >= 14.
        registerActivityLifecycleCallbacks(new AdTraceLifecycleCallbacks());

        // Put the SDK in offline mode.
        // AdTrace.setOfflineMode(true);

        // Disable the SDK
        // AdTrace.setEnabled(false);

        // Send push notification token.
        // AdTrace.setPushToken("token");
    }

    // You can use this class if your app is for Android 4.0 or higher
    private static final class AdTraceLifecycleCallbacks implements ActivityLifecycleCallbacks {
        @Override
        public void onActivityResumed(Activity activity) {
            AdTrace.onResume();
        }

        @Override
        public void onActivityPaused(Activity activity) {
            AdTrace.onPause();
        }

        @Override
        public void onActivityStopped(Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
        }

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        }

        @Override
        public void onActivityStarted(Activity activity) {
        }
    }
}
