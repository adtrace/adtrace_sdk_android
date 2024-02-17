package io.adtrace.examples

import android.app.Service
import android.content.Intent
import android.os.AsyncTask
import android.os.IBinder
import android.os.SystemClock
import android.util.Log

import io.adtrace.sdk.AdTrace
import io.adtrace.sdk.AdTraceEvent

class ServiceExample : Service() {
    init {
        Log.d("example", "ServiceExample constructor")
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.d("example", "ServiceExample onBind")

        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("example", "ServiceExample onCreate")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val startDefaultOption = super.onStartCommand(intent, flags, startId)
        Log.d("example", "ServiceExample onStartCommand")

        if (flip) {
            AdTrace.setEnabled(false)
            flip = false
        } else {
            AdTrace.setEnabled(true)
            flip = true
        }

        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg params: Void): Void? {
                Log.d("example", "ServiceExample background sleeping")
                SystemClock.sleep(3000)
                Log.d("example", "ServiceExample background awake")

                val event = AdTraceEvent(EVENT_TOKEN_BACKGROUND)
                AdTrace.trackEvent(event)

                Log.d("example", "ServiceExample background event tracked")

                return null
            }
        }.execute()

        return Service.START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("example", "ServiceExample onDestroy")
    }

    companion object {
        private val EVENT_TOKEN_BACKGROUND = "x1y2z3"

        private var flip = true
    }
}