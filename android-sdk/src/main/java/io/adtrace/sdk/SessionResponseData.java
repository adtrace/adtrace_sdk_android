package io.adtrace.sdk;

import org.json.JSONObject;
/**
 * Created by Morteza KhosraviNejad on 06/01/19.
 */
public class SessionResponseData extends ResponseData {
    private String sdkPlatform;

    public SessionResponseData(final ActivityPackage activityPackage) {
        this.sdkPlatform = Util.getSdkPrefixPlatform(activityPackage.getClientSdk());
    }

    public AdTraceSessionSuccess getSuccessResponseData() {
        if (!success) {
            return null;
        }

        AdTraceSessionSuccess successResponseData = new AdTraceSessionSuccess();
        if ("unity".equals(this.sdkPlatform)) {
            // Unity platform.
            successResponseData.message = message != null ? message : "";
            successResponseData.timestamp = timestamp != null ? timestamp : "";
            successResponseData.adid = adid != null ? adid : "";
            successResponseData.jsonResponse = jsonResponse != null ? jsonResponse : new JSONObject();
        } else {
            // Rest of all platforms.
            successResponseData.message = message;
            successResponseData.timestamp = timestamp;
            successResponseData.adid = adid;
            successResponseData.jsonResponse = jsonResponse;
        }

        return successResponseData;
    }

    public AdTraceSessionFailure getFailureResponseData() {
        if (success) {
            return null;
        }

        AdTraceSessionFailure failureResponseData = new AdTraceSessionFailure();
        if ("unity".equals(this.sdkPlatform)) {
            // Unity platform.
            failureResponseData.message = message != null ? message : "";
            failureResponseData.timestamp = timestamp != null ? timestamp : "";
            failureResponseData.adid = adid != null ? adid : "";
            failureResponseData.willRetry = willRetry;
            failureResponseData.jsonResponse = jsonResponse != null ? jsonResponse : new JSONObject();
        } else {
            // Rest of all platforms.
            failureResponseData.message = message;
            failureResponseData.timestamp = timestamp;
            failureResponseData.adid = adid;
            failureResponseData.willRetry = willRetry;
            failureResponseData.jsonResponse = jsonResponse;
        }

        return failureResponseData;
    }
}
