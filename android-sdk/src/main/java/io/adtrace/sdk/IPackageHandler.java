package io.adtrace.sdk;

import android.content.Context;

/**
 * Created by Morteza KhosraviNejad on 06/01/19.
 */
public interface IPackageHandler {
    void init(IActivityHandler activityHandler, Context context, boolean startsSending);

    void addPackage(ActivityPackage activityPackage);

    void sendFirstPackage();

    void sendNextPackage(ResponseData responseData);

    void closeFirstPackage(ResponseData responseData, ActivityPackage activityPackage);

    void pauseSending();

    void resumeSending();

    void updatePackages(SessionParameters sessionParameters);

    void flush();

    String getBasePath();

    String getGdprPath();

    void teardown();
}
