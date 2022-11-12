package com.kingfu.clok.notification.timer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.core.app.NotificationCompat
import com.kingfu.clok.R
import com.kingfu.clok.mainActivity.MainActivity

class TimerNotificationService(
    private val context: Context
) {
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    companion object {
        const val TIMER_CHANNEL_ID = "timer_channel_id"
    }

    fun createNotificationChannel() {
        val channel = NotificationChannel(
            TIMER_CHANNEL_ID,
            "Timer Notification",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        channel.description = "Used to notify the user that the timer is finished."

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }

    fun showNotification() {
        val activityIntent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val closeIntent = PendingIntent.getBroadcast(
            context,
            2,
            Intent(context, TimerNotificationReceiver::class.java),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(context, TIMER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_outline_hourglass_24)
            .setContentTitle("Timer:")
            .setContentText("Timer is finished!")
            .setContentIntent(activityPendingIntent)
            .addAction(
                R.drawable.ic_filled_close,
                "close",
                closeIntent
            )
            .setColor(Color.argb(255, 0, 255, 255))
            .build()

        notificationManager.notify(1, notification)
    }

}