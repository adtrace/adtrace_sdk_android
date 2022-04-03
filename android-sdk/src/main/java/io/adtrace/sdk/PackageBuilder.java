//
//  PackageBuilder.java
//  AdTrace SDK
//
//  Created by Christian Wellenbrock (@wellle) on 25th June 2013.
//  Copyright (c) 2013-2018 AdTrace GmbH. All rights reserved.
//

package io.adtrace.sdk;

import android.content.ContentResolver;
import android.text.TextUtils;

import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (namini40@gmail.com) on August 2021.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2021.
 */


public class PackageBuilder {
    private static ILogger logger = AdTraceFactory.getLogger();
    private long createdAt;
    private DeviceInfo deviceInfo;
    private AdTraceConfig adTraceConfig;
    private ActivityStateCopy activityStateCopy;
    private SessionParameters sessionParameters;

    long clickTimeInSeconds = -1;
    long clickTimeInMilliseconds = -1;
    long installBeginTimeInSeconds = -1;
    long clickTimeServerInSeconds = -1;
    long installBeginTimeServerInSeconds = -1;
    String reftag;
    String deeplink;
    String referrer;
    String installVersion;
    String rawReferrer;
    String referrerApi;
    String preinstallPayload;
    String preinstallLocation;
    Boolean googlePlayInstant;
    AdTraceAttribution attribution;
    Map<String, String> extraParameters;

    private class ActivityStateCopy {
        int eventCount = -1;
        int sessionCount = -1;
        int subsessionCount = -1;
        long timeSpent = -1;
        long lastInterval = -1;
        long sessionLength = -1;
        String uuid = null;
        String pushToken = null;

        ActivityStateCopy(ActivityState activityState) {
            if (activityState == null) {
                return;
            }
            this.eventCount = activityState.eventCount;
            this.sessionCount = activityState.sessionCount;
            this.subsessionCount = activityState.subsessionCount;
            this.timeSpent = activityState.timeSpent;
            this.lastInterval = activityState.lastInterval;
            this.sessionLength = activityState.sessionLength;
            this.uuid = activityState.uuid;
            this.pushToken = activityState.pushToken;
        }
    }

    PackageBuilder(AdTraceConfig adTraceConfig,
                   DeviceInfo deviceInfo,
                   ActivityState activityState,
                   SessionParameters sessionParameters,
                   long createdAt) {
        this.createdAt = createdAt;
        this.deviceInfo = deviceInfo;
        this.adTraceConfig = adTraceConfig;
        this.activityStateCopy = new ActivityStateCopy(activityState);
        this.sessionParameters = sessionParameters;
    }

    ActivityPackage buildSessionPackage(boolean isInDelay) {
        Map<String, String> parameters = getSessionParameters(isInDelay);
        ActivityPackage sessionPackage = getDefaultActivityPackage(ActivityKind.SESSION);
        sessionPackage.setPath("/session");
        sessionPackage.setSuffix("");

        AdTraceSigner.sign(parameters, ActivityKind.SESSION.toString(),
                sessionPackage.getClientSdk(), adTraceConfig.context, adTraceConfig.logger);

        sessionPackage.setParameters(parameters);
        return sessionPackage;
    }

    ActivityPackage buildEventPackage(AdTraceEvent event, boolean isInDelay) {
        Map<String, String> parameters = getEventParameters(event, isInDelay);
        ActivityPackage eventPackage = getDefaultActivityPackage(ActivityKind.EVENT);
        eventPackage.setPath("/event");
        eventPackage.setSuffix(getEventSuffix(event));

        AdTraceSigner.sign(parameters, ActivityKind.EVENT.toString(),
                eventPackage.getClientSdk(), adTraceConfig.context, adTraceConfig.logger);

        eventPackage.setParameters(parameters);

        if (isInDelay) {
            eventPackage.setCallbackParameters(event.callbackParameters);
            eventPackage.setPartnerParameters(event.eventValueParameters);
        }

        return eventPackage;
    }

    ActivityPackage buildInfoPackage(String source) {
        Map<String, String> parameters = getInfoParameters(source);
        ActivityPackage infoPackage = getDefaultActivityPackage(ActivityKind.INFO);
        infoPackage.setPath("/sdk_info");
        infoPackage.setSuffix("");

        AdTraceSigner.sign(parameters, ActivityKind.INFO.toString(),
                infoPackage.getClientSdk(), adTraceConfig.context, adTraceConfig.logger);

        infoPackage.setParameters(parameters);
        return infoPackage;
    }

    ActivityPackage buildClickPackage(String source) {
        Map<String, String> parameters = getClickParameters(source);
        ActivityPackage clickPackage = getDefaultActivityPackage(ActivityKind.CLICK);
        clickPackage.setPath("/sdk_click");
        clickPackage.setSuffix("");
        clickPackage.setClickTimeInMilliseconds(clickTimeInMilliseconds);
        clickPackage.setClickTimeInSeconds(clickTimeInSeconds);
        clickPackage.setInstallBeginTimeInSeconds(installBeginTimeInSeconds);
        clickPackage.setClickTimeServerInSeconds(clickTimeServerInSeconds);
        clickPackage.setInstallBeginTimeServerInSeconds(installBeginTimeServerInSeconds);
        clickPackage.setInstallVersion(installVersion);
        clickPackage.setGooglePlayInstant(googlePlayInstant);

        AdTraceSigner.sign(parameters, ActivityKind.CLICK.toString(),
                clickPackage.getClientSdk(), adTraceConfig.context, adTraceConfig.logger);

        clickPackage.setParameters(parameters);
        return clickPackage;
    }

    ActivityPackage buildAttributionPackage(String initiatedByDescription) {
        Map<String, String> parameters = getAttributionParameters(initiatedByDescription);
        ActivityPackage attributionPackage = getDefaultActivityPackage(ActivityKind.ATTRIBUTION);
        attributionPackage.setPath("attribution"); // does not contain '/' because of Uri.Builder.appendPath
        attributionPackage.setSuffix("");

        AdTraceSigner.sign(parameters, ActivityKind.ATTRIBUTION.toString(),
                attributionPackage.getClientSdk(), adTraceConfig.context, adTraceConfig.logger);

        attributionPackage.setParameters(parameters);
        return attributionPackage;
    }

    ActivityPackage buildGdprPackage() {
        Map<String, String> parameters = getGdprParameters();
        ActivityPackage gdprPackage = getDefaultActivityPackage(ActivityKind.GDPR);
        gdprPackage.setPath("/gdpr_forget_device");
        gdprPackage.setSuffix("");

        AdTraceSigner.sign(parameters, ActivityKind.GDPR.toString(),
                gdprPackage.getClientSdk(), adTraceConfig.context, adTraceConfig.logger);

        gdprPackage.setParameters(parameters);
        return gdprPackage;
    }

    ActivityPackage buildDisableThirdPartySharingPackage() {
        Map<String, String> parameters = getDisableThirdPartySharingParameters();
        ActivityPackage activityPackage = getDefaultActivityPackage(ActivityKind.DISABLE_THIRD_PARTY_SHARING);
        activityPackage.setPath("/disable_third_party_sharing");
        activityPackage.setSuffix("");

        AdTraceSigner.sign(parameters, ActivityKind.DISABLE_THIRD_PARTY_SHARING.toString(),
                activityPackage.getClientSdk(), adTraceConfig.context, adTraceConfig.logger);

        activityPackage.setParameters(parameters);
        return activityPackage;
    }

    ActivityPackage buildThirdPartySharingPackage(
            final AdTraceThirdPartySharing adTraceThirdPartySharing)
    {
        Map<String, String> parameters = getThirdPartySharingParameters(adTraceThirdPartySharing);
        ActivityPackage activityPackage = getDefaultActivityPackage(ActivityKind.THIRD_PARTY_SHARING);
        activityPackage.setPath("/third_party_sharing");
        activityPackage.setSuffix("");

        AdTraceSigner.sign(parameters, ActivityKind.THIRD_PARTY_SHARING.toString(),
                activityPackage.getClientSdk(), adTraceConfig.context, adTraceConfig.logger);

        activityPackage.setParameters(parameters);
        return activityPackage;
    }

    ActivityPackage buildMeasurementConsentPackage(final boolean consentMeasurement) {
        Map<String, String> parameters = getMeasurementConsentParameters(consentMeasurement);
        ActivityPackage activityPackage =
                getDefaultActivityPackage(ActivityKind.MEASUREMENT_CONSENT);
        activityPackage.setPath("/measurement_consent");
        activityPackage.setSuffix("");

        AdTraceSigner.sign(parameters, ActivityKind.MEASUREMENT_CONSENT.toString(),
                activityPackage.getClientSdk(), adTraceConfig.context, adTraceConfig.logger);

        activityPackage.setParameters(parameters);
        return activityPackage;
    }

    ActivityPackage buildAdRevenuePackage(String source, JSONObject adRevenueJson) {
        Map<String, String> parameters = getAdRevenueParameters(source, adRevenueJson);
        ActivityPackage adRevenuePackage = getDefaultActivityPackage(ActivityKind.AD_REVENUE);
        adRevenuePackage.setPath("/ad_revenue");
        adRevenuePackage.setSuffix("");

        AdTraceSigner.sign(parameters, ActivityKind.AD_REVENUE.toString(),
                adRevenuePackage.getClientSdk(), adTraceConfig.context, adTraceConfig.logger);

        adRevenuePackage.setParameters(parameters);
        return adRevenuePackage;
    }

    ActivityPackage buildAdRevenuePackage(AdTraceAdRevenue adTraceAdRevenue, boolean isInDelay) {
        Map<String, String> parameters = getAdRevenueParameters(adTraceAdRevenue, isInDelay);
        ActivityPackage adRevenuePackage = getDefaultActivityPackage(ActivityKind.AD_REVENUE);
        adRevenuePackage.setPath("/ad_revenue");
        adRevenuePackage.setSuffix("");

        AdTraceSigner.sign(parameters, ActivityKind.AD_REVENUE.toString(),
                          adRevenuePackage.getClientSdk(), adTraceConfig.context, adTraceConfig.logger);

        adRevenuePackage.setParameters(parameters);

        if (isInDelay) {
            adRevenuePackage.setCallbackParameters(adTraceAdRevenue.callbackParameters);
            adRevenuePackage.setPartnerParameters(adTraceAdRevenue.partnerParameters);
        }

        return adRevenuePackage;
    }

