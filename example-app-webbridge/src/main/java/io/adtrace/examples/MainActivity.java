package io.adtrace.examples;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import io.adtrace.sdk.AdTrace;
import io.adtrace.sdk.AdTraceEvent;

/**
 * Demo launcher with <b>two scenarios</b> in one app:
 * <ul>
 *   <li>Case A — WebBridge-only (no native events; JS initializes SDK)</li>
 *   <li>Case B — Hybrid (native event + Web Bridge; native initializes SDK)</li>
 * </ul>
 *
 * Pick one per process. To try the other, force-stop the app and relaunch.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshModeHint();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshModeHint();
    }

    /** Case A — no native AdTrace.onCreate / trackEvent; HTML will call onCreate. */
    public void onCaseAClick(View v) {
        if (!AdTraceDemoSession.tryUseMode(this, AdTraceDemoSession.MODE_CASE_A)) {
            return;
        }
        openWebView(AdTraceDemoSession.MODE_CASE_A);
    }

    /** Case B — native init + native simple event, then WebView (HTML skips onCreate). */
    public void onCaseBClick(View v) {
        if (!AdTraceDemoSession.tryUseMode(this, AdTraceDemoSession.MODE_CASE_B)) {
            return;
        }

        AdTraceDemoSession.ensureNativeSdkInitialized(getApplication());

        AdTraceEvent event = new AdTraceEvent(AdTraceConstants.EVENT_TOKEN_SIMPLE);
        AdTrace.trackEvent(event);

        openWebView(AdTraceDemoSession.MODE_CASE_B);
    }

    private void openWebView(String mode) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(AdTraceDemoSession.EXTRA_MODE, mode);
        startActivity(intent);
    }

    private void refreshModeHint() {
        TextView hint = findViewById(R.id.txtActiveMode);
        String locked = AdTraceDemoSession.getLockedMode();
        if (locked == null) {
            hint.setText(R.string.txt_mode_not_chosen);
        } else if (AdTraceDemoSession.MODE_CASE_A.equals(locked)) {
            hint.setText(R.string.txt_mode_locked_a);
        } else {
            hint.setText(R.string.txt_mode_locked_b);
        }
    }
}
