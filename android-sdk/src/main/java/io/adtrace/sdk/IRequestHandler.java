package io.adtrace.sdk;

/**
 * Created by Morteza KhosraviNejad on 06/01/19.
 */
public interface IRequestHandler {
    void init(IActivityHandler activityHandler, IPackageHandler packageHandler);

    void sendPackage(ActivityPackage activityPackage, int queueSize);

    void teardown();
}
