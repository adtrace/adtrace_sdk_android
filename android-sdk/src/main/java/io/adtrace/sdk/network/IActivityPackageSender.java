package io.adtrace.sdk.network;

import io.adtrace.sdk.ActivityPackage;
import io.adtrace.sdk.ResponseData;

import java.util.Map;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (github.com/namini40) on April 2022.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2022.
 */

public interface IActivityPackageSender {
    interface ResponseDataCallbackSubscriber {
        void onResponseDataCallback(ResponseData responseData);
    }

    void sendActivityPackage(ActivityPackage activityPackage,
                             Map<String, String> sendingParameters,
                             ResponseDataCallbackSubscriber responseCallback);

    ResponseData sendActivityPackageSync(ActivityPackage activityPackage,
                             Map<String, String> sendingParameters);
}
