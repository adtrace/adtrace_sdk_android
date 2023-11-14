package io.adtrace.examples

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast

import io.adtrace.sdk.AdTrace
import io.adtrace.sdk.AdTraceEvent

class MainActivity : AppCompatActivity() {

    private var btnEnableDisableSDK: Button? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = intent
        val data = intent.data
        AdTrace.appWillOpenUrl(data, applicationContext)

        // AdTrace UI according to SDK state.
        btnEnableDisableSDK = findViewById<View>(R.id.btnEnableDisableSDK) as Button
    }

    public override fun onResume() {
        super.onResume()

        if (AdTrace.isEnabled()) {
            btnEnableDisableSDK!!.setText(R.string.txt_disable_sdk)
        } else {
            btnEnableDisableSDK!!.setText(R.string.txt_enable_sdk)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    fun onTrackSimpleEventClick(v: View) {
        val event = AdTraceEvent(EVENT_TOKEN_SIMPLE)

        // Assign custom identifier to event which will be reported in success/failure callbacks.
        event.setCallbackId("PrettyRandomIdentifier")

        AdTrace.trackEvent(event)
    }

    fun onTrackRevenueEventClick(v: View) {
        val event = AdTraceEvent(EVENT_TOKEN_REVENUE)

        // Add revenue 52000 Rials.
        event.setRevenue(52000.0, "IRR")

        AdTrace.trackEvent(event)
    }

    fun onTrackCallbackEventClick(v: View) {
        val event = AdTraceEvent(EVENT_TOKEN_CALLBACK)

        // Add callback parameters to this parameter.
        event.addCallbackParameter("key", "value")

        AdTrace.trackEvent(event)
    }

    fun onTrackPartnerEventClick(v:View){
        val event = AdTraceEvent(EVENT_TOKEN_PARAMS)

        // Add event partner parameters to this event.
        event.addPartnerParameter("foo", "bar")

        AdTrace.trackEvent(event)
    }

    fun onTrackEventParameterClick(v: View) {
        val event = AdTraceEvent(EVENT_TOKEN_PARAMS)

        // Add event parameters to this event.
        event.addEventParameter("foo", "bar")

        AdTrace.trackEvent(event)
    }



    fun onEnableDisableOfflineModeClick(v: View) {
        if ((v as Button).text == applicationContext.resources.getString(R.string.txt_enable_offline_mode)) {
            AdTrace.setOfflineMode(true)
            v.setText(R.string.txt_disable_offline_mode)
        } else {
            AdTrace.setOfflineMode(false)
            v.setText(R.string.txt_enable_offline_mode)
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

    fun onIsSDKEnabledClick(v: View) {
        if (AdTrace.isEnabled()) {
            Toast.makeText(applicationContext, R.string.txt_sdk_is_enabled,
                    Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, R.string.txt_sdk_is_disabled,
                    Toast.LENGTH_SHORT).show()
        }
    }

    fun onFireIntentClick(v: View) {
        val intent = Intent("com.android.vending.INSTALL_REFERRER")
        intent.setPackage("io.adtrace.sample")
        intent.putExtra("referrer", "utm_source=test&utm_medium=test&utm_term=test&utm_content=test&utm_campaign=test")
        sendBroadcast(intent)
    }

    fun onServiceActivityClick(v: View) {
        val intent = Intent(this, ServiceActivity::class.java)
        startActivity(intent)
    }

    companion object {
        private val EVENT_TOKEN_SIMPLE = "xyz123"
        private val EVENT_TOKEN_REVENUE = "a1b2c3"
        private val EVENT_TOKEN_CALLBACK = "x1y2z3"
        private val EVENT_TOKEN_PARAMS = "abc123"
    }
}