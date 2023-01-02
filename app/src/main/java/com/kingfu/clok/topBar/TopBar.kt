package com.kingfu.clok.topBar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.kingfu.clok.R
import com.kingfu.clok.navigation.Screens
import com.kingfu.clok.ui.theme.Black00
import com.kingfu.clok.util.customFontSize
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
                    Screens.SettingsStopwatchLabelStyle.route -> {
                        Screens.SettingsStopwatchLabelStyle.name
                    }
                    Screens.SettingsTimerProgressBarStyle.route -> {
                        Screens.SettingsTimerProgressBarStyle.name
                    }
                    Screens.SettingsStopwatchBackgroundEffects.route ->{
                        "Stopwatch - ${Screens.SettingsStopwatchBackgroundEffects.name}"
                    }
                    Screens.SettingsTimerBackgroundEffects.route ->{
                       "Timer - ${Screens.SettingsTimerBackgroundEffects.name}"
                    }
                    Screens.BugReport.route -> {
                        Screens.BugReport.name
                    }
                    else -> {
                        ""
                    }
                },
                fontSize = customFontSize(textUnit = 20.sp),
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal
            )
        },
        backgroundColor = Black00,
        navigationIcon =
        {
            if (navController.previousBackStackEntry != null && currentRoute != Screens.Stopwatch.route
                && currentRoute != Screens.Timer.route
            ) {
                run {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            painterResource(id = R.drawable.ic_arrow_back_ios_new_24),
                            contentDescription = "Back"
                        )
                    }
                }
            }
        },
        actions = {
            if (currentRoute == Screens.Timer.route || currentRoute == Screens.Stopwatch.route) {
                IconButton(onClick = { showMenu = !showMenu }) {
                    Icon(
                        Icons.Filled.MoreVert,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
                ShowMenu(navController)
            }
        }
    )

}

