package io.adtrace.sdk.network;

import java.util.Map;

import io.adtrace.sdk.ActivityPackage;
import io.adtrace.sdk.ResponseData;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (namini40@gmail.com) on August 2021.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2021.
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
