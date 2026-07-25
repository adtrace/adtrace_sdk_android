package io.adtrace.examples

import io.adtrace.sdk.AdTraceConfig

/**
 * Central place for AdTrace tokens used in this example app.
 *
 * Replace [APP_TOKEN] with your token from the AdTrace dashboard before testing.
 */
object AdTraceConstants {

    /** Your app token from https://panel.adtrace.io */
    const val APP_TOKEN = "humkdip1g0yl"

    /**
     * Use [AdTraceConfig.ENVIRONMENT_SANDBOX] while developing and testing.
     * Switch to [AdTraceConfig.ENVIRONMENT_PRODUCTION] before publishing.
     */
    const val ENVIRONMENT = AdTraceConfig.ENVIRONMENT_SANDBOX

    // Event tokens from the AdTrace dashboard
    const val EVENT_TOKEN_SIMPLE = "p6j3p7"
    const val EVENT_TOKEN_REVENUE = "ijdu0k"
    const val EVENT_TOKEN_CALLBACK = "0issyz"
    const val EVENT_TOKEN_PARTNER = "905cgb"
    const val EVENT_TOKEN_PARAMS = "llo5i9"
    const val EVENT_TOKEN_BACKGROUND = "2bvv6z"
}