    ActivityPackage buildSubscriptionPackage(AdTracePlayStoreSubscription subscription, boolean isInDelay) {
        Map<String, String> parameters = getSubscriptionParameters(subscription, isInDelay);
        ActivityPackage subscriptionPackage = getDefaultActivityPackage(ActivityKind.SUBSCRIPTION);
        subscriptionPackage.setPath("/v2/purchase");
        subscriptionPackage.setSuffix("");

        AdTraceSigner.sign(parameters, ActivityKind.SUBSCRIPTION.toString(),
                subscriptionPackage.getClientSdk(), adTraceConfig.context, adTraceConfig.logger);

        subscriptionPackage.setParameters(parameters);
        return subscriptionPackage;
    }

    private Map<String, String> getSessionParameters(boolean isInDelay) {
        ContentResolver contentResolver = adTraceConfig.context.getContentResolver();
        Map<String, String> parameters = new HashMap<String, String>();
        Map<String, String> imeiParameters = Reflection.getImeiParameters(adTraceConfig.context, logger);

        // Check if plugin is used and if yes, add read parameters.
        if (imeiParameters != null) {
            parameters.putAll(imeiParameters);
        }

        // Check if oaid plugin is used and if yes, add the parameter
        Map<String, String> oaidParameters = Reflection.getOaidParameters(adTraceConfig.context, logger);
        if (oaidParameters != null) {
            parameters.putAll(oaidParameters);
        }

        // Callback and partner parameters.
        if (!isInDelay) {
            PackageBuilder.addMapJson(parameters, "callback_params", this.sessionParameters.callbackParameters);
            PackageBuilder.addMapJson(parameters, "partner_params", this.sessionParameters.partnerParameters);
        }

        // Device identifiers.
        deviceInfo.reloadPlayIds(adTraceConfig.context);
        PackageBuilder.addString(parameters, "android_uuid", activityStateCopy.uuid);
        PackageBuilder.addString(parameters, "gps_adid", deviceInfo.playAdId);
        PackageBuilder.addLong(parameters, "gps_adid_attempt", deviceInfo.playAdIdAttempt);
        PackageBuilder.addString(parameters, "gps_adid_src", deviceInfo.playAdIdSource);
        PackageBuilder.addBoolean(parameters, "tracking_enabled", deviceInfo.isTrackingEnabled);
        PackageBuilder.addString(parameters, "fire_adid", Util.getFireAdvertisingId(contentResolver));
        PackageBuilder.addBoolean(parameters, "fire_tracking_enabled", Util.getFireTrackingEnabled(contentResolver));

        if (!containsPlayIds(parameters) && !containsFireIds(parameters)) {
            logger.warn("Google Advertising ID or Fire Advertising ID not detected, " +
                    "fallback to non Google Play and Fire identifiers will take place");
            deviceInfo.reloadNonPlayIds(adTraceConfig.context);
            PackageBuilder.addString(parameters, "android_id", deviceInfo.androidId);
            PackageBuilder.addString(parameters, "mac_md5", deviceInfo.macShortMd5);
            PackageBuilder.addString(parameters, "mac_sha1", deviceInfo.macSha1);
        }

        // Rest of the parameters.
        PackageBuilder.addString(parameters, "api_level", deviceInfo.apiLevel);
        PackageBuilder.addString(parameters, "app_secret", adTraceConfig.appSecret);
        PackageBuilder.addString(parameters, "app_token", adTraceConfig.appToken);
        PackageBuilder.addString(parameters, "app_version", deviceInfo.appVersion);
        PackageBuilder.addBoolean(parameters, "attribution_deeplink", true);
        PackageBuilder.addLong(parameters, "connectivity_type", Util.getConnectivityType(adTraceConfig.context));
        PackageBuilder.addString(parameters, "country", deviceInfo.country);
        PackageBuilder.addString(parameters, "cpu_type", deviceInfo.abi);
        PackageBuilder.addDateInMilliseconds(parameters, "created_at", createdAt);
        PackageBuilder.addString(parameters, "default_tracker", adTraceConfig.defaultTracker);
        PackageBuilder.addBoolean(parameters, "device_known", adTraceConfig.deviceKnown);
        PackageBuilder.addBoolean(parameters, "needs_cost", adTraceConfig.needsCost);
        PackageBuilder.addString(parameters, "device_manufacturer", deviceInfo.deviceManufacturer);
        PackageBuilder.addString(parameters, "device_name", deviceInfo.deviceName);
        PackageBuilder.addString(parameters, "device_type", deviceInfo.deviceType);
        PackageBuilder.addLong(parameters, "ui_mode", deviceInfo.uiMode);
        PackageBuilder.addString(parameters, "display_height", deviceInfo.displayHeight);
        PackageBuilder.addString(parameters, "display_width", deviceInfo.displayWidth);
        PackageBuilder.addString(parameters, "environment", adTraceConfig.environment);
        PackageBuilder.addBoolean(parameters, "event_buffering_enabled", adTraceConfig.eventBufferingEnabled);
        PackageBuilder.addString(parameters, "external_device_id", adTraceConfig.externalDeviceId);
        PackageBuilder.addString(parameters, "fb_id", deviceInfo.fbAttributionId);
        PackageBuilder.addString(parameters, "hardware_name", deviceInfo.hardwareName);
        PackageBuilder.addString(parameters, "installed_at", deviceInfo.appInstallTime);
        PackageBuilder.addString(parameters, "language", deviceInfo.language);
        PackageBuilder.addDuration(parameters, "last_interval", activityStateCopy.lastInterval);
        PackageBuilder.addString(parameters, "mcc", Util.getMcc(adTraceConfig.context));
        PackageBuilder.addString(parameters, "mnc", Util.getMnc(adTraceConfig.context));
        PackageBuilder.addBoolean(parameters, "needs_response_details", true);
        PackageBuilder.addLong(parameters, "network_type", Util.getNetworkType(adTraceConfig.context));
        PackageBuilder.addString(parameters, "os_build", deviceInfo.buildName);
        PackageBuilder.addString(parameters, "os_name", deviceInfo.osName);
        PackageBuilder.addString(parameters, "os_version", deviceInfo.osVersion);
        PackageBuilder.addString(parameters, "package_name", deviceInfo.packageName);
        PackageBuilder.addString(parameters, "push_token", activityStateCopy.pushToken);
        PackageBuilder.addString(parameters, "screen_density", deviceInfo.screenDensity);
        PackageBuilder.addString(parameters, "screen_format", deviceInfo.screenFormat);
        PackageBuilder.addString(parameters, "screen_size", deviceInfo.screenSize);
        PackageBuilder.addString(parameters, "secret_id", adTraceConfig.secretId);
        PackageBuilder.addLong(parameters, "session_count", activityStateCopy.sessionCount);
        PackageBuilder.addDuration(parameters, "session_length", activityStateCopy.sessionLength);
        PackageBuilder.addLong(parameters, "subsession_count", activityStateCopy.subsessionCount);
        PackageBuilder.addDuration(parameters, "time_spent", activityStateCopy.timeSpent);
        PackageBuilder.addString(parameters, "updated_at", deviceInfo.appUpdateTime);

        checkDeviceIds(parameters);
        return parameters;
    }

