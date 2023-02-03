package com.kingfu.clok.variable

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object Variable {
    const val TIMER_HR = 100
    const val TIMER_MIN = 60
    const val TIMER_SEC = 60
    var timerShowNotification by mutableStateOf(false)
    var startDestination by mutableStateOf<String?>(null)
    var navigateToStartScreen by mutableStateOf(true)
    var showMenu by mutableStateOf(false)
    var showSnackBar by mutableStateOf(false)
    var settingsTimerSelectedFontStyleTopBarName by mutableStateOf("Scrolls font style")
    var settingsStopwatchSelectedFontStyleTopBarName by mutableStateOf("Scrolls font style")
}