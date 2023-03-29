package io.adtrace.sdk;

import android.content.Context;
import android.net.Uri;

import org.json.JSONObject;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (github.com/namini40) on April 2022.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2021.
 */

public interface IActivityHandler {
    void init(AdTraceConfig config);

    void onResume();

    void onPause();

    void trackEvent(AdTraceEvent event);

    void finishedTrackingActivity(ResponseData responseData);

    void setEnabled(boolean enabled);

    boolean isEnabled();

    void readOpenUrl(Uri url, long clickTime);

    boolean updateAttributionI(AdTraceAttribution attribution);

    void launchEventResponseTasks(EventResponseData eventResponseData);

    void launchSessionResponseTasks(SessionResponseData sessionResponseData);

    void launchSdkClickResponseTasks(SdkClickResponseData sdkClickResponseData);

    void launchAttributionResponseTasks(AttributionResponseData attributionResponseData);

    void sendReftagReferrer();

    void sendPreinstallReferrer();

    void sendInstallReferrer(ReferrerDetails referrerDetails, String referrerApi);

    void setOfflineMode(boolean enabled);

    void setAskingAttribution(boolean askingAttribution);

    void sendFirstPackages();

    void addSessionCallbackParameter(String key, String value);

    void addSessionPartnerParameter(String key, String value);

    void removeSessionCallbackParameter(String key);

    void removeSessionPartnerParameter(String key);

    void resetSessionCallbackParameters();

    void resetSessionPartnerParameters();

    void teardown();

    void setPushToken(String token, boolean preSaved);

    void gdprForgetMe();

    void disableThirdPartySharing();

    void trackThirdPartySharing(AdTraceThirdPartySharing adTraceThirdPartySharing);

    void trackMeasurementConsent(boolean consentMeasurement);

    void trackAdRevenue(String source, JSONObject adRevenueJson);

    void trackAdRevenue(AdTraceAdRevenue adTraceAdRevenue);

    void trackPlayStoreSubscription(AdTracePlayStoreSubscription subscription);

    void gotOptOutResponse();

    Context getContext();

    String getAdid();

    AdTraceAttribution getAttribution();

    AdTraceConfig getAdTraceConfig();

    DeviceInfo getDeviceInfo();

    ActivityState getActivityState();

    SessionParameters getSessionParameters();
}
