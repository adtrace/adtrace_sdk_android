package io.adtrace.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import io.adtrace.sdk.AdTrace;

public class ServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        Intent intent = getIntent();
        Uri data = intent.getData();
        AdTrace.appWillOpenUrl(data, getApplicationContext());
    }

    public void onServiceClick(View v) {
        Intent intent = new Intent(this, ServiceExample.class);
        startService(intent);
    }

    public void onReturnClick(View v) {
        finish();
    }

}