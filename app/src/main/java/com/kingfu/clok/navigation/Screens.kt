package com.kingfu.clok.navigation

import com.kingfu.clok.R


sealed class Screens(
    val route: String,
    val name: String,
    val filledIconId: Int?,
    val outlinedIconId: Int?
) {
    object Stopwatch : Screens(
        "stopwatch",
        "Stopwatch",
        filledIconId = R.drawable.ic_fill_timer_24,
        outlinedIconId = R.drawable.ic_outline_timer_24
    )

    object Timer : Screens(
        "timer",
        "Timer",
        filledIconId = R.drawable.ic_fill_hourglass_24,
        outlinedIconId = R.drawable.ic_outline_hourglass_24
    )

    object Settings : Screens(
        "settings",
        "Settings",
//        filledIconId = R.drawable.ic_sharp_fill_settings_24,
//        outlinedIconId = R.drawable.ic_outline_settings_24
        filledIconId = null,
        outlinedIconId = null
    )

    object SettingsStopwatchLabelStyle : Screens(
        "settingsStopwatchLabelStyle",
        "Label styles",
//        R.drawable.ic_sharp_fill_settings_24,
//        R.drawable.ic_outline_settings_24
        filledIconId = null,
        outlinedIconId = null
    )

    object SettingsTimerProgressBarStyle : Screens(
        "settingsTimerProgressBarStyle",
        "Progress bar styles",
//        R.drawable.ic_sharp_fill_settings_24,
//        R.drawable.ic_outline_settings_24
        filledIconId = null,
        outlinedIconId = null
    )

    object BugReport : Screens(
        "bugReport",
        "Bug report",
//        filledIconId = R.drawable.ic_sharp_fill_settings_24,
//        R.drawable.ic_outline_settings_24
        filledIconId = null,
        outlinedIconId = null
    )

    object SettingsStopwatchBackgroundEffects : Screens(
        "settingsStopwatchBackgroundEffects",
        "Label background effects",
//        R.drawable.ic_sharp_fill_settings_24,
//        R.drawable.ic_outline_settings_24
        filledIconId = null,
        outlinedIconId = null
    )

    object SettingsTimerBackgroundEffects : Screens(
        "settingsTimerBackgroundEffects",
        "Progress bar background effects",
//        R.drawable.ic_sharp_fill_settings_24,
//        R.drawable.ic_outline_settings_24
        filledIconId = null,
        outlinedIconId = null
    )
}

val items = listOf(
    Screens.Stopwatch,
    Screens.Timer,
    Screens.Settings,
    Screens.SettingsStopwatchLabelStyle,
    Screens.SettingsTimerProgressBarStyle,
    Screens.BugReport,
    Screens.SettingsStopwatchBackgroundEffects,
    Screens.SettingsTimerBackgroundEffects
)

