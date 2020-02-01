package io.adtrace.sdk.webbridge;

import android.app.Application;
import android.webkit.WebView;

public class AdTraceBridge {
    private static AdTraceBridgeInstance defaultInstance;

    // New builder gets dependencies
    public static synchronized AdTraceBridgeInstance registerAndGetInstance(Application application, WebView webView) {
        if (defaultInstance == null) {
            defaultInstance = new AdTraceBridgeInstance(application, webView);
        }
        return defaultInstance;
    }

    public static synchronized AdTraceBridgeInstance getDefaultInstance() {
        if (defaultInstance == null) {
            defaultInstance = new AdTraceBridgeInstance();
        }
        return defaultInstance;
    }

    public static void setWebView(WebView webView) {
        AdTraceBridge.getDefaultInstance().setWebView(webView);
    }

    public static void setApplicationContext(Application application) {
        AdTraceBridge.getDefaultInstance().setApplicationContext(application);
    }
}
