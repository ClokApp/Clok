package com.kingfu.clok.navigation.navGraphBuilder.settings.settingsTimer

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.kingfu.clok.navigation.Screen
import com.kingfu.clok.navigation.topBar.CustomTopBar
import com.kingfu.clok.navigation.topBar.LargeTopBar
import com.kingfu.clok.settings.settingsScreen.settingsApp.settingsThemeScreen.ThemeType
import com.kingfu.clok.settings.settingsScreen.settingsTimerScreen.SettingsTimerScrollsHapticFeedbackScreen
import com.kingfu.clok.settings.viewModel.settingsViewModelTimer.SettingsViewModelTimer
import kotlin.math.abs

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.settingsTimerScrollsHapticFeedbackGraph(
    currentDestination: NavDestination?,
    navController: NavHostController,
    settingsViewModelTimer: SettingsViewModelTimer,
    theme: ThemeType
) {
    composable(route = Screen.SettingsTimerScrollsHapticFeedback.route,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(durationMillis = 200)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(durationMillis = 200)
            )
        }
    ) {
        val exitUntilCollapsed = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
        val exitUntilCollapsedFraction = exitUntilCollapsed.state.collapsedFraction
        val pinned = TopAppBarDefaults.pinnedScrollBehavior()

        Scaffold(
            modifier = Modifier.nestedScroll(connection = exitUntilCollapsed.nestedScrollConnection),
            containerColor = Transparent,
            topBar = {
                if (exitUntilCollapsedFraction <= 0.545) {
                    LargeTopBar(
                        exitUntilCollapsed = exitUntilCollapsed,
                        currentRoute = currentDestination?.route,
                        navigateUp = { navController.navigateUp() },
                        theme = theme
                    )
                } else {
                    CustomTopBar(
                        exitUntilCollapsedFraction = exitUntilCollapsedFraction,
                        currentRoute = currentDestination?.route,
                        navigateUp = { navController.navigateUp() },
                        pinned = pinned,
                        theme = theme
                    )
                }
            },
            content = { paddingValues ->
                val scrollState = rememberScrollState()

                Column(
                    modifier = Modifier
                        .verticalScroll(state = scrollState)
                        .statusBarsPadding()
                        .padding(top = 180.dp * abs(x = 1 - exitUntilCollapsedFraction / 1.5f))
                ) {
                    SettingsTimerScrollsHapticFeedbackScreen(
                        updateScrollHapticFeedback = { string: String ->
                            settingsViewModelTimer.updateScrollHapticFeedback(string = string)
                        },
                        saveScrollsHapticFeedback = { settingsViewModelTimer.saveScrollsHapticFeedback() },
                        timerScrollsHapticFeedback = settingsViewModelTimer.state.timerScrollsHapticFeedback
                    )
                }
            }
        )
    }
}