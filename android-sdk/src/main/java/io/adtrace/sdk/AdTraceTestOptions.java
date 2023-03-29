package io.adtrace.sdk;

import android.content.Context;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (github.com/namini40) on April 2022.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2022.
 */

public class AdTraceTestOptions {
    public Context context;
    public String baseUrl;
    public String gdprUrl;
    public String subscriptionUrl;
    public String basePath;
    public String gdprPath;
    public String subscriptionPath;
    public Long timerIntervalInMilliseconds;
    public Long timerStartInMilliseconds;
    public Long sessionIntervalInMilliseconds;
    public Long subsessionIntervalInMilliseconds;
    public Boolean teardown;
    public Boolean tryInstallReferrer = false;
    public Boolean noBackoffWait;
    public Boolean enableSigning;
    public Boolean disableSigning;
}
