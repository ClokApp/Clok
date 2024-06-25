package com.kingfu.clok.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kingfu.clok.core.ThemeType
import com.kingfu.clok.settings.repository.SettingsPreferences
import com.kingfu.clok.settings.viewModel.settingsViewModel.SettingsFactory
import com.kingfu.clok.settings.viewModel.settingsViewModel.SettingsViewModel
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.TextBodyLarge

@Composable
fun DrawerContent(
    modifier: Modifier = Modifier,
    route: String?,
    navController: NavHostController,
    toggleDrawer: () -> Unit,
) {
    val screens = listOf(
        Screen.Stopwatch,
        Screen.Timer,
        Screen.Settings
    )

    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val settingsViewModel: SettingsViewModel = viewModel(
        factory = SettingsFactory(settingsPreferences = SettingsPreferences.getInstance(context = context))
    )

    Column(
        modifier = modifier
            .fillMaxHeight()
            .background(color = Transparent)
            .verticalScroll(state = scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {

        screens.forEach { screen ->

            val selected = route == screen.route
            val tint by animateColorAsState(
                targetValue = if (selected) colorScheme.primary else colorScheme.inverseSurface,
                label = ""
            )

            NavigationDrawerItem(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(percent = 0),
                icon = {
                    val icon = if (selected) screen.filledIcon else screen.outlinedIcon
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = tint
                    )
                },
                label = {
                    Row {
                        TextBodyLarge(
                            text = stringResource(id = screen.nameId),
                            color = tint,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                },
                selected = selected,
                onClick = {
                    navController.navigate(route = screen.route) {
                        if (screen.route == Screen.Stopwatch.route || screen.route == Screen.Timer.route) {
                            settingsViewModel.saveStartRoute(string = screen.route)
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    toggleDrawer()
                },
                colors = NavigationDrawerItemDefaults.colors(
                    selectedTextColor = colorScheme.primary,
                    unselectedContainerColor = Transparent,
                    selectedContainerColor = Transparent
                )
            )
        }
    }
}

@Composable
fun DrawerContentPreview(theme: ThemeType) {
    ClokTheme(
        theme = theme,
        content = {
            Surface {
                DrawerContent(
                    route = Screen.Stopwatch.route,
                    navController = rememberNavController(),
                    toggleDrawer = { }
                )
            }
        }
    )
}

@Preview
@Composable
fun DrawerContentPreviewDark() {
    DrawerContentPreview(theme = ThemeType.DARK)
}

@Preview
@Composable
fun DrawerContentPreviewLight() {
    DrawerContentPreview(theme = ThemeType.LIGHT)
}