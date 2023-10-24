package com.kingfu.clok.navigation.navGraphBuilder.stopwatch

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
import com.kingfu.clok.stopwatch.stopwatchScreen.StopwatchScreen
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel


fun NavGraphBuilder.stopwatchGraph(
    mainScaffoldPaddingValues: PaddingValues,
    stopwatchViewModel: StopwatchViewModel,
    isPortrait: Boolean,
    theme: ThemeType,
) {

    val slideAnimation = AnimatedContentTransitionScope.SlideDirection
    val tweenDuration = 200

    composable(
        route = Screen.Stopwatch.route,
        enterTransition = {
            when (initialState.destination.route) {
                Screen.Timer.route -> {
                    slideIntoContainer(
                        towards = if (isPortrait) slideAnimation.Right else slideAnimation.Down,
                        animationSpec = tween(durationMillis = tweenDuration)
                    )
                }

                else -> {
                    slideIntoContainer(
                        towards = slideAnimation.Up,
                        animationSpec = tween(durationMillis = tweenDuration)
                    )
                }
            }
        },
        exitTransition = {
            when (targetState.destination.route) {
                Screen.Timer.route -> {
                    slideOutOfContainer(
                        towards = if (isPortrait) slideAnimation.Left else slideAnimation.Up,
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
                Screen.Timer.route -> {
                    slideIntoContainer(
                        towards = if (isPortrait) slideAnimation.Right else slideAnimation.Down,
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
                Screen.Timer.route -> {
                    slideOutOfContainer(
                        towards = if (isPortrait) slideAnimation.Left else slideAnimation.Up,
                        animationSpec = tween(durationMillis = tweenDuration)
                    )
                }

                else -> {
                    slideOutOfContainer(
                        towards = slideAnimation.Up,
                        animationSpec = tween(durationMillis = 200)
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
                    PaddingValues(start = 20.dp)
                }
            )
        ) {
            StopwatchScreen(
                vm = stopwatchViewModel,
                theme = theme
            )
        }
    }
}