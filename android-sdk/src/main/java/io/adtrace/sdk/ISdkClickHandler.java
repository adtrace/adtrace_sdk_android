package io.adtrace.sdk;

import io.adtrace.sdk.network.IActivityPackageSender;


/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (github.com/namini40) on April 2022.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2022.
 */



public interface ISdkClickHandler {
    /**
     * Initialise SdkClickHandler instance.
     *
     * @param activityHandler Activity handler instance.
     * @param startsSending   Is sending paused?
     */
    void init(IActivityHandler activityHandler,
              boolean startsSending,
              IActivityPackageSender sdkClickHandlerActivityPackageSender);

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
     * Send sdk_click package carrying preinstall info.
     */
    void sendPreinstallPayload(String payload, String location);

    /**
     * Teardown SdkClickHandler instance.
     */
    void teardown();
}
