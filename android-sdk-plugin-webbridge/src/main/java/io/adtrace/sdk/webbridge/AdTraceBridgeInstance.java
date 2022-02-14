package io.adtrace.sdk.webbridge;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.app.Application;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

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
import io.adtrace.sdk.LogLevel;
import io.adtrace.sdk.OnAttributionChangedListener;
import io.adtrace.sdk.OnDeeplinkResponseListener;
import io.adtrace.sdk.OnDeviceIdsRead;
import io.adtrace.sdk.OnEventTrackingFailedListener;
import io.adtrace.sdk.OnEventTrackingSucceededListener;
import io.adtrace.sdk.OnSessionTrackingFailedListener;
import io.adtrace.sdk.OnSessionTrackingSucceededListener;

public class AdTraceBridgeInstance {
    private static final String LOG_LEVEL_VERBOSE = "VERBOSE";
    private static final String LOG_LEVEL_DEBUG = "DEBUG";
    private static final String LOG_LEVEL_INFO = "INFO";
    private static final String LOG_LEVEL_WARN = "WARN";
    private static final String LOG_LEVEL_ERROR = "ERROR";
    private static final String LOG_LEVEL_ASSERT = "ASSERT";
    private static final String LOG_LEVEL_SUPPRESS = "SUPPRESS";

    private WebView webView;
    private Application application;
    private boolean isInitialized = false;
    private boolean shouldDeferredDeeplinkBeLaunched = true;
    private FacebookSDKJSInterface facebookSDKJSInterface = null;

    AdTraceBridgeInstance() {}

    AdTraceBridgeInstance(Application application, WebView webView) {
        this.application = application;
        this.webView = webView;
        webView.addJavascriptInterface(this, "AdTraceBridge");
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
        this.webView.addJavascriptInterface(facebookSDKJSInterface, "fbmq_" + fbApplicationId);
    }

