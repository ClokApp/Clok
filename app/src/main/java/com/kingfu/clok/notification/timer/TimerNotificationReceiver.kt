package com.kingfu.clok.notification.timer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.kingfu.clok.variable.Variable.timerShowNotification

class TimerNotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, p1: Intent?) {
        TimerNotificationService(context).notificationManager.cancel(1)
        timerShowNotification = false
    }
}