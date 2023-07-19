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
    val filledIconId: ImageVector? = null,
    val outlinedIconId: ImageVector? = null
) {
    object BugReport : Screens(
        route = "bugReport",
        name = "Bug report",
    )

    object Settings : Screens(
        route = "settings",
        name = "Settings",
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
    )

    object SettingsStopwatchBackgroundEffects : Screens(
        route = "settingsStopwatchBackgroundEffects",
        name = "Label background effects",
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
    )

    object SettingsTimerProgressBarStyles : Screens(
        route = "settingsTimerProgressBarStyles",
        name = "Progress bar styles",
    )

    object SettingsTimerFontStyles : Screens(
        route = "settingsTimerFontStyles",
        name = "Font styles",
    )

    object SettingsTimerSelectedFontStyle : Screens(
        route = "settingsTimerSelectedFontStyle",
        name = settingsTimerSelectedFontStyle,
    )

    object SettingsTimerScrollsHapticFeedback : Screens(
        route = "settingsTimerScrollsHapticFeedback",
        name = "Settings Timer Scrolls Haptic Feedback",
    )

    object SettingsStopwatchFontStyles : Screens(
        route = "settingsStopwatchFontStyles",
        name = "Font styles",
    )

    object SettingsStopwatchSelectedFontStyle : Screens(
        route = "settingsStopwatchSelectedFontStyle",
        name = settingsStopwatchSelectedFontStyle,
    )
}






