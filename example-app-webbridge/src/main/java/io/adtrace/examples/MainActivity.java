package io.adtrace.examples;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import io.adtrace.sdk.webbridge.AdTraceBridge;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = (WebView) findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

        AdTraceBridge.registerAndGetInstance(getApplication(), webView);
        try {
            webView.loadUrl("file:///android_asset/AdTraceExample-WebView.html");
//            webView.loadUrl("https://adtraceweb.ir");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        AdTraceBridge.unregister();

        super.onDestroy();
    }
}
