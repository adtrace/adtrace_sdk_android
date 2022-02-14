package io.adtrace.sdk.webbridge;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.webkit.JavascriptInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import io.adtrace.sdk.AdTrace;
import io.adtrace.sdk.AdTraceEvent;
import io.adtrace.sdk.AdTraceFactory;

public class FacebookSDKJSInterface {
    private static final String PROTOCOL = "fbmq-0.1";
    private static final String PARAMETER_FBSDK_PIXEL_REFERRAL = "_fb_pixel_referral_id";
    private static final String APPLICATION_ID_PROPERTY = "com.facebook.sdk.ApplicationId";
    private String fbPixelDefaultEventToken;
    private Map<String, String> fbPixelMapping;

    public FacebookSDKJSInterface() {
        fbPixelMapping = new HashMap<String, String>();
    }

    private static Map<String, String> jsonStringToMap(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            Map<String, String> stringMap = new HashMap<String, String>(jsonObject.length());
            Iterator iter = jsonObject.keys();
            while (iter.hasNext()) {
                String key = (String) iter.next();
                String value = jsonObject.getString(key);
                stringMap.put(key, value);
            }
            return stringMap;
        } catch (JSONException ignored) {
            return new HashMap<String, String>();
        }
    }

    public static String getApplicationId(Context context) {
        String applicationId = null;
        ApplicationInfo ai = null;
        try {
            ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            AdTraceFactory.getLogger().error("Error loading fb ApplicationInfo: %s", e.getMessage());
            return null;
        }

        Object appId = ai.metaData.get(APPLICATION_ID_PROPERTY);
        if (appId instanceof String) {
            String appIdString = (String) appId;
            if (appIdString.toLowerCase(Locale.ROOT).startsWith("fb")) {
                applicationId = appIdString.substring(2);
            } else {
                applicationId = appIdString;
            }
        } else if (appId instanceof Integer) {
            AdTraceFactory.getLogger().error("App Ids cannot be directly placed in the manifest." +
                    "They must be prefixed by 'fb' or be placed in the string resource file.");
        } else {
            AdTraceFactory.getLogger().error("App Ids is not a string or integer");
        }
        return applicationId;
    }

    public void setDefaultEventToken(String fbPixelDefaultEventToken) {
        this.fbPixelDefaultEventToken = fbPixelDefaultEventToken;
    }

    public void addFbPixelEventTokenMapping(String key, String value) {
        this.fbPixelMapping.put(key, value);
    }

    @JavascriptInterface
    public void sendEvent(String pixelId, String event_name, String jsonString) {
        if (pixelId == null) {
            AdTraceFactory.getLogger().error("Can't bridge an event without a referral Pixel ID. " +
                    "Check your webview Pixel configuration");
            return;
        }

        String eventToken = fbPixelMapping.get(event_name);
        if (eventToken == null) {
            AdTraceFactory.getLogger().debug("No mapping found for the fb pixel event %s, trying to fall back to the default event token", event_name);
            eventToken = this.fbPixelDefaultEventToken;
        }

        if (eventToken == null) {
            AdTraceFactory.getLogger().warn("There is not a default event token configured or a mapping found for event named: '%s'. It won't be tracked as an adtrace event", event_name);
            return;
        }
        AdTraceEvent fbPixelEvent = new AdTraceEvent(eventToken);
        if (!fbPixelEvent.isValid()) {
            return;
        }

        Map<String, String> stringMap = jsonStringToMap(jsonString);
        stringMap.put(PARAMETER_FBSDK_PIXEL_REFERRAL, pixelId);
        // stringMap.put("_eventName", event_name);

        AdTraceFactory.getLogger().debug("FB pixel event received, eventName: %s, payload: %s", event_name, stringMap);

        for (Map.Entry<String,String> entry : stringMap.entrySet() ) {
            String key = entry.getKey();
            fbPixelEvent.addPartnerParameter(key, entry.getValue());
        }

        AdTrace.trackEvent(fbPixelEvent);
    }

    @JavascriptInterface
    public String getProtocol() {
        return PROTOCOL;
    }
}