    public Map<String, String> getEventParameters(AdTraceEvent event, boolean isInDelay) {
        ContentResolver contentResolver = adTraceConfig.context.getContentResolver();
        Map<String, String> parameters = new HashMap<String, String>();
        Map<String, String> imeiParameters = Reflection.getImeiParameters(adTraceConfig.context, logger);

        // Check if plugin is used and if yes, add read parameters.
        if (imeiParameters != null) {
            parameters.putAll(imeiParameters);
        }

        // Check if oaid plugin is used and if yes, add the parameter
        Map<String, String> oaidParameters = Reflection.getOaidParameters(adTraceConfig.context, logger);
        if (oaidParameters != null) {
            parameters.putAll(oaidParameters);
        }

        // Callback and partner parameters.
        if (!isInDelay) {
            PackageBuilder.addMapJson(parameters, "callback_params", Util.mergeParameters(this.sessionParameters.callbackParameters, event.callbackParameters, "Callback"));
            PackageBuilder.addMapJson(parameters, "event_value_params", Util.mergeParameters(this.sessionParameters.partnerParameters, event.eventValueParameters, "EventValueParams"));
        }

        // Device identifiers.
        deviceInfo.reloadPlayIds(adTraceConfig.context);
        PackageBuilder.addString(parameters, "android_uuid", activityStateCopy.uuid);
        PackageBuilder.addString(parameters, "gps_adid", deviceInfo.playAdId);
        PackageBuilder.addLong(parameters, "gps_adid_attempt", deviceInfo.playAdIdAttempt);
        PackageBuilder.addString(parameters, "gps_adid_src", deviceInfo.playAdIdSource);
        PackageBuilder.addBoolean(parameters, "tracking_enabled", deviceInfo.isTrackingEnabled);
        PackageBuilder.addString(parameters, "fire_adid", Util.getFireAdvertisingId(contentResolver));
        PackageBuilder.addBoolean(parameters, "fire_tracking_enabled", Util.getFireTrackingEnabled(contentResolver));

        if (!containsPlayIds(parameters) && !containsFireIds(parameters)) {
            logger.warn("Google Advertising ID or Fire Advertising ID not detected, " +
                    "fallback to non Google Play and Fire identifiers will take place");
            deviceInfo.reloadNonPlayIds(adTraceConfig.context);
            PackageBuilder.addString(parameters, "android_id", deviceInfo.androidId);
            PackageBuilder.addString(parameters, "mac_md5", deviceInfo.macShortMd5);
            PackageBuilder.addString(parameters, "mac_sha1", deviceInfo.macSha1);
        }

        // Rest of the parameters.
        PackageBuilder.addString(parameters, "api_level", deviceInfo.apiLevel);
        PackageBuilder.addString(parameters, "app_secret", adTraceConfig.appSecret);
        PackageBuilder.addString(parameters, "app_token", adTraceConfig.appToken);
        PackageBuilder.addString(parameters, "app_version", deviceInfo.appVersion);
        PackageBuilder.addBoolean(parameters, "attribution_deeplink", true);
        PackageBuilder.addLong(parameters, "connectivity_type", Util.getConnectivityType(adTraceConfig.context));
        PackageBuilder.addString(parameters, "country", deviceInfo.country);
        PackageBuilder.addString(parameters, "cpu_type", deviceInfo.abi);
        PackageBuilder.addDateInMilliseconds(parameters, "created_at", createdAt);
        PackageBuilder.addString(parameters, "currency", event.currency);
        PackageBuilder.addBoolean(parameters, "device_known", adTraceConfig.deviceKnown);
        PackageBuilder.addBoolean(parameters, "needs_cost", adTraceConfig.needsCost);
        PackageBuilder.addString(parameters, "device_manufacturer", deviceInfo.deviceManufacturer);
        PackageBuilder.addString(parameters, "device_name", deviceInfo.deviceName);
        PackageBuilder.addString(parameters, "device_type", deviceInfo.deviceType);
        PackageBuilder.addLong(parameters, "ui_mode", deviceInfo.uiMode);
        PackageBuilder.addString(parameters, "display_height", deviceInfo.displayHeight);
        PackageBuilder.addString(parameters, "display_width", deviceInfo.displayWidth);
        PackageBuilder.addString(parameters, "environment", adTraceConfig.environment);
        PackageBuilder.addString(parameters, "event_callback_id", event.callbackId);
        PackageBuilder.addLong(parameters, "event_count", activityStateCopy.eventCount);
        PackageBuilder.addBoolean(parameters, "event_buffering_enabled", adTraceConfig.eventBufferingEnabled);
        PackageBuilder.addString(parameters, "event_token", event.eventToken);
        PackageBuilder.addString(parameters, "external_device_id", adTraceConfig.externalDeviceId);
        PackageBuilder.addString(parameters, "fb_id", deviceInfo.fbAttributionId);
        PackageBuilder.addString(parameters, "hardware_name", deviceInfo.hardwareName);
        PackageBuilder.addString(parameters, "language", deviceInfo.language);
        PackageBuilder.addString(parameters, "mcc", Util.getMcc(adTraceConfig.context));
        PackageBuilder.addString(parameters, "mnc", Util.getMnc(adTraceConfig.context));
        PackageBuilder.addBoolean(parameters, "needs_response_details", true);
        PackageBuilder.addLong(parameters, "network_type", Util.getNetworkType(adTraceConfig.context));
        PackageBuilder.addString(parameters, "os_build", deviceInfo.buildName);
        PackageBuilder.addString(parameters, "os_name", deviceInfo.osName);
        PackageBuilder.addString(parameters, "os_version", deviceInfo.osVersion);
        PackageBuilder.addString(parameters, "package_name", deviceInfo.packageName);
        PackageBuilder.addString(parameters, "push_token", activityStateCopy.pushToken);
        PackageBuilder.addDouble(parameters, "revenue", event.revenue);
        PackageBuilder.addString(parameters, "screen_density", deviceInfo.screenDensity);
        PackageBuilder.addString(parameters, "screen_format", deviceInfo.screenFormat);
        PackageBuilder.addString(parameters, "screen_size", deviceInfo.screenSize);
        PackageBuilder.addString(parameters, "secret_id", adTraceConfig.secretId);
        PackageBuilder.addLong(parameters, "session_count", activityStateCopy.sessionCount);
        PackageBuilder.addDuration(parameters, "session_length", activityStateCopy.sessionLength);
        PackageBuilder.addLong(parameters, "subsession_count", activityStateCopy.subsessionCount);
        PackageBuilder.addDuration(parameters, "time_spent", activityStateCopy.timeSpent);

        checkDeviceIds(parameters);
        return parameters;
    }

    private Map<String, String> getInfoParameters(String source) {
        ContentResolver contentResolver = adTraceConfig.context.getContentResolver();
        Map<String, String> parameters = new HashMap<String, String>();
        Map<String, String> imeiParameters = Reflection.getImeiParameters(adTraceConfig.context, logger);

        // Check if plugin is used and if yes, add read parameters.
        if (imeiParameters != null) {
            parameters.putAll(imeiParameters);
        }

        // Check if oaid plugin is used and if yes, add the parameter
        Map<String, String> oaidParameters = Reflection.getOaidParameters(adTraceConfig.context, logger);
        if (oaidParameters != null) {
            parameters.putAll(oaidParameters);
        }

        // Device identifiers.
        deviceInfo.reloadPlayIds(adTraceConfig.context);
        PackageBuilder.addString(parameters, "android_uuid", activityStateCopy.uuid);
        PackageBuilder.addString(parameters, "gps_adid", deviceInfo.playAdId);
        PackageBuilder.addLong(parameters, "gps_adid_attempt", deviceInfo.playAdIdAttempt);
        PackageBuilder.addString(parameters, "gps_adid_src", deviceInfo.playAdIdSource);
        PackageBuilder.addBoolean(parameters, "tracking_enabled", deviceInfo.isTrackingEnabled);
        PackageBuilder.addString(parameters, "fire_adid", Util.getFireAdvertisingId(contentResolver));
        PackageBuilder.addBoolean(parameters, "fire_tracking_enabled", Util.getFireTrackingEnabled(contentResolver));

        if (!containsPlayIds(parameters) && !containsFireIds(parameters)) {
            logger.warn("Google Advertising ID or Fire Advertising ID not detected, " +
                    "fallback to non Google Play and Fire identifiers will take place");
            deviceInfo.reloadNonPlayIds(adTraceConfig.context);
            PackageBuilder.addString(parameters, "android_id", deviceInfo.androidId);
            PackageBuilder.addString(parameters, "mac_md5", deviceInfo.macShortMd5);
            PackageBuilder.addString(parameters, "mac_sha1", deviceInfo.macSha1);
        }

        // Rest of the parameters.
        PackageBuilder.addString(parameters, "app_secret", adTraceConfig.appSecret);
        PackageBuilder.addString(parameters, "app_token", adTraceConfig.appToken);
        PackageBuilder.addBoolean(parameters, "attribution_deeplink", true);
        PackageBuilder.addDateInMilliseconds(parameters, "created_at", createdAt);
        PackageBuilder.addBoolean(parameters, "device_known", adTraceConfig.deviceKnown);
        PackageBuilder.addBoolean(parameters, "needs_cost", adTraceConfig.needsCost);
        PackageBuilder.addString(parameters, "environment", adTraceConfig.environment);
        PackageBuilder.addBoolean(parameters, "event_buffering_enabled", adTraceConfig.eventBufferingEnabled);
        PackageBuilder.addString(parameters, "external_device_id", adTraceConfig.externalDeviceId);
        PackageBuilder.addBoolean(parameters, "needs_response_details", true);
        PackageBuilder.addString(parameters, "push_token", activityStateCopy.pushToken);
        PackageBuilder.addString(parameters, "secret_id", adTraceConfig.secretId);
        PackageBuilder.addString(parameters, "source", source);

        checkDeviceIds(parameters);
        return parameters;
    }

