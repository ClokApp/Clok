package com.kingfu.clok.settings.settingsView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.kingfu.clok.settings.settingsView.settingsStopwatchView.SettingsStopwatchView
import com.kingfu.clok.settings.settingsView.settingsTimerView.SettingsTimerView
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer
import com.kingfu.clok.ui.theme.Black00

@Composable
fun SettingsView(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    settingsViewModelStopwatch: SettingsViewModelStopwatch,
    settingsViewModelTimer: SettingsViewModelTimer,
) {
    val haptic = LocalHapticFeedback.current
    val settingsScrollState = rememberScrollState()

    Card(
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(30.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Black00)
                .verticalScroll(settingsScrollState)
        ) {
            SettingsStopwatchView(
                navController,
                settingsViewModelStopwatch,
                haptic,
            )

            SettingsTimerView(
                navController = navController,
                settingsViewModelTimer,
                haptic
            )
            Spacer(modifier = Modifier.height(100.dp))
        }
    }

}