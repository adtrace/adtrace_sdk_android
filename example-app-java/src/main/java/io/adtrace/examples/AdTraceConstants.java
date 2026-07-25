package io.adtrace.examples;

import io.adtrace.sdk.AdTraceConfig;

/**
 * Central place for AdTrace tokens used in this example app.
 *
 * Replace {@link #APP_TOKEN} with your token from the AdTrace dashboard before testing.
 */
public final class AdTraceConstants {

    private AdTraceConstants() {
    }

    /** Your app token from https://panel.adtrace.io */
    public static final String APP_TOKEN = "humkdip1g0yl";

    /**
     * Use {@link AdTraceConfig#ENVIRONMENT_SANDBOX} while developing and testing.
     * Switch to {@link AdTraceConfig#ENVIRONMENT_PRODUCTION} before publishing.
     */
    public static final String ENVIRONMENT = AdTraceConfig.ENVIRONMENT_SANDBOX;

    // Event tokens from the AdTrace dashboard
    public static final String EVENT_TOKEN_SIMPLE = "p6j3p7";
    public static final String EVENT_TOKEN_REVENUE = "ijdu0k";
    public static final String EVENT_TOKEN_CALLBACK = "0issyz";
    public static final String EVENT_TOKEN_PARTNER = "905cgb";
    public static final String EVENT_TOKEN_PARAMS = "llo5i9";
    public static final String EVENT_TOKEN_BACKGROUND = "2bvv6z";
}
