package io.adtrace.sdk.webbridge;

import android.webkit.WebView;
import android.app.Application;

/**
 * Created by uerceg on 10/06/16.
 */
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
        io.adtrace.sdk.webbridge.AdTraceBridge.getDefaultInstance().setWebView(webView);
    }

    public static void setApplicationContext(Application application) {
        io.adtrace.sdk.webbridge.AdTraceBridge.getDefaultInstance().setApplicationContext(application);
    }

    public static synchronized void unregister() {
        if (defaultInstance != null) {
            defaultInstance.unregister();
        }
        defaultInstance = null;
    }
}
