package io.adtrace.sdk;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (github.com/namini40) on April 2022.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2022.
 */

public interface Constants {
    int ONE_SECOND = 1000;
    int ONE_MINUTE = 60 * ONE_SECOND;
    int THIRTY_MINUTES = 30 * ONE_MINUTE;
    int ONE_HOUR = 2 * THIRTY_MINUTES;

    int CONNECTION_TIMEOUT = Constants.ONE_MINUTE;
    int SOCKET_TIMEOUT = Constants.ONE_MINUTE;
    int MAX_WAIT_INTERVAL = Constants.ONE_MINUTE;

    int MIN_LAST_INTERVAL_HARD_RESET_THRESHOLD = Constants.ONE_SECOND * 5;

    String BASE_URL = "https://app.adtrace.io";
    String GDPR_URL = "https://gdpr.adtrace.io";
    String SUBSCRIPTION_URL = "https://subscription.adtrace.com";
    String PURCHASE_VERIFICATION_URL = "https://ssrv.adtrace.io";

    String SCHEME = "https";
    String AUTHORITY = "app.adtrace.io";
    String CLIENT_SDK = "android2.5.0";
    String LOGTAG = "AdTrace";
    String REFTAG = "reftag";
    String INSTALL_REFERRER = "install_referrer";
    String REFERRER_API_GOOGLE = "google";
    String REFERRER_API_HUAWEI_ADS = "huawei_ads";
    String REFERRER_API_HUAWEI_APP_GALLERY = "huawei_app_gallery";
    String REFERRER_API_SAMSUNG = "samsung";
    String REFERRER_API_XIAOMI = "xiaomi";
    String REFERRER_API_VIVO = "vivo";
    String REFERRER_API_META = "meta";
    String DEEPLINK = "deeplink";
    String PUSH = "push";
    String THREAD_PREFIX = "AdTrace-";

    String ACTIVITY_STATE_FILENAME = "AdTraceIoActivityState";
    String ATTRIBUTION_FILENAME = "AdTraceAttribution";
    String SESSION_CALLBACK_PARAMETERS_FILENAME = "AdTraceSessionCallbackParameters";
    String SESSION_PARTNER_PARAMETERS_FILENAME = "AdTraceSessionPartnerParameters";

    String MALFORMED = "malformed";
    String SMALL = "small";
    String NORMAL = "normal";
    String LONG = "long";
    String LARGE = "large";
    String XLARGE = "xlarge";
    String LOW = "low";
    String MEDIUM = "medium";
    String HIGH = "high";
    String REFERRER = "referrer";

    String ENCODING = "UTF-8";
    String SHA256 = "SHA-256";
    int MINIMAL_ERROR_STATUS_CODE = 400;

    String CALLBACK_PARAMETERS = "callback_params";
    String PARTNER_PARAMETERS = "partner_params";

    String FCM_PAYLOAD_KEY = "adtrace_purpose";

    String FCM_PAYLOAD_VALUE = "uninstall detection";

    int MAX_INSTALL_REFERRER_RETRIES = 2;

    String FB_AUTH_REGEX = "^(fb|vk)[0-9]{5,}[^:]*://authorize.*access_token=.*";

    String PREINSTALL = "preinstall";
    String SYSTEM_PROPERTIES = "system_properties";
    String SYSTEM_PROPERTIES_REFLECTION = "system_properties_reflection";
    String SYSTEM_PROPERTIES_PATH = "system_properties_path";
    String SYSTEM_PROPERTIES_PATH_REFLECTION = "system_properties_path_reflection";
    String CONTENT_PROVIDER = "content_provider";
    String CONTENT_PROVIDER_INTENT_ACTION = "content_provider_intent_action";
    String CONTENT_PROVIDER_NO_PERMISSION = "content_provider_no_permission";
    String FILE_SYSTEM = "file_system";
    String SYSTEM_INSTALLER_REFERRER = "system_installer_referrer";

    String ADTRACE_PREINSTALL_SYSTEM_PROPERTY_PREFIX = "adtrace.preinstall.";
    String ADTRACE_PREINSTALL_SYSTEM_PROPERTY_PATH = "adtrace.preinstall.path";
    String ADTRACE_PREINSTALL_CONTENT_URI_AUTHORITY = "io.adtrace.preinstall";
    String ADTRACE_PREINSTALL_CONTENT_URI_PATH = "trackers";
    String ADTRACE_PREINSTALL_CONTENT_PROVIDER_INTENT_ACTION = "com.attribution.REFERRAL_PROVIDER";
    String ADTRACE_PREINSTALL_FILE_SYSTEM_PATH = "/data/local/tmp/adtrace.preinstall";
    String EXTRA_SYSTEM_INSTALLER_REFERRER = "com.attribution.EXTRA_SYSTEM_INSTALLER_REFERRER";
}
