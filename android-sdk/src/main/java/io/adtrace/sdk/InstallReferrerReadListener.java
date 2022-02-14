package io.adtrace.sdk;

/**
 * Created by Morteza KhosraviNejad on 06/01/19.
 */
public interface InstallReferrerReadListener {
    void onInstallReferrerRead(String installReferrer, long referrerClickTimestampSeconds, long installBeginTimestampSeconds);
}
