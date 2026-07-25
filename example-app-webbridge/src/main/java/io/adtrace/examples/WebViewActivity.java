package io.adtrace.examples;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import io.adtrace.sdk.webbridge.AdTraceBridge;

/**
 * Hosts the WebView + {@link AdTraceBridge}.
 *
 * Loads a <b>separate HTML file per case</b> (clearer for developers / AI):
 * <ul>
 *   <li>Case A → {@code AdTraceExample-CaseA-WebBridgeOnly.html} (JS SDK init)</li>
 *   <li>Case B → {@code AdTraceExample-CaseB-Hybrid.html} (no JS init)</li>
 * </ul>
 */
public class WebViewActivity extends AppCompatActivity {

    private static final String ASSET_CASE_A =
            "file:///android_asset/AdTraceExample-CaseA-WebBridgeOnly.html";
    private static final String ASSET_CASE_B =
            "file:///android_asset/AdTraceExample-CaseB-Hybrid.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        String mode = getIntent().getStringExtra(AdTraceDemoSession.EXTRA_MODE);
        if (mode == null) {
            mode = AdTraceDemoSession.MODE_CASE_B;
        }

        boolean caseA = AdTraceDemoSession.MODE_CASE_A.equals(mode);
        setTitle(caseA ? R.string.txt_webview_title_case_a : R.string.txt_webview_title_case_b);

        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

        AdTraceBridge.registerAndGetInstance(getApplication(), webView);

        webView.loadUrl(caseA ? ASSET_CASE_A : ASSET_CASE_B);
    }

    @Override
    protected void onDestroy() {
        AdTraceBridge.unregister();
        super.onDestroy();
    }
}
