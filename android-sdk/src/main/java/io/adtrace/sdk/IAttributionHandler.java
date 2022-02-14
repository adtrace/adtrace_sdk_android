//
//  IAttributionHandler.java
//  AdTrace SDK
//
//  Created by Pedro Silva (@nonelse) on 15th December 2014.
//  Copyright (c) 2014-2018 AdTrace GmbH. All rights reserved.
//

package io.adtrace.sdk;

/**
 * Created by Morteza KhosraviNejad on 06/01/19.
 */
public interface IAttributionHandler {
    void init(IActivityHandler activityHandler, boolean startsSending);
    void checkSessionResponse(SessionResponseData sessionResponseData);
    void checkSdkClickResponse(SdkClickResponseData sdkClickResponseData);
    void pauseSending();
    void resumeSending();
    void getAttribution();
    void teardown();
}