    private Map<String, String> getClickParameters(String source) {
        ContentResolver contentResolver = adTraceConfig.context.getContentResolver();
        Map<String, String> parameters = new HashMap<String, String>();
        Map<String, String> imeiParameters = Reflection.getImeiParameters(adTraceConfig.context, logger);

        // Check if plugin is used and if yes, add read parameters.
        if (imeiParameters != null) {
            parameters.putAll(imeiParameters);
        }

        // Check if oaid plugin is used and if yes, add the parameter
        Map<String, String> oaidParameters = Reflection.getOaidParameters(adTraceConfig.context, logger);
        if (oaidParameters != null) {
            parameters.putAll(oaidParameters);
        }

        // Device identifiers.
        deviceInfo.reloadPlayIds(adTraceConfig.context);
        PackageBuilder.addString(parameters, "android_uuid", activityStateCopy.uuid);
        PackageBuilder.addString(parameters, "gps_adid", deviceInfo.playAdId);
        PackageBuilder.addLong(parameters, "gps_adid_attempt", deviceInfo.playAdIdAttempt);
        PackageBuilder.addString(parameters, "gps_adid_src", deviceInfo.playAdIdSource);
        PackageBuilder.addBoolean(parameters, "tracking_enabled", deviceInfo.isTrackingEnabled);
        PackageBuilder.addString(parameters, "fire_adid", Util.getFireAdvertisingId(contentResolver));
        PackageBuilder.addBoolean(parameters, "fire_tracking_enabled", Util.getFireTrackingEnabled(contentResolver));

        if (!containsPlayIds(parameters) && !containsFireIds(parameters)) {
            logger.warn("Google Advertising ID or Fire Advertising ID not detected, " +
                    "fallback to non Google Play and Fire identifiers will take place");
            deviceInfo.reloadNonPlayIds(adTraceConfig.context);
            PackageBuilder.addString(parameters, "android_id", deviceInfo.androidId);
            PackageBuilder.addString(parameters, "mac_md5", deviceInfo.macShortMd5);
            PackageBuilder.addString(parameters, "mac_sha1", deviceInfo.macSha1);
        }

        // Attribution parameters.
        if (attribution != null) {
            PackageBuilder.addString(parameters, "tracker", attribution.trackerName);
            PackageBuilder.addString(parameters, "campaign", attribution.campaign);
            PackageBuilder.addString(parameters, "adgroup", attribution.adgroup);
            PackageBuilder.addString(parameters, "creative", attribution.creative);
        }

        // Rest of the parameters.
        PackageBuilder.addString(parameters, "api_level", deviceInfo.apiLevel);
        PackageBuilder.addString(parameters, "app_secret", adTraceConfig.appSecret);
        PackageBuilder.addString(parameters, "app_token", adTraceConfig.appToken);
        PackageBuilder.addString(parameters, "app_version", deviceInfo.appVersion);
        PackageBuilder.addBoolean(parameters, "attribution_deeplink", true);
        PackageBuilder.addMapJson(parameters, "callback_params", this.sessionParameters.callbackParameters);
        PackageBuilder.addDateInMilliseconds(parameters, "click_time", clickTimeInMilliseconds);
        PackageBuilder.addDateInSeconds(parameters, "click_time", clickTimeInSeconds);
        PackageBuilder.addDateInSeconds(parameters, "click_time_server", clickTimeServerInSeconds);
        PackageBuilder.addLong(parameters, "connectivity_type", Util.getConnectivityType(adTraceConfig.context));
        PackageBuilder.addString(parameters, "country", deviceInfo.country);
        PackageBuilder.addString(parameters, "cpu_type", deviceInfo.abi);
        PackageBuilder.addDateInMilliseconds(parameters, "created_at", createdAt);
        PackageBuilder.addString(parameters, "deeplink", deeplink);
        PackageBuilder.addBoolean(parameters, "device_known", adTraceConfig.deviceKnown);
        PackageBuilder.addBoolean(parameters, "needs_cost", adTraceConfig.needsCost);
        PackageBuilder.addString(parameters, "device_manufacturer", deviceInfo.deviceManufacturer);
        PackageBuilder.addString(parameters, "device_name", deviceInfo.deviceName);
        PackageBuilder.addString(parameters, "device_type", deviceInfo.deviceType);
        PackageBuilder.addLong(parameters, "ui_mode", deviceInfo.uiMode);
        PackageBuilder.addString(parameters, "display_height", deviceInfo.displayHeight);
        PackageBuilder.addString(parameters, "display_width", deviceInfo.displayWidth);
        PackageBuilder.addString(parameters, "environment", adTraceConfig.environment);
        PackageBuilder.addBoolean(parameters, "event_buffering_enabled", adTraceConfig.eventBufferingEnabled);
        PackageBuilder.addString(parameters, "external_device_id", adTraceConfig.externalDeviceId);
        PackageBuilder.addString(parameters, "fb_id", deviceInfo.fbAttributionId);
        PackageBuilder.addBoolean(parameters, "google_play_instant", googlePlayInstant);
        PackageBuilder.addString(parameters, "hardware_name", deviceInfo.hardwareName);
        PackageBuilder.addDateInSeconds(parameters, "install_begin_time", installBeginTimeInSeconds);
        PackageBuilder.addDateInSeconds(parameters, "install_begin_time_server", installBeginTimeServerInSeconds);
        PackageBuilder.addString(parameters, "install_version", installVersion);
        PackageBuilder.addString(parameters, "installed_at", deviceInfo.appInstallTime);
        PackageBuilder.addString(parameters, "language", deviceInfo.language);
        PackageBuilder.addDuration(parameters, "last_interval", activityStateCopy.lastInterval);
        PackageBuilder.addString(parameters, "mcc", Util.getMcc(adTraceConfig.context));
        PackageBuilder.addString(parameters, "mnc", Util.getMnc(adTraceConfig.context));
        PackageBuilder.addBoolean(parameters, "needs_response_details", true);
        PackageBuilder.addLong(parameters, "network_type", Util.getNetworkType(adTraceConfig.context));
        PackageBuilder.addString(parameters, "os_build", deviceInfo.buildName);
        PackageBuilder.addString(parameters, "os_name", deviceInfo.osName);
        PackageBuilder.addString(parameters, "os_version", deviceInfo.osVersion);
        PackageBuilder.addString(parameters, "package_name", deviceInfo.packageName);
        PackageBuilder.addMapJson(parameters, "params", extraParameters);
        PackageBuilder.addMapJson(parameters, "partner_params", this.sessionParameters.partnerParameters);
        PackageBuilder.addString(parameters, "push_token", activityStateCopy.pushToken);
        PackageBuilder.addString(parameters, "raw_referrer", rawReferrer);
        PackageBuilder.addString(parameters, "referrer", referrer);
        PackageBuilder.addString(parameters, "referrer_api", referrerApi);
        PackageBuilder.addString(parameters, "reftag", reftag);
        PackageBuilder.addString(parameters, "screen_density", deviceInfo.screenDensity);
        PackageBuilder.addString(parameters, "screen_format", deviceInfo.screenFormat);
        PackageBuilder.addString(parameters, "screen_size", deviceInfo.screenSize);
        PackageBuilder.addString(parameters, "secret_id", adTraceConfig.secretId);
        PackageBuilder.addLong(parameters, "session_count", activityStateCopy.sessionCount);
        PackageBuilder.addDuration(parameters, "session_length", activityStateCopy.sessionLength);
        PackageBuilder.addString(parameters, "source", source);
        PackageBuilder.addLong(parameters, "subsession_count", activityStateCopy.subsessionCount);
        PackageBuilder.addDuration(parameters, "time_spent", activityStateCopy.timeSpent);
        PackageBuilder.addString(parameters, "updated_at", deviceInfo.appUpdateTime);
        PackageBuilder.addString(parameters, "payload", preinstallPayload);
        PackageBuilder.addString(parameters, "found_location", preinstallLocation);

        checkDeviceIds(parameters);
        return parameters;
    }

    private Map<String, String> getAttributionParameters(String initiatedBy) {
        ContentResolver contentResolver = adTraceConfig.context.getContentResolver();
        Map<String, String> parameters = new HashMap<String, String>();
        Map<String, String> imeiParameters = Reflection.getImeiParameters(adTraceConfig.context, logger);

        // Check if plugin is used and if yes, add read parameters.
        if (imeiParameters != null) {
            parameters.putAll(imeiParameters);
        }

        // Check if oaid plugin is used and if yes, add the parameter
        Map<String, String> oaidParameters = Reflection.getOaidParameters(adTraceConfig.context, logger);
        if (oaidParameters != null) {
            parameters.putAll(oaidParameters);
        }

        // Device identifiers.
        deviceInfo.reloadPlayIds(adTraceConfig.context);
        PackageBuilder.addString(parameters, "android_uuid", activityStateCopy.uuid);
        PackageBuilder.addString(parameters, "gps_adid", deviceInfo.playAdId);
        PackageBuilder.addLong(parameters, "gps_adid_attempt", deviceInfo.playAdIdAttempt);
        PackageBuilder.addString(parameters, "gps_adid_src", deviceInfo.playAdIdSource);
        PackageBuilder.addBoolean(parameters, "tracking_enabled", deviceInfo.isTrackingEnabled);
        PackageBuilder.addString(parameters, "fire_adid", Util.getFireAdvertisingId(contentResolver));
        PackageBuilder.addBoolean(parameters, "fire_tracking_enabled", Util.getFireTrackingEnabled(contentResolver));

        if (!containsPlayIds(parameters) && !containsFireIds(parameters)) {
            logger.warn("Google Advertising ID or Fire Advertising ID not detected, " +
                    "fallback to non Google Play and Fire identifiers will take place");
            deviceInfo.reloadNonPlayIds(adTraceConfig.context);
            PackageBuilder.addString(parameters, "android_id", deviceInfo.androidId);
            PackageBuilder.addString(parameters, "mac_md5", deviceInfo.macShortMd5);
            PackageBuilder.addString(parameters, "mac_sha1", deviceInfo.macSha1);
        }

        // Rest of the parameters.
        PackageBuilder.addString(parameters, "api_level", deviceInfo.apiLevel);
        PackageBuilder.addString(parameters, "app_secret", adTraceConfig.appSecret);
        PackageBuilder.addString(parameters, "app_token", adTraceConfig.appToken);
        PackageBuilder.addString(parameters, "app_version", deviceInfo.appVersion);
        PackageBuilder.addBoolean(parameters, "attribution_deeplink", true);
        PackageBuilder.addDateInMilliseconds(parameters, "created_at", createdAt);
        PackageBuilder.addBoolean(parameters, "device_known", adTraceConfig.deviceKnown);
        PackageBuilder.addBoolean(parameters, "needs_cost", adTraceConfig.needsCost);
        PackageBuilder.addString(parameters, "device_name", deviceInfo.deviceName);
        PackageBuilder.addString(parameters, "device_type", deviceInfo.deviceType);
        PackageBuilder.addLong(parameters, "ui_mode", deviceInfo.uiMode);
        PackageBuilder.addString(parameters, "environment", adTraceConfig.environment);
        PackageBuilder.addBoolean(parameters, "event_buffering_enabled", adTraceConfig.eventBufferingEnabled);
        PackageBuilder.addString(parameters, "external_device_id", adTraceConfig.externalDeviceId);
        PackageBuilder.addString(parameters, "initiated_by", initiatedBy);
        PackageBuilder.addBoolean(parameters, "needs_response_details", true);
        PackageBuilder.addString(parameters, "os_name", deviceInfo.osName);
        PackageBuilder.addString(parameters, "os_version", deviceInfo.osVersion);
        PackageBuilder.addString(parameters, "package_name", deviceInfo.packageName);
        PackageBuilder.addString(parameters, "push_token", activityStateCopy.pushToken);
        PackageBuilder.addString(parameters, "secret_id", adTraceConfig.secretId);

        checkDeviceIds(parameters);
        return parameters;
    }

