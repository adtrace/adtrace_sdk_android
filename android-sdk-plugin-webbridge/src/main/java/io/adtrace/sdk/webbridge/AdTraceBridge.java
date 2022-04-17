package io.adtrace.sdk.webbridge;

import android.webkit.WebView;
import android.app.Application;

/**
 * AdTrace android SDK (https://adtrace.io)
 * Created by Nasser Amini (github.com/namini40) on April 2022.
 * Notice: See LICENSE.txt for modification and distribution information
 *                   Copyright © 2022.
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
        AdTraceBridge.getDefaultInstance().setWebView(webView);
    }

    public static void setApplicationContext(Application application) {
        AdTraceBridge.getDefaultInstance().setApplicationContext(application);
    }

    public static synchronized void unregister() {
        if (defaultInstance != null) {
            defaultInstance.unregister();
        }
        defaultInstance = null;
    }
}
