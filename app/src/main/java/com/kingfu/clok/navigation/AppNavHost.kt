package com.kingfu.clok.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kingfu.clok.bottomBar.BottomBarNavigation
import com.kingfu.clok.repository.preferencesDataStore.NavigationPreferences
import com.kingfu.clok.repository.preferencesDataStore.StopwatchPreferences
import com.kingfu.clok.repository.preferencesDataStore.TimerPreferences
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer
import com.kingfu.clok.stopwatch.stopwatchFactory.StopwatchFactory
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel
import com.kingfu.clok.timer.timerFactory.TimerFactory
import com.kingfu.clok.timer.timerViewModel.TimerViewModel
import com.kingfu.clok.topBar.TopBar

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val scaffoldState = rememberScaffoldState()
    val stopwatchViewModel: StopwatchViewModel = viewModel(
        factory = StopwatchFactory(StopwatchPreferences.getInstance(LocalContext.current))
    )

    val timerViewModel: TimerViewModel = viewModel(
        factory = TimerFactory(TimerPreferences.getInstance(LocalContext.current))
    )

    val settingsViewModelStopwatch: SettingsViewModelStopwatch = viewModel(
        factory = StopwatchFactory(StopwatchPreferences.getInstance(LocalContext.current))
    )
    val settingsViewModelTimer: SettingsViewModelTimer = viewModel(
        factory = TimerFactory(TimerPreferences.getInstance(LocalContext.current))
    )

    val navigationPreferences: NavigationPreferences =
        NavigationPreferences.getInstance(LocalContext.current)

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            if (currentDestination != null) {
                TopBar(
                    navController = navController,
                    currentDestination = currentDestination,
                )
            }
        },
        bottomBar = {
            BottomBarNavigation(
                currentDestination = currentDestination,
                navController = navController,
                navigationPreferences = navigationPreferences
            )
        },
        content = { paddingValues ->
            Box(Modifier.padding(paddingValues)) {

                Navigation(
                    navController = navController,
                    scaffoldState = scaffoldState,
                    timerViewModel = timerViewModel,
                    stopwatchViewModel = stopwatchViewModel,
                    settingsViewModelStopwatch = settingsViewModelStopwatch,
                    settingsViewModelTimer = settingsViewModelTimer,
                    navigationPreferences = navigationPreferences,
                )
            }
        },
    )
}