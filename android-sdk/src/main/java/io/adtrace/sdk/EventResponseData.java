package io.adtrace.sdk;

import org.json.JSONObject;


/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (namini40@gmail.com) on August 2021.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2021.
 */


public class EventResponseData extends ResponseData {
    private String eventToken;
    private String callbackId;
    private String sdkPlatform;

    public EventResponseData(final ActivityPackage activityPackage) {
        this.eventToken = activityPackage.getParameters().get("event_token");
        this.callbackId = activityPackage.getParameters().get("event_callback_id");
        this.sdkPlatform = Util.getSdkPrefixPlatform(activityPackage.getClientSdk());
    }

    public AdTraceEventSuccess getSuccessResponseData() {
        if (!success) {
            return null;
        }

        AdTraceEventSuccess successResponseData = new AdTraceEventSuccess();
        if ("unity".equals(this.sdkPlatform)) {
            // Unity platform.
            successResponseData.eventToken = this.eventToken != null ? this.eventToken : "";
            successResponseData.message = message != null ? message : "";
            successResponseData.timestamp = timestamp != null ? timestamp : "";
            successResponseData.adid = adid != null ? adid : "";
            successResponseData.callbackId = this.callbackId != null ? this.callbackId : "";
            successResponseData.jsonResponse = jsonResponse != null ? jsonResponse : new JSONObject();
        } else {
            // Rest of all platforms.
            successResponseData.eventToken = this.eventToken;
            successResponseData.message = message;
            successResponseData.timestamp = timestamp;
            successResponseData.adid = adid;
            successResponseData.callbackId = this.callbackId;
            successResponseData.jsonResponse = jsonResponse;
        }

        return successResponseData;
    }

    public AdTraceEventFailure getFailureResponseData() {
        if (success) {
            return null;
        }

        AdTraceEventFailure failureResponseData = new AdTraceEventFailure();
        if ("unity".equals(this.sdkPlatform)) {
            // Unity platform.
            failureResponseData.eventToken = this.eventToken != null ? this.eventToken : "";
            failureResponseData.message = message != null ? message : "";
            failureResponseData.timestamp = timestamp != null ? timestamp : "";
            failureResponseData.adid = adid != null ? adid : "";
            failureResponseData.callbackId = this.callbackId != null ? this.callbackId : "";
            failureResponseData.willRetry = willRetry;
            failureResponseData.jsonResponse = jsonResponse != null ? jsonResponse : new JSONObject();
        } else {
            // Rest of all platforms.
            failureResponseData.eventToken = this.eventToken;
            failureResponseData.message = message;
            failureResponseData.timestamp = timestamp;
            failureResponseData.adid = adid;
            failureResponseData.callbackId = this.callbackId;
            failureResponseData.willRetry = willRetry;
            failureResponseData.jsonResponse = jsonResponse;
        }

        return failureResponseData;
    }
}