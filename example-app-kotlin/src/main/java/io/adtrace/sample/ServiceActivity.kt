package io.adtrace.sample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View

import io.adtrace.sdk.AdTrace

class ServiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)

        val intent = intent
        val data = intent.data
        AdTrace.appWillOpenUrl(data, applicationContext)
    }

    fun onServiceClick(v: View) {
        val intent = Intent(this, ServiceExample::class.java)
        startService(intent)
    }

    fun onReturnClick(v: View) {
        finish()
    }
}