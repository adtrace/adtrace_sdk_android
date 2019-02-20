package io.adtrace.sdk;

import org.json.JSONObject;

/**
 * Created by Morteza KhosraviNejad on 06/01/19.
 */
public class AdTraceSessionSuccess {
    public String adid;
    public String message;
    public String timestamp;
    public JSONObject jsonResponse;

    @Override
    public String toString() {
        return Util.formatString("Session Success msg:%s time:%s adid:%s json:%s",
                message,
                timestamp,
                adid,
                jsonResponse);
    }
}
