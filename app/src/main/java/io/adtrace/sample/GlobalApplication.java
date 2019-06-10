package io.adtrace.sample;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import io.adtrace.sdk.AdTrace;
import io.adtrace.sdk.AdTraceConfig;

public class GlobalApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        String appToken = "abcdef123456";
        String environment = AdTraceConfig.ENVIRONMENT_PRODUCTION;
        AdTraceConfig config = new AdTraceConfig(this, appToken, environment);

        config.setAppSecret(1, 1200034776, 234989146, 608530464, 1281717387);

        AdTrace.onCreate(config);

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
