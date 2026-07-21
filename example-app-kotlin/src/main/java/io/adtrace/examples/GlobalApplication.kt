package io.adtrace.examples

import android.app.Application
import io.adtrace.sdk.AdTrace
import io.adtrace.sdk.AdTraceConfig
import io.adtrace.sdk.AdTraceFactory

/**
 * Step 4 — Initialize the AdTrace SDK.
 *
 * Register this class in AndroidManifest.xml:
 * ```
 * <application android:name=".GlobalApplication" ...>
 * ```
 *
 * See [example-app-kotlin/README.md] for the full integration guide.
 */
class GlobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Mirror SDK logs (including sent request payloads) in the example app UI
        AdTraceFactory.setLogger(InAppLogger())

        // Step 4a: Build config with your app token and environment
        val config = AdTraceConfig(
            this,
            AdTraceConstants.APP_TOKEN,
            AdTraceConstants.ENVIRONMENT
        )

        // Step 4b: Optional settings (callbacks, log level, etc.)
        AdTraceAdvancedConfig.apply(config)

        // Step 4c: Start the SDK
        AdTrace.onCreate(config)

        // Step 5: Session tracking via activity lifecycle callbacks
        registerActivityLifecycleCallbacks(AdTraceLifecycleCallbacks())
    }
}
