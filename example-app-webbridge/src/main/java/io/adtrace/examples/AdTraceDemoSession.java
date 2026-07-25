package io.adtrace.examples;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import io.adtrace.sdk.AdTrace;
import io.adtrace.sdk.AdTraceConfig;
import io.adtrace.sdk.LogLevel;

/**
 * Lets one demo process run either Case A or Case B — not both without an app restart.
 *
 * The AdTrace SDK can only be initialized once per process.
 */
public final class AdTraceDemoSession {

    public static final String EXTRA_MODE = "adtrace_demo_mode";
    public static final String MODE_CASE_A = "a"; // WebBridge-only: JS onCreate
    public static final String MODE_CASE_B = "b"; // Hybrid: native onCreate + native events

    private static String lockedMode;
    private static boolean nativeInitialized;
    private static boolean lifecycleRegistered;

    private AdTraceDemoSession() {
    }

    /** @return true if this mode can run now (first choice, or same mode again). */
    public static boolean tryUseMode(Context context, String mode) {
        if (lockedMode == null) {
            lockedMode = mode;
            return true;
        }
        if (lockedMode.equals(mode)) {
            return true;
        }
        Toast.makeText(
                context.getApplicationContext(),
                R.string.txt_restart_to_switch_mode,
                Toast.LENGTH_LONG
        ).show();
        return false;
    }

    public static String getLockedMode() {
        return lockedMode;
    }

    /**
     * Case B only: start SDK from native side once.
     */
    public static void ensureNativeSdkInitialized(Application application) {
        if (nativeInitialized) {
            return;
        }

        AdTraceConfig config = new AdTraceConfig(
                application,
                AdTraceConstants.APP_TOKEN,
                AdTraceConfig.ENVIRONMENT_SANDBOX);
        config.setLogLevel(LogLevel.VERBOSE);
        config.setSendInBackground(true);
        AdTrace.onCreate(config);

        if (!lifecycleRegistered) {
            application.registerActivityLifecycleCallbacks(new AdTraceLifecycleCallbacks());
            lifecycleRegistered = true;
        }
        nativeInitialized = true;
    }

    private static final class AdTraceLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
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
}
