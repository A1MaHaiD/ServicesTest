package com.handroid.servicestest

import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.handroid.servicestest.databinding.ActivityMainBinding
import com.handroid.servicestest.services.MyService

class MainActivity : AppCompatActivity() {

    var id = 0

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.tvSimpleService.setOnClickListener {
            startService(MyService.newIntent(this))
        }
        binding.tvForegroundService.setOnClickListener {
            showNotification()
        }
    }

    private fun showNotification() {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notificationCanal = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
            )
        notificationManager.createNotificationChannel(notificationCanal)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("${getEmojiByUnicode(convertUnicodeToInt())}")
            .setContentText("Покорми себе!!!")
            .setSmallIcon(R.drawable.ic_food)
            .build()
        notificationManager.notify(id++, notification)
    }

    private fun getEmojiByUnicode(unicode: Int): String {
        return String(Character.toChars(unicode))
    }

    private fun convertUnicodeToInt():Int{
        return Integer.parseInt(EMOJI_DIZZY_FACE, 16)
    }

    companion object {
        const val EMOJI_DIZZY_FACE = "1F635"
        const val EMOJI_WITH_SUNGLASSES = "1F60E"
        const val CHANNEL_ID = "CHANNEL_ID"
        const val CHANNEL_NAME = "CHANNEL_NAME"
    }
}