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
import com.kingfu.clok.timer.timerViewModel.TimerViewModel.TimerViewModelVariable.timerTotalTime
import com.kingfu.clok.variable.Variable.showSnackBar
import com.kingfu.clok.variable.Variable.timerShowNotification

const val TIMER_NAME = "Timer Notification"

const val CODE_ACTIVITY = 1
const val CODE_CLOSE = 2
const val NOTIFICATION_ID = 3

class TimerNotificationService(
    private val context: Context,
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
            NotificationManager.IMPORTANCE_DEFAULT
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
            .setSmallIcon(R.drawable.ic_round_hourglass_empty_24)
            .setContentTitle("Timer for ${formatTimerTimeForNotification(timerTotalTime.toLong())}")
            .setContentText("Timer is finished!")
            .setContentIntent(activityPendingIntent)
            .addAction(R.drawable.ic_round_hourglass_empty_24, "Close", closeIntent)
            .setColor(Color.argb(255, 0, 255, 255))
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun formatTimerTimeForNotification(timeMillis: Long): String {

        val hours = timeMillis / 1000 / 60 / 60 % 100
        val minutes = timeMillis / 1000 / 60 % 60
        val seconds = timeMillis / 1000 % 60

        val time =
            when {
                timeMillis >= 36_000_000L -> "${"%02d".format(hours)}:${"%02d".format(minutes)}:${
                    "%02d".format(
                        seconds
                    )
                }"
                timeMillis in 3_600_000L until 36_000_000 -> "${"%01d".format(hours)}:${
                    "%02d".format(
                        minutes
                    )
                }:${"%02d".format(seconds)}"
                timeMillis in 600_000L until 3_600_000L -> "${"%02d".format(minutes)}:${
                    "%02d".format(
                        seconds
                    )
                }"
                timeMillis in 60_000 until 600_000L -> "${"%01d".format(minutes)}:${
                    "%02d".format(
                        seconds
                    )
                }"
                timeMillis in 10_000L until 60_000L -> "%02d".format(seconds)
                timeMillis in 0L until 10_000L -> "${"%01d".format(seconds)}s"
                else -> "0"
            }

        return time
    }

    fun cancelNotification(){
        notificationManager.cancel(NOTIFICATION_ID)
        timerShowNotification = false
        showSnackBar = false
    }

}