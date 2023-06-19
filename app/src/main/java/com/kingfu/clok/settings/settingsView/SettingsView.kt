package com.kingfu.clok.settings.settingsView

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
import androidx.navigation.NavHostController
import com.kingfu.clok.settings.settingsView.settingsPrivacyView.SettingsPrivacyPolicyView
import com.kingfu.clok.settings.settingsView.settingsStopwatchView.SettingsStopwatchView
import com.kingfu.clok.settings.settingsView.settingsTimerView.SettingsTimerView
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer

@Composable
fun SettingsView(
    navController: NavHostController,
    settingsViewModelStopwatch: SettingsViewModelStopwatch,
    settingsViewModelTimer: SettingsViewModelTimer,
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
        SettingsStopwatchView(
            navController = navController,
            vm = settingsViewModelStopwatch,
            haptic = haptic,
            configuration = configuration
        )

        SettingsTimerView(
            navController = navController,
            vm = settingsViewModelTimer,
            haptic = haptic,
            configuration = configuration
        )

        SettingsPrivacyPolicyView(
            haptic = haptic,
            context = context
        )

        Spacer(modifier = Modifier.height(height = screenHeight * 0.20f))

    }
}
