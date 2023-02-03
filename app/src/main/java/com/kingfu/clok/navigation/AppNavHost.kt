package com.kingfu.clok.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kingfu.clok.components.bottomBar.BottomBarNavigation
import com.kingfu.clok.components.showSnackBar
import com.kingfu.clok.components.topBar.TopBar
import com.kingfu.clok.notification.timer.TimerNotificationService
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
import com.kingfu.clok.variable.Variable.showSnackBar

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    val stopwatchViewModel: StopwatchViewModel = viewModel(
        factory = StopwatchFactory(
            stopwatchPreferences = StopwatchPreferences.getInstance(context = context),
            StopwatchLapDatabase = StopwatchLapDatabase.getInstance(context = context),
            savedStateHandle = SavedStateHandle(),
        )
    )

    val timerViewModel: TimerViewModel = viewModel(
        factory = TimerFactory(
            timerPreferences = TimerPreferences.getInstance(LocalContext.current),
            savedStateHandle = SavedStateHandle()
        )
    )

    val settingsViewModelStopwatch: SettingsViewModelStopwatch = viewModel(
        factory = StopwatchFactory(
            stopwatchPreferences = StopwatchPreferences.getInstance(context = context),
            StopwatchLapDatabase = StopwatchLapDatabase.getInstance(context = context),
            savedStateHandle = SavedStateHandle(),
        )
    )

    val settingsViewModelTimer: SettingsViewModelTimer = viewModel(
        factory = TimerFactory(
            timerPreferences = TimerPreferences.getInstance(LocalContext.current),
            savedStateHandle = SavedStateHandle()
        )
    )

    val navigationPreferences: NavigationPreferences =
        NavigationPreferences.getInstance(LocalContext.current)

    LaunchedEffect(timerViewModel.timerIsFinished) {
        timerViewModel.timerNotification(context = context)
    }

    LaunchedEffect(timerViewModel.timerIsFinished) {
        if (showSnackBar) {
            showSnackBar(
                message = "Timer is finished!",
                actionLabel = "cancel",
                duration = SnackbarDuration.Short,
                scaffoldState = scaffoldState,
                action = {
                    TimerNotificationService(context = context).cancelNotification()
                },
                dismiss = {}
            )
        }
    }

    Scaffold(
        backgroundColor = Black00,
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
        }
    )
}