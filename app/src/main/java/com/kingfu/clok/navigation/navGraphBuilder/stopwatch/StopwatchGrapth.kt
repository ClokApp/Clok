package com.kingfu.clok.navigation.navGraphBuilder.stopwatch

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kingfu.clok.navigation.Screens
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch
import com.kingfu.clok.stopwatch.stopwatchScreen.StopwatchScreen
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel

fun NavGraphBuilder.stopwatchGraph(
    mainScaffoldPaddingValues: PaddingValues,
    stopwatchViewModel: StopwatchViewModel,
    settingsViewModelStopwatch: SettingsViewModelStopwatch
) {
    composable(route = Screens.Stopwatch.route) {
        Box(modifier = Modifier.padding(paddingValues = mainScaffoldPaddingValues)) {
            StopwatchScreen(
                vm = stopwatchViewModel,
                settingsViewModelStopwatch = settingsViewModelStopwatch,
            )
        }
    }
}