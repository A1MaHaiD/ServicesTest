package com.handroid.servicestest.services

import android.app.IntentService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.handroid.servicestest.R

class MyIntentService : IntentService(NAME) {

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification())
    }

    override fun onHandleIntent(p0: Intent?) {
        log("onHandleIntent")
        for (i in 0 until 10) {
            Thread.sleep(1000)
            log("Timer $i")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    private fun createNotificationChannel() {
        val fNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val fNotificationChannel = NotificationChannel(
                SERVICE_ID,
                SERVICE_CHANNEL,
                NotificationManager.IMPORTANCE_DEFAULT
            )
        fNotificationManager.createNotificationChannel(fNotificationChannel)
    }

    private fun createNotification() = NotificationCompat.Builder(this,SERVICE_ID)
        .setContentTitle("${getEmojiByUnicode(convertUnicodeToInt())}")
        .setContentText("Am I Dev?")
        .setSmallIcon(R.drawable.ic_android)
//        .setForegroundServiceBehavior(FOREGROUND_SERVICE_DEFAULT)
        .build()

    private fun log(message: String) {
        Log.d(SERVICE_TAG, "$NAME: $message")
    }

    private fun getEmojiByUnicode(unicode: Int): String {
        return String(Character.toChars(unicode))
    }

    private fun convertUnicodeToInt(): Int {
        return Integer.parseInt(EMOJI_WITH_HORNS, 16)
    }

    companion object {
        const val EMOJI_DIZZY_FACE = "1F635"
        const val EMOJI_WITH_SUNGLASSES = "1F60E"
        const val EMOJI_WITH_HORNS = "1F608"

        const val NOTIFICATION_ID = 1
        const val SERVICE_TAG = "SERVICE_TAG"
        const val SERVICE_ID = "SERVICE_ID"
        const val SERVICE_CHANNEL = "SERVICE_CHANNEL"
        const val NAME = "MyIntentService"

        fun newIntent(context: Context): Intent = Intent(context, MyIntentService::class.java)
    }
}