package com.kingfu.clok.navigation.navGraphBuilder.timer

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kingfu.clok.navigation.Screen
import com.kingfu.clok.settings.settingsScreen.settingsApp.settingsThemeScreen.ThemeType
import com.kingfu.clok.timer.timerScreen.TimerScreen
import com.kingfu.clok.timer.timerViewModel.TimerViewModel


fun NavGraphBuilder.timerGraph(
    mainScaffoldPaddingValues: PaddingValues,
    timerViewModel: TimerViewModel,
    isPortrait: Boolean,
    theme: ThemeType
) {

    val slideAnimation = AnimatedContentTransitionScope.SlideDirection
    val tweenDuration = 200

    composable(
        route = Screen.Timer.route,
        enterTransition = {
            when (initialState.destination.route) {
                Screen.Stopwatch.route -> {
                    slideIntoContainer(
                        towards = if (isPortrait) slideAnimation.Left else slideAnimation.Up,
                        animationSpec = tween(durationMillis = tweenDuration)
                    )
                }

                else -> {
                    slideIntoContainer(
                        towards = slideAnimation.Down,
                        animationSpec = tween(durationMillis = tweenDuration)
                    )
                }
            }
        },
        exitTransition = {
            when (targetState.destination.route) {
                Screen.Stopwatch.route -> {
                    slideOutOfContainer(
                        towards = if (isPortrait) slideAnimation.Right else slideAnimation.Down,
                        animationSpec = tween(durationMillis = tweenDuration)
                    )
                }

                else -> {
                    slideOutOfContainer(
                        towards = slideAnimation.Up,
                        animationSpec = tween(durationMillis = tweenDuration)
                    )
                }
            }
        },
        popEnterTransition = {
            when (initialState.destination.route) {
                Screen.Stopwatch.route -> {
                    slideIntoContainer(
                        towards = if (isPortrait) slideAnimation.Left else slideAnimation.Up,
                        animationSpec = tween(durationMillis = tweenDuration)
                    )
                }

                else -> {
                    slideIntoContainer(
                        towards = slideAnimation.Down,
                        animationSpec = tween(durationMillis = tweenDuration)
                    )
                }
            }
        },
        popExitTransition = {
            when (targetState.destination.route) {
                Screen.Stopwatch.route -> {
                    slideOutOfContainer(
                        towards = if (isPortrait) slideAnimation.Right else slideAnimation.Down,
                        animationSpec = tween(durationMillis = tweenDuration)
                    )
                }

                else -> {
                    slideOutOfContainer(
                        towards = slideAnimation.Down,
                        animationSpec = tween(durationMillis = tweenDuration)
                    )
                }
            }
        }
    ) {
        Box(
            modifier = Modifier.padding(
                paddingValues = if (isPortrait) {
                    mainScaffoldPaddingValues
                } else {
                    PaddingValues(all = 0.dp)
                }
            )
        ) {
            TimerScreen(
                vm = timerViewModel,
                theme = theme
            )
        }
    }
}