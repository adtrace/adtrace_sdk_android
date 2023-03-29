package io.adtrace.sdk;

import io.adtrace.sdk.network.IActivityPackageSender;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (github.com/namini40) on April 2022.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2022.
 */

public interface IAttributionHandler {
    void init(IActivityHandler activityHandler,
              boolean startsSending,
              IActivityPackageSender attributionHandlerActivityPackageSender);
    void checkSessionResponse(SessionResponseData sessionResponseData);
    void checkSdkClickResponse(SdkClickResponseData sdkClickResponseData);
    void pauseSending();
    void resumeSending();
    void getAttribution();
    void teardown();
}
