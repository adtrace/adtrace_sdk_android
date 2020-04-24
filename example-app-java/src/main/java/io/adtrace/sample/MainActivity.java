package io.adtrace.sample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import io.adtrace.sdk.AdTrace;
import io.adtrace.sdk.AdTraceEvent;

public class MainActivity extends AppCompatActivity {
    private static final String EVENT_TOKEN_SIMPLE = "gv1pq7";
    private static final String EVENT_TOKEN_REVENUE = "51avek";
    private static final String EVENT_TOKEN_CALLBACK = "mgm85o";
    private static final String EVENT_TOKEN_PARTNER = "dqp3ns";
    private static final String EVENT_TOKEN_VALUE = "jueov3";

    private Button btnEnableDisableSDK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Uri data = intent.getData();
        AdTrace.appWillOpenUrl(data, getApplicationContext());

        // AdTrace UI according to SDK state.
        btnEnableDisableSDK = findViewById(R.id.btnEnableDisableSDK);
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

    public void onTrackSimpleEventClick(View v) {
        AdTraceEvent event = new AdTraceEvent(EVENT_TOKEN_SIMPLE);

        // Assign custom identifier to event which will be reported in success/failure callbacks.
        event.setCallbackId("sampleCallbackId");

        AdTrace.trackEvent(event);
    }

    public void onTrackRevenueEventClick(View v) {
        AdTraceEvent event = new AdTraceEvent(EVENT_TOKEN_REVENUE);

        // Add revenue 1 cent of an euro.
        event.setRevenue(10000, "Rial");

        AdTrace.trackEvent(event);
    }

    public void onTrackCallbackEventClick(View v) {
        AdTraceEvent event = new AdTraceEvent(EVENT_TOKEN_CALLBACK);

        // Add callback parameters to this parameter.
        event.addCallbackParameter("key", "value");

        AdTrace.trackEvent(event);
    }

    public void onTrackPartnerEventClick(View v) {
        AdTraceEvent event = new AdTraceEvent(EVENT_TOKEN_PARTNER);

        // Add partner parameters to this parameter.
        event.addPartnerParameter("foo", "bar");

        AdTrace.trackEvent(event);
    }

    public void onTrackValueEventClick(View v) {
        AdTraceEvent event = new AdTraceEvent(EVENT_TOKEN_VALUE);

        // set event value.
        event.setEventValue("sampleValue");

        AdTrace.trackEvent(event);
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
}
