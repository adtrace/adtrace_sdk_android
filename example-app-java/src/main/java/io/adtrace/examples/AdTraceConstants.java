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
    public static final String APP_TOKEN = "wzfjavr56krp";

    /**
     * Use {@link AdTraceConfig#ENVIRONMENT_SANDBOX} while developing and testing.
     * Switch to {@link AdTraceConfig#ENVIRONMENT_PRODUCTION} before publishing.
     */
    public static final String ENVIRONMENT = AdTraceConfig.ENVIRONMENT_SANDBOX;

    // Event tokens — create matching events in your AdTrace dashboard
    public static final String EVENT_TOKEN_SIMPLE = "jmj5me";
    public static final String EVENT_TOKEN_REVENUE = "jmj5me";
    public static final String EVENT_TOKEN_CALLBACK = "jmj5me";
    public static final String EVENT_TOKEN_PARAMS = "abc123";
    public static final String EVENT_TOKEN_BACKGROUND = "xyz123";
}
