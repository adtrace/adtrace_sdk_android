package io.adtrace.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import io.adtrace.sdk.AdTrace;
import io.adtrace.sdk.AdTraceEvent;

public class MainActivity extends AppCompatActivity {

    private static final String EVENT_TOKEN_SIMPLE = "jmj5me";
    private static final String EVENT_TOKEN_REVENUE = "jmj5me";
    private static final String EVENT_TOKEN_CALLBACK = "jmj5me";
    private static final String EVENT_TOKEN_PARTNER = "abc123";

    private Button btnEnableDisableSDK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Uri data = intent.getData();
        AdTrace.appWillOpenUrl(data, getApplicationContext());

        // AdTrace UI according to SDK state.
        btnEnableDisableSDK = (Button) findViewById(R.id.btnEnableDisableSDK);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (AdTrace.isEnabled()) {
            btnEnableDisableSDK.setText(R.string.txt_disable_sdk);
        } else {
            btnEnableDisableSDK.setText(R.string.txt_enable_sdk);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onTrackSimpleEventClick(View v) {
        AdTraceEvent event = new AdTraceEvent(EVENT_TOKEN_SIMPLE);

        // Assign custom identifier to event which will be reported in success/failure callbacks.
        event.setCallbackId("PrettyRandomIdentifier");

        AdTrace.trackEvent(event);
    }

    public void onTrackRevenueEventClick(View v) {
        AdTraceEvent event = new AdTraceEvent(EVENT_TOKEN_REVENUE);

        // Add revenue 52000 Rials.
        event.setRevenue(52000.0, "IRR");

        AdTrace.trackEvent(event);
    }

    public void onTrackCallbackEventClick(View v) {
        AdTraceEvent event = new AdTraceEvent(EVENT_TOKEN_CALLBACK);

        // Add callback parameters to this parameter.
        event.addCallbackParameter("key", "value");

        AdTrace.trackEvent(event);
    }

    public void onTrackEventParameterClick(View v) {
        AdTraceEvent event = new AdTraceEvent(EVENT_TOKEN_PARTNER);

        // Add partner parameters to this parameter.
        event.addEventParameter("foo", "bar");

        AdTrace.trackEvent(event);
    }

    public void onEnableDisableOfflineModeClick(View v) {
        if (((Button) v).getText().equals(
                getApplicationContext().getResources().getString(R.string.txt_enable_offline_mode))) {
            AdTrace.setOfflineMode(true);
            ((Button) v).setText(R.string.txt_disable_offline_mode);
        } else {
            AdTrace.setOfflineMode(false);
            ((Button) v).setText(R.string.txt_enable_offline_mode);
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
        if (AdTrace.isEnabled()) {
            Toast.makeText(getApplicationContext(), R.string.txt_sdk_is_enabled,
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), R.string.txt_sdk_is_disabled,
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void onFireIntentClick(View v) {
        Intent intent = new Intent("com.android.vending.INSTALL_REFERRER");
        intent.setPackage("io.adtrace.sample");
        intent.putExtra("referrer", "utm_source=test&utm_medium=test&utm_term=test&utm_content=test&utm_campaign=test");
        sendBroadcast(intent);
    }

    public void onServiceActivityClick(View v) {
        Intent intent = new Intent(this, ServiceActivity.class);
        startActivity(intent);
    }
}