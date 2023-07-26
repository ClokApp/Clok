package com.kingfu.clok.navigation.navGraphBuilder.settings.settingsTimer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.kingfu.clok.components.topBar.LargeTopBar
import com.kingfu.clok.navigation.Screens
import com.kingfu.clok.settings.settingsScreen.settingsTimerScreen.fontStyle.SettingsTimerSelectedFontStyleScreen
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer


@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.settingsTimerSelectedFontStyleGraph(
    currentDestination: NavDestination?,
    navController: NavHostController,
    settingsViewModelTimer: SettingsViewModelTimer
){
    composable(route = Screens.SettingsTimerSelectedFontStyle.route) {
        val topBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

        Scaffold(
            modifier = Modifier.nestedScroll(connection = topBarScrollBehavior.nestedScrollConnection),
            containerColor = Color.Transparent,
            topBar = {
                LargeTopBar(
                    topBarScrollBehavior = topBarScrollBehavior,
                    currentDestination = currentDestination,
                    navigateUp = { navController.navigateUp() },
                )
            },
            content = { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues = paddingValues)) {
                    SettingsTimerSelectedFontStyleScreen(vm = settingsViewModelTimer)
                }
            }
        )
    }
}