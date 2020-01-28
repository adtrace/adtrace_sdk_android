package io.adtrace.sample;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import io.adtrace.sdk.AdTrace;
import io.adtrace.sdk.AdTraceConfig;
import io.adtrace.sdk.AdTraceEvent;
import io.adtrace.sdk.LogLevel;

public class GlobalApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AdTraceEvent afterEvent = new AdTraceEvent("re4ty1");
        String appToken = "6nr32h36sq7y";
        String environment = AdTraceConfig.ENVIRONMENT_SANDBOX;
        AdTraceConfig config = new AdTraceConfig(this, appToken, environment);
        config.setLogLevel(LogLevel.VERBOSE);

        AdTrace.onCreate(config);
        AdTrace.trackEvent(afterEvent);

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
