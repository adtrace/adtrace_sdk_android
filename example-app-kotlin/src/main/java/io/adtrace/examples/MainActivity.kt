package io.adtrace.examples

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.adtrace.sdk.AdTrace
import io.adtrace.sdk.AdTraceEvent

/**
 * Step 6 — Event tracking, deep links, and SDK controls.
 *
 * Each button demonstrates one AdTrace API. See [AdTraceConstants] for event tokens.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var btnEnableDisableSDK: Button
    private lateinit var txtSdkLogs: TextView
    private lateinit var sdkLogScrollView: ScrollView

    private val logListener: (String) -> Unit = { text ->
        txtSdkLogs.text = if (text.isEmpty()) {
            getString(R.string.txt_sdk_log_placeholder)
        } else {
            text
        }
        sdkLogScrollView.post { sdkLogScrollView.fullScroll(View.FOCUS_DOWN) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnEnableDisableSDK = findViewById(R.id.btnEnableDisableSDK)
        txtSdkLogs = findViewById(R.id.txtSdkLogs)
        sdkLogScrollView = findViewById(R.id.sdkLogScrollView)

        // Handle deep link when activity is first opened
        handleDeepLink(intent)
    }

    override fun onStart() {
        super.onStart()
        AdTraceLogStore.addListener(logListener)
    }

    override fun onStop() {
        AdTraceLogStore.removeListener(logListener)
        super.onStop()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // Handle deep link when activity is already running (e.g. singleTop launch mode)
        handleDeepLink(intent)
    }

    override fun onResume() {
        super.onResume()
        updateSdkToggleLabel()
    }

    /** Notify AdTrace of an incoming deep link for reattribution. */
    private fun handleDeepLink(intent: Intent?) {
        val uri: Uri? = intent?.data
        if (uri != null) {
            AdTrace.appWillOpenUrl(uri, applicationContext)
        }
    }

    private fun updateSdkToggleLabel() {
        btnEnableDisableSDK.setText(
            if (AdTrace.isEnabled()) R.string.txt_disable_sdk else R.string.txt_enable_sdk
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_settings) true else super.onOptionsItemSelected(item)
    }

    // --- Event tracking examples ---

    fun onTrackSimpleEventClick(@Suppress("UNUSED_PARAMETER") v: View) {
        val event = AdTraceEvent(AdTraceConstants.EVENT_TOKEN_SIMPLE)
        event.setCallbackId("PrettyRandomIdentifier")
        AdTrace.trackEvent(event)
    }

    fun onTrackRevenueEventClick(@Suppress("UNUSED_PARAMETER") v: View) {
        val event = AdTraceEvent(AdTraceConstants.EVENT_TOKEN_REVENUE)
        event.setRevenue(52000.0, "IRR")
        AdTrace.trackEvent(event)
    }

    fun onTrackCallbackEventClick(@Suppress("UNUSED_PARAMETER") v: View) {
        val event = AdTraceEvent(AdTraceConstants.EVENT_TOKEN_CALLBACK)
        event.addCallbackParameter("key", "value")
        AdTrace.trackEvent(event)
    }

    fun onTrackPartnerEventClick(@Suppress("UNUSED_PARAMETER") v: View) {
        val event = AdTraceEvent(AdTraceConstants.EVENT_TOKEN_PARAMS)
        event.addPartnerParameter("foo", "bar")
        AdTrace.trackEvent(event)
    }

    fun onTrackEventParameterClick(@Suppress("UNUSED_PARAMETER") v: View) {
        val event = AdTraceEvent(AdTraceConstants.EVENT_TOKEN_PARAMS)
        event.addEventParameter("foo", "bar")
        AdTrace.trackEvent(event)
    }

    // --- SDK state controls ---

    fun onEnableDisableOfflineModeClick(v: View) {
        val button = v as Button
        if (button.text == getString(R.string.txt_enable_offline_mode)) {
            AdTrace.setOfflineMode(true)
            button.setText(R.string.txt_disable_offline_mode)
        } else {
            AdTrace.setOfflineMode(false)
            button.setText(R.string.txt_enable_offline_mode)
        }
    }

    fun onEnableDisableSDKClick(v: View) {
        if (AdTrace.isEnabled()) {
            AdTrace.setEnabled(false)
            (v as Button).setText(R.string.txt_enable_sdk)
        } else {
            AdTrace.setEnabled(true)
            (v as Button).setText(R.string.txt_disable_sdk)
        }
    }

    fun onIsSDKEnabledClick(@Suppress("UNUSED_PARAMETER") v: View) {
        val message = if (AdTrace.isEnabled()) R.string.txt_sdk_is_enabled else R.string.txt_sdk_is_disabled
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    /** Debug helper: simulate an INSTALL_REFERRER broadcast for testing. */
    fun onFireIntentClick(@Suppress("UNUSED_PARAMETER") v: View) {
        val intent = Intent("com.android.vending.INSTALL_REFERRER").apply {
            setPackage(packageName)
            putExtra(
                "referrer",
                "utm_source=test&utm_medium=test&utm_term=test&utm_content=test&utm_campaign=test"
            )
        }
        sendBroadcast(intent)
    }

    fun onServiceActivityClick(@Suppress("UNUSED_PARAMETER") v: View) {
        startActivity(Intent(this, ServiceActivity::class.java))
    }

    fun onClearLogsClick(@Suppress("UNUSED_PARAMETER") v: View) {
        AdTraceLogStore.clear()
    }
}
