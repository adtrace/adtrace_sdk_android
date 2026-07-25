package io.adtrace.examples;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import io.adtrace.sdk.webbridge.AdTraceBridge;

/**
 * Hosts the AdTrace WebView bridge demo.
 *
 * Registers {@link AdTraceBridge} against the WebView, then loads
 * {@code AdTraceExample-WebView.html} from assets.
 */
public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        setTitle(R.string.txt_webview_title);

        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

        AdTraceBridge.registerAndGetInstance(getApplication(), webView);
        webView.loadUrl("file:///android_asset/AdTraceExample-WebView.html");
    }

    @Override
    protected void onDestroy() {
        AdTraceBridge.unregister();
        super.onDestroy();
    }
}
