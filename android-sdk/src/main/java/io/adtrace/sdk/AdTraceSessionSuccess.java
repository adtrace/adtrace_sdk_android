package io.adtrace.sdk;

import org.json.JSONObject;


/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (namini40@gmail.com) on August 2021.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2021.
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
