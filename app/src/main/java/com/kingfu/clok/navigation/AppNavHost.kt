package com.kingfu.clok.navigation

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kingfu.clok.components.TopBar
import com.kingfu.clok.repository.preferencesDataStore.NavigationPreferences
import com.kingfu.clok.repository.preferencesDataStore.StopwatchPreferences
import com.kingfu.clok.repository.preferencesDataStore.TimerPreferences
import com.kingfu.clok.viewModel.factory.StopwatchFactory
import com.kingfu.clok.viewModel.factory.TimerFactory
import com.kingfu.clok.viewModel.settingsViewModel.SettingsViewModelStopwatch
import com.kingfu.clok.viewModel.settingsViewModel.SettingsViewModelTimer
import com.kingfu.clok.viewModel.stopwatchViewModel.StopwatchViewModel
import com.kingfu.clok.viewModel.timerViewModel.TimerViewModel

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
                navigationPreferences = navigationPreferences,
            )
        },
        content = { paddingValues ->
            Navigation(
                navController = navController,
                scaffoldState = scaffoldState,
                paddingValues = paddingValues,
                timerViewModel = timerViewModel,
                stopwatchViewModel = stopwatchViewModel,
                settingsViewModelStopwatch = settingsViewModelStopwatch,
                settingsViewModelTimer = settingsViewModelTimer,
                navigationPreferences = navigationPreferences,
            )
        },
    )
}