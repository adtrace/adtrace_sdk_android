package io.adtrace.examples;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import io.adtrace.sdk.webbridge.AdTraceBridge;

/**
 * Step 3 — Host a WebView and register the AdTrace JavaScript bridge.
 *
 * Flow:
 * 1. Enable JavaScript on the WebView
 * 2. Call {@link AdTraceBridge#registerAndGetInstance} so JS can reach the native SDK
 * 3. Load your HTML page (local asset or remote URL)
 * 4. Call {@link AdTraceBridge#unregister()} in {@link #onDestroy()}
 *
 * See example-app-webbridge/README.md for the full integration guide.
 */
public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        setTitle(R.string.txt_webview_title);

        WebView webView = findViewById(R.id.webView);

        // Required: JS must be enabled for the AdTrace bridge
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

        // Step 3: Connect native AdTrace SDK ↔ JavaScript interface
        AdTraceBridge.registerAndGetInstance(getApplication(), webView);

        // Steps 4–6 live in the HTML/JS loaded below
        webView.loadUrl("file:///android_asset/AdTraceExample-WebView.html");
    }

    @Override
    protected void onDestroy() {
        AdTraceBridge.unregister();
        super.onDestroy();
    }
}
