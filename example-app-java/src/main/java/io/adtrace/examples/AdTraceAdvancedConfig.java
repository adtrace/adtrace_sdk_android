package io.adtrace.examples;

import android.net.Uri;
import android.util.Log;

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

/**
 * Optional AdTrace SDK configuration — not required for basic integration.
 *
 * Called from {@link GlobalApplication} before {@link io.adtrace.sdk.AdTrace#onCreate}.
 * Remove or trim this class if you only need the minimal setup.
 */
public final class AdTraceAdvancedConfig {

    private static final String LOG_TAG = "AdTraceExample";

    private AdTraceAdvancedConfig() {
    }

    public static void apply(AdTraceConfig config) {
        config.setLogLevel(LogLevel.VERBOSE);

        config.setOnAttributionChangedListener(new OnAttributionChangedListener() {
            @Override
            public void onAttributionChanged(AdTraceAttribution attribution) {
                Log.d(LOG_TAG, "Attribution: " + attribution);
            }
        });

        config.setOnEventTrackingSucceededListener(new OnEventTrackingSucceededListener() {
            @Override
            public void onFinishedEventTrackingSucceeded(AdTraceEventSuccess eventSuccessResponseData) {
                Log.d(LOG_TAG, "Event success: " + eventSuccessResponseData);
            }
        });

        config.setOnEventTrackingFailedListener(new OnEventTrackingFailedListener() {
            @Override
            public void onFinishedEventTrackingFailed(AdTraceEventFailure eventFailureResponseData) {
                Log.d(LOG_TAG, "Event failure: " + eventFailureResponseData);
            }
        });

        config.setOnSessionTrackingSucceededListener(new OnSessionTrackingSucceededListener() {
            @Override
            public void onFinishedSessionTrackingSucceeded(AdTraceSessionSuccess sessionSuccessResponseData) {
                Log.d(LOG_TAG, "Session success: " + sessionSuccessResponseData);
            }
        });

        config.setOnSessionTrackingFailedListener(new OnSessionTrackingFailedListener() {
            @Override
            public void onFinishedSessionTrackingFailed(AdTraceSessionFailure sessionFailureResponseData) {
                Log.d(LOG_TAG, "Session failure: " + sessionFailureResponseData);
            }
        });

        config.setOnDeeplinkResponseListener(new OnDeeplinkResponseListener() {
            @Override
            public boolean launchReceivedDeeplink(Uri deeplink) {
                Log.d(LOG_TAG, "Deferred deep link: " + deeplink);
                return true; // return true to let the SDK open the deep link
            }
        });

        config.setSendInBackground(true);

        // Uncomment any of the following as needed:
        // config.setDefaultTracker("{YourDefaultTracker}");
        // config.setEventBufferingEnabled(true);
        // config.setDelayStart(7);
        // config.setPreinstallTrackingEnabled(true);
        // config.setPlayStoreKidsAppEnabled(true);
        // config.setCoppaCompliantEnabled(true);
    }
}
