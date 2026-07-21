package io.adtrace.examples

import android.app.Activity
import android.app.Application
import io.adtrace.sdk.AdTrace

/**
 * Registers [AdTrace.onResume] and [AdTrace.onPause] for every activity.
 *
 * Required for session tracking when minSdkVersion >= 14.
 * Register once in your [Application.onCreate]:
 *
 * ```
 * registerActivityLifecycleCallbacks(AdTraceLifecycleCallbacks())
 * ```
 */
class AdTraceLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

    override fun onActivityResumed(activity: Activity) {
        AdTrace.onResume()
    }

    override fun onActivityPaused(activity: Activity) {
        AdTrace.onPause()
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: android.os.Bundle?) {}
    override fun onActivityStarted(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, outState: android.os.Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {}
}
