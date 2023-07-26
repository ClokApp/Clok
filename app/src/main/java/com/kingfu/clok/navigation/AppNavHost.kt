package com.kingfu.clok.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kingfu.clok.navigation.navGraphBuilder.bugReport.bugReportGraph
import com.kingfu.clok.navigation.navGraphBuilder.settings.settingsGraph
import com.kingfu.clok.navigation.navGraphBuilder.settings.settingsStopwatch.settingsStopwatchFontStylesGraph
import com.kingfu.clok.navigation.navGraphBuilder.settings.settingsStopwatch.settingsStopwatchLabelBackgroundEffectsGraph
import com.kingfu.clok.navigation.navGraphBuilder.settings.settingsStopwatch.settingsStopwatchLabelStyleGraph
import com.kingfu.clok.navigation.navGraphBuilder.settings.settingsStopwatch.settingsStopwatchSelectedFontStyleGraph
import com.kingfu.clok.navigation.navGraphBuilder.settings.settingsTimer.settingsTimerBackgroundEffectsGraph
import com.kingfu.clok.navigation.navGraphBuilder.settings.settingsTimer.settingsTimerFontStylesGraph
import com.kingfu.clok.navigation.navGraphBuilder.settings.settingsTimer.settingsTimerProgressBarStylesGraph
import com.kingfu.clok.navigation.navGraphBuilder.settings.settingsTimer.settingsTimerScrollsHapticFeedbackGraph
import com.kingfu.clok.navigation.navGraphBuilder.settings.settingsTimer.settingsTimerSelectedFontStyleGraph
import com.kingfu.clok.navigation.navGraphBuilder.stopwatch.stopwatchGraph
import com.kingfu.clok.navigation.navGraphBuilder.timer.timerGraph
import com.kingfu.clok.repository.preferencesDataStore.NavigationPreferences
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel
import com.kingfu.clok.timer.timerViewModel.TimerViewModel
import com.kingfu.clok.ui.theme.Black00
import com.kingfu.clok.variable.Variable.navigateToStartScreen
import com.kingfu.clok.variable.Variable.startDestination
import kotlinx.coroutines.flow.first


@Composable
fun AppHavHost(
    navController: NavHostController,
    currentDestination: NavDestination?,
    timerViewModel: TimerViewModel,
    stopwatchViewModel: StopwatchViewModel,
    settingsViewModelStopwatch: SettingsViewModelStopwatch,
    settingsViewModelTimer: SettingsViewModelTimer,
    navigationPreferences: NavigationPreferences,
    mainScaffoldPaddingValues: PaddingValues,
) {
    LaunchedEffect(key1 = Unit) {
        if (navigateToStartScreen) {
            startDestination = navigationPreferences.getStartDestination.first()
        }
        navigateToStartScreen = false
    }

    if (startDestination != null) {
        NavHost(
            navController = navController,
            startDestination = startDestination!!,
            modifier = Modifier.background(color = Black00)
        ) {

            stopwatchGraph(
                mainScaffoldPaddingValues = mainScaffoldPaddingValues,
                stopwatchViewModel = stopwatchViewModel,
                settingsViewModelStopwatch = settingsViewModelStopwatch
            )

            timerGraph(
                mainScaffoldPaddingValues = mainScaffoldPaddingValues,
                timerViewModel = timerViewModel,
                settingsViewModelTimer = settingsViewModelTimer
            )

            settingsGraph(
                currentDestination = currentDestination,
                navController = navController,
                settingsViewModelStopwatch = settingsViewModelStopwatch,
                settingsViewModelTimer = settingsViewModelTimer
            )

            settingsStopwatchLabelStyleGraph(
                currentDestination = currentDestination,
                navController = navController,
                settingsViewModelStopwatch = settingsViewModelStopwatch
            )

            settingsTimerProgressBarStylesGraph(
                currentDestination = currentDestination,
                navController = navController,
                settingsViewModelTimer = settingsViewModelTimer
            )

            bugReportGraph(
                currentDestination = currentDestination,
                navController = navController
            )

            settingsStopwatchLabelBackgroundEffectsGraph(
                currentDestination = currentDestination,
                navController = navController,
                settingsViewModelStopwatch = settingsViewModelStopwatch
            )

            settingsTimerBackgroundEffectsGraph(
                currentDestination = currentDestination,
                navController = navController,
                settingsViewModelTimer = settingsViewModelTimer
            )

            settingsTimerFontStylesGraph(
                currentDestination = currentDestination,
                navController = navController,
                settingsViewModelTimer = settingsViewModelTimer
            )

            settingsTimerSelectedFontStyleGraph(
                currentDestination = currentDestination,
                navController = navController,
                settingsViewModelTimer = settingsViewModelTimer
            )

            settingsStopwatchFontStylesGraph(
                currentDestination = currentDestination,
                navController = navController,
                settingsViewModelStopwatch = settingsViewModelStopwatch
            )

            settingsStopwatchSelectedFontStyleGraph(
                currentDestination = currentDestination,
                navController = navController,
                settingsViewModelStopwatch = settingsViewModelStopwatch
            )

            settingsTimerScrollsHapticFeedbackGraph(
                currentDestination = currentDestination,
                navController = navController,
                settingsViewModelTimer = settingsViewModelTimer
            )
        }
    }
}
