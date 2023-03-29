package io.adtrace.sdk;

import org.json.JSONObject;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (github.com/namini40) on April 2022.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2022.
 */

public class AdTraceEventSuccess {
    public String adid;
    public String message;
    public String timestamp;
    public String eventToken;
    public String callbackId;
    public JSONObject jsonResponse;

    @Override
    public String toString() {
        return Util.formatString("Event Success msg:%s time:%s adid:%s event:%s cid:%s json:%s",
                message,
                timestamp,
                adid,
                eventToken,
                callbackId,
                jsonResponse);
    }
}