    private Map<String, String> getGdprParameters() {
        ContentResolver contentResolver = adTraceConfig.context.getContentResolver();
        Map<String, String> parameters = new HashMap<String, String>();
        Map<String, String> imeiParameters = Reflection.getImeiParameters(adTraceConfig.context, logger);

        // Check if plugin is used and if yes, add read parameters.
        if (imeiParameters != null) {
            parameters.putAll(imeiParameters);
        }

        // Check if oaid plugin is used and if yes, add the parameter
        Map<String, String> oaidParameters = Reflection.getOaidParameters(adTraceConfig.context, logger);
        if (oaidParameters != null) {
            parameters.putAll(oaidParameters);
        }

        // Device identifiers.
        deviceInfo.reloadPlayIds(adTraceConfig.context);
        PackageBuilder.addString(parameters, "android_uuid", activityStateCopy.uuid);
        PackageBuilder.addString(parameters, "gps_adid", deviceInfo.playAdId);
        PackageBuilder.addLong(parameters, "gps_adid_attempt", deviceInfo.playAdIdAttempt);
        PackageBuilder.addString(parameters, "gps_adid_src", deviceInfo.playAdIdSource);
        PackageBuilder.addBoolean(parameters, "tracking_enabled", deviceInfo.isTrackingEnabled);
        PackageBuilder.addString(parameters, "fire_adid", Util.getFireAdvertisingId(contentResolver));
        PackageBuilder.addBoolean(parameters, "fire_tracking_enabled", Util.getFireTrackingEnabled(contentResolver));

        if (!containsPlayIds(parameters) && !containsFireIds(parameters)) {
            logger.warn("Google Advertising ID or Fire Advertising ID not detected, " +
                    "fallback to non Google Play and Fire identifiers will take place");
            deviceInfo.reloadNonPlayIds(adTraceConfig.context);
            PackageBuilder.addString(parameters, "android_id", deviceInfo.androidId);
            PackageBuilder.addString(parameters, "mac_md5", deviceInfo.macShortMd5);
            PackageBuilder.addString(parameters, "mac_sha1", deviceInfo.macSha1);
        }

        // Rest of the parameters.
        PackageBuilder.addString(parameters, "api_level", deviceInfo.apiLevel);
        PackageBuilder.addString(parameters, "app_secret", adTraceConfig.appSecret);
        PackageBuilder.addString(parameters, "app_token", adTraceConfig.appToken);
        PackageBuilder.addString(parameters, "app_version", deviceInfo.appVersion);
        PackageBuilder.addBoolean(parameters, "attribution_deeplink", true);
        PackageBuilder.addDateInMilliseconds(parameters, "created_at", createdAt);
        PackageBuilder.addBoolean(parameters, "device_known", adTraceConfig.deviceKnown);
        PackageBuilder.addBoolean(parameters, "needs_cost", adTraceConfig.needsCost);
        PackageBuilder.addString(parameters, "device_name", deviceInfo.deviceName);
        PackageBuilder.addString(parameters, "device_type", deviceInfo.deviceType);
        PackageBuilder.addLong(parameters, "ui_mode", deviceInfo.uiMode);
        PackageBuilder.addString(parameters, "environment", adTraceConfig.environment);
        PackageBuilder.addBoolean(parameters, "event_buffering_enabled", adTraceConfig.eventBufferingEnabled);
        PackageBuilder.addString(parameters, "external_device_id", adTraceConfig.externalDeviceId);
        PackageBuilder.addBoolean(parameters, "needs_response_details", true);
        PackageBuilder.addString(parameters, "os_name", deviceInfo.osName);
        PackageBuilder.addString(parameters, "os_version", deviceInfo.osVersion);
        PackageBuilder.addString(parameters, "package_name", deviceInfo.packageName);
        PackageBuilder.addString(parameters, "push_token", activityStateCopy.pushToken);
        PackageBuilder.addString(parameters, "secret_id", adTraceConfig.secretId);

        checkDeviceIds(parameters);
        return parameters;
    }

    private Map<String, String> getDisableThirdPartySharingParameters() {
        ContentResolver contentResolver = adTraceConfig.context.getContentResolver();
        Map<String, String> parameters = new HashMap<String, String>();
        Map<String, String> imeiParameters = Reflection.getImeiParameters(adTraceConfig.context, logger);

        // Check if plugin is used and if yes, add read parameters.
        if (imeiParameters != null) {
            parameters.putAll(imeiParameters);
        }

        // Check if oaid plugin is used and if yes, add the parameter
        Map<String, String> oaidParameters = Reflection.getOaidParameters(adTraceConfig.context, logger);
        if (oaidParameters != null) {
            parameters.putAll(oaidParameters);
        }

        // Device identifiers.
        deviceInfo.reloadPlayIds(adTraceConfig.context);
        PackageBuilder.addString(parameters, "android_uuid", activityStateCopy.uuid);
        PackageBuilder.addString(parameters, "gps_adid", deviceInfo.playAdId);
        PackageBuilder.addLong(parameters, "gps_adid_attempt", deviceInfo.playAdIdAttempt);
        PackageBuilder.addString(parameters, "gps_adid_src", deviceInfo.playAdIdSource);
        PackageBuilder.addBoolean(parameters, "tracking_enabled", deviceInfo.isTrackingEnabled);
        PackageBuilder.addString(parameters, "fire_adid", Util.getFireAdvertisingId(contentResolver));
        PackageBuilder.addBoolean(parameters, "fire_tracking_enabled", Util.getFireTrackingEnabled(contentResolver));

        if (!containsPlayIds(parameters) && !containsFireIds(parameters)) {
            logger.warn("Google Advertising ID or Fire Advertising ID not detected, " +
                    "fallback to non Google Play and Fire identifiers will take place");
            deviceInfo.reloadNonPlayIds(adTraceConfig.context);
            PackageBuilder.addString(parameters, "android_id", deviceInfo.androidId);
            PackageBuilder.addString(parameters, "mac_md5", deviceInfo.macShortMd5);
            PackageBuilder.addString(parameters, "mac_sha1", deviceInfo.macSha1);
        }

        // Rest of the parameters.
        PackageBuilder.addString(parameters, "api_level", deviceInfo.apiLevel);
        PackageBuilder.addString(parameters, "app_secret", adTraceConfig.appSecret);
        PackageBuilder.addString(parameters, "app_token", adTraceConfig.appToken);
        PackageBuilder.addString(parameters, "app_version", deviceInfo.appVersion);
        PackageBuilder.addBoolean(parameters, "attribution_deeplink", true);
        PackageBuilder.addDateInMilliseconds(parameters, "created_at", createdAt);
        PackageBuilder.addBoolean(parameters, "device_known", adTraceConfig.deviceKnown);
        PackageBuilder.addBoolean(parameters, "needs_cost", adTraceConfig.needsCost);
        PackageBuilder.addString(parameters, "device_name", deviceInfo.deviceName);
        PackageBuilder.addString(parameters, "device_type", deviceInfo.deviceType);
        PackageBuilder.addLong(parameters, "ui_mode", deviceInfo.uiMode);
        PackageBuilder.addString(parameters, "environment", adTraceConfig.environment);
        PackageBuilder.addBoolean(parameters, "event_buffering_enabled", adTraceConfig.eventBufferingEnabled);
        PackageBuilder.addString(parameters, "external_device_id", adTraceConfig.externalDeviceId);
        PackageBuilder.addBoolean(parameters, "needs_response_details", true);
        PackageBuilder.addString(parameters, "os_name", deviceInfo.osName);
        PackageBuilder.addString(parameters, "os_version", deviceInfo.osVersion);
        PackageBuilder.addString(parameters, "package_name", deviceInfo.packageName);
        PackageBuilder.addString(parameters, "push_token", activityStateCopy.pushToken);
        PackageBuilder.addString(parameters, "secret_id", adTraceConfig.secretId);

        checkDeviceIds(parameters);
        return parameters;
    }

    private Map<String, String> getThirdPartySharingParameters
            (final AdTraceThirdPartySharing adTraceThirdPartySharing)
    {
        ContentResolver contentResolver = adTraceConfig.context.getContentResolver();
        Map<String, String> parameters = new HashMap<String, String>();
        Map<String, String> imeiParameters = Reflection.getImeiParameters(adTraceConfig.context, logger);

        // Check if plugin is used and if yes, add read parameters.
        if (imeiParameters != null) {
            parameters.putAll(imeiParameters);
        }

        // Check if oaid plugin is used and if yes, add the parameter
        Map<String, String> oaidParameters = Reflection.getOaidParameters(adTraceConfig.context, logger);
        if (oaidParameters != null) {
            parameters.putAll(oaidParameters);
        }

        // Third Party Sharing
        if (adTraceThirdPartySharing.isEnabled != null) {
            PackageBuilder.addString(parameters, "sharing",
                    adTraceThirdPartySharing.isEnabled.booleanValue() ?
                            "enable" : "disable");
        }
        PackageBuilder.addMapJson(parameters, "granular_third_party_sharing_options",
                adTraceThirdPartySharing.granularOptions);

        // Device identifiers.
        deviceInfo.reloadPlayIds(adTraceConfig.context);
        PackageBuilder.addString(parameters, "android_uuid", activityStateCopy.uuid);
        PackageBuilder.addString(parameters, "gps_adid", deviceInfo.playAdId);
        PackageBuilder.addLong(parameters, "gps_adid_attempt", deviceInfo.playAdIdAttempt);
        PackageBuilder.addString(parameters, "gps_adid_src", deviceInfo.playAdIdSource);
        PackageBuilder.addBoolean(parameters, "tracking_enabled", deviceInfo.isTrackingEnabled);
        PackageBuilder.addString(parameters, "fire_adid", Util.getFireAdvertisingId(contentResolver));
        PackageBuilder.addBoolean(parameters, "fire_tracking_enabled", Util.getFireTrackingEnabled(contentResolver));

        if (!containsPlayIds(parameters) && !containsFireIds(parameters)) {
            logger.warn("Google Advertising ID or Fire Advertising ID not detected, " +
                    "fallback to non Google Play and Fire identifiers will take place");
            deviceInfo.reloadNonPlayIds(adTraceConfig.context);
            PackageBuilder.addString(parameters, "android_id", deviceInfo.androidId);
            PackageBuilder.addString(parameters, "mac_md5", deviceInfo.macShortMd5);
            PackageBuilder.addString(parameters, "mac_sha1", deviceInfo.macSha1);
        }

        // Rest of the parameters.
        PackageBuilder.addString(parameters, "api_level", deviceInfo.apiLevel);
        PackageBuilder.addString(parameters, "app_secret", adTraceConfig.appSecret);
        PackageBuilder.addString(parameters, "app_token", adTraceConfig.appToken);
        PackageBuilder.addString(parameters, "app_version", deviceInfo.appVersion);
        PackageBuilder.addBoolean(parameters, "attribution_deeplink", true);
        PackageBuilder.addDateInMilliseconds(parameters, "created_at", createdAt);
        PackageBuilder.addBoolean(parameters, "device_known", adTraceConfig.deviceKnown);
        PackageBuilder.addString(parameters, "device_name", deviceInfo.deviceName);
        PackageBuilder.addString(parameters, "device_type", deviceInfo.deviceType);
        PackageBuilder.addLong(parameters, "ui_mode", deviceInfo.uiMode);
        PackageBuilder.addString(parameters, "environment", adTraceConfig.environment);
        PackageBuilder.addBoolean(parameters, "event_buffering_enabled", adTraceConfig.eventBufferingEnabled);
        PackageBuilder.addString(parameters, "external_device_id", adTraceConfig.externalDeviceId);
        PackageBuilder.addBoolean(parameters, "needs_response_details", true);
        PackageBuilder.addString(parameters, "os_name", deviceInfo.osName);
        PackageBuilder.addString(parameters, "os_version", deviceInfo.osVersion);
        PackageBuilder.addString(parameters, "package_name", deviceInfo.packageName);
        PackageBuilder.addString(parameters, "push_token", activityStateCopy.pushToken);
        PackageBuilder.addString(parameters, "secret_id", adTraceConfig.secretId);

        checkDeviceIds(parameters);
        return parameters;
    }

