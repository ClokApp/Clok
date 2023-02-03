package com.kingfu.clok.components.topBar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.kingfu.clok.navigation.Screens
import com.kingfu.clok.ui.theme.Black00
import com.kingfu.clok.variable.Variable.settingsStopwatchSelectedFontStyleTopBarName
import com.kingfu.clok.variable.Variable.settingsTimerSelectedFontStyleTopBarName
import com.kingfu.clok.variable.Variable.showMenu


@Composable
fun TopBar(
    navController: NavController,
    currentDestination: NavDestination?,
) {
    val currentRoute = currentDestination?.route

    TopAppBar(
        title = {
            Text(
                text =
                when (currentRoute) {
                    Screens.Settings.route -> {
                        Screens.Settings.name
                    }
                    Screens.SettingsStopwatchLabelStyles.route -> {
                        Screens.SettingsStopwatchLabelStyles.name
                    }
                    Screens.SettingsTimerProgressBarStyles.route -> {
                        Screens.SettingsTimerProgressBarStyles.name
                    }
                    Screens.SettingsStopwatchBackgroundEffects.route -> {
                        Screens.SettingsStopwatchBackgroundEffects.name
                    }
                    Screens.SettingsTimerBackgroundEffects.route -> {
                        Screens.SettingsTimerBackgroundEffects.name
                    }
                    Screens.BugReport.route -> {
                        Screens.BugReport.name
                    }
                    Screens.SettingsTimerFontStyles.route->{
                        "${Screens.Timer.name} ${Screens.SettingsTimerFontStyles.name}"
                    }
                    Screens.SettingsTimerSelectedFontStyle.route->{
                        settingsTimerSelectedFontStyleTopBarName
                    }
                    Screens.SettingsStopwatchFontStyles.route->{
                        "${Screens.Stopwatch.name} ${Screens.SettingsStopwatchFontStyles.name}"
                    }
                    Screens.SettingsStopwatchSelectedFontStyle.route->{
                        settingsStopwatchSelectedFontStyleTopBarName
                    }
                    else -> {
                        ""
                    }
                },
                fontSize = 20.sp,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal
            )
        },
        backgroundColor = Black00,
        contentColor = Color.White,
        elevation = 0.dp,
        navigationIcon =
        {
            if (navController.previousBackStackEntry != null && currentRoute != Screens.Stopwatch.route
                && currentRoute != Screens.Timer.route
            ) {
                run {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBackIosNew,
                            contentDescription = null
                        )
                    }
                }
            }
        },
        actions = {
            if (currentRoute == Screens.Timer.route || currentRoute == Screens.Stopwatch.route) {
                IconButton(onClick = { showMenu = !showMenu }) {
                    Icon(
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
                ShowMenu(navController)
            }
        }
    )

}

