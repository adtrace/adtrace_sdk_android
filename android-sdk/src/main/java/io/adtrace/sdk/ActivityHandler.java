package io.adtrace.sdk;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (github.com/namini40) on April 2022.
 * Notice: See LICENSE.txt for modification and distribution information
 * Copyright © 2022.
 */


import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Handler;

import io.adtrace.sdk.network.ActivityPackageSender;
import io.adtrace.sdk.network.IActivityPackageSender;
import io.adtrace.sdk.network.UtilNetworking;
import io.adtrace.sdk.scheduler.SingleThreadCachedScheduler;
import io.adtrace.sdk.scheduler.ThreadExecutor;
import io.adtrace.sdk.scheduler.TimerCycle;
import io.adtrace.sdk.scheduler.TimerOnce;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static io.adtrace.sdk.Constants.ACTIVITY_STATE_FILENAME;
import static io.adtrace.sdk.Constants.ATTRIBUTION_FILENAME;
import static io.adtrace.sdk.Constants.REFERRER_API_META;
import static io.adtrace.sdk.Constants.MIN_LAST_INTERVAL_HARD_RESET_THRESHOLD;
import static io.adtrace.sdk.Constants.REFERRER_API_SAMSUNG;
import static io.adtrace.sdk.Constants.REFERRER_API_VIVO;
import static io.adtrace.sdk.Constants.REFERRER_API_XIAOMI;
import static io.adtrace.sdk.Constants.SESSION_CALLBACK_PARAMETERS_FILENAME;
import static io.adtrace.sdk.Constants.SESSION_PARTNER_PARAMETERS_FILENAME;

public class ActivityHandler implements IActivityHandler {
    private static long FOREGROUND_TIMER_INTERVAL;
    private static long FOREGROUND_TIMER_START;
    private static long BACKGROUND_TIMER_INTERVAL;
    private static long SESSION_INTERVAL;
    private static long SUBSESSION_INTERVAL;
    private static final String TIME_TRAVEL = "Time travel!";
    private static final String ACTIVITY_STATE_NAME = "Activity state";
    private static final String ATTRIBUTION_NAME = "Attribution";
    private static final String FOREGROUND_TIMER_NAME = "Foreground timer";
    private static final String BACKGROUND_TIMER_NAME = "Background timer";
    private static final String DELAY_START_TIMER_NAME = "Delay Start timer";
    private static final String SESSION_CALLBACK_PARAMETERS_NAME = "Session Callback parameters";
    private static final String SESSION_PARTNER_PARAMETERS_NAME = "Session Partner parameters";
    private static final String SESSION_PARAMETERS_NAME = "Session parameters";

    private ThreadExecutor executor;
    private IPackageHandler packageHandler;
    private ActivityState activityState;
    private ILogger logger;
    private TimerCycle foregroundTimer;
    private TimerOnce backgroundTimer;
    private TimerOnce delayStartTimer;
    private InternalState internalState;
    private String basePath;
    private String gdprPath;
    private String subscriptionPath;

    private DeviceInfo deviceInfo;
    private AdTraceConfig adtraceConfig; // always valid after construction
    private AdTraceAttribution attribution;
    private IAttributionHandler attributionHandler;
    private ISdkClickHandler sdkClickHandler;
    private IPurchaseVerificationHandler purchaseVerificationHandler;
    private SessionParameters sessionParameters;
    private InstallReferrer installReferrer;
    private InstallReferrerHuawei installReferrerHuawei;
    private InstallReferrerMeta installReferrerMeta;

    @Override
    public void teardown() {
        if (backgroundTimer != null) {
            backgroundTimer.teardown();
        }
        if (foregroundTimer != null) {
            foregroundTimer.teardown();
        }
        if (delayStartTimer != null) {
            delayStartTimer.teardown();
        }
        if (executor != null) {
            executor.teardown();
        }
        if (packageHandler != null) {
            packageHandler.teardown();
        }
        if (attributionHandler != null) {
            attributionHandler.teardown();
        }
        if (sdkClickHandler != null) {
            sdkClickHandler.teardown();
        }
        if (purchaseVerificationHandler != null) {
            purchaseVerificationHandler.teardown();
        }
        if (sessionParameters != null) {
            if (sessionParameters.callbackParameters != null) {
                sessionParameters.callbackParameters.clear();
            }
            if (sessionParameters.partnerParameters != null) {
                sessionParameters.partnerParameters.clear();
            }
        }

        teardownActivityStateS();
        teardownAttributionS();
        teardownAllSessionParametersS();

        packageHandler = null;
        logger = null;
        foregroundTimer = null;
        executor = null;
        backgroundTimer = null;
        delayStartTimer = null;
        internalState = null;
        deviceInfo = null;
        adtraceConfig = null;
        attributionHandler = null;
        sdkClickHandler = null;
        purchaseVerificationHandler = null;
        sessionParameters = null;
    }

    static void deleteState(Context context) {
        deleteActivityState(context);
        deleteAttribution(context);
        deleteSessionCallbackParameters(context);
        deleteSessionPartnerParameters(context);

        SharedPreferencesManager.getDefaultInstance(context).clear();
    }

    public class InternalState {
        boolean enabled;
        boolean offline;
        boolean background;
        boolean delayStart;
        boolean updatePackages;
        boolean firstLaunch;
        boolean sessionResponseProcessed;
        boolean firstSdkStart;
        boolean preinstallHasBeenRead;

        public boolean isEnabled() {
            return enabled;
        }

        public boolean isDisabled() {
            return !enabled;
        }

        public boolean isOffline() {
            return offline;
        }

        public boolean isOnline() {
            return !offline;
        }

        public boolean isInBackground() {
            return background;
        }

        public boolean isInForeground() {
            return !background;
        }

        public boolean isInDelayedStart() {
            return delayStart;
        }

        public boolean isNotInDelayedStart() {
            return !delayStart;
        }

        public boolean itHasToUpdatePackages() {
            return updatePackages;
        }

        public boolean isFirstLaunch() {
            return firstLaunch;
        }

        public boolean isNotFirstLaunch() {
            return !firstLaunch;
        }

        public boolean hasSessionResponseNotBeenProcessed() {
            return !sessionResponseProcessed;
        }

        public boolean hasFirstSdkStartOcurred() {
            return firstSdkStart;
        }

        public boolean hasFirstSdkStartNotOcurred() {
            return !firstSdkStart;
        }

        public boolean hasPreinstallBeenRead() {
            return preinstallHasBeenRead;
        }
    }

    private ActivityHandler(AdTraceConfig adtraceConfig) {
        init(adtraceConfig);

        // init logger to be available everywhere
        logger = AdTraceFactory.getLogger();

        logger.lockLogLevel();

        executor = new SingleThreadCachedScheduler("ActivityHandler");
        internalState = new InternalState();

        // enabled by default
        internalState.enabled = adtraceConfig.startEnabled != null ? adtraceConfig.startEnabled : true;
        // online by default
        internalState.offline = adtraceConfig.startOffline;
        // in the background by default
        internalState.background = true;
        // delay start not configured by default
        internalState.delayStart = false;
        // does not need to update packages by default
        internalState.updatePackages = false;
        // does not have the session response by default
        internalState.sessionResponseProcessed = false;
        // does not have first start by default
        internalState.firstSdkStart = false;
        // preinstall has not been read by default
        internalState.preinstallHasBeenRead = false;

        executor.submit(new Runnable() {
            @Override
            public void run() {
                initI();
            }
        });
    }

    @Override
    public AdTraceConfig getAdTraceConfig() {
        return adtraceConfig;
    }

    @Override
    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    @Override
    public ActivityState getActivityState() {
        return activityState;
    }

    @Override
    public SessionParameters getSessionParameters() {
        return sessionParameters;
    }

    @Override
    public void init(AdTraceConfig adtraceConfig) {
        this.adtraceConfig = adtraceConfig;
    }

    public static ActivityHandler getInstance(AdTraceConfig adtraceConfig) {
        if (adtraceConfig == null) {
            AdTraceFactory.getLogger().error("AdTraceConfig missing");
            return null;
        }

        if (!adtraceConfig.isValid()) {
            AdTraceFactory.getLogger().error("AdTraceConfig not initialized correctly");
            return null;
        }

        if (adtraceConfig.processName != null) {
            int currentPid = android.os.Process.myPid();
            ActivityManager manager = (ActivityManager) adtraceConfig.context.getSystemService(Context.ACTIVITY_SERVICE);

            if (manager == null) {
                return null;
            }

            List<ActivityManager.RunningAppProcessInfo> processInfoList = manager.getRunningAppProcesses();
            if (processInfoList == null) {
                return null;
            }

            for (ActivityManager.RunningAppProcessInfo processInfo : processInfoList) {
                if (processInfo.pid == currentPid) {
                    if (!processInfo.processName.equalsIgnoreCase(adtraceConfig.processName)) {
                        AdTraceFactory.getLogger().info("Skipping initialization in background process (%s)", processInfo.processName);
                        return null;
                    }
                    break;
                }
            }
        }

        ActivityHandler activityHandler = new ActivityHandler(adtraceConfig);
        return activityHandler;
    }

    @Override
    public void onResume() {
        internalState.background = false;

        executor.submit(new Runnable() {
            @Override
            public void run() {
                delayStartI();

                stopBackgroundTimerI();

                startForegroundTimerI();

                logger.verbose("Subsession start");

                startI();
            }
        });
    }

    @Override
    public void onPause() {
        internalState.background = true;

        executor.submit(new Runnable() {
            @Override
            public void run() {
                stopForegroundTimerI();

                startBackgroundTimerI();

                logger.verbose("Subsession end");

                endI();
            }
        });
    }