    private Map<String, String> getMeasurementConsentParameters(
            final boolean consentMeasurement)
    {
        ContentResolver contentResolver = adTraceConfig.context.getContentResolver();
        Map<String, String> parameters = new HashMap<String, String>();
        Map<String, String> imeiParameters = Reflection.getImeiParameters(adTraceConfig.context, logger);

        // Check if plugin is used and if yes, add read parameters.
        if (imeiParameters != null) {
            parameters.putAll(imeiParameters);
        }

        // Check if oaid plugin is used and if yes, add the parameter
        Map<String, String> oaidParameters = Reflection.getOaidParameters(adTraceConfig.context, logger);
        if (oaidParameters != null) {
            parameters.putAll(oaidParameters);
        }

        // Measurement Consent
        PackageBuilder.addString(parameters, "measurement",
                consentMeasurement ? "enable" : "disable");

        // Device identifiers.
        deviceInfo.reloadPlayIds(adTraceConfig.context);
        PackageBuilder.addString(parameters, "android_uuid", activityStateCopy.uuid);
        PackageBuilder.addString(parameters, "gps_adid", deviceInfo.playAdId);
        PackageBuilder.addLong(parameters, "gps_adid_attempt", deviceInfo.playAdIdAttempt);
        PackageBuilder.addString(parameters, "gps_adid_src", deviceInfo.playAdIdSource);
        PackageBuilder.addBoolean(parameters, "tracking_enabled", deviceInfo.isTrackingEnabled);
        PackageBuilder.addString(parameters, "fire_adid", Util.getFireAdvertisingId(contentResolver));
        PackageBuilder.addBoolean(parameters, "fire_tracking_enabled", Util.getFireTrackingEnabled(contentResolver));

        if (!containsPlayIds(parameters) && !containsFireIds(parameters)) {
            logger.warn("Google Advertising ID or Fire Advertising ID not detected, " +
                    "fallback to non Google Play and Fire identifiers will take place");
            deviceInfo.reloadNonPlayIds(adTraceConfig.context);
            PackageBuilder.addString(parameters, "android_id", deviceInfo.androidId);
            PackageBuilder.addString(parameters, "mac_md5", deviceInfo.macShortMd5);
            PackageBuilder.addString(parameters, "mac_sha1", deviceInfo.macSha1);
        }

        // Rest of the parameters.
        PackageBuilder.addString(parameters, "api_level", deviceInfo.apiLevel);
        PackageBuilder.addString(parameters, "app_secret", adTraceConfig.appSecret);
        PackageBuilder.addString(parameters, "app_token", adTraceConfig.appToken);
        PackageBuilder.addString(parameters, "app_version", deviceInfo.appVersion);
        PackageBuilder.addBoolean(parameters, "attribution_deeplink", true);
        PackageBuilder.addDateInMilliseconds(parameters, "created_at", createdAt);
        PackageBuilder.addBoolean(parameters, "device_known", adTraceConfig.deviceKnown);
        PackageBuilder.addString(parameters, "device_name", deviceInfo.deviceName);
        PackageBuilder.addString(parameters, "device_type", deviceInfo.deviceType);
        PackageBuilder.addLong(parameters, "ui_mode", deviceInfo.uiMode);
        PackageBuilder.addString(parameters, "environment", adTraceConfig.environment);
        PackageBuilder.addBoolean(parameters, "event_buffering_enabled", adTraceConfig.eventBufferingEnabled);
        PackageBuilder.addString(parameters, "external_device_id", adTraceConfig.externalDeviceId);
        PackageBuilder.addBoolean(parameters, "needs_response_details", true);
        PackageBuilder.addString(parameters, "os_name", deviceInfo.osName);
        PackageBuilder.addString(parameters, "os_version", deviceInfo.osVersion);
        PackageBuilder.addString(parameters, "package_name", deviceInfo.packageName);
        PackageBuilder.addString(parameters, "push_token", activityStateCopy.pushToken);
        PackageBuilder.addString(parameters, "secret_id", adTraceConfig.secretId);

        checkDeviceIds(parameters);
        return parameters;
    }

    private Map<String, String> getAdRevenueParameters(String source, JSONObject adRevenueJson) {
        ContentResolver contentResolver = adTraceConfig.context.getContentResolver();
        Map<String, String> parameters = new HashMap<String, String>();
        Map<String, String> imeiParameters = Reflection.getImeiParameters(adTraceConfig.context, logger);

        // Check if plugin is used and if yes, add read parameters.
        if (imeiParameters != null) {
            parameters.putAll(imeiParameters);
        }

        // Check if oaid plugin is used and if yes, add the parameter
        Map<String, String> oaidParameters = Reflection.getOaidParameters(adTraceConfig.context, logger);
        if (oaidParameters != null) {
            parameters.putAll(oaidParameters);
        }

        // Device identifiers.
        deviceInfo.reloadPlayIds(adTraceConfig.context);
        PackageBuilder.addString(parameters, "android_uuid", activityStateCopy.uuid);
        PackageBuilder.addString(parameters, "gps_adid", deviceInfo.playAdId);
        PackageBuilder.addLong(parameters, "gps_adid_attempt", deviceInfo.playAdIdAttempt);
        PackageBuilder.addString(parameters, "gps_adid_src", deviceInfo.playAdIdSource);
        PackageBuilder.addBoolean(parameters, "tracking_enabled", deviceInfo.isTrackingEnabled);
        PackageBuilder.addString(parameters, "fire_adid", Util.getFireAdvertisingId(contentResolver));
        PackageBuilder.addBoolean(parameters, "fire_tracking_enabled", Util.getFireTrackingEnabled(contentResolver));

        if (!containsPlayIds(parameters) && !containsFireIds(parameters)) {
            logger.warn("Google Advertising ID or Fire Advertising ID not detected, " +
                    "fallback to non Google Play and Fire identifiers will take place");
            deviceInfo.reloadNonPlayIds(adTraceConfig.context);
            PackageBuilder.addString(parameters, "android_id", deviceInfo.androidId);
            PackageBuilder.addString(parameters, "mac_md5", deviceInfo.macShortMd5);
            PackageBuilder.addString(parameters, "mac_sha1", deviceInfo.macSha1);
        }

        // Rest of the parameters.
        PackageBuilder.addString(parameters, "api_level", deviceInfo.apiLevel);
        PackageBuilder.addString(parameters, "app_secret", adTraceConfig.appSecret);
        PackageBuilder.addString(parameters, "app_token", adTraceConfig.appToken);
        PackageBuilder.addString(parameters, "app_version", deviceInfo.appVersion);
        PackageBuilder.addBoolean(parameters, "attribution_deeplink", true);
        PackageBuilder.addLong(parameters, "connectivity_type", Util.getConnectivityType(adTraceConfig.context));
        PackageBuilder.addString(parameters, "country", deviceInfo.country);
        PackageBuilder.addString(parameters, "cpu_type", deviceInfo.abi);
        PackageBuilder.addDateInMilliseconds(parameters, "created_at", createdAt);
        PackageBuilder.addString(parameters, "default_tracker", adTraceConfig.defaultTracker);
        PackageBuilder.addBoolean(parameters, "device_known", adTraceConfig.deviceKnown);
        PackageBuilder.addBoolean(parameters, "needs_cost", adTraceConfig.needsCost);
        PackageBuilder.addString(parameters, "device_manufacturer", deviceInfo.deviceManufacturer);
        PackageBuilder.addString(parameters, "device_name", deviceInfo.deviceName);
        PackageBuilder.addString(parameters, "device_type", deviceInfo.deviceType);
        PackageBuilder.addLong(parameters, "ui_mode", deviceInfo.uiMode);
        PackageBuilder.addString(parameters, "display_height", deviceInfo.displayHeight);
        PackageBuilder.addString(parameters, "display_width", deviceInfo.displayWidth);
        PackageBuilder.addString(parameters, "environment", adTraceConfig.environment);
        PackageBuilder.addBoolean(parameters, "event_buffering_enabled", adTraceConfig.eventBufferingEnabled);
        PackageBuilder.addString(parameters, "external_device_id", adTraceConfig.externalDeviceId);
        PackageBuilder.addString(parameters, "fb_id", deviceInfo.fbAttributionId);
        PackageBuilder.addString(parameters, "hardware_name", deviceInfo.hardwareName);
        PackageBuilder.addString(parameters, "installed_at", deviceInfo.appInstallTime);
        PackageBuilder.addString(parameters, "language", deviceInfo.language);
        PackageBuilder.addDuration(parameters, "last_interval", activityStateCopy.lastInterval);
        PackageBuilder.addString(parameters, "mcc", Util.getMcc(adTraceConfig.context));
        PackageBuilder.addString(parameters, "mnc", Util.getMnc(adTraceConfig.context));
        PackageBuilder.addBoolean(parameters, "needs_response_details", true);
        PackageBuilder.addLong(parameters, "network_type", Util.getNetworkType(adTraceConfig.context));
        PackageBuilder.addString(parameters, "os_build", deviceInfo.buildName);
        PackageBuilder.addString(parameters, "os_name", deviceInfo.osName);
        PackageBuilder.addString(parameters, "os_version", deviceInfo.osVersion);
        PackageBuilder.addString(parameters, "package_name", deviceInfo.packageName);
        PackageBuilder.addString(parameters, "push_token", activityStateCopy.pushToken);
        PackageBuilder.addString(parameters, "screen_density", deviceInfo.screenDensity);
        PackageBuilder.addString(parameters, "screen_format", deviceInfo.screenFormat);
        PackageBuilder.addString(parameters, "screen_size", deviceInfo.screenSize);
        PackageBuilder.addString(parameters, "secret_id", adTraceConfig.secretId);
        PackageBuilder.addString(parameters, "source", source);
        PackageBuilder.addJsonObject(parameters, "payload", adRevenueJson);
        PackageBuilder.addLong(parameters, "session_count", activityStateCopy.sessionCount);
        PackageBuilder.addDuration(parameters, "session_length", activityStateCopy.sessionLength);
        PackageBuilder.addLong(parameters, "subsession_count", activityStateCopy.subsessionCount);
        PackageBuilder.addDuration(parameters, "time_spent", activityStateCopy.timeSpent);
        PackageBuilder.addString(parameters, "updated_at", deviceInfo.appUpdateTime);

        checkDeviceIds(parameters);
        return parameters;
    }

