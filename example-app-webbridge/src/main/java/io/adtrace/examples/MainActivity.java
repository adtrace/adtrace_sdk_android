package io.adtrace.examples;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Native entry screen for the WebBridge example.
 *
 * Opens {@link WebViewActivity}, where the AdTrace JavaScript bridge runs
 * inside a WebView loaded from assets.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onShowWebViewClick(View v) {
        startActivity(new Intent(this, WebViewActivity.class));
    }
}
