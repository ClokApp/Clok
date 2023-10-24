package com.kingfu.clok.settings.settingsScreen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.kingfu.clok.settings.settingsScreen.settingsApp.SettingsApp
import com.kingfu.clok.settings.settingsScreen.settingsPrivacy.SettingsPrivacyPolicy
import com.kingfu.clok.settings.settingsScreen.settingsStopwatchScreen.SettingsStopwatch
import com.kingfu.clok.settings.settingsScreen.settingsTimerScreen.SettingsTimer
import com.kingfu.clok.settings.viewModel.settingsViewModelStopwatch.SettingsViewModelStopwatch
import com.kingfu.clok.settings.viewModel.settingsViewModelTimer.SettingsViewModelTimer
import com.kingfu.clok.ui.util.screenHeight

@Composable
fun SettingsScreen(
    settingsViewModelStopwatch: SettingsViewModelStopwatch,
    settingsViewModelTimer: SettingsViewModelTimer,
    goToSettingsStopwatchBackgroundEffects: () -> Unit,
    goToSettingsStopwatchFontStyles: () -> Unit,
    goToSettingsStopwatchLabelStyles: () -> Unit,
    goToSettingsTimerProgressBarStyles: () -> Unit,
    goToSettingsTimerFontStyles: () -> Unit,
    goToSettingsTimerBackgroundEffects: () -> Unit,
    goToSettingsTimerScrollsHapticFeedback: () -> Unit,
    goToTheme: () -> Unit
) {
    val context = LocalContext.current

    SettingsStopwatch(
        goToSettingsStopwatchBackgroundEffects = { goToSettingsStopwatchBackgroundEffects() },
        goToSettingsStopwatchFontStyles = { goToSettingsStopwatchFontStyles() },
        goToSettingsStopwatchLabelStyles = { goToSettingsStopwatchLabelStyles() },
        stopwatchSetShowLabelCheckState = { settingsViewModelStopwatch.toggleStopwatchIsShowLabel() },
        saveStopwatchShowLabel = { settingsViewModelStopwatch.saveStopwatchShowLabel() },
//        updateStopwatchRefreshRateValue = { refreshRate: Float ->
        updateStopwatchRefreshRateValue = { refreshRate: Long ->
            settingsViewModelStopwatch.updateStopwatchRefreshRateValue(refreshRate = refreshRate)
        },
        saveStopwatchRefreshRateValue = { settingsViewModelStopwatch.saveStopwatchRefreshRateValue() },
        stopwatchShowLabel = settingsViewModelStopwatch.state.stopwatchIsShowLabel,
        stopwatchRefreshRateValue = settingsViewModelStopwatch.state.stopwatchRefreshRateValue,
    )

    SettingsTimer(
        goToSettingsTimerProgressBarStyles = { goToSettingsTimerProgressBarStyles() },
        goToSettingsTimerFontStyles = { goToSettingsTimerFontStyles() },
        goToSettingsTimerBackgroundEffects = { goToSettingsTimerBackgroundEffects() },
        goToSettingsTimerScrollsHapticFeedback = { goToSettingsTimerScrollsHapticFeedback() },
        timerIsEdit = { settingsViewModelTimer.timerIsEdit() },
        timerToggleCountOvertime = { settingsViewModelTimer.timerToggleCountOvertime() },
        saveTimerCountOvertime = { settingsViewModelTimer.saveTimerIsCountOvertime() },
        timerIsCountOvertime = settingsViewModelTimer.state.timerIsCountOvertime,
        timerNotification = settingsViewModelTimer.state.timerNotification,
        updateTimerNotification = { value: Float ->
            settingsViewModelTimer.updateTimerNotification(float = value)
        },
        saveTimerNotification = { settingsViewModelTimer.saveTimerNotification() }
    )

    SettingsApp(goToTheme = { goToTheme() })

    SettingsPrivacyPolicy(context = context)

    Spacer(modifier = Modifier.height(height = screenHeight() * 0.80f))
}





