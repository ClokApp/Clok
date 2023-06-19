package com.kingfu.clok.notification.timer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.kingfu.clok.variable.Variable.isShowTimerNotification

class TimerNotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        TimerNotificationService(context = context).notificationManager.cancel(NOTIFICATION_ID)
        isShowTimerNotification = false
    }
}