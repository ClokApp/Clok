package com.kingfu.clok.settings.settingsScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.kingfu.clok.settings.settingsScreen.settingsPrivacyScreen.SettingsPrivacyPolicyScreen
import com.kingfu.clok.settings.settingsScreen.settingsStopwatchScreen.SettingsStopwatchScreen
import com.kingfu.clok.settings.settingsScreen.settingsTimerScreen.SettingsTimerScreen
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer

@Composable
fun SettingsScreen(
    settingsViewModelStopwatch: SettingsViewModelStopwatch,
    settingsViewModelTimer: SettingsViewModelTimer,
    navigateToSettingsStopwatchBackgroundEffects: () -> Unit,
    navigateToSettingsStopwatchFontStyles: () -> Unit,
    navigateToSettingsStopwatchLabelStyles: () -> Unit,
    navigateToSettingsTimerProgressBarStyles: () -> Unit,
    navigateToSettingsTimerFontStyles: () -> Unit,
    navigateToSettingsTimerBackgroundEffects: () -> Unit,
    navigateToSettingsTimerScrollsHapticFeedback: () -> Unit,
) {
    val haptic = LocalHapticFeedback.current
    val settingsScrollState = rememberScrollState()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(size = 20.dp))
            .verticalScroll(state = settingsScrollState)
    ) {
        SettingsStopwatchScreen(
            vm = settingsViewModelStopwatch,
            haptic = haptic,
            configuration = configuration,
            navigateToSettingsStopwatchBackgroundEffects = { navigateToSettingsStopwatchBackgroundEffects() },
            navigateToSettingsStopwatchFontStyles = { navigateToSettingsStopwatchFontStyles() },
            navigateToSettingsStopwatchLabelStyles = { navigateToSettingsStopwatchLabelStyles() }
        )

        SettingsTimerScreen(
            vm = settingsViewModelTimer,
            haptic = haptic,
            configuration = configuration,
            navigateToSettingsTimerProgressBarStyles = { navigateToSettingsTimerProgressBarStyles() },
            navigateToSettingsTimerFontStyles = { navigateToSettingsTimerFontStyles() },
            navigateToSettingsTimerBackgroundEffects = { navigateToSettingsTimerBackgroundEffects() },
            navigateToSettingsTimerScrollsHapticFeedback = { navigateToSettingsTimerScrollsHapticFeedback() }
        )

        SettingsPrivacyPolicyScreen(
            haptic = haptic,
            context = context
        )

        Spacer(modifier = Modifier.height(height = screenHeight * 0.20f))

    }
}



