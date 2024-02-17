package io.adtrace.sdk;

import io.adtrace.sdk.network.IActivityPackageSender;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (github.com/namini40) on April 2022.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2022.
 */

public interface IPurchaseVerificationHandler {
    /**
     * Initialise PurchaseVerificationHandler instance.
     *
     * @param activityHandler Activity handler instance.
     * @param startsSending   Is sending paused?
     */
    void init(IActivityHandler activityHandler,
              boolean startsSending,
              IActivityPackageSender purchaseVerificationHandlerActivityPackageSender);

    /**
     * Pause sending from PurchaseVerificationHandler.
     */
    void pauseSending();

    /**
     * Resume sending from PurchaseVerificationHandler.
     */
    void resumeSending();

    /**
     * Send purchase_verification package.
     *
     * @param purchaseVerification purchase_verification package to be sent.
     */
    void sendPurchaseVerificationPackage(ActivityPackage purchaseVerification);

    /**
     * Teardown PurchaseVerificationHandler instance.
     */
    void teardown();
}
