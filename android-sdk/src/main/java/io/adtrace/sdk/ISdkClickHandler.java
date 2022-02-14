package io.adtrace.sdk;

/**
 * Created by Morteza KhosraviNejad on 06/01/19.
 */
public interface ISdkClickHandler {
    /**
     * Initialise SdkClickHandler instance.
     *
     * @param activityHandler Activity handler instance.
     * @param startsSending   Is sending paused?
     */
    void init(IActivityHandler activityHandler, boolean startsSending);

    /**
     * Pause sending from SdkClickHandler.
     */
    void pauseSending();

    /**
     * Resume sending from SdkClickHandler.
     */
    void resumeSending();

    /**
     * Send sdk_click package.
     *
     * @param sdkClick sdk_click package to be sent.
     */
    void sendSdkClick(ActivityPackage sdkClick);

    /**
     * Send sdk_click packages made from all the persisted intent type referrers.
     */
    void sendReftagReferrers();

    /**
     * Teardown SdkClickHandler instance.
     */
    void teardown();
}