    @JavascriptInterface
    public void onCreate(String adTraceConfigString) {
        // Initialise SDK only if it's not already initialised.
        if (isInitialized) {
            AdTraceBridgeUtil.getLogger().warn("AdTrace bridge is already initialized. Ignoring further attempts");
            return;
        }
        if (!isInitialized()) {
            return;
        }

        try {
            AdTraceBridgeUtil.getLogger().verbose("Web bridge onCreate adTraceConfigString: " + adTraceConfigString);

            JSONObject jsonAdTraceConfig = new JSONObject(adTraceConfigString);
            Object appTokenField = jsonAdTraceConfig.get("appToken");
            Object environmentField = jsonAdTraceConfig.get("environment");
            Object allowSuppressLogLevelField = jsonAdTraceConfig.get("allowSuppressLogLevel");
            Object eventBufferingEnabledField = jsonAdTraceConfig.get("eventBufferingEnabled");
            Object sendInBackgroundField = jsonAdTraceConfig.get("sendInBackground");
            Object enableInstalledAppsField = jsonAdTraceConfig.get("enableInstalledApps");
            Object logLevelField = jsonAdTraceConfig.get("logLevel");
            Object sdkPrefixField = jsonAdTraceConfig.get("sdkPrefix");
            Object processNameField = jsonAdTraceConfig.get("processName");
            Object defaultTrackerField = jsonAdTraceConfig.get("defaultTracker");
            Object attributionCallbackNameField = jsonAdTraceConfig.get("attributionCallbackName");
            Object deviceKnownField = jsonAdTraceConfig.get("deviceKnown");
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

            String appToken = AdTraceBridgeUtil.fieldToString(appTokenField);
            String environment = AdTraceBridgeUtil.fieldToString(environmentField);
            Boolean allowSuppressLogLevel = AdTraceBridgeUtil.fieldToBoolean(allowSuppressLogLevelField);

            AdTraceConfig adTraceConfig;
            if (allowSuppressLogLevel == null) {
                adTraceConfig = new AdTraceConfig(application.getApplicationContext(), appToken, environment);
            } else {
                adTraceConfig = new AdTraceConfig(application.getApplicationContext(), appToken, environment, allowSuppressLogLevel.booleanValue());
            }

            if (!adTraceConfig.isValid()) {
                return;
            }

            // Event buffering
            Boolean eventBufferingEnabled = AdTraceBridgeUtil.fieldToBoolean(eventBufferingEnabledField);
            if (eventBufferingEnabled != null) {
                adTraceConfig.setEventBufferingEnabled(eventBufferingEnabled);
            }

            // Send in the background
            Boolean sendInBackground = AdTraceBridgeUtil.fieldToBoolean(sendInBackgroundField);
            if (sendInBackground != null) {
                adTraceConfig.setSendInBackground(sendInBackground);
            }

            // Enable installed apps
            Boolean enableInstalledApps = AdTraceBridgeUtil.fieldToBoolean(enableInstalledAppsField);
            if (enableInstalledApps != null) {
                adTraceConfig.enableSendInstalledApps(enableInstalledApps);
            }

            // Log level
            String logLevelString = AdTraceBridgeUtil.fieldToString(logLevelField);
            if (logLevelString != null) {
                if (logLevelString.equalsIgnoreCase(LOG_LEVEL_VERBOSE)) {
                    adTraceConfig.setLogLevel(LogLevel.VERBOSE);
                } else if (logLevelString.equalsIgnoreCase(LOG_LEVEL_DEBUG)) {
                    adTraceConfig.setLogLevel(LogLevel.DEBUG);
                } else if (logLevelString.equalsIgnoreCase(LOG_LEVEL_INFO)) {
                    adTraceConfig.setLogLevel(LogLevel.INFO);
                } else if (logLevelString.equalsIgnoreCase(LOG_LEVEL_WARN)) {
                    adTraceConfig.setLogLevel(LogLevel.WARN);
                } else if (logLevelString.equalsIgnoreCase(LOG_LEVEL_ERROR)) {
                    adTraceConfig.setLogLevel(LogLevel.ERROR);
                } else if (logLevelString.equalsIgnoreCase(LOG_LEVEL_ASSERT)) {
                    adTraceConfig.setLogLevel(LogLevel.ASSERT);
                } else if (logLevelString.equalsIgnoreCase(LOG_LEVEL_SUPPRESS)) {
                    adTraceConfig.setLogLevel(LogLevel.SUPRESS);
                }
            }

            // SDK prefix
            String sdkPrefix = AdTraceBridgeUtil.fieldToString(sdkPrefixField);
            if (sdkPrefix != null) {
                adTraceConfig.setSdkPrefix(sdkPrefix);
            }

            // Main process name
            String processName = AdTraceBridgeUtil.fieldToString(processNameField);
            if (processName != null) {
                adTraceConfig.setProcessName(processName);
            }

            // Default tracker
            String defaultTracker = AdTraceBridgeUtil.fieldToString(defaultTrackerField);
            if (defaultTracker != null) {
                adTraceConfig.setDefaultTracker(defaultTracker);
            }

            // Attribution callback name
            final String attributionCallbackName = AdTraceBridgeUtil.fieldToString(attributionCallbackNameField);
            if (attributionCallbackName != null) {
                adTraceConfig.setOnAttributionChangedListener(new OnAttributionChangedListener() {
                    @Override
                    public void onAttributionChanged(AdTraceAttribution attribution) {
                        AdTraceBridgeUtil.execAttributionCallbackCommand(webView, attributionCallbackName, attribution);
                    }
                });
            }

            // Is device known
            Boolean deviceKnown = AdTraceBridgeUtil.fieldToBoolean(deviceKnownField);
            if (deviceKnown != null) {
                adTraceConfig.setDeviceKnown(deviceKnown);
            }

            // Event success callback
            final String eventSuccessCallbackName = AdTraceBridgeUtil.fieldToString(eventSuccessCallbackNameField);
            if (eventSuccessCallbackName != null) {
                adTraceConfig.setOnEventTrackingSucceededListener(new OnEventTrackingSucceededListener() {
                    public void onFinishedEventTrackingSucceeded(AdTraceEventSuccess eventSuccessResponseData) {
                        AdTraceBridgeUtil.execEventSuccessCallbackCommand(webView, eventSuccessCallbackName, eventSuccessResponseData);
                    }
                });
            }

            // Event failure callback
            final String eventFailureCallbackName = AdTraceBridgeUtil.fieldToString(eventFailureCallbackNameField);
            if (eventFailureCallbackName != null) {
                adTraceConfig.setOnEventTrackingFailedListener(new OnEventTrackingFailedListener() {
                    public void onFinishedEventTrackingFailed(AdTraceEventFailure eventFailureResponseData) {
                        AdTraceBridgeUtil.execEventFailureCallbackCommand(webView, eventFailureCallbackName, eventFailureResponseData);
                    }
                });
            }

            // Session success callback
            final String sessionSuccessCallbackName = AdTraceBridgeUtil.fieldToString(sessionSuccessCallbackNameField);
            if (sessionSuccessCallbackName != null) {
                adTraceConfig.setOnSessionTrackingSucceededListener(new OnSessionTrackingSucceededListener() {
                    @Override
                    public void onFinishedSessionTrackingSucceeded(AdTraceSessionSuccess sessionSuccessResponseData) {
                        AdTraceBridgeUtil.execSessionSuccessCallbackCommand(webView, sessionSuccessCallbackName, sessionSuccessResponseData);
                    }
                });
            }

            // Session failure callback
            final String sessionFailureCallbackName = AdTraceBridgeUtil.fieldToString(sessionFailureCallbackNameField);
            if (sessionFailureCallbackName != null) {
                adTraceConfig.setOnSessionTrackingFailedListener(new OnSessionTrackingFailedListener() {
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
                adTraceConfig.setOnDeeplinkResponseListener(new OnDeeplinkResponseListener() {
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
                adTraceConfig.setDelayStart(delayStart);
            }

            // User agent
            String userAgent = AdTraceBridgeUtil.fieldToString(userAgentField);
            if (userAgent != null) {
                adTraceConfig.setUserAgent(userAgent);
            }

            // App secret
            Long secretId = AdTraceBridgeUtil.fieldToLong(secretIdField);
            Long info1 = AdTraceBridgeUtil.fieldToLong(info1Field);
            Long info2 = AdTraceBridgeUtil.fieldToLong(info2Field);
            Long info3 = AdTraceBridgeUtil.fieldToLong(info3Field);
            Long info4 = AdTraceBridgeUtil.fieldToLong(info4Field);
            if (secretId != null && info1 != null && info2 != null && info3 != null && info4 != null) {
                adTraceConfig.setAppSecret(secretId, info1, info2, info3, info4);
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

            // Manually call onResume() because web view initialisation will happen a bit delayed.
            // With this delay, it will miss lifecycle callback onResume() initial firing.
            AdTrace.onCreate(adTraceConfig);
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
    public void trackEvent(String adTraceEventString) {
        if (!isInitialized()) {
            return;
        }

        try {
            JSONObject jsonAdTraceEvent = new JSONObject(adTraceEventString);

            Object eventTokenField = jsonAdTraceEvent.get("eventToken");
            Object revenueField = jsonAdTraceEvent.get("revenue");
            Object currencyField = jsonAdTraceEvent.get("currency");
            Object callbackParametersField = jsonAdTraceEvent.get("callbackParameters");
            Object partnerParametersField = jsonAdTraceEvent.get("partnerParameters");
            Object orderIdField = jsonAdTraceEvent.get("orderId");
            Object callbackIdField = jsonAdTraceEvent.get("callbackId");
            Object eventValueField = jsonAdTraceEvent.get("eventValue");

            String eventToken = AdTraceBridgeUtil.fieldToString(eventTokenField);
            AdTraceEvent adTraceEvent = new AdTraceEvent(eventToken);

            if (!adTraceEvent.isValid()) {
                return;
            }

            // Revenue
            Double revenue = AdTraceBridgeUtil.fieldToDouble(revenueField);
            String currency = AdTraceBridgeUtil.fieldToString(currencyField);
            if (revenue != null && currency != null) {
                adTraceEvent.setRevenue(revenue, currency);
            }

            // Callback parameters
            String[] callbackParameters = AdTraceBridgeUtil.jsonArrayToArray((JSONArray)callbackParametersField);
            if (callbackParameters != null) {
                for (int i = 0; i < callbackParameters.length; i += 2) {
                    String key = callbackParameters[i];
                    String value = callbackParameters[i+1];
                    adTraceEvent.addCallbackParameter(key, value);
                }
            }

            // Partner parameters
            String[] partnerParameters = AdTraceBridgeUtil.jsonArrayToArray((JSONArray)partnerParametersField);
            if (partnerParameters != null) {
                for (int i = 0; i < partnerParameters.length; i += 2) {
                    String key = partnerParameters[i];
                    String value = partnerParameters[i+1];
                    adTraceEvent.addPartnerParameter(key, value);
                }
            }

            // Revenue deduplication
            String orderId = AdTraceBridgeUtil.fieldToString(orderIdField);
            if (orderId != null) {
                adTraceEvent.setOrderId(orderId);
            }

            // Callback id
            String callbackId = AdTraceBridgeUtil.fieldToString(callbackIdField);
            if (callbackId != null) {
                adTraceEvent.setCallbackId(callbackId);
            }

            // Event value
            String eventValue = AdTraceBridgeUtil.fieldToString(eventValueField);
            if (eventValue != null) {
                adTraceEvent.setEventValue(eventValue);
            }

            // Track event
            AdTrace.trackEvent(adTraceEvent);
        } catch (Exception e) {
            AdTraceFactory.getLogger().error("AdTraceBridgeInstance trackEvent: %s", e.getMessage());
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
    public void setEnableLocation(String isEnableLocationString) {
        if (!isInitialized()) {
            return;
        }
        Boolean isEnable = AdTraceBridgeUtil.fieldToBoolean(isEnableLocationString);
        if (isEnable != null) {
            AdTrace.enableLocation(isEnable);
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
    public void setTestOptions(final String testOptionsString) {
        AdTraceFactory.getLogger().verbose("AdTraceBridgeInstance setTestOptions: %s", testOptionsString);

        if (!isInitialized()) {
            return;
        }

        try {
            AdTraceTestOptions adTraceTestOptions = new AdTraceTestOptions();
            JSONObject jsonAdTraceTestOptions = new JSONObject(testOptionsString);

            Object baseUrlField = jsonAdTraceTestOptions.get("baseUrl");
            Object gdprUrlField = jsonAdTraceTestOptions.get("gdprUrl");
            Object basePathField = jsonAdTraceTestOptions.get("basePath");
            Object gdprPathField = jsonAdTraceTestOptions.get("gdprPath");
            Object useTestConnectionOptionsField = jsonAdTraceTestOptions.get("useTestConnectionOptions");
            Object timerIntervalInMillisecondsField = jsonAdTraceTestOptions.get("timerIntervalInMilliseconds");
            Object timerStartInMillisecondsField = jsonAdTraceTestOptions.get("timerStartInMilliseconds");
            Object sessionIntervalInMillisecondsField = jsonAdTraceTestOptions.get("sessionIntervalInMilliseconds");
            Object subsessionIntervalInMillisecondsField = jsonAdTraceTestOptions.get("subsessionIntervalInMilliseconds");
            Object teardownField = jsonAdTraceTestOptions.get("teardown");
            Object tryInstallReferrerField = jsonAdTraceTestOptions.get("tryInstallReferrer");
            Object noBackoffWaitField = jsonAdTraceTestOptions.get("noBackoffWait");
            Object hasContextField = jsonAdTraceTestOptions.get("hasContext");

            String gdprUrl = AdTraceBridgeUtil.fieldToString(gdprUrlField);
            if (gdprUrl != null) {
                adTraceTestOptions.gdprUrl = gdprUrl;
            }

            String baseUrl = AdTraceBridgeUtil.fieldToString(baseUrlField);
            if (baseUrl != null) {
                adTraceTestOptions.baseUrl = baseUrl;
            }

            String basePath = AdTraceBridgeUtil.fieldToString(basePathField);
            if (basePath != null) {
                adTraceTestOptions.basePath = basePath;
            }

            String gdprPath = AdTraceBridgeUtil.fieldToString(gdprPathField);
            if (gdprPath != null) {
                adTraceTestOptions.gdprPath = gdprPath;
            }

            Boolean useTestConnectionOptions = AdTraceBridgeUtil.fieldToBoolean(useTestConnectionOptionsField);
            if (useTestConnectionOptions != null) {
                adTraceTestOptions.useTestConnectionOptions = useTestConnectionOptions;
            }

            Long timerIntervalInMilliseconds = AdTraceBridgeUtil.fieldToLong(timerIntervalInMillisecondsField);
            if (timerIntervalInMilliseconds != null) {
                adTraceTestOptions.timerIntervalInMilliseconds = timerIntervalInMilliseconds;
            }

            Long timerStartInMilliseconds = AdTraceBridgeUtil.fieldToLong(timerStartInMillisecondsField);
            if (timerStartInMilliseconds != null) {
                adTraceTestOptions.timerStartInMilliseconds = timerStartInMilliseconds;
            }

            Long sessionIntervalInMilliseconds = AdTraceBridgeUtil.fieldToLong(sessionIntervalInMillisecondsField);
            if (sessionIntervalInMilliseconds != null) {
                adTraceTestOptions.sessionIntervalInMilliseconds = sessionIntervalInMilliseconds;
            }

            Long subsessionIntervalInMilliseconds = AdTraceBridgeUtil.fieldToLong(subsessionIntervalInMillisecondsField);
            if (subsessionIntervalInMilliseconds != null) {
                adTraceTestOptions.subsessionIntervalInMilliseconds = subsessionIntervalInMilliseconds;
            }

            Boolean teardown = AdTraceBridgeUtil.fieldToBoolean(teardownField);
            if (teardown != null) {
                adTraceTestOptions.teardown = teardown;
            }

            Boolean tryInstallReferrer = AdTraceBridgeUtil.fieldToBoolean(tryInstallReferrerField);
            if (tryInstallReferrer != null) {
                adTraceTestOptions.tryInstallReferrer = tryInstallReferrer;
            }

            Boolean noBackoffWait = AdTraceBridgeUtil.fieldToBoolean(noBackoffWaitField);
            if (noBackoffWait != null) {
                adTraceTestOptions.noBackoffWait = noBackoffWait;
            }

            Boolean hasContext = AdTraceBridgeUtil.fieldToBoolean(hasContextField);
            if (hasContext != null && hasContext.booleanValue()) {
                adTraceTestOptions.context = application.getApplicationContext();
            }

            AdTrace.setTestOptions(adTraceTestOptions);
        } catch (Exception e) {
            AdTraceFactory.getLogger().error("AdTraceBridgeInstance setTestOptions: %s", e.getMessage());
        }
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
    }

    public void setApplicationContext(Application application) {
        this.application = application;
    }
}
