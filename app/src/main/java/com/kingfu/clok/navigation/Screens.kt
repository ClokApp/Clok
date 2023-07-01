package com.kingfu.clok.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material.icons.rounded.HourglassEmpty
import androidx.compose.material.icons.rounded.HourglassFull
import androidx.compose.ui.graphics.vector.ImageVector
import com.kingfu.clok.variable.Variable.settingsStopwatchSelectedFontStyle
import com.kingfu.clok.variable.Variable.settingsTimerSelectedFontStyle


sealed class Screens(
    val route: String,
    val name: String,
    val filledIconId: ImageVector?,
    val outlinedIconId: ImageVector?
) {
    object BugReport : Screens(
        route = "bugReport",
        name = "Bug report",
        filledIconId = null,
        outlinedIconId = null
    )

    object Settings : Screens(
        route = "settings",
        name = "Settings",
        filledIconId = null,
        outlinedIconId = null
    )

    object Stopwatch : Screens(
        route = "stopwatch",
        name = "Stopwatch",
        filledIconId = Icons.Filled.Timer,
        outlinedIconId = Icons.Outlined.Timer
    )

    object SettingsStopwatchLabelStyles : Screens(
        route = "settingsStopwatchLabelStyles",
        name = "Label styles",
        filledIconId = null,
        outlinedIconId = null
    )


    object SettingsStopwatchBackgroundEffects : Screens(
        route = "settingsStopwatchBackgroundEffects",
        name = "Label background effects",
        filledIconId = null,
        outlinedIconId = null
    )

    object Timer : Screens(
        route = "timer",
        name = "Timer",
        filledIconId = Icons.Rounded.HourglassFull,
        outlinedIconId = Icons.Rounded.HourglassEmpty
    )

    object SettingsTimerBackgroundEffects : Screens(
        route = "settingsTimerBackgroundEffects",
        name = "Progress bar background effects",
        filledIconId = null,
        outlinedIconId = null
    )

    object SettingsTimerProgressBarStyles : Screens(
        route = "settingsTimerProgressBarStyles",
        name = "Progress bar styles",
        filledIconId = null,
        outlinedIconId = null
    )

    object SettingsTimerFontStyles : Screens(
        route = "settingsTimerFontStyles",
        name = "Font styles",
        filledIconId = null,
        outlinedIconId = null
    )

    object SettingsTimerSelectedFontStyle: Screens(
        route = "settingsTimerSelectedFontStyle",
        name = settingsTimerSelectedFontStyle,
        filledIconId = null,
        outlinedIconId = null
    )

    object SettingsTimerScrollsHapticFeedback: Screens(
        route = "settingsTimerScrollsHapticFeedback",
        name = "Settings Timer Scrolls Haptic Feedback",
        filledIconId = null,
        outlinedIconId = null
    )

    object SettingsStopwatchFontStyles : Screens(
        route = "settingsStopwatchFontStyles",
        name = "Font styles",
        filledIconId = null,
        outlinedIconId = null
    )

    object SettingsStopwatchSelectedFontStyle: Screens(
        route = "settingsStopwatchSelectedFontStyle",
        name = settingsStopwatchSelectedFontStyle,
        filledIconId = null,
        outlinedIconId = null
    )





}


