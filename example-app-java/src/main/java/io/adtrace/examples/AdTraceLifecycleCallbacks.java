package io.adtrace.examples;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import io.adtrace.sdk.AdTrace;

/**
 * Registers {@link AdTrace#onResume()} and {@link AdTrace#onPause()} for every activity.
 *
 * Required for session tracking when minSdkVersion &gt;= 14.
 * Register once in your {@link Application#onCreate()}:
 *
 * <pre>
 * registerActivityLifecycleCallbacks(new AdTraceLifecycleCallbacks());
 * </pre>
 */
public class AdTraceLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityResumed(Activity activity) {
        AdTrace.onResume();
    }

    @Override
    public void onActivityPaused(Activity activity) {
        AdTrace.onPause();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }
}
