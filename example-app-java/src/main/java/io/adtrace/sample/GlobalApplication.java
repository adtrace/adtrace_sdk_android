package io.adtrace.sample;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import io.adtrace.sdk.AdTrace;
import io.adtrace.sdk.AdTraceConfig;
import io.adtrace.sdk.LogLevel;

public class GlobalApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        String appToken = "psfzgt48knum";
        String environment;

        if (BuildConfig.DEBUG) {
            environment = AdTraceConfig.ENVIRONMENT_SANDBOX;
        }
        else {
            environment = AdTraceConfig.ENVIRONMENT_PRODUCTION;
        }
        AdTraceConfig adTraceConfig = new AdTraceConfig(this, appToken, environment);
        adTraceConfig.setLogLevel(LogLevel.VERBOSE);
        adTraceConfig.enableSendInstalledApps(true);
        AdTrace.onCreate(adTraceConfig);

        registerActivityLifecycleCallbacks(new AdTraceLifecycleCallbacks());
    }

    private static final class AdTraceLifecycleCallbacks implements ActivityLifecycleCallbacks {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {
            AdTrace.onResume();
        }

        @Override
        public void onActivityPaused(Activity activity) {
            AdTrace.onPause();
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
}
