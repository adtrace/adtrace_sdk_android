package io.adtrace.examples;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Native launcher screen (same pattern as other AdTrace example apps).
 *
 * Not required for WebBridge integration — opens {@link WebViewActivity}
 * where the actual AdTrace bridge setup happens.
 *
 * See example-app-webbridge/README.md for the full integration guide.
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
