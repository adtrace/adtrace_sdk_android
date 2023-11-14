package io.adtrace.sdk.webbridge;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.app.Application;

import io.adtrace.sdk.AdTrace;
import io.adtrace.sdk.AdTraceAttribution;
import io.adtrace.sdk.AdTraceConfig;
import io.adtrace.sdk.AdTraceEvent;
import io.adtrace.sdk.AdTraceEventFailure;
import io.adtrace.sdk.AdTraceEventSuccess;
import io.adtrace.sdk.AdTraceFactory;
import io.adtrace.sdk.AdTraceSessionFailure;
import io.adtrace.sdk.AdTraceSessionSuccess;
import io.adtrace.sdk.AdTraceTestOptions;
import io.adtrace.sdk.AdTraceThirdPartySharing;
import io.adtrace.sdk.LogLevel;
import io.adtrace.sdk.OnAttributionChangedListener;
import io.adtrace.sdk.OnDeeplinkResponseListener;
import io.adtrace.sdk.OnDeviceIdsRead;
import io.adtrace.sdk.OnEventTrackingFailedListener;
import io.adtrace.sdk.OnEventTrackingSucceededListener;
import io.adtrace.sdk.OnSessionTrackingFailedListener;
import io.adtrace.sdk.OnSessionTrackingSucceededListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (github.com/namini40) on April 2022.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2022.
 */
public class AdTraceBridgeInstance {
    private static final String LOG_LEVEL_VERBOSE = "VERBOSE";
    private static final String LOG_LEVEL_DEBUG = "DEBUG";
    private static final String LOG_LEVEL_INFO = "INFO";
    private static final String LOG_LEVEL_WARN = "WARN";
    private static final String LOG_LEVEL_ERROR = "ERROR";
    private static final String LOG_LEVEL_ASSERT = "ASSERT";
    private static final String LOG_LEVEL_SUPPRESS = "SUPPRESS";

    private static final String JAVASCRIPT_INTERFACE_NAME = "AdTraceBridge";
    private static final String FB_JAVASCRIPT_INTERFACE_NAME_PREFIX = "fbmq_";

    private WebView webView;
    private Application application;
    private boolean isInitialized = false;
    private boolean shouldDeferredDeeplinkBeLaunched = true;
    private FacebookSDKJSInterface facebookSDKJSInterface = null;

    AdTraceBridgeInstance() {}

    AdTraceBridgeInstance(Application application, WebView webView) {
        this.application = application;
        setWebView(webView);
    }

    // Automatically subscribe to Android lifecycle callbacks to properly handle session tracking.
    // This requires user to have minimal supported API level set to 14.
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private static final class AdTraceLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
        @Override
        public void onActivityResumed(Activity activity) {
            AdTrace.onResume();
        }

        @Override
        public void onActivityPaused(Activity activity) {
            AdTrace.onPause();
        }