    private Map<String, String> getAdRevenueParameters(AdTraceAdRevenue adTraceAdRevenue, boolean isInDelay) {
        ContentResolver contentResolver = adTraceConfig.context.getContentResolver();
        Map<String, String> parameters = new HashMap<String, String>();
        Map<String, String> imeiParameters = Reflection.getImeiParameters(adTraceConfig.context, logger);

        // Check if plugin is used and if yes, add read parameters.
        if (imeiParameters != null) {
            parameters.putAll(imeiParameters);
        }

        // Check if oaid plugin is used and if yes, add the parameter
        Map<String, String> oaidParameters = Reflection.getOaidParameters(adTraceConfig.context, logger);
        if (oaidParameters != null) {
            parameters.putAll(oaidParameters);
        }

        // Callback and partner parameters.
        if (!isInDelay) {
            PackageBuilder.addMapJson(parameters, "callback_params", Util.mergeParameters(this.sessionParameters.callbackParameters, adTraceAdRevenue.callbackParameters, "Callback"));
            PackageBuilder.addMapJson(parameters, "partner_params", Util.mergeParameters(this.sessionParameters.partnerParameters, adTraceAdRevenue.partnerParameters, "Partner"));
        }

        // Device identifiers.
        deviceInfo.reloadPlayIds(adTraceConfig.context);
        PackageBuilder.addString(parameters, "android_uuid", activityStateCopy.uuid);
        PackageBuilder.addString(parameters, "gps_adid", deviceInfo.playAdId);
        PackageBuilder.addLong(parameters, "gps_adid_attempt", deviceInfo.playAdIdAttempt);
        PackageBuilder.addString(parameters, "gps_adid_src", deviceInfo.playAdIdSource);
        PackageBuilder.addBoolean(parameters, "tracking_enabled", deviceInfo.isTrackingEnabled);
        PackageBuilder.addString(parameters, "fire_adid", Util.getFireAdvertisingId(contentResolver));
        PackageBuilder.addBoolean(parameters, "fire_tracking_enabled", Util.getFireTrackingEnabled(contentResolver));

        if (!containsPlayIds(parameters) && !containsFireIds(parameters)) {
            logger.warn("Google Advertising ID or Fire Advertising ID not detected, " +
                        "fallback to non Google Play and Fire identifiers will take place");
            deviceInfo.reloadNonPlayIds(adTraceConfig.context);
            PackageBuilder.addString(parameters, "android_id", deviceInfo.androidId);
            PackageBuilder.addString(parameters, "mac_md5", deviceInfo.macShortMd5);
            PackageBuilder.addString(parameters, "mac_sha1", deviceInfo.macSha1);
        }

        // Rest of the parameters.
        PackageBuilder.addString(parameters, "api_level", deviceInfo.apiLevel);
        PackageBuilder.addString(parameters, "app_secret", adTraceConfig.appSecret);
        PackageBuilder.addString(parameters, "app_token", adTraceConfig.appToken);
        PackageBuilder.addString(parameters, "app_version", deviceInfo.appVersion);
        PackageBuilder.addBoolean(parameters, "attribution_deeplink", true);
        PackageBuilder.addLong(parameters, "connectivity_type", Util.getConnectivityType(adTraceConfig.context));
        PackageBuilder.addString(parameters, "country", deviceInfo.country);
        PackageBuilder.addString(parameters, "cpu_type", deviceInfo.abi);
        PackageBuilder.addDateInMilliseconds(parameters, "created_at", createdAt);
        PackageBuilder.addString(parameters, "default_tracker", adTraceConfig.defaultTracker);
        PackageBuilder.addBoolean(parameters, "device_known", adTraceConfig.deviceKnown);
        PackageBuilder.addBoolean(parameters, "needs_cost", adTraceConfig.needsCost);
        PackageBuilder.addString(parameters, "device_manufacturer", deviceInfo.deviceManufacturer);
        PackageBuilder.addString(parameters, "device_name", deviceInfo.deviceName);
        PackageBuilder.addString(parameters, "device_type", deviceInfo.deviceType);
        PackageBuilder.addLong(parameters, "ui_mode", deviceInfo.uiMode);
        PackageBuilder.addString(parameters, "display_height", deviceInfo.displayHeight);
        PackageBuilder.addString(parameters, "display_width", deviceInfo.displayWidth);
        PackageBuilder.addString(parameters, "environment", adTraceConfig.environment);
        PackageBuilder.addBoolean(parameters, "event_buffering_enabled", adTraceConfig.eventBufferingEnabled);
        PackageBuilder.addString(parameters, "external_device_id", adTraceConfig.externalDeviceId);
        PackageBuilder.addString(parameters, "fb_id", deviceInfo.fbAttributionId);
        PackageBuilder.addString(parameters, "hardware_name", deviceInfo.hardwareName);
        PackageBuilder.addString(parameters, "installed_at", deviceInfo.appInstallTime);
        PackageBuilder.addString(parameters, "language", deviceInfo.language);
        PackageBuilder.addDuration(parameters, "last_interval", activityStateCopy.lastInterval);
        PackageBuilder.addString(parameters, "mcc", Util.getMcc(adTraceConfig.context));
        PackageBuilder.addString(parameters, "mnc", Util.getMnc(adTraceConfig.context));
        PackageBuilder.addBoolean(parameters, "needs_response_details", true);
        PackageBuilder.addLong(parameters, "network_type", Util.getNetworkType(adTraceConfig.context));
        PackageBuilder.addString(parameters, "os_build", deviceInfo.buildName);
        PackageBuilder.addString(parameters, "os_name", deviceInfo.osName);
        PackageBuilder.addString(parameters, "os_version", deviceInfo.osVersion);
        PackageBuilder.addString(parameters, "package_name", deviceInfo.packageName);
        PackageBuilder.addString(parameters, "push_token", activityStateCopy.pushToken);
        PackageBuilder.addString(parameters, "screen_density", deviceInfo.screenDensity);
        PackageBuilder.addString(parameters, "screen_format", deviceInfo.screenFormat);
        PackageBuilder.addString(parameters, "screen_size", deviceInfo.screenSize);
        PackageBuilder.addString(parameters, "secret_id", adTraceConfig.secretId);
        PackageBuilder.addString(parameters, "source", adTraceAdRevenue.source);
        PackageBuilder.addDoubleWithoutRounding(parameters, "revenue", adTraceAdRevenue.revenue);
        PackageBuilder.addString(parameters, "currency", adTraceAdRevenue.currency);
        PackageBuilder.addInteger(parameters, "ad_impressions_count", adTraceAdRevenue.adImpressionsCount);
        PackageBuilder.addString(parameters, "ad_revenue_network", adTraceAdRevenue.adRevenueNetwork);
        PackageBuilder.addString(parameters, "ad_revenue_unit", adTraceAdRevenue.adRevenueUnit);
        PackageBuilder.addString(parameters, "ad_revenue_placement", adTraceAdRevenue.adRevenuePlacement);
        PackageBuilder.addLong(parameters, "session_count", activityStateCopy.sessionCount);
        PackageBuilder.addDuration(parameters, "session_length", activityStateCopy.sessionLength);
        PackageBuilder.addLong(parameters, "subsession_count", activityStateCopy.subsessionCount);
        PackageBuilder.addDuration(parameters, "time_spent", activityStateCopy.timeSpent);
        PackageBuilder.addString(parameters, "updated_at", deviceInfo.appUpdateTime);

        checkDeviceIds(parameters);
        return parameters;
    }

