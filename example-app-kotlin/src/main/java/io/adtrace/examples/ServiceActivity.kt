package io.adtrace.examples

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import io.adtrace.sdk.AdTrace

/** Demo activity showing deep-link handling in a secondary screen. */
class ServiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)
        handleDeepLink(intent)
    }

    private fun handleDeepLink(intent: Intent?) {
        val uri: Uri? = intent?.data
        if (uri != null) {
            AdTrace.appWillOpenUrl(uri, applicationContext)
        }
    }

    fun onServiceClick(@Suppress("UNUSED_PARAMETER") v: View) {
        startService(Intent(this, ServiceExample::class.java))
    }

    fun onReturnClick(@Suppress("UNUSED_PARAMETER") v: View) {
        finish()
    }
}
