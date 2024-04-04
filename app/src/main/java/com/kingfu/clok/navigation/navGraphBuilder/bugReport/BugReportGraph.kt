package com.kingfu.clok.navigation.navGraphBuilder.bugReport

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.kingfu.clok.bugReport.BugReportScreen
import com.kingfu.clok.navigation.Screen
import com.kingfu.clok.navigation.topBar.CustomTopBar
import com.kingfu.clok.navigation.topBar.LargeTopBar
import com.kingfu.clok.ui.theme.ThemeType
import kotlin.math.abs

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.bugReportGraph(
    currentDestination: NavDestination?,
    navController: NavHostController,
    theme: ThemeType
) {
    val slideAnimation = AnimatedContentTransitionScope.SlideDirection
    val tweenDuration = 200
    composable(
        route = Screen.BugReport.route,
        enterTransition = {
            slideIntoContainer(
                towards = slideAnimation.Up,
                animationSpec = tween(durationMillis = tweenDuration)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = slideAnimation.Down,
                animationSpec = tween(durationMillis = tweenDuration)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = slideAnimation.Up,
                animationSpec = tween(durationMillis = tweenDuration)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = slideAnimation.Down,
                animationSpec = tween(durationMillis = tweenDuration)
            )
        }
    ) {
        val exitUntilCollapsed = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
        val exitUntilCollapsedFraction = exitUntilCollapsed.state.collapsedFraction
        val pinned = TopAppBarDefaults.pinnedScrollBehavior()

        Scaffold(
            modifier = Modifier.nestedScroll(connection = exitUntilCollapsed.nestedScrollConnection),
            containerColor = Color.Transparent,
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
                        pinned = pinned,
                        exitUntilCollapsedFraction = exitUntilCollapsedFraction,
                        currentRoute = currentDestination?.route,
                        navigateUp = { navController.navigateUp() },
                        theme = theme
                    )
                }
            },
            content = { paddingValue ->

                val scrollState = rememberScrollState()

                Column(
                    modifier = Modifier
                        .verticalScroll(state = scrollState)
                        .statusBarsPadding()
                        .padding(top = 180.dp * abs(x = 1 - exitUntilCollapsedFraction / 1.5f))
                ) {
                    BugReportScreen()
                }
            }
        )
    }
}