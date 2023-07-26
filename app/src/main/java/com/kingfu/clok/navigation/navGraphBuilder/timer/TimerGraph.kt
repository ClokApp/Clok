package com.kingfu.clok.navigation.navGraphBuilder.timer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kingfu.clok.navigation.Screens
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer
import com.kingfu.clok.timer.timerScreen.TimerScreen
import com.kingfu.clok.timer.timerViewModel.TimerViewModel


fun NavGraphBuilder.timerGraph(
    mainScaffoldPaddingValues: PaddingValues,
    timerViewModel: TimerViewModel,
    settingsViewModelTimer: SettingsViewModelTimer
) {
    composable(route = Screens.Timer.route) {
        Box(modifier = Modifier.padding(paddingValues = mainScaffoldPaddingValues)) {
            TimerScreen(
                vm = timerViewModel,
                settingsViewModelTimer = settingsViewModelTimer
            )
        }
    }
}