package com.kingfu.clok.timer.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.NotificationCompat
import com.kingfu.clok.MainActivity
import com.kingfu.clok.R
import com.kingfu.clok.core.Variables.isShowTimerNotification

const val TIMER_NAME = "Timer Notification"

const val CODE_ACTIVITY = 1
const val CODE_CLOSE = 2
const val NOTIFICATION_ID = 3

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
            TIMER_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }

    fun showNotification() {
        val activityIntent = Intent(context, MainActivity::class.java)

        val activityPendingIntent = PendingIntent.getActivity(
            context,
            CODE_ACTIVITY,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val closeIntent = PendingIntent.getBroadcast(
            context,
            CODE_CLOSE,
            Intent(context, TimerNotificationReceiver::class.java),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )


        val notification = NotificationCompat.Builder(context, TIMER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_clok_notification)
            .setContentText("Timer is finished!")
            .setContentIntent(activityPendingIntent)
            .addAction(R.drawable.baseline_close_24, "Close", closeIntent)
            .setColor(Black.toArgb())
            .setPriority(NotificationManager.IMPORTANCE_MAX)
            .build()


        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    fun cancelNotification(){
        notificationManager.cancel(NOTIFICATION_ID)
        isShowTimerNotification = false
    }

}