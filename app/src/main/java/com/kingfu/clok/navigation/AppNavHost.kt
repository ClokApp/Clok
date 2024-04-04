package com.kingfu.clok.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kingfu.clok.navigation.navGraphBuilder.bugReport.bugReportGraph
import com.kingfu.clok.navigation.navGraphBuilder.settings.settingsGraph
import com.kingfu.clok.navigation.navGraphBuilder.settings.settingsStopwatch.fontStyle.settingsStopwatchLabelFontStyleGraph
import com.kingfu.clok.navigation.navGraphBuilder.settings.settingsStopwatch.fontStyle.settingsStopwatchLapTimeFontStyleGraph
import com.kingfu.clok.navigation.navGraphBuilder.settings.settingsStopwatch.fontStyle.settingsStopwatchTimeFontStyleGraph
import com.kingfu.clok.navigation.navGraphBuilder.settings.settingsStopwatch.settingsStopwatchFontStylesGraph
import com.kingfu.clok.navigation.navGraphBuilder.settings.settingsStopwatch.settingsStopwatchLabelBackgroundEffectsGraph
import com.kingfu.clok.navigation.navGraphBuilder.settings.settingsStopwatch.settingsStopwatchLabelStyleGraph
import com.kingfu.clok.navigation.navGraphBuilder.settings.settingsTheme.settingsThemeGraph
import com.kingfu.clok.navigation.navGraphBuilder.settings.settingsTimer.fontStyle.settingsTimerScrollsFontStyleGraph
import com.kingfu.clok.navigation.navGraphBuilder.settings.settingsTimer.fontStyle.settingsTimerTimeFontStyleGraph
import com.kingfu.clok.navigation.navGraphBuilder.settings.settingsTimer.settingsTimerBackgroundEffectsGraph
import com.kingfu.clok.navigation.navGraphBuilder.settings.settingsTimer.settingsTimerFontStylesGraph
import com.kingfu.clok.navigation.navGraphBuilder.settings.settingsTimer.settingsTimerProgressBarStylesGraph
import com.kingfu.clok.navigation.navGraphBuilder.settings.settingsTimer.settingsTimerScrollsHapticFeedbackGraph
import com.kingfu.clok.navigation.navGraphBuilder.stopwatch.stopwatchGraph
import com.kingfu.clok.navigation.navGraphBuilder.timer.timerGraph
import com.kingfu.clok.repository.preferencesDataStore.NavigationPreferences
import com.kingfu.clok.settings.viewModel.settingsViewModel.SettingsViewModel
import com.kingfu.clok.settings.viewModel.settingsViewModelStopwatch.SettingsViewModelStopwatch
import com.kingfu.clok.settings.viewModel.settingsViewModelTimer.SettingsViewModelTimer
import com.kingfu.clok.stopwatch.viewModel.StopwatchViewModel
import com.kingfu.clok.timer.viewModel.TimerViewModel
import com.kingfu.clok.ui.theme.ThemeType
import com.kingfu.clok.util.Variable.navigateToStartScreen
import com.kingfu.clok.util.Variable.startDestination
import kotlinx.coroutines.flow.first


@Composable
fun AppHavHost(
    navController: NavHostController,
    currentDestination: NavDestination?,
    timerViewModel: TimerViewModel,
    stopwatchViewModel: StopwatchViewModel,
    settingsViewModelStopwatch: SettingsViewModelStopwatch,
    settingsViewModelTimer: SettingsViewModelTimer,
    settingsViewModel: SettingsViewModel,
    navigationPreferences: NavigationPreferences,
    mainScaffoldPaddingValues: PaddingValues,
    isPortrait: Boolean,
    theme: ThemeType
) {
    LaunchedEffect(key1 = Unit) {
        if (navigateToStartScreen) {
            startDestination = navigationPreferences.getStartRoute.first()
        }
        navigateToStartScreen = false
    }

    if (startDestination != null) {
        NavHost(
            navController = navController,
            startDestination = startDestination!!,
        ) {

            stopwatchGraph(
                mainScaffoldPaddingValues = mainScaffoldPaddingValues,
                stopwatchViewModel = stopwatchViewModel,
                isPortrait = isPortrait,
                theme = theme
            )

            timerGraph(
                mainScaffoldPaddingValues = mainScaffoldPaddingValues,
                timerViewModel = timerViewModel,
                isPortrait = isPortrait,
                theme = theme
            )

            settingsGraph(
                currentDestination = currentDestination,
                navController = navController,
                settingsViewModelStopwatch = settingsViewModelStopwatch,
                settingsViewModelTimer = settingsViewModelTimer,
                theme = theme
            )

            settingsStopwatchLabelStyleGraph(
                currentDestination = currentDestination,
                navController = navController,
                settingsViewModelStopwatch = settingsViewModelStopwatch,
                theme = theme
            )

            settingsTimerProgressBarStylesGraph(
                currentDestination = currentDestination,
                navController = navController,
                settingsViewModelTimer = settingsViewModelTimer,
                theme = theme
            )

            bugReportGraph(
                currentDestination = currentDestination,
                navController = navController,
                theme = theme
            )

            settingsStopwatchLabelBackgroundEffectsGraph(
                currentDestination = currentDestination,
                navController = navController,
                settingsViewModelStopwatch = settingsViewModelStopwatch,
                theme = theme
            )

            settingsTimerBackgroundEffectsGraph(
                currentDestination = currentDestination,
                navController = navController,
                settingsViewModelTimer = settingsViewModelTimer,
                theme = theme
            )

            settingsTimerFontStylesGraph(
                currentDestination = currentDestination,
                navController = navController,
                theme = theme
            )

            settingsTimerTimeFontStyleGraph(
                currentDestination = currentDestination,
                navController = navController,
                settingsViewModelTimer = settingsViewModelTimer,
                theme = theme
            )

            settingsTimerScrollsFontStyleGraph(
                currentDestination = currentDestination,
                navController = navController,
                settingsViewModelTimer = settingsViewModelTimer,
                theme = theme
            )

            settingsStopwatchFontStylesGraph(
                currentDestination = currentDestination,
                navController = navController,
                theme = theme
            )

            settingsStopwatchLabelFontStyleGraph(
                currentDestination = currentDestination,
                navController = navController,
                settingsViewModelStopwatch = settingsViewModelStopwatch,
                theme = theme
            )

            settingsStopwatchTimeFontStyleGraph(
                currentDestination = currentDestination,
                navController = navController,
                settingsViewModelStopwatch = settingsViewModelStopwatch,
                theme = theme
            )

            settingsStopwatchLapTimeFontStyleGraph(
                currentDestination = currentDestination,
                navController = navController,
                settingsViewModelStopwatch = settingsViewModelStopwatch,
                theme = theme
            )

            settingsTimerScrollsHapticFeedbackGraph(
                currentDestination = currentDestination,
                navController = navController,
                settingsViewModelTimer = settingsViewModelTimer,
                theme = theme
            )

            settingsThemeGraph(
                currentDestination = currentDestination,
                navController = navController,
                settingsViewModel = settingsViewModel,
                theme = theme
            )
        }
    }
}
