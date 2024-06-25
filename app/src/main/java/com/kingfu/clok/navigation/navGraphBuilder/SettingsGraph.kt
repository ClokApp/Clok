package com.kingfu.clok.navigation.navGraphBuilder

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kingfu.clok.navigation.Screen
import com.kingfu.clok.settings.screen.SettingsScreen
import com.kingfu.clok.settings.viewModel.settingsViewModel.SettingsViewModel

fun NavGraphBuilder.settingsGraph(
    route: String?,
    toggleDrawer: () -> Unit,
    isDrawerOpen: () -> Boolean,
    vm: SettingsViewModel,
) {

    composable(route = Screen.Settings.route) {
        SettingsScreen(
            isDrawerOpen = isDrawerOpen,
            toggleDrawer = toggleDrawer,
            theme = vm.state.theme,
            setTheme = { vm.setTheme(it) },
            route = route,
            setIsFullScreen = { vm.setIsFullScreen(it) },
            isFullScreen = vm.state.isFullScreen,
            isShowDialogTheme = vm.state.isShowDialogTheme,
            setIsShowDialogTheme = { vm.setIsShowDialogTheme(it) }
        )
    }
}