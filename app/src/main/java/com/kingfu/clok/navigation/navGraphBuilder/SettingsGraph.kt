package com.kingfu.clok.navigation.navGraphBuilder

import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.kingfu.clok.core.ThemeType
import com.kingfu.clok.navigation.AppDestination
import com.kingfu.clok.navigation.Dialog
import com.kingfu.clok.navigation.Screen
import com.kingfu.clok.navigation.topBar.TopBarBack
import com.kingfu.clok.settings.screen.SettingsScreen
import com.kingfu.clok.settings.viewModel.SettingsViewModel

fun NavGraphBuilder.settingsGraph(
    vm: SettingsViewModel,
    navController: NavHostController
) {
    composable<Screen.Settings> {
        SettingsScreen(
            state = vm.state,
            topBar = {
                TopBarBack(
                    title = stringResource(id = AppDestination.SETTINGS.label),
                    onClick = { navController.navigateUp() },
                )
            },
            goToDialogTheme = { theme: ThemeType ->
                navController.navigate(
                    route = Dialog.Theme(theme = theme)
                ) {
                    launchSingleTop = true
                    restoreState = true
                }
            },
        )
    }
}