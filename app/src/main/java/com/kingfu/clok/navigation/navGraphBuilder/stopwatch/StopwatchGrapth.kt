package com.kingfu.clok.navigation.navGraphBuilder.stopwatch

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
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
    settingsViewModelStopwatch: SettingsViewModelStopwatch,
) {
    composable(
        route = Screens.Stopwatch.route,
        enterTransition = {
            when(initialState.destination.route){
                Screens.Timer.route -> {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(durationMillis = 200)
                    )
                }
                else ->{
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Up,
                        animationSpec = tween(durationMillis = 200)
                    )
                }
            }
        },
        exitTransition = {
            when(targetState.destination.route){
                Screens.Timer.route -> {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(durationMillis = 200)
                    )
                }
                else ->{
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Up,
                        animationSpec = tween(durationMillis = 200)
                    )
                }
            }
        },
        popEnterTransition = {
            when(initialState.destination.route){
                Screens.Timer.route -> {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(durationMillis = 200)
                    )
                }
                else ->{
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Down,
                        animationSpec = tween(durationMillis = 200)
                    )
                }
            }
        },
        popExitTransition = {
            when(targetState.destination.route){
                Screens.Timer.route -> {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(durationMillis = 200)
                    )
                }
                else -> {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Up,
                        animationSpec = tween(durationMillis = 200)
                    )
                }
            }
        }
    ) {
        Box(modifier = Modifier.padding(paddingValues = mainScaffoldPaddingValues)) {
            StopwatchScreen(
                vm = stopwatchViewModel,
                settingsViewModelStopwatch = settingsViewModelStopwatch,
            )
        }
    }
}