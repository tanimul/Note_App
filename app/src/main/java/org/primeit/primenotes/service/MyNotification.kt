package org.primeit.primenotes.service


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import org.primeit.primenotes.R
import org.primeit.primenotes.ui.HomeActivity
import java.util.*


class MyNotification : BroadcastReceiver() {
    private val CHANNEL_ID = "note_app"

    @Override
    override fun onReceive(context: Context, p1: Intent?) {
        Log.d("MyNotification", "onReceive: ")
        val message = "Save your Note Everyday and make your life easier"
        val title = "Daily Remainder"
        val homeIntent =
            Intent(context, HomeActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        sendNotification(context, homeIntent, title, message)
    }

    fun sendNotification(context: Context?, intent: Intent?, title: String, body: String) {

        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = Random().nextInt()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val attributes =
                AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION).build()

            val channelName = "channelName"
            val channel = NotificationChannel(CHANNEL_ID, channelName, IMPORTANCE_HIGH).apply {
                description = "My channel description"
                enableLights(true)
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                lightColor = Color.GREEN
                setSound(soundUri, attributes)
            }
            notificationManager.createNotificationChannel(channel)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            notificationId,
            intent,
            FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(body)
            )
            .setSmallIcon(R.drawable.notification)
            .setAutoCancel(true)
            .setSound(soundUri)
            .setVibrate(longArrayOf(0, 1000, 500, 1000))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .build()
        notificationManager.notify(notificationId, notification)
    }
}