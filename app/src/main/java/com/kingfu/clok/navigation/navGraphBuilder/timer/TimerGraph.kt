package com.kingfu.clok.navigation.navGraphBuilder.timer

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kingfu.clok.navigation.Screens
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer
import com.kingfu.clok.timer.timerScreen.TimerScreen
import com.kingfu.clok.timer.timerViewModel.TimerViewModel


fun NavGraphBuilder.timerGraph(
    mainScaffoldPaddingValues: PaddingValues,
    timerViewModel: TimerViewModel,
    settingsViewModelTimer: SettingsViewModelTimer,
) {
    composable(
        route = Screens.Timer.route,
        enterTransition = {
            when(initialState.destination.route){
                Screens.Stopwatch.route -> {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
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
        exitTransition = {
            when(targetState.destination.route){
                Screens.Stopwatch.route -> {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
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
                Screens.Stopwatch.route -> {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
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
                Screens.Stopwatch.route -> {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(durationMillis = 200)
                    )
                }
                else -> {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Down,
                        animationSpec = tween(durationMillis = 200)
                    )
                }
            }
        }
    ) {
        Box(modifier = Modifier.padding(paddingValues = mainScaffoldPaddingValues)) {
            TimerScreen(
                vm = timerViewModel,
                settingsViewModelTimer = settingsViewModelTimer
            )
        }
    }
}