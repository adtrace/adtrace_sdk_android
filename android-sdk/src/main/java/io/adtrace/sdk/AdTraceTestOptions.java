package io.adtrace.sdk;

import android.content.Context;

/**
 * Created by Morteza KhosraviNejad on 06/01/19.
 */

public class AdTraceTestOptions {
    public Context context;
    public String baseUrl;
    public String gdprUrl;
    public String basePath;
    public String gdprPath;
    public Boolean useTestConnectionOptions;
    public Long timerIntervalInMilliseconds;
    public Long timerStartInMilliseconds;
    public Long sessionIntervalInMilliseconds;
    public Long subsessionIntervalInMilliseconds;
    public Boolean teardown;
    public Boolean tryInstallReferrer = false;
    public Boolean noBackoffWait;
}
