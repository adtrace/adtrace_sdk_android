package io.adtrace.sdk;

/**
 * Created by Morteza KhosraviNejad on 06/01/19.
 */
public interface Constants {
    int ONE_SECOND = 1000;
    int ONE_MINUTE = 60 * ONE_SECOND;
    int THIRTY_MINUTES = 30 * ONE_MINUTE;
    int ONE_HOUR = 2 * THIRTY_MINUTES;

    int CONNECTION_TIMEOUT = Constants.ONE_MINUTE;
    int SOCKET_TIMEOUT = Constants.ONE_MINUTE;
    int MAX_WAIT_INTERVAL = Constants.ONE_MINUTE;

    String BASE_URL = "https://app.adtrace.io";
    String GDPR_URL = "https://gdpr.adtrace.io";
    String SCHEME = "https";
    String AUTHORITY = "app.adtrace.io";
    String CLIENT_SDK = "android0.0.7";
    String LOGTAG = "AdTrace";
    String REFTAG = "reftag";
    String INSTALL_REFERRER = "install_referrer";
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
    String MD5 = "MD5";
    String SHA1 = "SHA-1";
    String SHA256 = "SHA-256";

    String CALLBACK_PARAMETERS = "callback_params";
    String PARTNER_PARAMETERS = "partner_params";

    int MAX_INSTALL_REFERRER_RETRIES = 2;

    String FB_AUTH_REGEX = "^(fb|vk)[0-9]{5,}[^:]*://authorize.*access_token=.*";
}
