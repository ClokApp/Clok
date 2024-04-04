package com.kingfu.clok.navigation.navGraphBuilder.settings.settingsStopwatch.fontStyle

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
import com.kingfu.clok.ui.theme.ThemeType
import com.kingfu.clok.settings.settingsScreen.settingsStopwatchScreen.fontStyle.SettingsStopwatchTimeFontStyleScreen
import com.kingfu.clok.settings.viewModel.settingsViewModelStopwatch.SettingsViewModelStopwatch
import kotlin.math.abs

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.settingsStopwatchTimeFontStyleGraph(
    currentDestination: NavDestination?,
    navController: NavHostController,
    settingsViewModelStopwatch: SettingsViewModelStopwatch,
    theme: ThemeType
) {
    val slideAnimation = AnimatedContentTransitionScope.SlideDirection
    val tweenDuration = 200

    composable(
        route = Screen.SettingsStopwatchTimeFontStyle.route,
        enterTransition = {
            slideIntoContainer(
                towards = slideAnimation.Left,
                animationSpec = tween(durationMillis = tweenDuration)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = slideAnimation.Right,
                animationSpec = tween(durationMillis = tweenDuration)
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
                    SettingsStopwatchTimeFontStyleScreen(
                        updateStopwatchTimeFontStyle = { string: String ->
                            settingsViewModelStopwatch.updateStopwatchTimeFontStyle(string = string)
                        },
                        saveStopwatchTimeFontStyleSelectedOption = {
                            settingsViewModelStopwatch.saveStopwatchTimeFontStyle()
                        },
                        stopwatchTimeFontStyle = settingsViewModelStopwatch.state.stopwatchTimeFontStyle
                    )
                }
            }
        )
    }
}