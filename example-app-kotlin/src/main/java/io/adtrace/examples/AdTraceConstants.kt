package io.adtrace.examples

import io.adtrace.sdk.AdTraceConfig

/**
 * Central place for AdTrace tokens used in this example app.
 *
 * Replace [APP_TOKEN] with your token from the AdTrace dashboard before testing.
 */
object AdTraceConstants {

    /** Your app token from https://panel.adtrace.io */
    const val APP_TOKEN = "xyz123abc456"

    /**
     * Use [AdTraceConfig.ENVIRONMENT_SANDBOX] while developing and testing.
     * Switch to [AdTraceConfig.ENVIRONMENT_PRODUCTION] before publishing.
     */
    const val ENVIRONMENT = AdTraceConfig.ENVIRONMENT_SANDBOX

    // Event tokens — create matching events in your AdTrace dashboard
    const val EVENT_TOKEN_SIMPLE = "xyz123"
    const val EVENT_TOKEN_REVENUE = "a1b2c3"
    const val EVENT_TOKEN_CALLBACK = "x1y2z3"
    const val EVENT_TOKEN_PARAMS = "abc123"
    const val EVENT_TOKEN_BACKGROUND = "x1y2z3"
}
