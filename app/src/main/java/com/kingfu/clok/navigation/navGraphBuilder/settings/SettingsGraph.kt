package com.kingfu.clok.navigation.navGraphBuilder.settings

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.kingfu.clok.components.topBar.LargeTopBar
import com.kingfu.clok.navigation.Screens
import com.kingfu.clok.settings.settingsScreen.SettingsScreen
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.settingsGraph(
    currentDestination: NavDestination?,
    navController: NavHostController,
    settingsViewModelStopwatch: SettingsViewModelStopwatch,
    settingsViewModelTimer: SettingsViewModelTimer
) {
    composable(
        route = Screens.Settings.route,
        enterTransition = {
            when(initialState.destination.route){
                Screens.Timer.route -> {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Up,
                        animationSpec = tween(durationMillis = 200)
                    )
                }
                Screens.Stopwatch.route -> {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Up,
                        animationSpec = tween(durationMillis = 200)
                    )
                }
                else ->{
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(durationMillis = 200)
                    )
                }
            }
        },
        exitTransition = {
            when(targetState.destination.route){
                Screens.Timer.route -> {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Down,
                        animationSpec = tween(durationMillis = 200)
                    )
                }
                Screens.Stopwatch.route -> {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Down,
                        animationSpec = tween(durationMillis = 200)
                    )
                }
                else ->{
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(durationMillis = 200)
                    )
                }
            }
        },
        popEnterTransition = {
            when(initialState.destination.route){
                Screens.Settings.route -> {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Down,
                        animationSpec = tween(durationMillis = 200)
                    )
                }
                else ->{
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(durationMillis = 200)
                    )
                }
            }
        },
        popExitTransition = {
            when(targetState.destination.route){
                Screens.Stopwatch.route -> {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Down,
                        animationSpec = tween(durationMillis = 200)
                    )
                }
                Screens.Timer.route -> {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Down,
                        animationSpec = tween(durationMillis = 200)
                    )
                }
                else -> {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(durationMillis = 200)
                    )
                }
            }
        }
    ) {
        val topBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

        Scaffold(
            modifier = Modifier.nestedScroll(connection = topBarScrollBehavior.nestedScrollConnection),
            containerColor = Transparent,
            topBar = {
                LargeTopBar(
                    topBarScrollBehavior = topBarScrollBehavior,
                    currentDestination = currentDestination,
                    navigateUp = { navController.navigateUp() },
                )
            },
            content = { paddingValue ->
                Box(modifier = Modifier.padding(paddingValues = paddingValue)) {
                    val vm: SettingsViewModelStopwatch = settingsViewModelStopwatch
                    SettingsScreen(
                        settingsViewModelStopwatch = vm,
                        settingsViewModelTimer = settingsViewModelTimer,
                        navigateToSettingsStopwatchBackgroundEffects = {
                            navController.navigate(
                                route = Screens.SettingsStopwatchLabelBackgroundEffects.route
                            )
                        },
                        navigateToSettingsStopwatchFontStyles = {
                            navController.navigate(
                                route = Screens.SettingsStopwatchFontStyles.route
                            )
                        },
                        navigateToSettingsStopwatchLabelStyles = {
                            navController.navigate(
                                route = Screens.SettingsStopwatchLabelStyles.route
                            )
                        },
                        navigateToSettingsTimerProgressBarStyles = {
                            navController.navigate(
                                route = Screens.SettingsTimerProgressBarStyles.route
                            )
                        },
                        navigateToSettingsTimerFontStyles = {
                            navController.navigate(
                                route = Screens.SettingsTimerFontStyles.route
                            )
                        },
                        navigateToSettingsTimerBackgroundEffects = {
                            navController.navigate(
                                route = Screens.SettingsTimerProgressBarBackgroundEffects.route
                            )
                        },
                        navigateToSettingsTimerScrollsHapticFeedback = {
                            navController.navigate(
                                route = Screens.SettingsTimerScrollsHapticFeedback.route
                            )
                        }
                    )
                }
            }
        )
    }
}