    @Override
    public void trackEvent(final AdTraceEvent event) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                if (internalState.hasFirstSdkStartNotOcurred()) {
                    logger.warn("Event tracked before first activity resumed.\n" +
                            "If it was triggered in the Application class, it might timestamp or even send an install long before the user opens the app.\n");
                    startI();
                }
                trackEventI(event);
            }
        });
    }

    @Override
    public void finishedTrackingActivity(ResponseData responseData) {
        // redirect session responses to attribution handler to check for attribution information
        if (responseData instanceof SessionResponseData) {
            logger.debug("Finished tracking session");
            attributionHandler.checkSessionResponse((SessionResponseData) responseData);
            return;
        }
        // redirect sdk click responses to attribution handler to check for attribution information
        if (responseData instanceof SdkClickResponseData) {
            checkForInstallReferrerInfo((SdkClickResponseData) responseData);
            attributionHandler.checkSdkClickResponse((SdkClickResponseData)responseData);
            return;
        }
        // check if it's an event response
        if (responseData instanceof EventResponseData) {
            launchEventResponseTasks((EventResponseData)responseData);
            return;
        }
        // check if it's a purchase verification response
        if (responseData instanceof PurchaseVerificationResponseData) {
            launchPurchaseVerificationResponseTasks((PurchaseVerificationResponseData)responseData);
        }
    }

    @Override
    public void setEnabled(final boolean enabled) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                setEnabledI(enabled);
            }
        });
    }

    @Override
    public void setOfflineMode(final boolean offline) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                setOfflineModeI(offline);
            }
        });
    }

    @Override
    public boolean isEnabled() {
        return isEnabledI();
    }

    private boolean isEnabledI() {
        if (activityState != null) {
            return activityState.enabled;
        } else {
            return internalState.isEnabled();
        }
    }

    @Override
    public void readOpenUrl(final Uri url, final long clickTime) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                readOpenUrlI(url, clickTime);
            }
        });
    }

    private void updateAdidI(final String adid) {
        if (adid == null) {
            return;
        }

        if (adid.equals(activityState.adid)) {
            return;
        }

        activityState.adid = adid;
        writeActivityStateI();
        return;
    }

    @Override
    public boolean updateAttributionI(AdTraceAttribution attribution) {
        if (attribution == null) {
            return false;
        }

        if (adtraceConfig.finalAttributionEnabled && activityState.askingAttribution) {
            logger.info("Not updating intermediate attribution");
            return false;
        }

        if (attribution.equals(this.attribution)) {
            return false;
        }

        this.attribution = attribution;
        writeAttributionI();
        return true;
    }

    @Override
    public void setAskingAttribution(final boolean askingAttribution) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                setAskingAttributionI(askingAttribution);
            }
        });
    }

    @Override
    public void sendReftagReferrer() {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                sendReftagReferrerI();
            }
        });
    }

    @Override
    public void sendPreinstallReferrer() {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                sendPreinstallReferrerI();
            }
        });
    }

    @Override
    public void sendInstallReferrer(final ReferrerDetails referrerDetails,
                                    final String referrerApi) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                sendInstallReferrerI(referrerDetails, referrerApi);
            }
        });
    }

    @Override
    public void launchEventResponseTasks(final EventResponseData eventResponseData) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                launchEventResponseTasksI(eventResponseData);
            }
        });
    }

    @Override
    public void launchSdkClickResponseTasks(final SdkClickResponseData sdkClickResponseData) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                launchSdkClickResponseTasksI(sdkClickResponseData);
            }
        });
    }

    @Override
    public void launchSessionResponseTasks(final SessionResponseData sessionResponseData) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                launchSessionResponseTasksI(sessionResponseData);
            }
        });
    }

    @Override
    public void launchAttributionResponseTasks(final AttributionResponseData attributionResponseData) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                launchAttributionResponseTasksI(attributionResponseData);
            }
        });
    }

    @Override
    public void launchPurchaseVerificationResponseTasks(final PurchaseVerificationResponseData purchaseVerificationResponseData) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                launchPurchaseVerificationResponseTasksI(purchaseVerificationResponseData);
            }
        });
    }

    @Override
    public void sendFirstPackages () {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                sendFirstPackagesI();
            }
        });
    }

    @Override
    public void addSessionCallbackParameter(final String key, final String value) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                addSessionCallbackParameterI(key, value);
            }
        });
    }

    @Override
    public void addSessionPartnerParameter(final String key, final String value) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                addSessionPartnerParameterI(key, value);
            }
        });
    }

    @Override
    public void removeSessionCallbackParameter(final String key) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                removeSessionCallbackParameterI(key);
            }
        });
    }

    @Override
    public void removeSessionPartnerParameter(final String key) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                removeSessionPartnerParameterI(key);
            }
        });
    }

    @Override
    public void resetSessionCallbackParameters() {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                resetSessionCallbackParametersI();
            }
        });
    }

    @Override
    public void resetSessionPartnerParameters() {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                resetSessionPartnerParametersI();
            }
        });
    }

    @Override
    public void setPushToken(final String token, final boolean preSaved) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                if (!preSaved) {
                    SharedPreferencesManager.getDefaultInstance(getContext()).savePushToken(token);
                }

                if (internalState.hasFirstSdkStartNotOcurred()) {
                    // No install has been tracked so far.
                    // Push token is saved, ready for the session package to pick it up.
                    return;
                } else {
                    setPushTokenI(token);
                }
            }
        });
    }

    @Override
    public void gdprForgetMe() {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                gdprForgetMeI();
            }
        });
    }

    @Override
    public void disableThirdPartySharing() {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                disableThirdPartySharingI();
            }
        });
    }

    @Override
    public void trackThirdPartySharing(final AdTraceThirdPartySharing adtraceThirdPartySharing) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                trackThirdPartySharingI(adtraceThirdPartySharing);
            }
        });
    }

    @Override
    public void trackMeasurementConsent(final boolean consentMeasurement) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                trackMeasurementConsentI(consentMeasurement);
            }
        });
    }

    @Override
    public void trackAdRevenue(final String source, final JSONObject adRevenueJson) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                trackAdRevenueI(source, adRevenueJson);
            }
        });
    }

    @Override
    public void trackAdRevenue(final AdTraceAdRevenue adtraceAdRevenue) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                trackAdRevenueI(adtraceAdRevenue);
            }
        });
    }

    @Override
    public void trackPlayStoreSubscription(final AdTracePlayStoreSubscription subscription) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                trackSubscriptionI(subscription);
            }
        });
    }

    @Override
    public void gotOptOutResponse() {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                gotOptOutResponseI();
            }
        });
    }

    @Override
    public Context getContext() {
        return adtraceConfig.context;
    }

    public void foregroundTimerFired() {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                foregroundTimerFiredI();
            }
        });
    }

    public void backgroundTimerFired() {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                backgroundTimerFiredI();
            }
        });
    }

    @Override
    public String getAdid() {
        if (activityState == null) {
            return null;
        }
        return activityState.adid;
    }

    @Override
    public AdTraceAttribution getAttribution() {
        return attribution;
    }

    @Override
    public void verifyPurchase(final AdTracePurchase purchase, final OnPurchaseVerificationFinishedListener callback) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                verifyPurchaseI(purchase, callback);
            }
        });
    }

    public InternalState getInternalState() {
        return internalState;
    }

    private void initI() {
        SESSION_INTERVAL = AdTraceFactory.getSessionInterval();
        SUBSESSION_INTERVAL = AdTraceFactory.getSubsessionInterval();
        // get timer values
        FOREGROUND_TIMER_INTERVAL = AdTraceFactory.getTimerInterval();
        FOREGROUND_TIMER_START = AdTraceFactory.getTimerStart();
        BACKGROUND_TIMER_INTERVAL = AdTraceFactory.getTimerInterval();

        // has to be read in the background
        readAttributionI(adtraceConfig.context);
        readActivityStateI(adtraceConfig.context);

        sessionParameters = new SessionParameters();
        readSessionCallbackParametersI(adtraceConfig.context);
        readSessionPartnerParametersI(adtraceConfig.context);

        if (adtraceConfig.startEnabled != null) {
            adtraceConfig.preLaunchActions.preLaunchActionsArray.add(new IRunActivityHandler() {
                @Override
                public void run(ActivityHandler activityHandler) {
                    activityHandler.setEnabledI(adtraceConfig.startEnabled);
                }
            });
        }

        if (internalState.hasFirstSdkStartOcurred()) {
            internalState.enabled = activityState.enabled;
            internalState.updatePackages = activityState.updatePackages;
            internalState.firstLaunch = false;
        } else {
            internalState.firstLaunch = true; // first launch if activity state is null
        }

        readConfigFile(adtraceConfig.context);

        deviceInfo = new DeviceInfo(adtraceConfig);

        if (adtraceConfig.eventBufferingEnabled) {
            logger.info("Event buffering is enabled");
        }

        deviceInfo.reloadPlayIds(adtraceConfig);
        if (deviceInfo.playAdId == null) {
            if (!Util.canReadPlayIds(adtraceConfig)) {
                if (adtraceConfig.coppaCompliantEnabled) {
                    logger.info("Cannot read Google Play Services Advertising ID with COPPA enabled");
                }

                if (adtraceConfig.playStoreKidsAppEnabled) {
                    logger.info("Cannot read Google Play Services Advertising ID with play store kids app enabled");
                }
            } else {
                logger.warn("Unable to get Google Play Services Advertising ID at start time");
            }

            if (deviceInfo.androidId == null) {
                if (!Util.canReadNonPlayIds(adtraceConfig)) {
                    if (adtraceConfig.coppaCompliantEnabled) {
                        logger.info("Cannot read non Play IDs with COPPA enabled");
                    }

                    if (adtraceConfig.playStoreKidsAppEnabled) {
                        logger.info("Cannot read non Play IDs with play store kids app enabled");
                    }
                } else {
                    logger.error("Unable to get any Device IDs. Please check if Proguard is correctly set with AdTrace SDK");
                }
            }
        } else {
            logger.info("Google Play Services Advertising ID read correctly at start time");
        }

        if (adtraceConfig.defaultTracker != null) {
            logger.info("Default tracker: '%s'", adtraceConfig.defaultTracker);
        }

        if (adtraceConfig.pushToken != null) {
            logger.info("Push token: '%s'", adtraceConfig.pushToken);
            if (internalState.hasFirstSdkStartOcurred()) {
                // since sdk has already started, try to send current push token
                setPushToken(adtraceConfig.pushToken, false);
            } else {
                // since sdk has not yet started, save current push token for when it does
                SharedPreferencesManager.getDefaultInstance(getContext()).savePushToken(adtraceConfig.pushToken);
            }
        } else {
            // since sdk has already started, check if there is a saved push from previous runs
            if (internalState.hasFirstSdkStartOcurred()) {
                String savedPushToken = SharedPreferencesManager.getDefaultInstance(getContext()).getPushToken();
                if(savedPushToken!=null)
                    setPushToken(savedPushToken, true);
            }
        }

        // GDPR
        if (internalState.hasFirstSdkStartOcurred()) {
            SharedPreferencesManager sharedPreferencesManager = SharedPreferencesManager.getDefaultInstance(getContext());
            if (sharedPreferencesManager.getGdprForgetMe()) {
                gdprForgetMe();
            } else {
                if (sharedPreferencesManager.getDisableThirdPartySharing()) {
                    disableThirdPartySharing();
                }
                for (AdTraceThirdPartySharing adtraceThirdPartySharing :
                        adtraceConfig.preLaunchActions.preLaunchAdTraceThirdPartySharingArray) {
                    trackThirdPartySharing(adtraceThirdPartySharing);
                }
                if (adtraceConfig.preLaunchActions.lastMeasurementConsentTracked != null) {
                    trackMeasurementConsent(
                            adtraceConfig.preLaunchActions.
                                    lastMeasurementConsentTracked.booleanValue());
                }

                adtraceConfig.preLaunchActions.preLaunchAdTraceThirdPartySharingArray =
                        new ArrayList<>();
                adtraceConfig.preLaunchActions.lastMeasurementConsentTracked = null;
            }
        }

        foregroundTimer = new TimerCycle(
                new Runnable() {
                    @Override
                    public void run() {
                        foregroundTimerFired();
                    }
                }, FOREGROUND_TIMER_START, FOREGROUND_TIMER_INTERVAL, FOREGROUND_TIMER_NAME);

        // create background timer
        if (adtraceConfig.sendInBackground) {
            logger.info("Send in background configured");

            backgroundTimer = new TimerOnce(new Runnable() {
                @Override
                public void run() {
                    backgroundTimerFired();
                }
            }, BACKGROUND_TIMER_NAME);
        }

        // configure delay start timer
        if (internalState.hasFirstSdkStartNotOcurred() &&
                adtraceConfig.delayStart != null &&
                adtraceConfig.delayStart > 0.0) {
            logger.info("Delay start configured");
            internalState.delayStart = true;
            delayStartTimer = new TimerOnce(new Runnable() {
                @Override
                public void run() {
                    sendFirstPackages();
                }
            }, DELAY_START_TIMER_NAME);
        }

        UtilNetworking.setUserAgent(adtraceConfig.userAgent);

        IActivityPackageSender packageHandlerActivitySender =
                new ActivityPackageSender(
                        adtraceConfig.urlStrategy,
                        adtraceConfig.basePath,
                        adtraceConfig.gdprPath,
                        adtraceConfig.subscriptionPath,
                        adtraceConfig.purchaseVerificationPath,
                        deviceInfo.clientSdk);
        packageHandler = AdTraceFactory.getPackageHandler(
                this,
                adtraceConfig.context,
                toSendI(false),
                packageHandlerActivitySender);

        IActivityPackageSender attributionHandlerActivitySender =
                new ActivityPackageSender(
                        adtraceConfig.urlStrategy,
                        adtraceConfig.basePath,
                        adtraceConfig.gdprPath,
                        adtraceConfig.subscriptionPath,
                        adtraceConfig.purchaseVerificationPath,
                        deviceInfo.clientSdk);

        attributionHandler = AdTraceFactory.getAttributionHandler(
                this,
                toSendI(false),
                attributionHandlerActivitySender);

        IActivityPackageSender sdkClickHandlerActivitySender =
                new ActivityPackageSender(
                        adtraceConfig.urlStrategy,
                        adtraceConfig.basePath,
                        adtraceConfig.gdprPath,
                        adtraceConfig.subscriptionPath,
                        adtraceConfig.purchaseVerificationPath,
                        deviceInfo.clientSdk);

        sdkClickHandler = AdTraceFactory.getSdkClickHandler(
                this,
                toSendI(true),
                sdkClickHandlerActivitySender);

        IActivityPackageSender purchaseVerificationHandlerActivitySender =
                new ActivityPackageSender(
                        adtraceConfig.urlStrategy,
                        adtraceConfig.basePath,
                        adtraceConfig.gdprPath,
                        adtraceConfig.subscriptionPath,
                        adtraceConfig.purchaseVerificationPath,
                        deviceInfo.clientSdk);

        purchaseVerificationHandler = AdTraceFactory.getPurchaseVerificationHandler(
                this,
                toSendI(true),
                purchaseVerificationHandlerActivitySender);

        if (isToUpdatePackagesI()) {
            updatePackagesI();
        }

        installReferrer = new InstallReferrer(adtraceConfig.context, new InstallReferrerReadListener() {
            @Override
            public void onInstallReferrerRead(ReferrerDetails referrerDetails, String referrerApi) {
                sendInstallReferrer(referrerDetails, referrerApi);
            }
        });

        installReferrerHuawei = new InstallReferrerHuawei(adtraceConfig.context, new InstallReferrerReadListener() {
            @Override
            public void onInstallReferrerRead(ReferrerDetails referrerDetails, String referrerApi) {
                sendInstallReferrer(referrerDetails, referrerApi);
            }
        });

        installReferrerMeta = new InstallReferrerMeta(adtraceConfig.context, adtraceConfig.fbAppId,
                new InstallReferrerReadListener() {
                    @Override
                    public void onInstallReferrerRead(ReferrerDetails referrerDetails, String referrerApi) {
                        sendInstallReferrer(referrerDetails, referrerApi);
                    }
                });
        preLaunchActionsI(adtraceConfig.preLaunchActions.preLaunchActionsArray);
        sendReftagReferrerI();
    }

    private void checkForPreinstallI() {
        if (activityState == null) return;
        if (!activityState.enabled) return;
        if (activityState.isGdprForgotten) return;

        // sending preinstall referrer doesn't require preinstall tracking flag to be enabled
        sendPreinstallReferrerI();

        if (!adtraceConfig.preinstallTrackingEnabled) return;
        if (internalState.hasPreinstallBeenRead()) return;

        if (deviceInfo.packageName == null || deviceInfo.packageName.isEmpty()) {
            logger.debug("Can't read preinstall payload, invalid package name");
            return;
        }

        SharedPreferencesManager sharedPreferencesManager = SharedPreferencesManager.getDefaultInstance(getContext());
        long readStatus = sharedPreferencesManager.getPreinstallPayloadReadStatus();

        if (PreinstallUtil.hasAllLocationsBeenRead(readStatus)) {
            internalState.preinstallHasBeenRead = true;
            return;
        }

        // 1. try reading preinstall payload from standard system property
        if (PreinstallUtil.hasNotBeenRead(Constants.SYSTEM_PROPERTIES, readStatus)) {
            String payloadSystemProperty = PreinstallUtil.getPayloadFromSystemProperty(
                    deviceInfo.packageName, logger);

            if (payloadSystemProperty != null && !payloadSystemProperty.isEmpty()) {
                sdkClickHandler.sendPreinstallPayload(payloadSystemProperty, Constants.SYSTEM_PROPERTIES);
            } else {
                readStatus = PreinstallUtil.markAsRead(Constants.SYSTEM_PROPERTIES, readStatus);
            }
        }

        // 2. try reading preinstall payload from system property using reflection
        if (PreinstallUtil.hasNotBeenRead(Constants.SYSTEM_PROPERTIES_REFLECTION, readStatus)) {
            String payloadSystemPropertyReflection = PreinstallUtil.getPayloadFromSystemPropertyReflection(
                    deviceInfo.packageName, logger);

            if (payloadSystemPropertyReflection != null && !payloadSystemPropertyReflection.isEmpty()) {
                sdkClickHandler.sendPreinstallPayload(payloadSystemPropertyReflection, Constants.SYSTEM_PROPERTIES_REFLECTION);
            } else {
                readStatus = PreinstallUtil.markAsRead(Constants.SYSTEM_PROPERTIES_REFLECTION, readStatus);
            }
        }

        // 3. try reading preinstall payload from system property file path
        if (PreinstallUtil.hasNotBeenRead(Constants.SYSTEM_PROPERTIES_PATH, readStatus)) {
            String payloadSystemPropertyFilePath = PreinstallUtil.getPayloadFromSystemPropertyFilePath(
                    deviceInfo.packageName, logger);

            if (payloadSystemPropertyFilePath != null && !payloadSystemPropertyFilePath.isEmpty()) {
                sdkClickHandler.sendPreinstallPayload(payloadSystemPropertyFilePath, Constants.SYSTEM_PROPERTIES_PATH);
            } else {
                readStatus = PreinstallUtil.markAsRead(Constants.SYSTEM_PROPERTIES_PATH, readStatus);
            }
        }

        // 4. try reading preinstall payload from system property file path using reflection
        if (PreinstallUtil.hasNotBeenRead(Constants.SYSTEM_PROPERTIES_PATH_REFLECTION, readStatus)) {
            String payloadSystemPropertyFilePathReflection = PreinstallUtil.getPayloadFromSystemPropertyFilePathReflection(
                    deviceInfo.packageName, logger);

            if (payloadSystemPropertyFilePathReflection != null && !payloadSystemPropertyFilePathReflection.isEmpty()) {
                sdkClickHandler.sendPreinstallPayload(payloadSystemPropertyFilePathReflection, Constants.SYSTEM_PROPERTIES_PATH_REFLECTION);
            } else {
                readStatus = PreinstallUtil.markAsRead(Constants.SYSTEM_PROPERTIES_PATH_REFLECTION, readStatus);
            }
        }

        // 5. try reading preinstall payload from default content uri
        if (PreinstallUtil.hasNotBeenRead(Constants.CONTENT_PROVIDER, readStatus)) {
            String payloadContentProviderDefault = PreinstallUtil.getPayloadFromContentProviderDefault(
                    adtraceConfig.context,
                    deviceInfo.packageName,
                    logger);

            if (payloadContentProviderDefault != null && !payloadContentProviderDefault.isEmpty()) {
                sdkClickHandler.sendPreinstallPayload(payloadContentProviderDefault, Constants.CONTENT_PROVIDER);
            } else {
                readStatus = PreinstallUtil.markAsRead(Constants.CONTENT_PROVIDER, readStatus);
            }
        }

        // 6. try reading preinstall payload from all content provider with intent action and with install permission
        if (PreinstallUtil.hasNotBeenRead(Constants.CONTENT_PROVIDER_INTENT_ACTION, readStatus)) {
            List<String> payloadListContentProviderIntentAction = PreinstallUtil.getPayloadsFromContentProviderIntentAction(
                    adtraceConfig.context,
                    deviceInfo.packageName,
                    logger);

            if (payloadListContentProviderIntentAction != null && !payloadListContentProviderIntentAction.isEmpty()) {
                for (String payload : payloadListContentProviderIntentAction) {
                    sdkClickHandler.sendPreinstallPayload(payload, Constants.CONTENT_PROVIDER_INTENT_ACTION);
                }
            } else {
                readStatus = PreinstallUtil.markAsRead(Constants.CONTENT_PROVIDER_INTENT_ACTION, readStatus);
            }
        }

        // 7. try reading preinstall payload from all content provider with intent action and without install permission
        if (PreinstallUtil.hasNotBeenRead(Constants.CONTENT_PROVIDER_NO_PERMISSION, readStatus)) {
            List<String> payloadListContentProviderIntentAction = PreinstallUtil.getPayloadsFromContentProviderNoPermission(
                    adtraceConfig.context,
                    deviceInfo.packageName,
                    logger);

            if (payloadListContentProviderIntentAction != null && !payloadListContentProviderIntentAction.isEmpty()) {
                for (String payload : payloadListContentProviderIntentAction) {
                    sdkClickHandler.sendPreinstallPayload(payload, Constants.CONTENT_PROVIDER_NO_PERMISSION);
                }
            } else {
                readStatus = PreinstallUtil.markAsRead(Constants.CONTENT_PROVIDER_NO_PERMISSION, readStatus);
            }
        }

        // 8. try reading preinstall payload from file system (world readable)
        if (PreinstallUtil.hasNotBeenRead(Constants.FILE_SYSTEM, readStatus)) {
            String payloadFileSystem = PreinstallUtil.getPayloadFromFileSystem(
                    deviceInfo.packageName,
                    adtraceConfig.preinstallFilePath,
                    logger);

            if (payloadFileSystem != null && !payloadFileSystem.isEmpty()) {
                sdkClickHandler.sendPreinstallPayload(payloadFileSystem, Constants.FILE_SYSTEM);
            } else {
                readStatus = PreinstallUtil.markAsRead(Constants.FILE_SYSTEM, readStatus);
            }
        }

        sharedPreferencesManager.setPreinstallPayloadReadStatus(readStatus);

        internalState.preinstallHasBeenRead = true;
    }

    private void readConfigFile(Context context) {
        Properties properties;

        try {
            InputStream inputStream = context.getAssets().open("adtrace_config.properties");
            properties = new Properties();
            properties.load(inputStream);
        } catch (Exception e) {
            logger.debug("%s file not found in this app", e.getMessage());
            return;
        }

        logger.verbose("adtrace_config.properties file read and loaded");

        String defaultTracker = properties.getProperty("defaultTracker");

        if (defaultTracker != null) {
            adtraceConfig.defaultTracker = defaultTracker;
        }
    }

    private void preLaunchActionsI(List<IRunActivityHandler> preLaunchActionsArray) {
        if (preLaunchActionsArray == null) {
            return;
        }

        for (IRunActivityHandler preLaunchAction : preLaunchActionsArray) {
            preLaunchAction.run(this);
        }
    }

    private void startI() {
        // check if it's the first sdk start
        if (internalState.hasFirstSdkStartNotOcurred()) {
            AdTraceSigner.onResume(adtraceConfig.logger);
            startFirstSessionI();
            return;
        }

        // it shouldn't start if it was disabled after a first session
        if (!activityState.enabled) {
            return;
        }

        AdTraceSigner.onResume(adtraceConfig.logger);

        updateHandlersStatusAndSendI();

        processCoppaComplianceI();

        processSessionI();

        checkAttributionStateI();

        processCachedDeeplinkI();
    }

    private void startFirstSessionI() {
        activityState = new ActivityState();
        internalState.firstSdkStart = true;

        // still update handlers status
        updateHandlersStatusAndSendI();

        long now = System.currentTimeMillis();

        SharedPreferencesManager sharedPreferencesManager = SharedPreferencesManager.getDefaultInstance(getContext());
        activityState.pushToken = sharedPreferencesManager.getPushToken();
        // activityState.isGdprForgotten = sharedPreferencesManager.getGdprForgetMe();

        // track the first session package only if it's enabled
        if (internalState.isEnabled()) {
            if (sharedPreferencesManager.getGdprForgetMe()) {
                gdprForgetMeI();
            } else {
                processCoppaComplianceI();

                // check if disable third party sharing request came, then send it first
                if (sharedPreferencesManager.getDisableThirdPartySharing()) {
                    disableThirdPartySharingI();
                }
                for (AdTraceThirdPartySharing adtraceThirdPartySharing :
                        adtraceConfig.preLaunchActions.preLaunchAdTraceThirdPartySharingArray) {
                    trackThirdPartySharingI(adtraceThirdPartySharing);
                }
                if (adtraceConfig.preLaunchActions.lastMeasurementConsentTracked != null) {
                    trackMeasurementConsentI(
                            adtraceConfig.preLaunchActions.
                                    lastMeasurementConsentTracked.booleanValue());
                }

                adtraceConfig.preLaunchActions.preLaunchAdTraceThirdPartySharingArray =
                        new ArrayList<>();
                adtraceConfig.preLaunchActions.lastMeasurementConsentTracked = null;


                activityState.sessionCount = 1; // this is the first session
                transferSessionPackageI(now);
                checkAfterNewStartI(sharedPreferencesManager);
            }
        }

        activityState.resetSessionAttributes(now);
        activityState.enabled = internalState.isEnabled();
        activityState.updatePackages = internalState.itHasToUpdatePackages();

        writeActivityStateI();
        sharedPreferencesManager.removePushToken();
        sharedPreferencesManager.removeGdprForgetMe();
        sharedPreferencesManager.removeDisableThirdPartySharing();

        // check for cached deep links
        processCachedDeeplinkI();

        // don't check attribution right after first sdk start
    }

    private void processSessionI() {
        if (activityState.isGdprForgotten) {
            return;
        }

        long now = System.currentTimeMillis();

        long lastInterval = now - activityState.lastActivity;

        if (lastInterval < 0) {
            logger.error(TIME_TRAVEL);
            activityState.lastActivity = now;
            writeActivityStateI();
            return;
        }

        // new session
        if (lastInterval > SESSION_INTERVAL) {
            trackNewSessionI(now);
            checkAfterNewStartI();
            return;
        }

        // new subsession
        if (lastInterval > SUBSESSION_INTERVAL) {
            activityState.subsessionCount++;
            activityState.sessionLength += lastInterval;
            activityState.lastActivity = now;
            logger.verbose("Started subsession %d of session %d",
                    activityState.subsessionCount,
                    activityState.sessionCount);
            writeActivityStateI();

            checkForPreinstallI();

            // Try to check if there's new referrer information.
            installReferrer.startConnection();
            installReferrerHuawei.readReferrer();
            installReferrerMeta.readReferrer();
            readInstallReferrerSamsung();
            readInstallReferrerXiaomi();
            readInstallReferrerVivo();

            return;
        }

        logger.verbose("Time span since last activity too short for a new subsession");
    }

    private void readInstallReferrerSamsung() {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                ReferrerDetails referrerDetails = Reflection.getSamsungReferrer(getContext(), logger);
                if (referrerDetails != null) {
                    sendInstallReferrer(referrerDetails, REFERRER_API_SAMSUNG);
                }
            }
        });
    }

    private void readInstallReferrerXiaomi() {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                ReferrerDetails referrerDetails = Reflection.getXiaomiReferrer(getContext(), logger);
                if (referrerDetails != null) {
                    sendInstallReferrer(referrerDetails, REFERRER_API_XIAOMI);
                }
            }
        });
    }

    private void readInstallReferrerVivo() {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                ReferrerDetails referrerDetails = Reflection.getVivoReferrer(getContext(), logger);
                if (referrerDetails != null) {
                    sendInstallReferrer(referrerDetails, REFERRER_API_VIVO);
                }
            }
        });
    }

    private void trackNewSessionI(final long now) {
        long lastInterval = now - activityState.lastActivity;

        activityState.sessionCount++;
        activityState.lastInterval = lastInterval;

        // lastIntervalHardReset is previous lastInterval and if new interval was more than a minute reset it
        if (lastInterval > MIN_LAST_INTERVAL_HARD_RESET_THRESHOLD)
            activityState.lastIntervalHardReset = lastInterval;

        transferSessionPackageI(now);
        activityState.resetSessionAttributes(now);
        writeActivityStateI();
    }

    private void checkAttributionStateI() {
        if (!checkActivityStateI(activityState)) {
            return;
        }

        // if it's the first launch
        if (internalState.isFirstLaunch()) {
            // and it hasn't received the session response
            if (internalState.hasSessionResponseNotBeenProcessed()) {
                return;
            }
        }

        // if there is already an attribution saved and there was no attribution being asked
        if (attribution != null && !activityState.askingAttribution) {
            return;
        }

        attributionHandler.getAttribution();
    }

    private void processCachedDeeplinkI() {
        if (!checkActivityStateI(activityState)) {
            return;
        }

        SharedPreferencesManager sharedPreferencesManager = SharedPreferencesManager.getDefaultInstance(getContext());
        String cachedDeeplinkUrl = sharedPreferencesManager.getDeeplinkUrl();
        long cachedDeeplinkClickTime = sharedPreferencesManager.getDeeplinkClickTime();

        if (cachedDeeplinkUrl == null) {
            return;
        }
        if (cachedDeeplinkClickTime == -1) {
            return;
        }

        readOpenUrl(Uri.parse(cachedDeeplinkUrl), cachedDeeplinkClickTime);

        sharedPreferencesManager.removeDeeplink();
    }

    private void endI() {
        // pause sending if it's not allowed to send
        if (!toSendI()) {
            pauseSendingI();
        }

        if (updateActivityStateI(System.currentTimeMillis())) {
            writeActivityStateI();
        }
    }

    private void trackEventI(AdTraceEvent event) {
        if (!checkActivityStateI(activityState)) return;
        if (!isEnabledI()) return;
        if (!checkEventI(event)) return;
        if (!checkOrderIdI(event.orderId)) return;
        if (activityState.isGdprForgotten) return;

        long now = System.currentTimeMillis();

        activityState.eventCount++;
        updateActivityStateI(now);

        PackageBuilder eventBuilder = new PackageBuilder(adtraceConfig, deviceInfo, activityState, sessionParameters, now);
        ActivityPackage eventPackage = eventBuilder.buildEventPackage(event, internalState.isInDelayedStart());
        packageHandler.addPackage(eventPackage);

        if (adtraceConfig.eventBufferingEnabled) {
            logger.info("Buffered event %s", eventPackage.getSuffix());
        } else {
            packageHandler.sendFirstPackage();
        }

        // if it is in the background and it can send, start the background timer
        if (adtraceConfig.sendInBackground && internalState.isInBackground()) {
            startBackgroundTimerI();
        }

        writeActivityStateI();
    }

    private void launchEventResponseTasksI(final EventResponseData eventResponseData) {
        // try to update adid from response
        updateAdidI(eventResponseData.adid);

        Handler handler = new Handler(adtraceConfig.context.getMainLooper());

        // success callback
        if (eventResponseData.success && adtraceConfig.onEventTrackingSucceededListener != null) {
            logger.debug("Launching success event tracking listener");
            // add it to the handler queue
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (adtraceConfig == null) {
                        return;
                    }
                    if (adtraceConfig.onEventTrackingSucceededListener == null) {
                        return;
                    }
                    adtraceConfig.onEventTrackingSucceededListener.onFinishedEventTrackingSucceeded(eventResponseData.getSuccessResponseData());
                }
            };
            handler.post(runnable);

            return;
        }
        // failure callback
        if (!eventResponseData.success && adtraceConfig.onEventTrackingFailedListener != null) {
            logger.debug("Launching failed event tracking listener");
            // add it to the handler queue
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (adtraceConfig == null) {
                        return;
                    }
                    if (adtraceConfig.onEventTrackingFailedListener == null) {
                        return;
                    }
                    adtraceConfig.onEventTrackingFailedListener.onFinishedEventTrackingFailed(eventResponseData.getFailureResponseData());
                }
            };
            handler.post(runnable);

            return;
        }
    }

    private void launchSdkClickResponseTasksI(SdkClickResponseData sdkClickResponseData) {
        // try to update adid from response
        updateAdidI(sdkClickResponseData.adid);

        // use the same handler to ensure that all tasks are executed sequentially
        Handler handler = new Handler(adtraceConfig.context.getMainLooper());

        // try to update the attribution
        boolean attributionUpdated = updateAttributionI(sdkClickResponseData.attribution);

        // if attribution changed, launch attribution changed delegate
        if (attributionUpdated) {
            launchAttributionListenerI(handler);
        }
    }

    private void launchSessionResponseTasksI(SessionResponseData sessionResponseData) {
        logger.debug("Launching SessionResponse tasks");

        // try to update adid from response
        updateAdidI(sessionResponseData.adid);

        // use the same handler to ensure that all tasks are executed sequentially
        Handler handler = new Handler(adtraceConfig.context.getMainLooper());

        // try to update the attribution
        boolean attributionUpdated = updateAttributionI(sessionResponseData.attribution);

        // if attribution changed, launch attribution changed delegate
        if (attributionUpdated) {
            launchAttributionListenerI(handler);
        }

        // if attribution didn't update and it's still null
        // ask for attribution
        if (this.attribution == null && activityState.askingAttribution == false) {
            this.attributionHandler.getAttribution();
        }

        // mark install as tracked on success
        if (sessionResponseData.success) {
            SharedPreferencesManager.getDefaultInstance(getContext()).setInstallTracked();
        }

        // launch Session tracking listener if available
        launchSessionResponseListenerI(sessionResponseData, handler);

        // mark session response has proccessed
        internalState.sessionResponseProcessed = true;
    }

    private void launchSessionResponseListenerI(final SessionResponseData sessionResponseData, Handler handler) {
        // success callback
        if (sessionResponseData.success && adtraceConfig.onSessionTrackingSucceededListener != null) {
            logger.debug("Launching success session tracking listener");
            // add it to the handler queue
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (adtraceConfig == null) {
                        return;
                    }
                    if (adtraceConfig.onSessionTrackingSucceededListener == null) {
                        return;
                    }
                    adtraceConfig.onSessionTrackingSucceededListener.onFinishedSessionTrackingSucceeded(sessionResponseData.getSuccessResponseData());
                }
            };
            handler.post(runnable);

            return;
        }
        // failure callback
        if (!sessionResponseData.success && adtraceConfig.onSessionTrackingFailedListener != null) {
            logger.debug("Launching failed session tracking listener");
            // add it to the handler queue
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (adtraceConfig == null) {
                        return;
                    }
                    if (adtraceConfig.onSessionTrackingFailedListener == null) {
                        return;
                    }
                    adtraceConfig.onSessionTrackingFailedListener.onFinishedSessionTrackingFailed(sessionResponseData.getFailureResponseData());
                }
            };
            handler.post(runnable);

            return;
        }
    }

    private void launchAttributionResponseTasksI(AttributionResponseData attributionResponseData) {
        // try to update adid from response
        updateAdidI(attributionResponseData.adid);

        Handler handler = new Handler(adtraceConfig.context.getMainLooper());

        // try to update the attribution
        boolean attributionUpdated = updateAttributionI(attributionResponseData.attribution);

        // if attribution changed, launch attribution changed delegate
        if (attributionUpdated) {
            launchAttributionListenerI(handler);
        }

        // if there is any, try to launch the deeplink
        prepareDeeplinkI(attributionResponseData.deeplink, handler);
    }

    private void launchAttributionListenerI(Handler handler) {
        if (adtraceConfig.onAttributionChangedListener == null) {
            return;
        }
        // add it to the handler queue
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (adtraceConfig == null) {
                    return;
                }
                if (adtraceConfig.onAttributionChangedListener == null) {
                    return;
                }
                adtraceConfig.onAttributionChangedListener.onAttributionChanged(attribution);
            }
        };
        handler.post(runnable);
    }

    private void launchPurchaseVerificationResponseTasksI(PurchaseVerificationResponseData purchaseVerificationResponseData) {
        // use the same handler to ensure that all tasks are executed sequentially
        Handler handler = new Handler(adtraceConfig.context.getMainLooper());
        JSONObject jsonResponse = purchaseVerificationResponseData.jsonResponse;

        // check and parse response data
        AdTracePurchaseVerificationResult verificationResult;
        if (jsonResponse == null) {
            verificationResult = new AdTracePurchaseVerificationResult(
                    "not_verified",
                    101,
                    purchaseVerificationResponseData.message);
        } else {
            verificationResult = new AdTracePurchaseVerificationResult(
                    UtilNetworking.extractJsonString(jsonResponse, "verification_status"),
                    UtilNetworking.extractJsonInt(jsonResponse, "code"),
                    UtilNetworking.extractJsonString(jsonResponse, "message"));
        }

        // trigger purchase verification callback with the verification result
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                purchaseVerificationResponseData.activityPackage.getPurchaseVerificationCallback().onVerificationFinished(verificationResult);
            }
        };
        handler.post(runnable);
    }

    private void prepareDeeplinkI(final Uri deeplink, final Handler handler) {
        if (deeplink == null) {
            return;
        }

        logger.info("Deferred deeplink received (%s)", deeplink);

        final Intent deeplinkIntent = createDeeplinkIntentI(deeplink);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (adtraceConfig == null) {
                    return;
                }
                boolean toLaunchDeeplink = true;
                if (adtraceConfig.onDeeplinkResponseListener != null) {
                    toLaunchDeeplink = adtraceConfig.onDeeplinkResponseListener.launchReceivedDeeplink(deeplink);
                }
                if (toLaunchDeeplink) {
                    launchDeeplinkMain(deeplinkIntent, deeplink);
                }
            }
        };
        handler.post(runnable);
    }

    private Intent createDeeplinkIntentI(Uri deeplink) {
        Intent mapIntent;
        if (adtraceConfig.deepLinkComponent == null) {
            mapIntent = new Intent(Intent.ACTION_VIEW, deeplink);
        } else {
            mapIntent = new Intent(Intent.ACTION_VIEW, deeplink, adtraceConfig.context, adtraceConfig.deepLinkComponent);
        }
        mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        mapIntent.setPackage(adtraceConfig.context.getPackageName());

        return mapIntent;
    }

    private void launchDeeplinkMain(Intent deeplinkIntent, Uri deeplink) {
        // Verify it resolves
        PackageManager packageManager = adtraceConfig.context.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(deeplinkIntent, 0);
        boolean isIntentSafe = activities.size() > 0;

        // Start an activity if it's safe
        if (!isIntentSafe) {
            logger.error("Unable to open deferred deep link (%s)", deeplink);
            return;
        }

        // add it to the handler queue
        logger.info("Open deferred deep link (%s)", deeplink);
        adtraceConfig.context.startActivity(deeplinkIntent);
    }

    private void setEnabledI(boolean enabled) {
        // compare with the saved or internal state
        if (!hasChangedStateI(this.isEnabledI(), enabled,
                "AdTrace already enabled", "AdTrace already disabled")) {
            return;
        }

        if (enabled) {
            if (activityState != null && activityState.isGdprForgotten) {
                logger.error("Re-enabling SDK not possible for forgotten user");
                return;
            }
        }

        // save new enabled state in internal state
        internalState.enabled = enabled;

        if (internalState.hasFirstSdkStartNotOcurred()) {
            updateStatusI(!enabled,
                    "Handlers will start as paused due to the SDK being disabled",
                    "Handlers will still start as paused",
                    "Handlers will start as active due to the SDK being enabled");
            return;
        }

        activityState.enabled = enabled;
        writeActivityStateI();

        if (enabled) {
            SharedPreferencesManager sharedPreferencesManager = SharedPreferencesManager.getDefaultInstance(getContext());

            if (sharedPreferencesManager.getGdprForgetMe()) {
                gdprForgetMeI();
            } else {
                processCoppaComplianceI();

                if (sharedPreferencesManager.getDisableThirdPartySharing()) {
                    disableThirdPartySharingI();
                }
                for (AdTraceThirdPartySharing adtraceThirdPartySharing :
                        adtraceConfig.preLaunchActions.preLaunchAdTraceThirdPartySharingArray) {
                    trackThirdPartySharingI(adtraceThirdPartySharing);
                }
                if (adtraceConfig.preLaunchActions.lastMeasurementConsentTracked != null) {
                    trackMeasurementConsentI(
                            adtraceConfig.preLaunchActions.
                                    lastMeasurementConsentTracked.booleanValue());
                }

                adtraceConfig.preLaunchActions.preLaunchAdTraceThirdPartySharingArray =
                        new ArrayList<>();
                adtraceConfig.preLaunchActions.lastMeasurementConsentTracked = null;
            }

            // check if install was tracked
            if (!sharedPreferencesManager.getInstallTracked()) {
                logger.debug("Detected that install was not tracked at enable time");
                long now = System.currentTimeMillis();
                trackNewSessionI(now);
            }
            checkAfterNewStartI(sharedPreferencesManager);
        }

        updateStatusI(!enabled,
                "Pausing handlers due to SDK being disabled",
                "Handlers remain paused",
                "Resuming handlers due to SDK being enabled");
    }


    private void checkAfterNewStartI() {
        checkAfterNewStartI(SharedPreferencesManager.getDefaultInstance(getContext()));
    }

    private void checkAfterNewStartI(SharedPreferencesManager sharedPreferencesManager) {
        // check if there is a saved push token to send
        String pushToken = sharedPreferencesManager.getPushToken();

        if (pushToken != null && !pushToken.equals(activityState.pushToken)) {
            // queue set push token
            setPushToken(pushToken, true);
        }

        // check if there are token to send
        Object referrers = sharedPreferencesManager.getRawReferrerArray();
        if (referrers != null) {
            // queue send referrer tag
            sendReftagReferrer();
        }

        checkForPreinstallI();

        // try to read and send the install referrer
        installReferrer.startConnection();
        installReferrerHuawei.readReferrer();
        installReferrerMeta.readReferrer();
        readInstallReferrerSamsung();
        readInstallReferrerXiaomi();
        readInstallReferrerVivo();
    }

    private void setOfflineModeI(boolean offline) {
        // compare with the internal state
        if (!hasChangedStateI(internalState.isOffline(), offline,
                "AdTrace already in offline mode",
                "AdTrace already in online mode")) {
            return;
        }

        internalState.offline = offline;

        if (internalState.hasFirstSdkStartNotOcurred()) {
            updateStatusI(offline,
                    "Handlers will start paused due to SDK being offline",
                    "Handlers will still start as paused",
                    "Handlers will start as active due to SDK being online");
            return;
        }

        updateStatusI(offline,
                "Pausing handlers to put SDK offline mode",
                "Handlers remain paused",
                "Resuming handlers to put SDK in online mode");

    }

    private boolean hasChangedStateI(boolean previousState, boolean newState,
                                     String trueMessage, String falseMessage)
    {
        if (previousState != newState) {
            return true;
        }

        if (previousState) {
            logger.debug(trueMessage);
        } else {
            logger.debug(falseMessage);
        }

        return false;
    }

    private void updateStatusI(boolean pausingState, String pausingMessage,
                               String remainsPausedMessage, String unPausingMessage)
    {
        // it is changing from an active state to a pause state
        if (pausingState) {
            logger.info(pausingMessage);
        }
        // check if it's remaining in a pause state
        else if (pausedI(false)) {
            // including the sdk click handler
            if (pausedI(true)) {
                logger.info(remainsPausedMessage);
            } else {
                logger.info(remainsPausedMessage + ", except the Sdk Click Handler");
            }
        } else {
            // it is changing from a pause state to an active state
            logger.info(unPausingMessage);
        }

        updateHandlersStatusAndSendI();
    }

    private void setAskingAttributionI(boolean askingAttribution) {
        activityState.askingAttribution = askingAttribution;

        writeActivityStateI();
    }

    private void sendReftagReferrerI() {
        if (!isEnabledI()) {
            return;
        }
        if (internalState.hasFirstSdkStartNotOcurred()) {
            return;
        }

        sdkClickHandler.sendReftagReferrers();
    }

    private void sendPreinstallReferrerI() {
        if (!isEnabledI()) {
            return;
        }
        if (internalState.hasFirstSdkStartNotOcurred()) {
            return;
        }

        String referrerPayload = SharedPreferencesManager.getDefaultInstance(getContext()).getPreinstallReferrer();

        if (referrerPayload == null || referrerPayload.isEmpty()) {
            return;
        }

        sdkClickHandler.sendPreinstallPayload(referrerPayload, Constants.SYSTEM_INSTALLER_REFERRER);
    }

    private void sendInstallReferrerI(ReferrerDetails referrerDetails, String referrerApi) {
        if (!isEnabledI()) {
            return;
        }

        if (!isValidReferrerDetails(referrerDetails)) {
            return;
        }

        if (Util.isEqualReferrerDetails(referrerDetails, referrerApi, activityState)) {
            // Same click already sent before, nothing to be done.
            return;
        }

        // Create sdk click
        ActivityPackage sdkClickPackage = PackageFactory.buildInstallReferrerSdkClickPackage(
                referrerDetails,
                referrerApi,
                activityState,
                adtraceConfig,
                deviceInfo,
                sessionParameters);

        sdkClickHandler.sendSdkClick(sdkClickPackage);
    }

    private boolean isValidReferrerDetails(final ReferrerDetails referrerDetails) {
        if (referrerDetails == null) {
            return false;
        }

        if (referrerDetails.installReferrer == null) {
            return false;
        }

        return referrerDetails.installReferrer.length() != 0;
    }

    private void readOpenUrlI(Uri url, long clickTime) {
        if (!isEnabledI()) {
            return;
        }

        if (Util.isUrlFilteredOut(url)) {
            logger.debug("Deep link (" + url.toString() + ") processing skipped");
            return;
        }

        ActivityPackage sdkClickPackage = PackageFactory.buildDeeplinkSdkClickPackage(
                url,
                clickTime,
                activityState,
                adtraceConfig,
                deviceInfo,
                sessionParameters);

        if (sdkClickPackage == null) {
            return;
        }

        sdkClickHandler.sendSdkClick(sdkClickPackage);
    }

    private void updateHandlersStatusAndSendI() {
        // check if it should stop sending
        if (!toSendI()) {
            pauseSendingI();
            return;
        }

        resumeSendingI();

        // if event buffering is not enabled
        if (!adtraceConfig.eventBufferingEnabled ||
                // or if it's the first launch and it hasn't received the session response
                (internalState.isFirstLaunch() && internalState.hasSessionResponseNotBeenProcessed()))
        {
            // try to send
            packageHandler.sendFirstPackage();
        }
    }

    private void pauseSendingI() {
        attributionHandler.pauseSending();
        packageHandler.pauseSending();
        // the conditions to pause the sdk click handler are less restrictive
        // it's possible for the sdk click handler to be active while others are paused
        if (!toSendI(true)) {
            sdkClickHandler.pauseSending();
            purchaseVerificationHandler.pauseSending();
        } else {
            sdkClickHandler.resumeSending();
            purchaseVerificationHandler.resumeSending();
        }
    }

    private void resumeSendingI() {
        attributionHandler.resumeSending();
        packageHandler.resumeSending();
        sdkClickHandler.resumeSending();
        purchaseVerificationHandler.resumeSending();
    }

    private boolean updateActivityStateI(long now) {
        if (!checkActivityStateI(activityState)) { return false; }

        long lastInterval = now - activityState.lastActivity;

        if (lastInterval>MIN_LAST_INTERVAL_HARD_RESET_THRESHOLD)
            activityState.lastIntervalHardReset = lastInterval;

        // ignore late updates
        if (lastInterval > SESSION_INTERVAL) {
            return false;
        }
        activityState.lastActivity = now;

        if (lastInterval < 0) {
            logger.error(TIME_TRAVEL);
        } else {
            activityState.sessionLength += lastInterval;
            activityState.timeSpent += lastInterval;
        }
        return true;
    }

    public static boolean deleteActivityState(Context context) {
        return context.deleteFile(ACTIVITY_STATE_FILENAME);
    }

    public static boolean deleteAttribution(Context context) {
        return context.deleteFile(ATTRIBUTION_FILENAME);
    }

    public static boolean deleteSessionCallbackParameters(Context context) {
        return context.deleteFile(SESSION_CALLBACK_PARAMETERS_FILENAME);
    }

    public static boolean deleteSessionPartnerParameters(Context context) {
        return context.deleteFile(SESSION_PARTNER_PARAMETERS_FILENAME);
    }

    private void transferSessionPackageI(long now) {
        PackageBuilder builder = new PackageBuilder(adtraceConfig, deviceInfo, activityState,
                sessionParameters, now);
        ActivityPackage sessionPackage = builder.buildSessionPackage(internalState.isInDelayedStart());
        packageHandler.addPackage(sessionPackage);
        packageHandler.sendFirstPackage();
    }

    private void startForegroundTimerI() {
        // don't start the timer if it's disabled
        if (!isEnabledI()) {
            return;
        }

        foregroundTimer.start();
    }

    private void stopForegroundTimerI() {
        foregroundTimer.suspend();
    }

    private void foregroundTimerFiredI() {
        // stop the timer cycle if it's disabled
        if (!isEnabledI()) {
            stopForegroundTimerI();
            return;
        }

        if (toSendI()) {
            packageHandler.sendFirstPackage();
        }

        if (updateActivityStateI(System.currentTimeMillis())) {
            writeActivityStateI();
        }
    }

    private void startBackgroundTimerI() {
        if (backgroundTimer == null) {
            return;
        }

        // check if it can send in the background
        if (!toSendI()) {
            return;
        }

        // background timer already started
        if (backgroundTimer.getFireIn() > 0) {
            return;
        }

        backgroundTimer.startIn(BACKGROUND_TIMER_INTERVAL);
    }

    private void stopBackgroundTimerI() {
        if (backgroundTimer == null) {
            return;
        }

        backgroundTimer.cancel();
    }

    private void backgroundTimerFiredI() {
        if (toSendI()) {
            packageHandler.sendFirstPackage();
        }
    }

    private void delayStartI() {
        // it's not configured to start delayed or already finished
        if (internalState.isNotInDelayedStart()) {
            return;
        }

        // the delay has already started
        if (isToUpdatePackagesI()) {
            return;
        }

        // check against max start delay
        double delayStartSeconds = adtraceConfig.delayStart != null ? adtraceConfig.delayStart : 0.0;
        long maxDelayStartMilli = AdTraceFactory.getMaxDelayStart();

        long delayStartMilli = (long) (delayStartSeconds * 1000);
        if (delayStartMilli > maxDelayStartMilli) {
            double maxDelayStartSeconds = maxDelayStartMilli / 1000;
            String delayStartFormatted = Util.SecondsDisplayFormat.format(delayStartSeconds);
            String maxDelayStartFormatted = Util.SecondsDisplayFormat.format(maxDelayStartSeconds);

            logger.warn("Delay start of %s seconds bigger than max allowed value of %s seconds", delayStartFormatted, maxDelayStartFormatted);
            delayStartMilli = maxDelayStartMilli;
            delayStartSeconds = maxDelayStartSeconds;
        }

        String delayStartFormatted = Util.SecondsDisplayFormat.format(delayStartSeconds);
        logger.info("Waiting %s seconds before starting first session", delayStartFormatted);

        delayStartTimer.startIn(delayStartMilli);

        internalState.updatePackages = true;

        if (activityState != null) {
            activityState.updatePackages = true;
            writeActivityStateI();
        }
    }

    private void sendFirstPackagesI() {
        if (internalState.isNotInDelayedStart()) {
            logger.info("Start delay expired or never configured");
            return;
        }

        // update packages in queue
        updatePackagesI();
        // no longer is in delay start
        internalState.delayStart = false;
        // cancel possible still running timer if it was called by user
        delayStartTimer.cancel();
        // and release timer
        delayStartTimer = null;
        // update the status and try to send first package
        updateHandlersStatusAndSendI();
    }

    private void updatePackagesI() {
        // update activity packages
        packageHandler.updatePackages(sessionParameters);
        // no longer needs to update packages
        internalState.updatePackages = false;
        if (activityState != null) {
            activityState.updatePackages = false;
            writeActivityStateI();
        }
    }

    private boolean isToUpdatePackagesI() {
        if (activityState != null) {
            return activityState.updatePackages;
        } else {
            return internalState.itHasToUpdatePackages();
        }
    }

    public void addSessionCallbackParameterI(String key, String value) {
        if (!Util.isValidParameter(key, "key", "Session Callback")) return;
        if (!Util.isValidParameter(value, "value", "Session Callback")) return;

        if (sessionParameters.callbackParameters == null) {
            sessionParameters.callbackParameters = new LinkedHashMap<String, String>();
        }

        String oldValue = sessionParameters.callbackParameters.get(key);

        if (value.equals(oldValue)) {
            logger.verbose("Key %s already present with the same value", key);
            return;
        }

        if (oldValue != null) {
            logger.warn("Key %s will be overwritten", key);
        }

        sessionParameters.callbackParameters.put(key, value);

        writeSessionCallbackParametersI();
    }

    public void addSessionPartnerParameterI(String key, String value) {
        if (!Util.isValidParameter(key, "key", "Session Partner")) return;
        if (!Util.isValidParameter(value, "value", "Session Partner")) return;

        if (sessionParameters.partnerParameters == null) {
            sessionParameters.partnerParameters = new LinkedHashMap<String, String>();
        }

        String oldValue = sessionParameters.partnerParameters.get(key);

        if (value.equals(oldValue)) {
            logger.verbose("Key %s already present with the same value", key);
            return;
        }

        if (oldValue != null) {
            logger.warn("Key %s will be overwritten", key);
        }

        sessionParameters.partnerParameters.put(key, value);

        writeSessionPartnerParametersI();
    }

    public void removeSessionCallbackParameterI(String key) {
        if (!Util.isValidParameter(key, "key", "Session Callback")) return;

        if (sessionParameters.callbackParameters == null) {
            logger.warn("Session Callback parameters are not set");
            return;
        }

        String oldValue = sessionParameters.callbackParameters.remove(key);

        if (oldValue == null) {
            logger.warn("Key %s does not exist", key);
            return;
        }

        logger.debug("Key %s will be removed", key);

        writeSessionCallbackParametersI();
    }

    public void removeSessionPartnerParameterI(String key) {
        if (!Util.isValidParameter(key, "key", "Session Partner")) return;

        if (sessionParameters.partnerParameters == null) {
            logger.warn("Session Partner parameters are not set");
            return;
        }

        String oldValue = sessionParameters.partnerParameters.remove(key);

        if (oldValue == null) {
            logger.warn("Key %s does not exist", key);
            return;
        }

        logger.debug("Key %s will be removed", key);

        writeSessionPartnerParametersI();
    }

    public void resetSessionCallbackParametersI() {
        if (sessionParameters.callbackParameters == null) {
            logger.warn("Session Callback parameters are not set");
        }

        sessionParameters.callbackParameters = null;

        writeSessionCallbackParametersI();
    }

    public void resetSessionPartnerParametersI() {
        if (sessionParameters.partnerParameters == null) {
            logger.warn("Session Partner parameters are not set");
        }

        sessionParameters.partnerParameters = null;

        writeSessionPartnerParametersI();
    }

    private void setPushTokenI(String token) {
        if (!checkActivityStateI(activityState)) { return; }
        if (!isEnabledI()) { return; }
        if (activityState.isGdprForgotten) { return; }

        if (token == null) { return; }
        if (token.equals(activityState.pushToken)) { return; }

        // save new push token
        activityState.pushToken = token;
        writeActivityStateI();

        long now = System.currentTimeMillis();
        PackageBuilder infoPackageBuilder = new PackageBuilder(adtraceConfig, deviceInfo, activityState, sessionParameters, now);

        ActivityPackage infoPackage = infoPackageBuilder.buildInfoPackage(Constants.PUSH);
        packageHandler.addPackage(infoPackage);

        // If push token was cached, remove it.
        SharedPreferencesManager.getDefaultInstance(getContext()).removePushToken();

        if (adtraceConfig.eventBufferingEnabled) {
            logger.info("Buffered event %s", infoPackage.getSuffix());
        } else {
            packageHandler.sendFirstPackage();
        }
    }

    private void gdprForgetMeI() {
        if (!checkActivityStateI(activityState)) { return; }
        if (!isEnabledI()) { return; }
        if (activityState.isGdprForgotten) { return; }

        activityState.isGdprForgotten = true;
        writeActivityStateI();

        long now = System.currentTimeMillis();
        PackageBuilder gdprPackageBuilder = new PackageBuilder(adtraceConfig, deviceInfo, activityState, sessionParameters, now);

        ActivityPackage gdprPackage = gdprPackageBuilder.buildGdprPackage();
        packageHandler.addPackage(gdprPackage);

        // If GDPR choice was cached, remove it.
        SharedPreferencesManager.getDefaultInstance(getContext()).removeGdprForgetMe();

        if (adtraceConfig.eventBufferingEnabled) {
            logger.info("Buffered event %s", gdprPackage.getSuffix());
        } else {
            packageHandler.sendFirstPackage();
        }
    }

    private void disableThirdPartySharingI() {
        // cache the disable third party sharing request, so that the request order maintains
        // even this call returns before making server request
        SharedPreferencesManager sharedPreferencesManager = SharedPreferencesManager.getDefaultInstance(getContext());
        sharedPreferencesManager.setDisableThirdPartySharing();

        if (!checkActivityStateI(activityState)) { return; }
        if (!isEnabledI()) { return; }
        if (activityState.isGdprForgotten) { return; }
        if (activityState.isThirdPartySharingDisabled) { return; }
        if (adtraceConfig.coppaCompliantEnabled) {
            logger.warn("Call to disable third party sharing API ignored, already done when COPPA enabled");
            return;
        }

        activityState.isThirdPartySharingDisabled = true;
        writeActivityStateI();

        long now = System.currentTimeMillis();
        PackageBuilder packageBuilder = new PackageBuilder(adtraceConfig, deviceInfo, activityState, sessionParameters, now);

        ActivityPackage activityPackage = packageBuilder.buildDisableThirdPartySharingPackage();
        packageHandler.addPackage(activityPackage);

        // Removed the cached disable third party sharing flag.
        sharedPreferencesManager.removeDisableThirdPartySharing();

        if (adtraceConfig.eventBufferingEnabled) {
            logger.info("Buffered event %s", activityPackage.getSuffix());
        } else {
            packageHandler.sendFirstPackage();
        }
    }

    private void trackThirdPartySharingI(final AdTraceThirdPartySharing adtraceThirdPartySharing) {
        if (!checkActivityStateI(activityState)) {
            adtraceConfig.preLaunchActions.preLaunchAdTraceThirdPartySharingArray.add(
                    adtraceThirdPartySharing);
            return;
        }
        if (!isEnabledI()) {
            return;
        }
        if (!isEnabledI()) { return; }
        if (activityState.isGdprForgotten) { return; }
        if (adtraceConfig.coppaCompliantEnabled) {
            logger.warn("Calling third party sharing API not allowed when COPPA enabled");
            return;
        }

        long now = System.currentTimeMillis();
        PackageBuilder packageBuilder = new PackageBuilder(
                adtraceConfig, deviceInfo, activityState, sessionParameters, now);

        ActivityPackage activityPackage =
                packageBuilder.buildThirdPartySharingPackage(adtraceThirdPartySharing);
        packageHandler.addPackage(activityPackage);

        if (adtraceConfig.eventBufferingEnabled) {
            logger.info("Buffered event %s", activityPackage.getSuffix());
        } else {
            packageHandler.sendFirstPackage();
        }
    }

    private void trackMeasurementConsentI(final boolean consentMeasurement) {
        if (!checkActivityStateI(activityState)) {
            adtraceConfig.preLaunchActions.lastMeasurementConsentTracked = consentMeasurement;
            return;
        }
        if (!isEnabledI()) { return; }
        if (activityState.isGdprForgotten) { return; }

        long now = System.currentTimeMillis();
        PackageBuilder packageBuilder = new PackageBuilder(
                adtraceConfig, deviceInfo, activityState, sessionParameters, now);

        ActivityPackage activityPackage =
                packageBuilder.buildMeasurementConsentPackage(consentMeasurement);
        packageHandler.addPackage(activityPackage);

        if (adtraceConfig.eventBufferingEnabled) {
            logger.info("Buffered event %s", activityPackage.getSuffix());
        } else {
            packageHandler.sendFirstPackage();
        }
    }

    private void trackAdRevenueI(String source, JSONObject adRevenueJson) {
        if (!checkActivityStateI(activityState)) { return; }
        if (!isEnabledI()) { return; }
        if (activityState.isGdprForgotten) { return; }

        long now = System.currentTimeMillis();

        PackageBuilder packageBuilder = new PackageBuilder(adtraceConfig, deviceInfo, activityState, sessionParameters, now);

        ActivityPackage adRevenuePackage = packageBuilder.buildAdRevenuePackage(source, adRevenueJson);
        packageHandler.addPackage(adRevenuePackage);
        packageHandler.sendFirstPackage();
    }

    private void trackAdRevenueI(AdTraceAdRevenue adtraceAdRevenue) {
        if (!checkActivityStateI(activityState)) { return; }
        if (!isEnabledI()) { return; }
        if (!checkAdTraceAdRevenue(adtraceAdRevenue)) { return; }
        if (activityState.isGdprForgotten) { return; }

        long now = System.currentTimeMillis();

        PackageBuilder packageBuilder = new PackageBuilder(adtraceConfig, deviceInfo, activityState, sessionParameters, now);

        ActivityPackage adRevenuePackage = packageBuilder.buildAdRevenuePackage(adtraceAdRevenue, internalState.isInDelayedStart());
        packageHandler.addPackage(adRevenuePackage);
        packageHandler.sendFirstPackage();
    }

    private void trackSubscriptionI(final AdTracePlayStoreSubscription subscription) {
        if (!checkActivityStateI(activityState)) { return; }
        if (!isEnabledI()) { return; }
        if (activityState.isGdprForgotten) { return; }

        long now = System.currentTimeMillis();

        PackageBuilder packageBuilder = new PackageBuilder(adtraceConfig, deviceInfo, activityState, sessionParameters, now);

        ActivityPackage subscriptionPackage = packageBuilder.buildSubscriptionPackage(subscription, internalState.isInDelayedStart());
        packageHandler.addPackage(subscriptionPackage);
        packageHandler.sendFirstPackage();
    }

    private void verifyPurchaseI(final AdTracePurchase purchase, final OnPurchaseVerificationFinishedListener callback) {
        if (callback == null) {
            logger.warn("Purchase verification aborted because verification callback is null");
            return;
        }
        // from this moment on we know that we can ping client callback in case of error
        if (adtraceConfig.urlStrategy != null &&
                (adtraceConfig.urlStrategy.equals(AdTraceConfig.DATA_RESIDENCY_EU) ||
                        adtraceConfig.urlStrategy.equals(AdTraceConfig.DATA_RESIDENCY_US) ||
                                adtraceConfig.urlStrategy.equals(AdTraceConfig.DATA_RESIDENCY_TR))) {
            logger.warn("Purchase verification not available for data residency users right now");
            return;
        }
        if (!checkActivityStateI(activityState)) {
            AdTracePurchaseVerificationResult result = new AdTracePurchaseVerificationResult(
                    "not_verified",
                    102,
                    "Purchase verification aborted because SDK is still not initialized");
            callback.onVerificationFinished(result);
            logger.warn("Purchase verification aborted because SDK is still not initialized");
            return;
        }
        if (!isEnabledI()) {
            AdTracePurchaseVerificationResult result = new AdTracePurchaseVerificationResult(
                    "not_verified",
                    103,
                    "Purchase verification aborted because SDK is disabled");
            callback.onVerificationFinished(result);
            logger.warn("Purchase verification aborted because SDK is disabled");
            return;
        }
        if (activityState.isGdprForgotten) {
            AdTracePurchaseVerificationResult result = new AdTracePurchaseVerificationResult(
                    "not_verified",
                    104,
                    "Purchase verification aborted because user is GDPR forgotten");
            callback.onVerificationFinished(result);
            logger.warn("Purchase verification aborted because user is GDPR forgotten");
            return;
        }
        if (purchase == null) {
            logger.warn("Purchase verification aborted because purchase instance is null");
            AdTracePurchaseVerificationResult verificationResult =
                    new AdTracePurchaseVerificationResult(
                            "not_verified",
                            105,
                            "Purchase verification aborted because purchase instance is null");
            callback.onVerificationFinished(verificationResult);
            return;
        }

        long now = System.currentTimeMillis();
        PackageBuilder packageBuilder = new PackageBuilder(adtraceConfig, deviceInfo, activityState, sessionParameters, now);
        ActivityPackage verificationPackage = packageBuilder.buildVerificationPackage(purchase, callback);
        if (verificationPackage == null) {
            logger.warn("Purchase verification aborted because verification package is null");
            AdTracePurchaseVerificationResult verificationResult =
                    new AdTracePurchaseVerificationResult(
                            "not_verified",
                            106,
                            "Purchase verification aborted because verification package is null");
            callback.onVerificationFinished(verificationResult);
            return;
        }
        purchaseVerificationHandler.sendPurchaseVerificationPackage(verificationPackage);
    }

    private void gotOptOutResponseI() {
        activityState.isGdprForgotten = true;
        writeActivityStateI();

        packageHandler.flush();
        setEnabledI(false);
    }

    private void readActivityStateI(Context context) {
        try {
            activityState = Util.readObject(context, ACTIVITY_STATE_FILENAME, ACTIVITY_STATE_NAME, ActivityState.class);
        } catch (Exception e) {
            logger.error("Failed to read %s file (%s)", ACTIVITY_STATE_NAME, e.getMessage());
            activityState = null;
        }
        if (activityState != null) {
            internalState.firstSdkStart = true;
        }
    }

    private void readAttributionI(Context context) {
        try {
            attribution = Util.readObject(context, ATTRIBUTION_FILENAME, ATTRIBUTION_NAME, AdTraceAttribution.class);
        } catch (Exception e) {
            logger.error("Failed to read %s file (%s)", ATTRIBUTION_NAME, e.getMessage());
            attribution = null;
        }
    }

    private void readSessionCallbackParametersI(Context context) {
        try {
            sessionParameters.callbackParameters = Util.readObject(context,
                    SESSION_CALLBACK_PARAMETERS_FILENAME,
                    SESSION_CALLBACK_PARAMETERS_NAME,
                    (Class<Map<String, String>>) (Class) Map.class);
        } catch (Exception e) {
            logger.error("Failed to read %s file (%s)", SESSION_CALLBACK_PARAMETERS_NAME, e.getMessage());
            sessionParameters.callbackParameters = null;
        }
    }

    private void readSessionPartnerParametersI(Context context) {
        try {
            sessionParameters.partnerParameters = Util.readObject(context,
                    SESSION_PARTNER_PARAMETERS_FILENAME,
                    SESSION_PARTNER_PARAMETERS_NAME,
                    (Class<Map<String, String>>) (Class) Map.class);
        } catch (Exception e) {
            logger.error("Failed to read %s file (%s)", SESSION_PARTNER_PARAMETERS_NAME, e.getMessage());
            sessionParameters.partnerParameters = null;
        }
    }

    private void writeActivityStateI() {
        synchronized (ActivityState.class) {
            if (activityState == null) {
                return;
            }
            Util.writeObject(activityState, adtraceConfig.context, ACTIVITY_STATE_FILENAME, ACTIVITY_STATE_NAME);
        }
    }

    private void teardownActivityStateS() {
        synchronized (ActivityState.class) {
            if (activityState == null) {
                return;
            }
            activityState = null;
        }
    }

    private void writeAttributionI() {
        synchronized (AdTraceAttribution.class) {
            if (attribution == null) {
                return;
            }
            Util.writeObject(attribution, adtraceConfig.context, ATTRIBUTION_FILENAME, ATTRIBUTION_NAME);
        }
    }

    private void teardownAttributionS() {
        synchronized (AdTraceAttribution.class) {
            if (attribution == null) {
                return;
            }
            attribution = null;
        }
    }

    private void writeSessionCallbackParametersI() {
        synchronized (SessionParameters.class) {
            if (sessionParameters == null) {
                return;
            }
            Util.writeObject(sessionParameters.callbackParameters, adtraceConfig.context, SESSION_CALLBACK_PARAMETERS_FILENAME, SESSION_CALLBACK_PARAMETERS_NAME);
        }
    }

    private void writeSessionPartnerParametersI() {
        synchronized (SessionParameters.class) {
            if (sessionParameters == null) {
                return;
            }
            Util.writeObject(sessionParameters.partnerParameters, adtraceConfig.context, SESSION_PARTNER_PARAMETERS_FILENAME, SESSION_PARTNER_PARAMETERS_NAME);
        }
    }

    private void teardownAllSessionParametersS() {
        synchronized (SessionParameters.class) {
            if (sessionParameters == null) {
                return;
            }
            sessionParameters = null;
        }
    }

    private boolean checkEventI(AdTraceEvent event) {
        if (event == null) {
            logger.error("Event missing");
            return false;
        }

        if (!event.isValid()) {
            logger.error("Event not initialized correctly");
            return false;
        }

        return true;
    }

    private boolean checkOrderIdI(String orderId) {
        if (orderId == null || orderId.isEmpty()) {
            return true;  // no order ID given
        }

        if (activityState.findOrderId(orderId)) {
            logger.info("Skipping duplicated order ID '%s'", orderId);
            return false; // order ID found -> used already
        }

        activityState.addOrderId(orderId);
        logger.verbose("Added order ID '%s'", orderId);
        // activity state will get written by caller
        return true;
    }

    private boolean checkAdTraceAdRevenue(AdTraceAdRevenue adtraceAdRevenue) {
        if (adtraceAdRevenue == null) {
            logger.error("Ad revenue object missing");
            return false;
        }

        if (!adtraceAdRevenue.isValid()) {
            logger.error("Ad revenue object not initialized correctly");
            return false;
        }

        return true;
    }

    private boolean checkActivityStateI(ActivityState activityState) {
        if (internalState.hasFirstSdkStartNotOcurred()) {
            logger.error("Sdk did not yet start");
            return false;
        }
        return true;
    }

    private boolean pausedI() {
        return pausedI(false);
    }

    private boolean pausedI(boolean sdkClickHandlerOnly) {
        if (sdkClickHandlerOnly) {
            // sdk click handler is paused if either:
            return internalState.isOffline() ||     // it's offline
                    !isEnabledI();                  // is disabled
        }
        // other handlers are paused if either:
        return internalState.isOffline() ||      // it's offline
                !isEnabledI() ||      // is disabled
                internalState.isInDelayedStart();   // is in delayed start
    }

    private boolean toSendI() {
        return toSendI(false);
    }

    private boolean toSendI(boolean sdkClickHandlerOnly) {
        // don't send when it's paused
        if (pausedI(sdkClickHandlerOnly)) {
            return false;
        }

        // has the option to send in the background -> is to send
        if (adtraceConfig.sendInBackground) {
            return true;
        }

        // doesn't have the option -> depends on being on the background/foreground
        return internalState.isInForeground();
    }

    private void checkForInstallReferrerInfo(final SdkClickResponseData responseData) {
        if (!responseData.isInstallReferrer) {
            return;
        }

        boolean isInstallReferrerHuaweiAds =
                responseData.referrerApi != null &&
                        (responseData.referrerApi.equalsIgnoreCase(Constants.REFERRER_API_HUAWEI_ADS));
        if (isInstallReferrerHuaweiAds) {
            activityState.clickTimeHuawei = responseData.clickTime;
            activityState.installBeginHuawei = responseData.installBegin;
            activityState.installReferrerHuawei = responseData.installReferrer;

            writeActivityStateI();
            return;
        }

        boolean isInstallReferrerHuaweiAppGallery =
                responseData.referrerApi != null &&
                        (responseData.referrerApi.equalsIgnoreCase(Constants.REFERRER_API_HUAWEI_APP_GALLERY));

        if (isInstallReferrerHuaweiAppGallery) {
            activityState.clickTimeHuawei = responseData.clickTime;
            activityState.installBeginHuawei = responseData.installBegin;
            activityState.installReferrerHuaweiAppGallery = responseData.installReferrer;

            writeActivityStateI();
            return;
        }

        boolean isInstallReferrerMeta =
                responseData.referrerApi != null &&
                        (responseData.referrerApi.equalsIgnoreCase(REFERRER_API_META));

        if (isInstallReferrerMeta) {
            activityState.clickTimeMeta = responseData.clickTime;
            activityState.installReferrerMeta = responseData.installReferrer;
            activityState.isClickMeta = responseData.isClick;

            writeActivityStateI();
            return;
        }

        boolean isInstallReferrerSamsung =
                responseData.referrerApi != null &&
                        (responseData.referrerApi.equalsIgnoreCase(REFERRER_API_SAMSUNG));

        if (isInstallReferrerSamsung) {
            activityState.clickTimeSamsung = responseData.clickTime;
            activityState.installBeginSamsung = responseData.installBegin;
            activityState.installReferrerSamsung = responseData.installReferrer;

            writeActivityStateI();
            return;
        }

        boolean isInstallReferrerXiaomi =
                responseData.referrerApi != null &&
                        (responseData.referrerApi.equalsIgnoreCase(REFERRER_API_XIAOMI));

        if (isInstallReferrerXiaomi) {
            activityState.clickTimeXiaomi = responseData.clickTime;
            activityState.installBeginXiaomi = responseData.installBegin;
            activityState.installReferrerXiaomi = responseData.installReferrer;
            activityState.clickTimeServerXiaomi = responseData.clickTimeServer;
            activityState.installBeginServerXiaomi = responseData.installBeginServer;
            activityState.installVersionXiaomi = responseData.installVersion;

            writeActivityStateI();
            return;
        }

        boolean isInstallReferrerVivo =
                responseData.referrerApi != null &&
                        (responseData.referrerApi.equalsIgnoreCase(REFERRER_API_VIVO));

        if (isInstallReferrerVivo) {
            activityState.clickTimeVivo = responseData.clickTime;
            activityState.installBeginVivo = responseData.installBegin;
            activityState.installReferrerVivo = responseData.installReferrer;
            activityState.installVersionVivo = responseData.installVersion;

            writeActivityStateI();
            return;
        }

        activityState.clickTime = responseData.clickTime;
        activityState.installBegin = responseData.installBegin;
        activityState.installReferrer = responseData.installReferrer;
        activityState.clickTimeServer = responseData.clickTimeServer;
        activityState.installBeginServer = responseData.installBeginServer;
        activityState.installVersion = responseData.installVersion;
        activityState.googlePlayInstant = responseData.googlePlayInstant;

        writeActivityStateI();
    }

    private void processCoppaComplianceI() {
        if (!adtraceConfig.coppaCompliantEnabled) {
            resetThirdPartySharingCoppaActivityStateI();
            return;
        }

        disableThirdPartySharingForCoppaEnabledI();
    }

    private void disableThirdPartySharingForCoppaEnabledI() {
        if (!shouldDisableThirdPartySharingWhenCoppaEnabled()) {
            return;
        }

        activityState.isThirdPartySharingDisabledForCoppa = true;
        writeActivityStateI();
        AdTraceThirdPartySharing adtraceThirdPartySharing =
                new AdTraceThirdPartySharing(false);

        long now = System.currentTimeMillis();
        PackageBuilder packageBuilder = new PackageBuilder(
                adtraceConfig, deviceInfo, activityState, sessionParameters, now);

        ActivityPackage activityPackage =
                packageBuilder.buildThirdPartySharingPackage(adtraceThirdPartySharing);
        packageHandler.addPackage(activityPackage);

        if (adtraceConfig.eventBufferingEnabled) {
            logger.info("Buffered event %s", activityPackage.getSuffix());
        } else {
            packageHandler.sendFirstPackage();
        }
    }

    private void resetThirdPartySharingCoppaActivityStateI() {
        if (activityState == null) { return; }
        if (activityState.isThirdPartySharingDisabledForCoppa) {
            activityState.isThirdPartySharingDisabledForCoppa = false;
            writeActivityStateI();
        }
    }

    private boolean shouldDisableThirdPartySharingWhenCoppaEnabled() {
        if (activityState == null) {
            return false;
        }

        if (!isEnabledI()) {
            return false;
        }

        if (activityState.isGdprForgotten) {
            return false;
        }

        return !activityState.isThirdPartySharingDisabledForCoppa;
    }
}