        @Override
        public void onActivityStopped(Activity activity) {}

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}

        @Override
        public void onActivityDestroyed(Activity activity) {}

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {}

        @Override
        public void onActivityStarted(Activity activity) {}
    }

    private boolean isInitialized() {
        if (webView == null) {
            AdTraceBridgeUtil.getLogger().error("Webview missing. Call AdTraceBridge.setWebView before");
            return false;
        }
        if (application == null) {
            AdTraceBridgeUtil.getLogger().error("Application context missing. Call AdTraceBridge.setApplicationContext before");
            return false;
        }
        return true;
    }

    public void registerFacebookSDKJSInterface() {
        // Configure the web view to add fb pixel interface
        String fbApplicationId = FacebookSDKJSInterface.getApplicationId(application.getApplicationContext());
        AdTraceFactory.getLogger().info("AdTraceBridgeInstance fbApplicationId: %s", fbApplicationId);

        if (fbApplicationId == null) {
            return;
        }

        this.facebookSDKJSInterface = new FacebookSDKJSInterface();

        // Add FB pixel to JS interface.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            this.webView.addJavascriptInterface(facebookSDKJSInterface,
                                                FB_JAVASCRIPT_INTERFACE_NAME_PREFIX
                                                + fbApplicationId
                                               );
        }
    }

    @JavascriptInterface
    public void onCreate(String adtraceConfigString) {
        // Initialise SDK only if it's not already initialised.
        if (isInitialized) {
            AdTraceBridgeUtil.getLogger().warn("AdTrace bridge is already initialized. Ignoring further attempts");
            return;
        }
        if (!isInitialized()) {
            return;
        }

        try {
            AdTraceBridgeUtil.getLogger().verbose("Web bridge onCreate adtraceConfigString: " + adtraceConfigString);

            JSONObject jsonAdTraceConfig = new JSONObject(adtraceConfigString);
            Object appTokenField = jsonAdTraceConfig.get("appToken");
            Object environmentField = jsonAdTraceConfig.get("environment");
            Object allowSuppressLogLevelField = jsonAdTraceConfig.get("allowSuppressLogLevel");
            Object eventBufferingEnabledField = jsonAdTraceConfig.get("eventBufferingEnabled");
            Object sendInBackgroundField = jsonAdTraceConfig.get("sendInBackground");
            Object logLevelField = jsonAdTraceConfig.get("logLevel");
            Object sdkPrefixField = jsonAdTraceConfig.get("sdkPrefix");
            Object processNameField = jsonAdTraceConfig.get("processName");
            Object defaultTrackerField = jsonAdTraceConfig.get("defaultTracker");
            Object externalDeviceIdField = jsonAdTraceConfig.get("externalDeviceId");
            Object attributionCallbackNameField = jsonAdTraceConfig.get("attributionCallbackName");
            Object deviceKnownField = jsonAdTraceConfig.get("deviceKnown");
            Object needsCostField = jsonAdTraceConfig.get("needsCost");
            Object eventSuccessCallbackNameField = jsonAdTraceConfig.get("eventSuccessCallbackName");
            Object eventFailureCallbackNameField = jsonAdTraceConfig.get("eventFailureCallbackName");
            Object sessionSuccessCallbackNameField = jsonAdTraceConfig.get("sessionSuccessCallbackName");
            Object sessionFailureCallbackNameField = jsonAdTraceConfig.get("sessionFailureCallbackName");
            Object openDeferredDeeplinkField = jsonAdTraceConfig.get("openDeferredDeeplink");
            Object deferredDeeplinkCallbackNameField = jsonAdTraceConfig.get("deferredDeeplinkCallbackName");
            Object delayStartField = jsonAdTraceConfig.get("delayStart");
            Object userAgentField = jsonAdTraceConfig.get("userAgent");
            Object secretIdField = jsonAdTraceConfig.get("secretId");
            Object info1Field = jsonAdTraceConfig.get("info1");
            Object info2Field = jsonAdTraceConfig.get("info2");
            Object info3Field = jsonAdTraceConfig.get("info3");
            Object info4Field = jsonAdTraceConfig.get("info4");
            Object fbPixelDefaultEventTokenField = jsonAdTraceConfig.get("fbPixelDefaultEventToken");
            Object fbPixelMappingField = jsonAdTraceConfig.get("fbPixelMapping");
            Object urlStrategyField = jsonAdTraceConfig.get("urlStrategy");
            Object preinstallTrackingEnabledField = jsonAdTraceConfig.get("preinstallTrackingEnabled");
            Object preinstallFilePathField = jsonAdTraceConfig.get("preinstallFilePath");
            Object playStoreKidsAppEnabledField = jsonAdTraceConfig.get("playStoreKidsAppEnabled");
            Object coppaCompliantEnabledField = jsonAdTraceConfig.get("coppaCompliantEnabled");
            Object finalAttributionEnabledField = jsonAdTraceConfig.get("finalAttributionEnabled");
            Object fbAppIdField = jsonAdTraceConfig.get("fbAppId");

            String appToken = AdTraceBridgeUtil.fieldToString(appTokenField);
            String environment = AdTraceBridgeUtil.fieldToString(environmentField);
            Boolean allowSuppressLogLevel = AdTraceBridgeUtil.fieldToBoolean(allowSuppressLogLevelField);

            AdTraceConfig adtraceConfig;
            if (allowSuppressLogLevel == null) {
                adtraceConfig = new AdTraceConfig(application.getApplicationContext(), appToken, environment);
            } else {
                adtraceConfig = new AdTraceConfig(application.getApplicationContext(), appToken, environment, allowSuppressLogLevel.booleanValue());
            }

            if (!adtraceConfig.isValid()) {
                return;
            }

            // Event buffering
            Boolean eventBufferingEnabled = AdTraceBridgeUtil.fieldToBoolean(eventBufferingEnabledField);
            if (eventBufferingEnabled != null) {
                adtraceConfig.setEventBufferingEnabled(eventBufferingEnabled);
            }

            // Send in the background
            Boolean sendInBackground = AdTraceBridgeUtil.fieldToBoolean(sendInBackgroundField);
            if (sendInBackground != null) {
                adtraceConfig.setSendInBackground(sendInBackground);
            }

            // Log level
            String logLevelString = AdTraceBridgeUtil.fieldToString(logLevelField);
            if (logLevelString != null) {
                if (logLevelString.equalsIgnoreCase(LOG_LEVEL_VERBOSE)) {
                    adtraceConfig.setLogLevel(LogLevel.VERBOSE);
                } else if (logLevelString.equalsIgnoreCase(LOG_LEVEL_DEBUG)) {
                    adtraceConfig.setLogLevel(LogLevel.DEBUG);
                } else if (logLevelString.equalsIgnoreCase(LOG_LEVEL_INFO)) {
                    adtraceConfig.setLogLevel(LogLevel.INFO);
                } else if (logLevelString.equalsIgnoreCase(LOG_LEVEL_WARN)) {
                    adtraceConfig.setLogLevel(LogLevel.WARN);
                } else if (logLevelString.equalsIgnoreCase(LOG_LEVEL_ERROR)) {
                    adtraceConfig.setLogLevel(LogLevel.ERROR);
                } else if (logLevelString.equalsIgnoreCase(LOG_LEVEL_ASSERT)) {
                    adtraceConfig.setLogLevel(LogLevel.ASSERT);
                } else if (logLevelString.equalsIgnoreCase(LOG_LEVEL_SUPPRESS)) {
                    adtraceConfig.setLogLevel(LogLevel.SUPRESS);
                }
            }

            // SDK prefix
            String sdkPrefix = AdTraceBridgeUtil.fieldToString(sdkPrefixField);
            if (sdkPrefix != null) {
                adtraceConfig.setSdkPrefix(sdkPrefix);
            }

            // Main process name
            String processName = AdTraceBridgeUtil.fieldToString(processNameField);
            if (processName != null) {
                adtraceConfig.setProcessName(processName);
            }

            // Default tracker
            String defaultTracker = AdTraceBridgeUtil.fieldToString(defaultTrackerField);
            if (defaultTracker != null) {
                adtraceConfig.setDefaultTracker(defaultTracker);
            }

            // External device ID
            String externalDeviceId = AdTraceBridgeUtil.fieldToString(externalDeviceIdField);
            if (externalDeviceId != null) {
                adtraceConfig.setExternalDeviceId(externalDeviceId);
            }

            // Attribution callback name
            final String attributionCallbackName = AdTraceBridgeUtil.fieldToString(attributionCallbackNameField);
            if (attributionCallbackName != null) {
                adtraceConfig.setOnAttributionChangedListener(new OnAttributionChangedListener() {
                    @Override
                    public void onAttributionChanged(AdTraceAttribution attribution) {
                        AdTraceBridgeUtil.execAttributionCallbackCommand(webView, attributionCallbackName, attribution);
                    }
                });
            }

            // Is device known
            Boolean deviceKnown = AdTraceBridgeUtil.fieldToBoolean(deviceKnownField);
            if (deviceKnown != null) {
                adtraceConfig.setDeviceKnown(deviceKnown);
            }

            // Needs cost
            Boolean needsCost = AdTraceBridgeUtil.fieldToBoolean(needsCostField);
            if (needsCost != null) {
                adtraceConfig.setNeedsCost(needsCost);
            }

            // Event success callback
            final String eventSuccessCallbackName = AdTraceBridgeUtil.fieldToString(eventSuccessCallbackNameField);
            if (eventSuccessCallbackName != null) {
                adtraceConfig.setOnEventTrackingSucceededListener(new OnEventTrackingSucceededListener() {
                    public void onFinishedEventTrackingSucceeded(AdTraceEventSuccess eventSuccessResponseData) {
                        AdTraceBridgeUtil.execEventSuccessCallbackCommand(webView, eventSuccessCallbackName, eventSuccessResponseData);
                    }
                });
            }

            // Event failure callback
            final String eventFailureCallbackName = AdTraceBridgeUtil.fieldToString(eventFailureCallbackNameField);
            if (eventFailureCallbackName != null) {
                adtraceConfig.setOnEventTrackingFailedListener(new OnEventTrackingFailedListener() {
                    public void onFinishedEventTrackingFailed(AdTraceEventFailure eventFailureResponseData) {
                        AdTraceBridgeUtil.execEventFailureCallbackCommand(webView, eventFailureCallbackName, eventFailureResponseData);
                    }
                });
            }

            // Session success callback
            final String sessionSuccessCallbackName = AdTraceBridgeUtil.fieldToString(sessionSuccessCallbackNameField);
            if (sessionSuccessCallbackName != null) {
                adtraceConfig.setOnSessionTrackingSucceededListener(new OnSessionTrackingSucceededListener() {
                    @Override
                    public void onFinishedSessionTrackingSucceeded(AdTraceSessionSuccess sessionSuccessResponseData) {
                        AdTraceBridgeUtil.execSessionSuccessCallbackCommand(webView, sessionSuccessCallbackName, sessionSuccessResponseData);
                    }
                });
            }

            // Session failure callback
            final String sessionFailureCallbackName = AdTraceBridgeUtil.fieldToString(sessionFailureCallbackNameField);
            if (sessionFailureCallbackName != null) {
                adtraceConfig.setOnSessionTrackingFailedListener(new OnSessionTrackingFailedListener() {
                    @Override
                    public void onFinishedSessionTrackingFailed(AdTraceSessionFailure failureResponseData) {
                        AdTraceBridgeUtil.execSessionFailureCallbackCommand(webView, sessionFailureCallbackName, failureResponseData);
                    }
                });
            }

            // Should deferred deep link be opened?
            Boolean openDeferredDeeplink = AdTraceBridgeUtil.fieldToBoolean(openDeferredDeeplinkField);
            if (openDeferredDeeplink != null) {
                shouldDeferredDeeplinkBeLaunched = openDeferredDeeplink;
            }

            // Deferred deeplink callback
            final String deferredDeeplinkCallbackName = AdTraceBridgeUtil.fieldToString(deferredDeeplinkCallbackNameField);
            if (deferredDeeplinkCallbackName != null) {
                adtraceConfig.setOnDeeplinkResponseListener(new OnDeeplinkResponseListener() {
                    @Override
                    public boolean launchReceivedDeeplink(Uri deeplink) {
                        AdTraceBridgeUtil.execSingleValueCallback(webView, deferredDeeplinkCallbackName, deeplink.toString());
                        return shouldDeferredDeeplinkBeLaunched;
                    }
                });
            }

            // Delay start
            Double delayStart = AdTraceBridgeUtil.fieldToDouble(delayStartField);
            if (delayStart != null) {
                adtraceConfig.setDelayStart(delayStart);
            }

            // User agent
            String userAgent = AdTraceBridgeUtil.fieldToString(userAgentField);
            if (userAgent != null) {
                adtraceConfig.setUserAgent(userAgent);
            }

            // App secret
            Long secretId = AdTraceBridgeUtil.fieldToLong(secretIdField);
            Long info1 = AdTraceBridgeUtil.fieldToLong(info1Field);
            Long info2 = AdTraceBridgeUtil.fieldToLong(info2Field);
            Long info3 = AdTraceBridgeUtil.fieldToLong(info3Field);
            Long info4 = AdTraceBridgeUtil.fieldToLong(info4Field);
            if (secretId != null && info1 != null && info2 != null && info3 != null && info4 != null) {
                adtraceConfig.setAppSecret(secretId, info1, info2, info3, info4);
            }

            // Check Pixel Default Event Token
            String fbPixelDefaultEventToken = AdTraceBridgeUtil.fieldToString(fbPixelDefaultEventTokenField);
            if (fbPixelDefaultEventToken != null && this.facebookSDKJSInterface != null) {
                this.facebookSDKJSInterface.setDefaultEventToken(fbPixelDefaultEventToken);
            }

            // Add Pixel mappings
            try {
                String[] fbPixelMapping = AdTraceBridgeUtil.jsonArrayToArray((JSONArray)fbPixelMappingField);
                if (fbPixelMapping != null && this.facebookSDKJSInterface != null) {
                    for (int i = 0; i < fbPixelMapping.length; i += 2) {
                        String key = fbPixelMapping[i];
                        String value = fbPixelMapping[i+1];
                        this.facebookSDKJSInterface.addFbPixelEventTokenMapping(key, value);
                    }
                }
            } catch (Exception e) {
                AdTraceFactory.getLogger().error("AdTraceBridgeInstance.configureFbPixel: %s", e.getMessage());
            }

            // Set url strategy
            String urlStrategy = AdTraceBridgeUtil.fieldToString(urlStrategyField);
            if (urlStrategy != null) {
                adtraceConfig.setUrlStrategy(urlStrategy);
            }

            // Preinstall tracking
            Boolean preinstallTrackingEnabled = AdTraceBridgeUtil.fieldToBoolean(preinstallTrackingEnabledField);
            if (preinstallTrackingEnabled != null) {
                adtraceConfig.setPreinstallTrackingEnabled(preinstallTrackingEnabled);
            }

            // Preinstall secondary file path
            String preinstallFilePath = AdTraceBridgeUtil.fieldToString(preinstallFilePathField);
            if (preinstallFilePath != null) {
                adtraceConfig.setPreinstallFilePath(preinstallFilePath);
            }

            // PlayStore Kids app
            Boolean playStoreKidsAppEnabled = AdTraceBridgeUtil.fieldToBoolean(playStoreKidsAppEnabledField);
            if (playStoreKidsAppEnabled != null) {
                adtraceConfig.setPlayStoreKidsAppEnabled(playStoreKidsAppEnabled);
            }

            // Coppa compliant
            Boolean coppaCompliantEnabled = AdTraceBridgeUtil.fieldToBoolean(coppaCompliantEnabledField);
            if (coppaCompliantEnabled != null) {
                adtraceConfig.setCoppaCompliantEnabled(coppaCompliantEnabled);
            }

            // Final attribution config
            Boolean finalAttributionEnabled = AdTraceBridgeUtil.fieldToBoolean(finalAttributionEnabledField);
            if (finalAttributionEnabled != null) {
                adtraceConfig.setFinalAttributionEnabled(finalAttributionEnabled);
            }

            // FB App ID
            String fbAppId = AdTraceBridgeUtil.fieldToString(fbAppIdField);
            if (fbAppId != null) {
                adtraceConfig.setFbAppId(fbAppId);
            }

            // Manually call onResume() because web view initialisation will happen a bit delayed.
            // With this delay, it will miss lifecycle callback onResume() initial firing.
            AdTrace.onCreate(adtraceConfig);
            AdTrace.onResume();

            isInitialized = true;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                application.registerActivityLifecycleCallbacks(new AdTraceLifecycleCallbacks());
            }
        } catch (Exception e) {
            AdTraceFactory.getLogger().error("AdTraceBridgeInstance onCreate: %s", e.getMessage());
        }
    }

    @JavascriptInterface
    public void trackEvent(String adtraceEventString) {
        if (!isInitialized()) {
            return;
        }

        try {
            JSONObject jsonAdTraceEvent = new JSONObject(adtraceEventString);

            Object eventTokenField = jsonAdTraceEvent.get("eventToken");
            Object revenueField = jsonAdTraceEvent.get("revenue");
            Object currencyField = jsonAdTraceEvent.get("currency");
            Object callbackParametersField = jsonAdTraceEvent.get("callbackParameters");
            Object eventParametersField = jsonAdTraceEvent.get("eventParameters");
            Object partnerParametersField = jsonAdTraceEvent.get("partnerParameters");
            Object orderIdField = jsonAdTraceEvent.get("orderId");
            Object callbackIdField = jsonAdTraceEvent.get("callbackId");

            String eventToken = AdTraceBridgeUtil.fieldToString(eventTokenField);
            AdTraceEvent adtraceEvent = new AdTraceEvent(eventToken);

            if (!adtraceEvent.isValid()) {
                return;
            }

            // Revenue
            Double revenue = AdTraceBridgeUtil.fieldToDouble(revenueField);
            String currency = AdTraceBridgeUtil.fieldToString(currencyField);
            if (revenue != null && currency != null) {
                adtraceEvent.setRevenue(revenue, currency);
            }

            // Callback parameters
            String[] callbackParameters = AdTraceBridgeUtil.jsonArrayToArray((JSONArray)callbackParametersField);
            if (callbackParameters != null) {
                for (int i = 0; i < callbackParameters.length; i += 2) {
                    String key = callbackParameters[i];
                    String value = callbackParameters[i+1];
                    adtraceEvent.addCallbackParameter(key, value);
                }
            }

            // event parameters
            String[] eventParameters = AdTraceBridgeUtil.jsonArrayToArray((JSONArray)eventParametersField);
            if (eventParameters != null) {
                for (int i = 0; i < eventParameters.length; i += 2) {
                    String key = eventParameters[i];
                    String value = eventParameters[i+1];
                    adtraceEvent.addEventParameter(key, value);
                }
            }

            // event partner parameters
            String[] partnerParameters = AdTraceBridgeUtil.jsonArrayToArray((JSONArray)partnerParametersField);
            if (partnerParameters != null) {
                for (int i = 0; i < partnerParameters.length; i += 2) {
                    String key = partnerParameters[i];
                    String value = partnerParameters[i+1];
                    adtraceEvent.addPartnerParameter(key, value);
                }
            }

            // Revenue deduplication
            String orderId = AdTraceBridgeUtil.fieldToString(orderIdField);
            if (orderId != null) {
                adtraceEvent.setOrderId(orderId);
            }

            // Callback id
            String callbackId = AdTraceBridgeUtil.fieldToString(callbackIdField);
            if (callbackId != null) {
                adtraceEvent.setCallbackId(callbackId);
            }

            // Track event
            AdTrace.trackEvent(adtraceEvent);
        } catch (Exception e) {
            AdTraceFactory.getLogger().error("AdTraceBridgeInstance trackEvent: %s", e.getMessage());
        }
    }

    @JavascriptInterface
    public void trackAdRevenue(final String source, final String payload) {
        try {
            // payload JSON string is URL encoded
            String decodedPayload = URLDecoder.decode(payload, "UTF-8");
            JSONObject jsonPayload = new JSONObject(decodedPayload);
            AdTrace.trackAdRevenue(source, jsonPayload);
        } catch (JSONException je) {
            AdTraceFactory.getLogger().debug("Ad revenue payload does not seem to be a valid JSON string");
        } catch (UnsupportedEncodingException ue) {
            AdTraceFactory.getLogger().debug("Unable to URL decode given JSON string");
        }
    }

    @JavascriptInterface
    public void onResume() {
        if (!isInitialized()) {
            return;
        }
        AdTrace.onResume();
    }

    @JavascriptInterface
    public void onPause() {
        if (!isInitialized()) {
            return;
        }
        AdTrace.onPause();
    }

    @JavascriptInterface
    public void setEnabled(String isEnabledString) {
        if (!isInitialized()) {
            return;
        }
        Boolean isEnabled = AdTraceBridgeUtil.fieldToBoolean(isEnabledString);
        if (isEnabled != null) {
            AdTrace.setEnabled(isEnabled);
        }
    }

    @JavascriptInterface
    public void isEnabled(String callback) {
        if (!isInitialized()) {
            return;
        }
        boolean isEnabled = AdTrace.isEnabled();
        AdTraceBridgeUtil.execSingleValueCallback(webView, callback, String.valueOf(isEnabled));
    }

    @JavascriptInterface
    public boolean isEnabled() {
        if (!isInitialized()) {
            return false;
        }
        return AdTrace.isEnabled();
    }

    @JavascriptInterface
    public void appWillOpenUrl(String deeplinkString) {
        if (!isInitialized()) {
            return;
        }
        Uri deeplink = null;
        if (deeplinkString != null) {
            deeplink = Uri.parse(deeplinkString);
        }
        AdTrace.appWillOpenUrl(deeplink, application.getApplicationContext());
    }

    @JavascriptInterface
    public void setReferrer(String referrer) {
        if (!isInitialized()) {
            return;
        }
        AdTrace.setReferrer(referrer, application.getApplicationContext());
    }

    @JavascriptInterface
    public void setOfflineMode(String isOfflineString) {
        if (!isInitialized()) {
            return;
        }
        Boolean isOffline = AdTraceBridgeUtil.fieldToBoolean(isOfflineString);
        if (isOffline != null) {
            AdTrace.setOfflineMode(isOffline);
        }
    }

    @JavascriptInterface
    public void sendFirstPackages() {
        if (!isInitialized()) {
            return;
        }
        AdTrace.sendFirstPackages();
    }

    @JavascriptInterface
    public void addSessionCallbackParameter(String key, String value) {
        if (!isInitialized()) {
            return;
        }
        AdTrace.addSessionCallbackParameter(key, value);
    }

    @JavascriptInterface
    public void addSessionPartnerParameter(String key, String value) {
        if (!isInitialized()) {
            return;
        }
        AdTrace.addSessionPartnerParameter(key, value);
    }

    @JavascriptInterface
    public void removeSessionCallbackParameter(String key) {
        if (!isInitialized()) {
            return;
        }
        AdTrace.removeSessionCallbackParameter(key);
    }

    @JavascriptInterface
    public void removeSessionPartnerParameter(String key) {
        if (!isInitialized()) {
            return;
        }
        AdTrace.removeSessionPartnerParameter(key);
    }

    @JavascriptInterface
    public void resetSessionCallbackParameters() {
        if (!isInitialized()) {
            return;
        }
        AdTrace.resetSessionCallbackParameters();
    }

    @JavascriptInterface
    public void resetSessionPartnerParameters() {
        if (!isInitialized()) {
            return;
        }
        AdTrace.resetSessionPartnerParameters();
    }

    @JavascriptInterface
    public void setPushToken(String pushToken) {
        if (!isInitialized()) {
            return;
        }

        AdTrace.setPushToken(pushToken, application.getApplicationContext());
    }

    @JavascriptInterface
    public void gdprForgetMe() {
        if (!isInitialized()) {
            return;
        }
        AdTrace.gdprForgetMe(application.getApplicationContext());
    }

    @JavascriptInterface
    public void disableThirdPartySharing() {
        if (!isInitialized()) {
            return;
        }
        AdTrace.disableThirdPartySharing(application.getApplicationContext());
    }

    @JavascriptInterface
    public void trackThirdPartySharing(String adtraceThirdPartySharingString) {
        if (!isInitialized()) {
            return;
        }

        try {
            JSONObject jsonAdTraceThirdPartySharing = new JSONObject(adtraceThirdPartySharingString);

            Object isEnabledField =
                    jsonAdTraceThirdPartySharing.get("isEnabled");
            Object granularOptionsField = jsonAdTraceThirdPartySharing.get("granularOptions");
            Object partnerSharingSettingsField = jsonAdTraceThirdPartySharing.get("partnerSharingSettings");

            Boolean isEnabled = AdTraceBridgeUtil.fieldToBoolean(isEnabledField);

            AdTraceThirdPartySharing adtraceThirdPartySharing =
                    new AdTraceThirdPartySharing(isEnabled);

            // Callback parameters
            String[] granularOptions =
                    AdTraceBridgeUtil.jsonArrayToArray((JSONArray)granularOptionsField);
            if (granularOptions != null) {
                for (int i = 0; i < granularOptions.length; i += 3) {
                    String partnerName = granularOptions[i];
                    String key = granularOptions[i + 1];
                    String value = granularOptions[i + 2];
                    adtraceThirdPartySharing.addGranularOption(partnerName, key, value);
                }
            }

            // Partner sharing settings
            String[] partnerSharingSettings =
                    AdTraceBridgeUtil.jsonArrayToArray((JSONArray)partnerSharingSettingsField);
            if (partnerSharingSettings != null) {
                for (int i = 0; i < partnerSharingSettings.length; i += 3) {
                    String partnerName = partnerSharingSettings[i];
                    String key = partnerSharingSettings[i + 1];
                    Boolean value = AdTraceBridgeUtil.fieldToBoolean(partnerSharingSettings[i + 2]);
                    if (value != null) {
                        adtraceThirdPartySharing.addPartnerSharingSetting(partnerName, key, value);
                    } else {
                        AdTraceFactory.getLogger().error("Cannot add partner sharing setting with non boolean value");
                    }
                }
            }

            // Track ThirdPartySharing
            AdTrace.trackThirdPartySharing(adtraceThirdPartySharing);
        } catch (Exception e) {
            AdTraceFactory.getLogger().error(
                    "AdTraceBridgeInstance trackThirdPartySharing: %s", e.getMessage());
        }
    }

    @JavascriptInterface
    public void trackMeasurementConsent(String consentMeasurementString) {
        if (!isInitialized()) {
            return;
        }
        Boolean consentMeasurement = AdTraceBridgeUtil.fieldToBoolean(consentMeasurementString);
        if (consentMeasurement != null) {
            AdTrace.trackMeasurementConsent(consentMeasurement);
        }
    }

    @JavascriptInterface
    public void getGoogleAdId(final String callback) {
        if (!isInitialized()) {
            return;
        }
        AdTrace.getGoogleAdId(application.getApplicationContext(), new OnDeviceIdsRead() {
            @Override
            public void onGoogleAdIdRead(String googleAdId) {
                AdTraceBridgeUtil.execSingleValueCallback(webView, callback, googleAdId);
            }
        });
    }

    @JavascriptInterface
    public String getAmazonAdId() {
        if (!isInitialized()) {
            return null;
        }
        return AdTrace.getAmazonAdId(application.getApplicationContext());
    }

    @JavascriptInterface
    public String getAdid() {
        if (!isInitialized()) {
            return null;
        }
        return AdTrace.getAdid();
    }

    @JavascriptInterface
    public void getAttribution(final String callback) {
        if (!isInitialized()) {
            return;
        }
        AdTraceAttribution attribution = AdTrace.getAttribution();
        AdTraceBridgeUtil.execAttributionCallbackCommand(webView, callback, attribution);
    }

    @JavascriptInterface
    public String getSdkVersion() {
        return AdTrace.getSdkVersion();
    }

    @JavascriptInterface
    public void fbPixelEvent(String pixelId, String event_name, String jsonString) {
        this.facebookSDKJSInterface.sendEvent(pixelId, event_name, jsonString);
    }

    @JavascriptInterface
    public void teardown() {
        isInitialized = false;
        shouldDeferredDeeplinkBeLaunched = true;
    }

    public void setWebView(WebView webView) {
        this.webView = webView;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webView.addJavascriptInterface(this, JAVASCRIPT_INTERFACE_NAME);
        }
    }

    public void setApplicationContext(Application application) {
        this.application = application;
    }

    public void unregister() {
        if (!isInitialized()) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            this.webView.removeJavascriptInterface(JAVASCRIPT_INTERFACE_NAME);
        }

        unregisterFacebookSDKJSInterface();

        application = null;
        webView = null;
        isInitialized = false;
    }

    public void unregisterFacebookSDKJSInterface() {
        if (!isInitialized()) {
            return;
        }

        if (this.facebookSDKJSInterface == null) {
            return;
        }

        String fbApplicationId = FacebookSDKJSInterface.getApplicationId(application.getApplicationContext());
        if (fbApplicationId == null) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            this.webView.removeJavascriptInterface(FB_JAVASCRIPT_INTERFACE_NAME_PREFIX + fbApplicationId);
        }

        this.facebookSDKJSInterface = null;
    }
}
