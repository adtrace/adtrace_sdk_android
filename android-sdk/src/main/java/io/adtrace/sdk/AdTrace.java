package io.adtrace.sdk;

import android.content.Context;
import android.net.Uri;

/**
 * The main interface to AdTrace.
 * Use the methods of this class to tell AdTrace about the usage of your app.
 * See the README for details.
 */

/**
 * Created by Morteza KhosraviNejad on 06/01/19.
 */
public class AdTrace {
    /**
     * Singleton AdTrace SDK instance.
     */
    private static AdTraceInstance defaultInstance;

    /**
     * Private constructor.
     */
    private AdTrace() {
    }

    /**
     * Method used to obtain AdTrace SDK singleton instance.
     *
     * @return AdTrace SDK singleton instance.
     */
    public static synchronized AdTraceInstance getDefaultInstance() {
        @SuppressWarnings("unused")
        String VERSION = "!SDK-VERSION-STRING!:io.adtrace:sdk-android:0.0.3";

        if (defaultInstance == null) {
            defaultInstance = new AdTraceInstance();
        }
        return defaultInstance;
    }

    /**
     * Called upon SDK initialisation.
     *
     * @param adTraceConfig AdTraceConfig object used for SDK initialisation
     */
    public static void onCreate(AdTraceConfig adTraceConfig) {
        AdTraceInstance adTraceInstance = AdTrace.getDefaultInstance();
        adTraceInstance.onCreate(adTraceConfig);
    }

    /**
     * Called to track event.
     *
     * @param event AdTraceEvent object to be tracked
     */
    public static void trackEvent(AdTraceEvent event) {
        AdTraceInstance adTraceInstance = AdTrace.getDefaultInstance();
        adTraceInstance.trackEvent(event);
    }

    /**
     * Called upon each Activity's onResume() method call.
     */
    public static void onResume() {
        AdTraceInstance adTraceInstance = AdTrace.getDefaultInstance();
        adTraceInstance.onResume();
    }

    /**
     * Called upon each Activity's onPause() method call.
     */
    public static void onPause() {
        AdTraceInstance adTraceInstance = AdTrace.getDefaultInstance();
        adTraceInstance.onPause();
    }

    /**
     * Called to disable/enable SDK.
     *
     * @param enabled boolean indicating whether SDK should be enabled or disabled
     */
    public static void setEnabled(boolean enabled) {
        AdTraceInstance adTraceInstance = AdTrace.getDefaultInstance();
        adTraceInstance.setEnabled(enabled);
    }

    /**
     * Get information if SDK is enabled or not.
     *
     * @return boolean indicating whether SDK is enabled or not
     */
    public static boolean isEnabled() {
        AdTraceInstance adTraceInstance = AdTrace.getDefaultInstance();
        return adTraceInstance.isEnabled();
    }

    /**
     * Called to process deep link.
     *
     * @param url Deep link URL to process
     *
     * @deprecated Use {@link #appWillOpenUrl(Uri, Context)}} instead.
     */
    @Deprecated
    public static void appWillOpenUrl(Uri url) {
        AdTraceInstance adTraceInstance = AdTrace.getDefaultInstance();
        adTraceInstance.appWillOpenUrl(url);
    }

    /**
     * Called to process deep link.
     *
     * @param url Deep link URL to process
     * @param context Application context
     */
    public static void appWillOpenUrl(Uri url, Context context) {
        AdTraceInstance adTraceInstance = AdTrace.getDefaultInstance();
        adTraceInstance.appWillOpenUrl(url, context);
    }

    /**
     * Called to process referrer information sent with INSTALL_REFERRER intent.
     *
     * @param referrer Referrer content
     * @param context  Application context
     */
    public static void setReferrer(String referrer, Context context) {
        AdTraceInstance adTraceInstance = AdTrace.getDefaultInstance();
        adTraceInstance.sendReferrer(referrer, context);
    }

    /**
     * Called to set SDK to offline or online mode.
     *
     * @param enabled boolean indicating should SDK be in offline mode (true) or not (false)
     */
    public static void setOfflineMode(boolean enabled) {
        AdTraceInstance adTraceInstance = AdTrace.getDefaultInstance();
        adTraceInstance.setOfflineMode(enabled);
    }

    /**
     * Called to enable or disable location status.
     *
     * @param enabled boolean indicating should SDK use location of device or not
     */
    public static void enableLocation(boolean enabled) {
        AdTraceInstance adTraceInstance = AdTrace.getDefaultInstance();
        adTraceInstance.enableLocation(enabled);
    }

    /**
     * Called if SDK initialisation was delayed and you would like to stop waiting for timer.
     */
    public static void sendFirstPackages() {
        AdTraceInstance adTraceInstance = AdTrace.getDefaultInstance();
        adTraceInstance.sendFirstPackages();
    }

