package com.kingfu.clok.timer.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.kingfu.clok.core.Variables.isShowTimerNotification

class TimerNotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        TimerNotificationService(context = context).cancelNotification()
        isShowTimerNotification = false
    }
}