package io.adtrace.sdk;

import org.json.JSONObject;

import java.util.Map;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (github.com/namini40) on April 2022.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2022.
 */

public class ResponseData {
    public boolean success;
    public boolean willRetry;
    public String adid;
    public String message;
    public String timestamp;
    public JSONObject jsonResponse;
    public ActivityKind activityKind;
    public TrackingState trackingState;
    public AdTraceAttribution attribution;
    public Long askIn;
    public Long retryIn;
    public Long continueIn;

    public ActivityPackage activityPackage;
    public Map<String, String> sendingParameters;

    protected ResponseData() {
        success = false;
        willRetry = false;
    }

    public static ResponseData buildResponseData(
            ActivityPackage activityPackage,
            Map<String, String> sendingParameters)
    {
        ResponseData responseData;
        ActivityKind activityKind = activityPackage.getActivityKind();
        switch (activityKind) {
            case SESSION:
                responseData = new SessionResponseData(activityPackage);
                break;
            case CLICK:
                responseData = new SdkClickResponseData();
                break;
            case ATTRIBUTION:
                responseData = new AttributionResponseData();
                break;
            case EVENT:
                responseData = new EventResponseData(activityPackage);
                break;
            case PURCHASE_VERIFICATION:
                responseData = new PurchaseVerificationResponseData();
                break;
            default:
                responseData = new ResponseData();
                break;
        }
        responseData.activityKind = activityKind;
        responseData.activityPackage = activityPackage;
        responseData.sendingParameters = sendingParameters;

        return responseData;
    }

    @Override
    public String toString() {
        return Util.formatString("message:%s timestamp:%s json:%s", message, timestamp, jsonResponse);
    }
}