    /**
     * Called to add global callback parameter that will be sent with each session and event.
     *
     * @param key   Global callback parameter key
     * @param value Global callback parameter value
     */
    public static void addSessionCallbackParameter(String key, String value) {
        AdTraceInstance adTraceInstance = AdTrace.getDefaultInstance();
        adTraceInstance.addSessionCallbackParameter(key, value);
    }

    /**
     * Called to add global partner parameter that will be sent with each session and event.
     *
     * @param key   Global partner parameter key
     * @param value Global partner parameter value
     */
    public static void addSessionPartnerParameter(String key, String value) {
        AdTraceInstance adTraceInstance = AdTrace.getDefaultInstance();
        adTraceInstance.addSessionPartnerParameter(key, value);
    }

    /**
     * Called to remove global callback parameter from session and event packages.
     *
     * @param key Global callback parameter key
     */
    public static void removeSessionCallbackParameter(String key) {
        AdTraceInstance adTraceInstance = AdTrace.getDefaultInstance();
        adTraceInstance.removeSessionCallbackParameter(key);
    }

    /**
     * Called to remove global partner parameter from session and event packages.
     *
     * @param key Global partner parameter key
     */
    public static void removeSessionPartnerParameter(String key) {
        AdTraceInstance adTraceInstance = AdTrace.getDefaultInstance();
        adTraceInstance.removeSessionPartnerParameter(key);
    }

    /**
     * Called to remove all added global callback parameters.
     */
    public static void resetSessionCallbackParameters() {
        AdTraceInstance adTraceInstance = AdTrace.getDefaultInstance();
        adTraceInstance.resetSessionCallbackParameters();
    }

    /**
     * Called to remove all added global partner parameters.
     */
    public static void resetSessionPartnerParameters() {
        AdTraceInstance adTraceInstance = AdTrace.getDefaultInstance();
        adTraceInstance.resetSessionPartnerParameters();
    }

    /**
     * Called to set user's push notifications token.
     *
     * @param token Push notifications token
     * @deprecated use {@link #setPushToken(String, Context)} instead.
     */
    public static void setPushToken(String token) {
        AdTraceInstance adTraceInstance = AdTrace.getDefaultInstance();
        adTraceInstance.setPushToken(token);
    }

    /**
     * Called to set user's push notifications token.
     *
     * @param token   Push notifications token
     * @param context Application context
     */
    public static void setPushToken(final String token, final Context context) {
        AdTraceInstance adTraceInstance = AdTrace.getDefaultInstance();
        adTraceInstance.setPushToken(token, context);
    }

    /**
     * Called to forget the user in accordance with GDPR law.
     *
     * @param context Application context
     */
    public static void gdprForgetMe(final Context context) {
        AdTraceInstance adTraceInstance = AdTrace.getDefaultInstance();
        adTraceInstance.gdprForgetMe(context);
    }

    /**
     * Called to get value of Google Play Advertising Identifier.
     *
     * @param context        Application context
     * @param onDeviceIdRead Callback to get triggered once identifier is obtained
     */
    public static void getGoogleAdId(Context context, OnDeviceIdsRead onDeviceIdRead) {
        Util.getGoogleAdId(context, onDeviceIdRead);
    }

    /**
     * Called to get value of Amazon Advertising Identifier.
     *
     * @param context Application context
     * @return Amazon Advertising Identifier
     */
    public static String getAmazonAdId(final Context context) {
        return Util.getFireAdvertisingId(context.getContentResolver());
    }

    /**
     * Called to get value of unique AdTrace device identifier.
     *
     * @return Unique AdTrace device indetifier
     */
    public static String getAdid() {
        AdTraceInstance adTraceInstance = AdTrace.getDefaultInstance();
        return adTraceInstance.getAdid();
    }

    /**
     * Called to get user's current attribution value.
     *
     * @return AdTraceAttribution object with current attribution value
     */
    public static AdTraceAttribution getAttribution() {
        AdTraceInstance adTraceInstance = AdTrace.getDefaultInstance();
        return adTraceInstance.getAttribution();
    }

    /**
     * Called to get native SDK version string.
     *
     * @return Native SDK version string.
     */
    public static String getSdkVersion() {
        AdTraceInstance adTraceInstance = AdTrace.getDefaultInstance();
        return adTraceInstance.getSdkVersion();
    }

    /**
     * Used for testing purposes only. Do NOT use this method.
     *
     * @param testOptions AdTrace integration tests options
     */
    public static void setTestOptions(AdTraceTestOptions testOptions) {
        if (testOptions.teardown != null && testOptions.teardown.booleanValue()) {
            if (defaultInstance != null) {
                defaultInstance.teardown();
            }
            defaultInstance = null;
            AdTraceFactory.teardown(testOptions.context);
        }

        AdTraceInstance adTraceInstance = AdTrace.getDefaultInstance();
        adTraceInstance.setTestOptions(testOptions);
    }
}
