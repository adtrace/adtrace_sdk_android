package io.adtrace.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import io.adtrace.sdk.AdTrace;
import io.adtrace.sdk.AdTraceEvent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.event_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdTraceEvent event = new AdTraceEvent("abc123");
                AdTrace.trackEvent(event);
            }
        });


    }
}
