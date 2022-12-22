package com.kingfu.clok.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kingfu.clok.repository.preferencesDataStore.NavigationPreferences
import com.kingfu.clok.ui.theme.Black00
import com.kingfu.clok.variable.Variable.navigateToStartScreen
import com.kingfu.clok.variable.Variable.startDestination
import com.kingfu.clok.variable.Variable.timerShowNotification
import com.kingfu.clok.bugReport.BugReport
import com.kingfu.clok.settings.settingsView.settingsStopwatchView.SettingsStopwatchLabelStyle
import com.kingfu.clok.settings.settingsView.settingsTimerView.SettingsTimerProgressBarStyle
import com.kingfu.clok.settings.settingsView.SettingsView
import com.kingfu.clok.stopwatch.stopwatchView.StopwatchView
import com.kingfu.clok.timer.timerView.TimerView
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel
import com.kingfu.clok.timer.timerViewModel.TimerViewModel
import kotlinx.coroutines.flow.first

@Composable
fun Navigation(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
//    paddingValues: PaddingValues,
    timerViewModel: TimerViewModel,
    stopwatchViewModel: StopwatchViewModel,
    settingsViewModelStopwatch: SettingsViewModelStopwatch,
    settingsViewModelTimer: SettingsViewModelTimer,
    navigationPreferences: NavigationPreferences,
) {
    val context = LocalContext.current

    if (navigateToStartScreen) {
        LaunchedEffect(Unit) {
            startDestination = navigationPreferences.getStartDestination.first()
        }
        navigateToStartScreen = false
    }

    if (timerShowNotification) {
        LaunchedEffect(timerShowNotification){
            timerViewModel.timerShowSnackBar(scaffoldState, context = context)
        }
        LaunchedEffect(timerShowNotification){
            timerViewModel.timerNotification(context = context)
        }
    }

    if (startDestination != null) {
        NavHost(
            navController,
            startDestination = startDestination!!,
            modifier = Modifier
                .background(Black00)
//                .padding(paddingValues = paddingValues)
        ) {

            composable(Screens.Stopwatch.route) {
                StopwatchView(navController = navController, scaffoldState, stopwatchViewModel)
            }

            composable(Screens.Timer.route) {
                TimerView(navController = navController, scaffoldState, timerViewModel)
            }

            composable(Screens.Settings.route) {
                SettingsView(
                    navController = navController,
                    scaffoldState,
                    settingsViewModelStopwatch,
                    settingsViewModelTimer
                )
            }

            composable(Screens.SettingsStopwatchLabelStyle.route) {
                SettingsStopwatchLabelStyle(settingsViewModelStopwatch)
            }

            composable(Screens.SettingsTimerProgressBarStyle.route) {
                SettingsTimerProgressBarStyle(settingsViewModelTimer)
            }

            composable(Screens.BugReport.route){
                BugReport()
            }
        }
    }
}