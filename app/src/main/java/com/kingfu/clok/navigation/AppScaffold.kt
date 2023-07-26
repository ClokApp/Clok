package com.kingfu.clok.navigation

import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kingfu.clok.components.bottomBar.BottomBar
import com.kingfu.clok.components.showSnackBar
import com.kingfu.clok.components.topBar.TopBar
import com.kingfu.clok.repository.preferencesDataStore.NavigationPreferences
import com.kingfu.clok.repository.preferencesDataStore.StopwatchPreferences
import com.kingfu.clok.repository.preferencesDataStore.TimerPreferences
import com.kingfu.clok.repository.room.stopwatchRoom.StopwatchLapDatabase
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchFactory
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel
import com.kingfu.clok.timer.timerViewModel.TimerFactory
import com.kingfu.clok.timer.timerViewModel.TimerViewModel
import com.kingfu.clok.ui.theme.Black00
import com.kingfu.clok.variable.Variable.isShowSnackbar
import com.kingfu.clok.variable.Variable.isShowTimerNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun AppScaffold() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    val stopwatchViewModel: StopwatchViewModel = viewModel(
        factory = StopwatchFactory(
            stopwatchPreferences = StopwatchPreferences.getInstance(context = context),
            stopwatchLapDatabase = StopwatchLapDatabase.getInstance(context = context)
        )
    )

    val timerViewModel: TimerViewModel = viewModel(
        factory = TimerFactory(timerPreferences = TimerPreferences.getInstance(context = context))
    )

    val settingsViewModelStopwatch: SettingsViewModelStopwatch = viewModel(
        factory = StopwatchFactory(
            stopwatchPreferences = StopwatchPreferences.getInstance(context = context),
            stopwatchLapDatabase = StopwatchLapDatabase.getInstance(context = context)
        )
    )

    val settingsViewModelTimer: SettingsViewModelTimer = viewModel(
        factory = TimerFactory(timerPreferences = TimerPreferences.getInstance(context = context))
    )

    val navigationPreferences: NavigationPreferences =
        NavigationPreferences.getInstance(context = context)

    LaunchedEffect(key1 = isShowTimerNotification) {
        timerViewModel.timerNotification(context = context)
    }

    LaunchedEffect(key1 = isShowSnackbar) {
        showSnackBar(snackbarHostState = snackbarHostState)
    }

    Scaffold(
        containerColor = Black00,
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.imePadding()
            )
        },
        topBar = {
            if (currentDestination?.route == Screens.Stopwatch.route || currentDestination?.route == Screens.Timer.route) {
                TopBar(
                    currentDestination = currentDestination,
                    navigateToSettingsScreen = {
                        navController.navigate(route = Screens.Settings.route)
                    },
                    navigateToBugReportScreen = {
                        navController.navigate(route = Screens.BugReport.route)
                    }
                )
            }
        },
        bottomBar = {
            if (currentDestination?.route == Screens.Stopwatch.route || currentDestination?.route == Screens.Timer.route) {
                BottomBar(
                    currentDestination = currentDestination,
                    navigateToStopwatch = {
                        navController.navigate(route = Screens.Stopwatch.route) {
                            CoroutineScope(context = Dispatchers.IO).launch {
                                navigationPreferences.setStartDestination(string = Screens.Stopwatch.route)
                            }
                            popUpTo(id = navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    navigateToTimer = {
                        navController.navigate(route = Screens.Timer.route) {
                            CoroutineScope(context = Dispatchers.IO).launch {
                                navigationPreferences.setStartDestination(string = Screens.Timer.route)
                            }
                            popUpTo(id = navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        },
        content = { paddingValues ->
            AppHavHost(
                navController = navController,
                currentDestination = currentDestination,
                timerViewModel = timerViewModel,
                stopwatchViewModel = stopwatchViewModel,
                settingsViewModelStopwatch = settingsViewModelStopwatch,
                settingsViewModelTimer = settingsViewModelTimer,
                navigationPreferences = navigationPreferences,
                mainScaffoldPaddingValues = paddingValues
            )
        }
    )
}
