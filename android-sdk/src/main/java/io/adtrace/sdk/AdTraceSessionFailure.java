package io.adtrace.sdk;

import org.json.JSONObject;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (github.com/namini40) on April 2022.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2022.
 */

public class AdTraceSessionFailure {
    public boolean willRetry;
    public String adid;
    public String message;
    public String timestamp;
    public JSONObject jsonResponse;

    @Override
    public String toString() {
        return Util.formatString("Session Failure msg:%s time:%s adid:%s retry:%b json:%s",
                message,
                timestamp,
                adid,
                willRetry,
                jsonResponse);
    }
}
