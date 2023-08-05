package com.kingfu.clok.navigation.navGraphBuilder.settings.settingsTimer

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
import com.kingfu.clok.settings.settingsScreen.settingsTimerScreen.fontStyle.SettingsTimerFontStylesScreen
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.settingsTimerFontStylesGraph(
    currentDestination: NavDestination?,
    navController: NavHostController,
    settingsViewModelTimer: SettingsViewModelTimer
){
    composable(
        route = Screens.SettingsTimerFontStyles.route,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(durationMillis = 200)
            )
        },
        exitTransition = {
            when (targetState.destination.route) {
                Screens.Settings.route -> {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
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
        },
        popEnterTransition = {
            when (initialState.destination.route) {
                Screens.Settings.route -> {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(durationMillis = 200)
                    )
                }
                else -> {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(durationMillis = 200)
                    )
                }
            }
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(durationMillis = 200)
            )
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
                    SettingsTimerFontStylesScreen(
                        vm = settingsViewModelTimer,
                        navigateToSettingsTimerSelectedFontStyle = {
                            navController.navigate(
                                route = Screens.SettingsTimerSelectedFontStyle.route
                            )
                        }
                    )
                }
            }
        )
    }
}