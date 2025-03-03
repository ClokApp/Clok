package com.kingfu.clok.navigation

import androidx.annotation.StringRes
import com.kingfu.clok.R

enum class AppDestination(
    @StringRes val label: Int,
    val screen: Screen
) {
    STOPWATCH( label = R.string.stopwatch, screen = Screen.Stopwatch),
    TIMER(label = R.string.timer, screen = Screen.Timer),
    SETTINGS(label = R.string.settings, screen = Screen.Settings)
}