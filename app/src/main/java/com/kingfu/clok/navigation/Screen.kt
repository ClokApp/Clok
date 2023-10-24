package com.kingfu.clok.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material.icons.rounded.HourglassEmpty
import androidx.compose.material.icons.rounded.HourglassFull
import androidx.compose.ui.graphics.vector.ImageVector
import com.kingfu.clok.R



sealed class Screen(
    val route: String,
    @StringRes val nameId: Int,
    val filledIconId: ImageVector? = null,
    val outlinedIconId: ImageVector? = null
) {
    data object BugReport : Screen(
        route = "bug_report",
        nameId = R.string.bug_report_name_id,
    )

    data object Settings : Screen(
        route = "settings",
        nameId = R.string.settings_name_id,
    )

    data object Stopwatch : Screen(
        route = "stopwatch",
        nameId = R.string.stopwatch_name_id,
        filledIconId = Icons.Filled.Timer,
        outlinedIconId = Icons.Outlined.Timer
    )

    data object SettingsStopwatchLabelStyles : Screen(
        route = "settings_stopwatch_label_styles",
        nameId = R.string.settings_stopwatch_label_styles_name_id
    )

    data object SettingsStopwatchLabelBackgroundEffects : Screen(
        route = "settings_stopwatch_background_effects",
        nameId = R.string.label_background_effects_name_id,
    )

    data object Timer : Screen(
        route = "timer",
        nameId = R.string.timer_name_id,
        filledIconId = Icons.Rounded.HourglassFull,
        outlinedIconId = Icons.Rounded.HourglassEmpty
    )

    data object SettingsTimerProgressBarBackgroundEffects : Screen(
        route = "settings_timer_progress_bar_background_effects",
        nameId = R.string.settings_timer_progress_bar_background_effects_name_id
    )

    data object SettingsTimerProgressBarStyles : Screen(
        route = "settings_timer_progress_bar_styles",
        nameId = R.string.settings_timer_progress_bar_styles_name_id
    )

    data object SettingsTimerFontStyles : Screen(
        route = "settings_timer_font_styles",
        nameId = R.string.settings_timer_font_styles_name_id
    )

    data object SettingsTimerTimeFontStyle : Screen(
        route = "settings_timer_time_font_style",
        nameId = R.string.settings_timer_time_font_style_name_id
    )

    data object SettingsTimerScrollsFontStyle : Screen(
        route = "settings_timer_scrolls_font_style",
        nameId = R.string.settings_timer_scrolls_font_style_name_id
    )

    data object SettingsTimerScrollsHapticFeedback : Screen(
        route = "settings_timer_scrolls_haptic_feedback",
        nameId = R.string.settings_timer_scrolls_haptic_feedback_name_id
    )

    data object SettingsStopwatchFontStyles : Screen(
        route = "settings_stopwatch_font_styles",
        nameId = R.string.settings_stopwatch_font_styles_name_id
    )

    data object SettingsStopwatchLabelFontStyle : Screen(
        route = "settings_stopwatch_label_font_style",
        nameId = R.string.settings_stopwatch_label_font_style_name_id
    )

    data object SettingsStopwatchTimeFontStyle : Screen(
        route = "settings_stopwatch_Time_font_style",
        nameId = R.string.settings_stopwatch_Time_font_style_name_id
    )

    data object SettingsStopwatchLapTimeFontStyle : Screen(
        route = "settings_stopwatch_lap_time_font_style",
        nameId = R.string.settings_stopwatch_lap_time_font_style_name_id
    )

    data object SettingsTheme : Screen(
        route = "settings_App",
        nameId = R.string.settings_theme_name_id
    )
}









