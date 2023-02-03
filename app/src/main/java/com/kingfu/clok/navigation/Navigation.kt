package com.kingfu.clok.navigation

import androidx.compose.foundation.background
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kingfu.clok.bugReport.BugReport
import com.kingfu.clok.repository.preferencesDataStore.NavigationPreferences
import com.kingfu.clok.settings.settingsView.SettingsView
import com.kingfu.clok.settings.settingsView.settingsStopwatchView.SettingsStopwatchBackgroundEffects
import com.kingfu.clok.settings.settingsView.settingsStopwatchView.SettingsStopwatchLabelStyle
import com.kingfu.clok.settings.settingsView.settingsStopwatchView.fontStyle.SettingsStopwatchFontStylesView
import com.kingfu.clok.settings.settingsView.settingsStopwatchView.fontStyle.SettingsStopwatchSelectedFontStyleView
import com.kingfu.clok.settings.settingsView.settingsTimerView.SettingsTimerBackgroundEffects
import com.kingfu.clok.settings.settingsView.settingsTimerView.SettingsTimerProgressBarStyle
import com.kingfu.clok.settings.settingsView.settingsTimerView.fontStyle.SettingsTimerFontStyles
import com.kingfu.clok.settings.settingsView.settingsTimerView.fontStyle.SettingsTimerSelectedFontStyleView
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer
import com.kingfu.clok.stopwatch.stopwatchView.StopwatchView
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel
import com.kingfu.clok.timer.timerView.TimerView
import com.kingfu.clok.timer.timerViewModel.TimerViewModel
import com.kingfu.clok.ui.theme.Black00
import com.kingfu.clok.variable.Variable.navigateToStartScreen
import com.kingfu.clok.variable.Variable.startDestination
import kotlinx.coroutines.flow.first

@Composable
fun Navigation(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    timerViewModel: TimerViewModel,
    stopwatchViewModel: StopwatchViewModel,
    settingsViewModelStopwatch: SettingsViewModelStopwatch,
    settingsViewModelTimer: SettingsViewModelTimer,
    navigationPreferences: NavigationPreferences,
) {
    if (navigateToStartScreen) {
        LaunchedEffect(Unit) {
            startDestination = navigationPreferences.getStartDestination.first()
        }
        navigateToStartScreen = false
    }

    if (startDestination != null) {
        NavHost(
            navController = navController,
            startDestination = startDestination!!,
            modifier = Modifier.background(Black00)
        ) {

            composable(Screens.Stopwatch.route) {
                StopwatchView(
                    navController = navController,
                    scaffoldState = scaffoldState,
                    vm = stopwatchViewModel
                )
            }

            composable(Screens.Timer.route) {
                TimerView(
                    navController = navController,
                    scaffoldState = scaffoldState,
                    vm = timerViewModel
                )
            }

            composable(Screens.Settings.route) {
                SettingsView(
                    navController = navController,
                    scaffoldState = scaffoldState,
                    settingsViewModelStopwatch = settingsViewModelStopwatch,
                    settingsViewModelTimer = settingsViewModelTimer
                )
            }

            composable(Screens.SettingsStopwatchLabelStyles.route) {
                SettingsStopwatchLabelStyle(vm = settingsViewModelStopwatch)
            }

            composable(Screens.SettingsTimerProgressBarStyles.route) {
                SettingsTimerProgressBarStyle(vm = settingsViewModelTimer)
            }

            composable(Screens.BugReport.route) {
                BugReport()
            }

            composable(Screens.SettingsStopwatchBackgroundEffects.route) {
                SettingsStopwatchBackgroundEffects(vm = settingsViewModelStopwatch)
            }

            composable(Screens.SettingsTimerBackgroundEffects.route) {
                SettingsTimerBackgroundEffects(vm = settingsViewModelTimer)
            }

            composable(Screens.SettingsTimerFontStyles.route) {
                SettingsTimerFontStyles(vm = settingsViewModelTimer, navController = navController)
            }

            composable(Screens.SettingsTimerSelectedFontStyle.route) {
                SettingsTimerSelectedFontStyleView(vm = settingsViewModelTimer)
            }

            composable(Screens.SettingsStopwatchFontStyles.route) {
                SettingsStopwatchFontStylesView(
                    vm = settingsViewModelStopwatch,
                    navController = navController
                )
            }

            composable(Screens.SettingsStopwatchSelectedFontStyle.route) {
                SettingsStopwatchSelectedFontStyleView(vm = settingsViewModelStopwatch)
            }




        }
    }
}