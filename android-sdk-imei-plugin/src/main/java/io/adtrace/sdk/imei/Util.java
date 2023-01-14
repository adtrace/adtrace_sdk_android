package io.adtrace.sdk.imei;


import static io.adtrace.sdk.imei.TelephonyIdsUtil.injectImei;

import android.content.Context;


import java.util.HashMap;
import java.util.Map;

import io.adtrace.sdk.ILogger;

public class Util {
    public static Map<String, String> getImeiParameters(Context context, ILogger logger) {
        Map<String, String> parameters = new HashMap<String, String>();
        injectImei(parameters, context, logger);
        return parameters;
    }
}
