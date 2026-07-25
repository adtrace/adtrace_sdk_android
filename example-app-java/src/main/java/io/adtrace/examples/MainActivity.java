package io.adtrace.examples;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.adtrace.sdk.AdTrace;
import io.adtrace.sdk.AdTraceEvent;

/**
 * Step 6 — Event tracking, deep links, and SDK controls.
 *
 * Each button demonstrates one AdTrace API. See {@link AdTraceConstants} for event tokens.
 */
public class MainActivity extends AppCompatActivity {

    private Button btnEnableDisableSDK;
    private TextView txtSdkLogs;
    private ScrollView sdkLogScrollView;

    private final AdTraceLogStore.Listener logListener = new AdTraceLogStore.Listener() {
        @Override
        public void onLogsUpdated(String text) {
            if (text == null || text.isEmpty()) {
                txtSdkLogs.setText(R.string.txt_sdk_log_placeholder);
            } else {
                txtSdkLogs.setText(text);
            }
            sdkLogScrollView.post(new Runnable() {
                @Override
                public void run() {
                    sdkLogScrollView.fullScroll(View.FOCUS_DOWN);
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEnableDisableSDK = findViewById(R.id.btnEnableDisableSDK);
        txtSdkLogs = findViewById(R.id.txtSdkLogs);
        sdkLogScrollView = findViewById(R.id.sdkLogScrollView);

        // Handle deep link when activity is first opened
        handleDeepLink(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // Handle deep link when activity is already running (e.g. singleTop launch mode)
        handleDeepLink(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        AdTraceLogStore.addListener(logListener);
    }

    @Override
    protected void onStop() {
        AdTraceLogStore.removeListener(logListener);
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateSdkToggleLabel();
    }

    /** Notify AdTrace of an incoming deep link for reattribution. */
    private void handleDeepLink(Intent intent) {
        if (intent == null) {
            return;
        }
        Uri uri = intent.getData();
        if (uri != null) {
            AdTrace.appWillOpenUrl(uri, getApplicationContext());
        }
    }

    private void updateSdkToggleLabel() {
        if (AdTrace.isEnabled()) {
            btnEnableDisableSDK.setText(R.string.txt_disable_sdk);
        } else {
            btnEnableDisableSDK.setText(R.string.txt_enable_sdk);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // --- Event tracking examples ---

    public void onTrackSimpleEventClick(View v) {
        AdTraceEvent event = new AdTraceEvent(AdTraceConstants.EVENT_TOKEN_SIMPLE);
        event.setCallbackId("PrettyRandomIdentifier");
        AdTrace.trackEvent(event);
    }

    public void onTrackRevenueEventClick(View v) {
        AdTraceEvent event = new AdTraceEvent(AdTraceConstants.EVENT_TOKEN_REVENUE);
        event.setRevenue(52000.0, "IRR");
        AdTrace.trackEvent(event);
    }

    public void onTrackCallbackEventClick(View v) {
        AdTraceEvent event = new AdTraceEvent(AdTraceConstants.EVENT_TOKEN_CALLBACK);
        event.addCallbackParameter("key", "value");
        AdTrace.trackEvent(event);
    }

    public void onTrackPartnerEventClick(View v) {
        AdTraceEvent event = new AdTraceEvent(AdTraceConstants.EVENT_TOKEN_PARTNER);
        event.addPartnerParameter("foo", "bar");
        AdTrace.trackEvent(event);
    }

    public void onTrackEventParameterClick(View v) {
        AdTraceEvent event = new AdTraceEvent(AdTraceConstants.EVENT_TOKEN_PARAMS);
        event.addEventParameter("foo", "bar");
        AdTrace.trackEvent(event);
    }

    // --- SDK state controls ---

    public void onEnableDisableOfflineModeClick(View v) {
        Button button = (Button) v;
        if (button.getText().equals(getString(R.string.txt_enable_offline_mode))) {
            AdTrace.setOfflineMode(true);
            button.setText(R.string.txt_disable_offline_mode);
        } else {
            AdTrace.setOfflineMode(false);
            button.setText(R.string.txt_enable_offline_mode);
        }
    }

    public void onEnableDisableSDKClick(View v) {
        if (AdTrace.isEnabled()) {
            AdTrace.setEnabled(false);
            ((Button) v).setText(R.string.txt_enable_sdk);
        } else {
            AdTrace.setEnabled(true);
            ((Button) v).setText(R.string.txt_disable_sdk);
        }
    }

    public void onIsSDKEnabledClick(View v) {
        int message = AdTrace.isEnabled()
                ? R.string.txt_sdk_is_enabled
                : R.string.txt_sdk_is_disabled;
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /** Debug helper: simulate an INSTALL_REFERRER broadcast for testing. */
    public void onFireIntentClick(View v) {
        Intent intent = new Intent("com.android.vending.INSTALL_REFERRER");
        intent.setPackage(getPackageName());
        intent.putExtra(
                "referrer",
                "utm_source=test&utm_medium=test&utm_term=test&utm_content=test&utm_campaign=test");
        sendBroadcast(intent);
    }

    public void onServiceActivityClick(View v) {
        startActivity(new Intent(this, ServiceActivity.class));
    }

    public void onClearLogsClick(View v) {
        AdTraceLogStore.clear();
    }
}
