package com.handroid.servicestest.services

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.util.Log

class MyIntentServiceForLowApi : IntentService(NAME) {

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
        setIntentRedelivery(true)
    }

    override fun onHandleIntent(intent: Intent?) {
        log("onHandleIntent")
        val page = intent?.getIntExtra(PAGE, 0) ?: 0
        for (i in 0 until 5) {
            Thread.sleep(1000)
            log("Timer $i $page")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    private fun log(message: String) {
        Log.d(SERVICE_TAG, "$NAME: $message")
    }

    companion object {
        const val SERVICE_TAG = "SERVICE_TAG"
        private const val NAME = "MyIntentServiceForLowApi"
        private const val PAGE = "page"

        fun newIntent(context: Context, page: Int): Intent =
            Intent(context, MyIntentServiceForLowApi::class.java).apply {
                putExtra(PAGE, page)
            }
    }
}