    private Map<String, String> getSubscriptionParameters(AdTracePlayStoreSubscription subscription, boolean isInDelay) {
        ContentResolver contentResolver = adTraceConfig.context.getContentResolver();
        Map<String, String> parameters = new HashMap<String, String>();
        Map<String, String> imeiParameters = Reflection.getImeiParameters(adTraceConfig.context, logger);

        // Check if plugin is used and if yes, add read parameters.
        if (imeiParameters != null) {
            parameters.putAll(imeiParameters);
        }

        // Check if oaid plugin is used and if yes, add the parameter
        Map<String, String> oaidParameters = Reflection.getOaidParameters(adTraceConfig.context, logger);
        if (oaidParameters != null) {
            parameters.putAll(oaidParameters);
        }

        // Device identifiers.
        deviceInfo.reloadPlayIds(adTraceConfig.context);
        PackageBuilder.addString(parameters, "android_uuid", activityStateCopy.uuid);
        PackageBuilder.addString(parameters, "gps_adid", deviceInfo.playAdId);
        PackageBuilder.addLong(parameters, "gps_adid_attempt", deviceInfo.playAdIdAttempt);
        PackageBuilder.addString(parameters, "gps_adid_src", deviceInfo.playAdIdSource);
        PackageBuilder.addBoolean(parameters, "tracking_enabled", deviceInfo.isTrackingEnabled);
        PackageBuilder.addString(parameters, "fire_adid", Util.getFireAdvertisingId(contentResolver));
        PackageBuilder.addBoolean(parameters, "fire_tracking_enabled", Util.getFireTrackingEnabled(contentResolver));

        if (!containsPlayIds(parameters) && !containsFireIds(parameters)) {
            logger.warn("Google Advertising ID or Fire Advertising ID not detected, " +
                    "fallback to non Google Play and Fire identifiers will take place");
            deviceInfo.reloadNonPlayIds(adTraceConfig.context);
            PackageBuilder.addString(parameters, "android_id", deviceInfo.androidId);
            PackageBuilder.addString(parameters, "mac_md5", deviceInfo.macShortMd5);
            PackageBuilder.addString(parameters, "mac_sha1", deviceInfo.macSha1);
        }

        // Callback and partner parameters.
        if (!isInDelay) {
            PackageBuilder.addMapJson(parameters, "callback_params", Util.mergeParameters(this.sessionParameters.callbackParameters, subscription.getCallbackParameters(), "Callback"));
            PackageBuilder.addMapJson(parameters, "partner_params", Util.mergeParameters(this.sessionParameters.partnerParameters, subscription.getPartnerParameters(), "Partner"));
        }

        // Rest of the parameters.
        PackageBuilder.addString(parameters, "api_level", deviceInfo.apiLevel);
        PackageBuilder.addString(parameters, "app_secret", adTraceConfig.appSecret);
        PackageBuilder.addString(parameters, "app_token", adTraceConfig.appToken);
        PackageBuilder.addString(parameters, "app_version", deviceInfo.appVersion);
        PackageBuilder.addBoolean(parameters, "attribution_deeplink", true);
        PackageBuilder.addLong(parameters, "connectivity_type", Util.getConnectivityType(adTraceConfig.context));
        PackageBuilder.addString(parameters, "country", deviceInfo.country);
        PackageBuilder.addString(parameters, "cpu_type", deviceInfo.abi);
        PackageBuilder.addDateInMilliseconds(parameters, "created_at", createdAt);
        PackageBuilder.addString(parameters, "default_tracker", adTraceConfig.defaultTracker);
        PackageBuilder.addBoolean(parameters, "device_known", adTraceConfig.deviceKnown);
        PackageBuilder.addBoolean(parameters, "needs_cost", adTraceConfig.needsCost);
        PackageBuilder.addString(parameters, "device_manufacturer", deviceInfo.deviceManufacturer);
        PackageBuilder.addString(parameters, "device_name", deviceInfo.deviceName);
        PackageBuilder.addString(parameters, "device_type", deviceInfo.deviceType);
        PackageBuilder.addLong(parameters, "ui_mode", deviceInfo.uiMode);
        PackageBuilder.addString(parameters, "display_height", deviceInfo.displayHeight);
        PackageBuilder.addString(parameters, "display_width", deviceInfo.displayWidth);
        PackageBuilder.addString(parameters, "environment", adTraceConfig.environment);
        PackageBuilder.addBoolean(parameters, "event_buffering_enabled", adTraceConfig.eventBufferingEnabled);
        PackageBuilder.addString(parameters, "external_device_id", adTraceConfig.externalDeviceId);
        PackageBuilder.addString(parameters, "fb_id", deviceInfo.fbAttributionId);
        PackageBuilder.addString(parameters, "hardware_name", deviceInfo.hardwareName);
        PackageBuilder.addString(parameters, "installed_at", deviceInfo.appInstallTime);
        PackageBuilder.addString(parameters, "language", deviceInfo.language);
        PackageBuilder.addDuration(parameters, "last_interval", activityStateCopy.lastInterval);
        PackageBuilder.addString(parameters, "mcc", Util.getMcc(adTraceConfig.context));
        PackageBuilder.addString(parameters, "mnc", Util.getMnc(adTraceConfig.context));
        PackageBuilder.addBoolean(parameters, "needs_response_details", true);
        PackageBuilder.addLong(parameters, "network_type", Util.getNetworkType(adTraceConfig.context));
        PackageBuilder.addString(parameters, "os_build", deviceInfo.buildName);
        PackageBuilder.addString(parameters, "os_name", deviceInfo.osName);
        PackageBuilder.addString(parameters, "os_version", deviceInfo.osVersion);
        PackageBuilder.addString(parameters, "package_name", deviceInfo.packageName);
        PackageBuilder.addString(parameters, "push_token", activityStateCopy.pushToken);
        PackageBuilder.addString(parameters, "screen_density", deviceInfo.screenDensity);
        PackageBuilder.addString(parameters, "screen_format", deviceInfo.screenFormat);
        PackageBuilder.addString(parameters, "screen_size", deviceInfo.screenSize);
        PackageBuilder.addString(parameters, "secret_id", adTraceConfig.secretId);
        PackageBuilder.addLong(parameters, "session_count", activityStateCopy.sessionCount);
        PackageBuilder.addDuration(parameters, "session_length", activityStateCopy.sessionLength);
        PackageBuilder.addLong(parameters, "subsession_count", activityStateCopy.subsessionCount);
        PackageBuilder.addDuration(parameters, "time_spent", activityStateCopy.timeSpent);
        PackageBuilder.addString(parameters, "updated_at", deviceInfo.appUpdateTime);

        // subscription specific parameters
        PackageBuilder.addString(parameters, "billing_store", subscription.getBillingStore());
        PackageBuilder.addString(parameters, "currency", subscription.getCurrency());
        PackageBuilder.addString(parameters, "product_id", subscription.getSku());
        PackageBuilder.addString(parameters, "purchase_token", subscription.getPurchaseToken());
        PackageBuilder.addString(parameters, "receipt", subscription.getSignature());
        PackageBuilder.addLong(parameters, "revenue", subscription.getPrice());
        PackageBuilder.addDateInMilliseconds(parameters, "transaction_date", subscription.getPurchaseTime());
        PackageBuilder.addString(parameters, "transaction_id", subscription.getOrderId());

        checkDeviceIds(parameters);
        return parameters;
    }

    private ActivityPackage getDefaultActivityPackage(ActivityKind activityKind) {
        ActivityPackage activityPackage = new ActivityPackage(activityKind);
        activityPackage.setClientSdk(deviceInfo.clientSdk);
        return activityPackage;
    }

    public static void addString(Map<String, String> parameters, String key, String value) {
        if (TextUtils.isEmpty(value)) {
            return;
        }
        parameters.put(key, value);
    }

    public static void addBoolean(Map<String, String> parameters, String key, Boolean value) {
        if (value == null) {
            return;
        }
        int intValue = value ? 1 : 0;
        PackageBuilder.addLong(parameters, key, intValue);
    }

    static void addJsonObject(Map<String, String> parameters, String key, JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }

        PackageBuilder.addString(parameters, key, jsonObject.toString());
    }

    static void addMapJson(Map<String, String> parameters, String key, Map map) {
        if (map == null) {
            return;
        }
        if (map.size() == 0) {
            return;
        }

        JSONObject jsonObject = new JSONObject(map);
        String jsonString = jsonObject.toString();
        PackageBuilder.addString(parameters, key, jsonString);
    }

    public static void addLong(Map<String, String> parameters, String key, long value) {
        if (value < 0) {
            return;
        }
        String valueString = Long.toString(value);
        PackageBuilder.addString(parameters, key, valueString);
    }

    private static void addDateInMilliseconds(Map<String, String> parameters, String key, long value) {
        if (value <= 0) {
            return;
        }
        Date date = new Date(value);
        PackageBuilder.addDate(parameters, key, date);
    }

    private static void addDateInSeconds(Map<String, String> parameters, String key, long value) {
        if (value <= 0) {
            return;
        }
        Date date = new Date(value * 1000);
        PackageBuilder.addDate(parameters, key, date);
    }

    private static void addDate(Map<String, String> parameters, String key, Date value) {
        if (value == null) {
            return;
        }
        String dateString = Util.dateFormatter.format(value);
        PackageBuilder.addString(parameters, key, dateString);
    }

    private static void addDuration(Map<String, String> parameters, String key, long durationInMilliSeconds) {
        if (durationInMilliSeconds < 0) {
            return;
        }
        long durationInSeconds = (durationInMilliSeconds + 500) / 1000;
        PackageBuilder.addLong(parameters, key, durationInSeconds);
    }

    private static void addDouble(Map<String, String> parameters, String key, Double value) {
        if (value == null) {
            return;
        }
        String doubleString = Util.formatString("%.5f", value);
        PackageBuilder.addString(parameters, key, doubleString);
    }

    private static void addDoubleWithoutRounding(Map<String, String> parameters, String key, Double value) {
        if (value == null) {
            return;
        }
        String doubleString = Double.toString(value);
        PackageBuilder.addString(parameters, key, doubleString);
    }

    private static void addInteger(Map<String, String> parameters, String key, Integer value) {
        if (value == null) {
            return;
        }
        String intString = Integer.toString(value);
        PackageBuilder.addString(parameters, key, intString);
    }

    private boolean containsPlayIds(Map<String, String> parameters) {
        if (parameters == null) {
            return false;
        }
        return parameters.containsKey("gps_adid");
    }

    private boolean containsFireIds(Map<String, String> parameters) {
        if (parameters == null) {
            return false;
        }
        return parameters.containsKey("fire_adid");
    }

    private void checkDeviceIds(Map<String, String> parameters) {
        if (parameters != null && !parameters.containsKey("mac_sha1")
                && !parameters.containsKey("mac_md5")
                && !parameters.containsKey("android_id")
                && !parameters.containsKey("gps_adid")
                && !parameters.containsKey("fire_adid")
                && !parameters.containsKey("oaid")
                && !parameters.containsKey("imei")
                && !parameters.containsKey("meid")
                && !parameters.containsKey("device_id")
                && !parameters.containsKey("imeis")
                && !parameters.containsKey("meids")
                && !parameters.containsKey("device_ids")) {
            logger.error("Missing device id's. Please check if Proguard is correctly set with AdTrace SDK");
        }
    }

    private String getEventSuffix(AdTraceEvent event) {
        if (event.revenue == null) {
            return Util.formatString("'%s'", event.eventToken);
        } else {
            return Util.formatString("(%.5f %s, '%s')", event.revenue, event.currency, event.eventToken);
        }
    }
}
