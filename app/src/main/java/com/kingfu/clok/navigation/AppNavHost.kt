package com.kingfu.clok.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kingfu.clok.core.Variables.isShowTimerNotification
import com.kingfu.clok.navigation.navGraphBuilder.settingsGraph
import com.kingfu.clok.navigation.navGraphBuilder.stopwatchGraph
import com.kingfu.clok.navigation.navGraphBuilder.timerGraph
import com.kingfu.clok.settings.repository.SettingsPreferences
import com.kingfu.clok.settings.viewModel.settingsViewModel.SettingsFactory
import com.kingfu.clok.settings.viewModel.settingsViewModel.SettingsViewModel
import com.kingfu.clok.stopwatch.repository.StopwatchPreferences
import com.kingfu.clok.stopwatch.repository.stopwatchRoom.StopwatchLapDatabase
import com.kingfu.clok.stopwatch.viewModel.StopwatchFactory
import com.kingfu.clok.stopwatch.viewModel.StopwatchViewModel
import com.kingfu.clok.timer.repository.TimerPreferences
import com.kingfu.clok.timer.viewModel.TimerFactory
import com.kingfu.clok.timer.viewModel.TimerViewModel


@Composable
fun AppNavHost(
    modifier: Modifier,
    navController: NavHostController,
    route: String?,
    toggleDrawer: () -> Unit,
    isDrawerOpen: () -> Boolean,
) {
    val context = LocalContext.current
    val stopwatchFactory = StopwatchFactory(
        stopwatchPreferences = StopwatchPreferences.getInstance(context = context),
        stopwatchLapDatabase = StopwatchLapDatabase.getInstance(context = context)
    )
    val timerFactory =
        TimerFactory(timerPreferences = TimerPreferences.getInstance(context = context))
    val stopwatchViewModel: StopwatchViewModel = viewModel(factory = stopwatchFactory)
    val timerViewModel: TimerViewModel = viewModel(factory = timerFactory)
    val settingsViewModel: SettingsViewModel = viewModel(
        factory = SettingsFactory(settingsPreferences = SettingsPreferences.getInstance(context = context))
    )
    val enterAndExitTransition = AnimatedContentTransitionScope.SlideDirection.Left
    val popTransition = AnimatedContentTransitionScope.SlideDirection.Right
    val duration = 200

    LaunchedEffect(key1 = isShowTimerNotification) {
        timerViewModel.showNotifications(context = context)
    }


    if (settingsViewModel.state.isLoaded && timerViewModel.state.isLoaded) {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = settingsViewModel.state.startRoute,
            enterTransition = {
                slideIntoContainer(
                    towards = enterAndExitTransition,
                    animationSpec = tween(durationMillis = duration)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = enterAndExitTransition,
                    animationSpec = tween(durationMillis = duration)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = popTransition,
                    animationSpec = tween(durationMillis = duration)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = popTransition,
                    animationSpec = tween(durationMillis = duration)
                )
            }
        ) {
            stopwatchGraph(
                vm = stopwatchViewModel,
                toggleDrawer = toggleDrawer,
                isDrawerOpen = isDrawerOpen,
            )

            timerGraph(
                vm = timerViewModel,
                toggleDrawer = toggleDrawer,
                isDrawerOpen = isDrawerOpen
            )

            settingsGraph(
                route = route,
                isDrawerOpen = isDrawerOpen,
                toggleDrawer = toggleDrawer,
                vm = settingsViewModel
            )
        }
    }
}


