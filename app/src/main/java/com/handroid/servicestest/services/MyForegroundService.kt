package com.handroid.servicestest.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.FOREGROUND_SERVICE_DEFAULT
import com.handroid.servicestest.R
import kotlinx.coroutines.*

class MyForegroundService : Service() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification())
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        log("onDestroy")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        log("onStartCommand")
        coroutineScope.launch {
            for (i in 0 until 100) {
                delay(1000)
                log("Timer $i")
            }
            stopSelf()
        }
        return START_STICKY
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
        .setContentText("Влаштуйся в Android Dev")
        .setSmallIcon(R.drawable.ic_android)
//        .setForegroundServiceBehavior(FOREGROUND_SERVICE_DEFAULT)
        .build()

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    private fun log(message: String) {
        Log.d(SERVICE_TAG, "MyForegroundService: $message")
    }

    private fun getEmojiByUnicode(unicode: Int): String {
        return String(Character.toChars(unicode))
    }

    private fun convertUnicodeToInt(): Int {
        return Integer.parseInt(EMOJI_WITH_SUNGLASSES, 16)
    }

    companion object {
        const val EMOJI_DIZZY_FACE = "1F635"
        const val EMOJI_WITH_SUNGLASSES = "1F60E"

        const val NOTIFICATION_ID = 1
        const val SERVICE_TAG = "SERVICE_TAG"
        const val SERVICE_ID = "SERVICE_ID"
        const val SERVICE_CHANNEL = "SERVICE_CHANNEL"

        fun newIntent(context: Context): Intent = Intent(context, MyForegroundService::class.java)
    }
}