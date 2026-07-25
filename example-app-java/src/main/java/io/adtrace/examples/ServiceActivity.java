package io.adtrace.examples;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import io.adtrace.sdk.AdTrace;

/** Demo activity showing deep-link handling in a secondary screen. */
public class ServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        handleDeepLink(getIntent());
    }

    private void handleDeepLink(Intent intent) {
        if (intent == null) {
            return;
        }
        Uri uri = intent.getData();
        if (uri != null) {
            AdTrace.appWillOpenUrl(uri, getApplicationContext());
        }
    }

    public void onServiceClick(View v) {
        startService(new Intent(this, ServiceExample.class));
    }

    public void onReturnClick(View v) {
        finish();
    }
}
