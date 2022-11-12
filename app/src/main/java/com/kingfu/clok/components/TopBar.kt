package com.kingfu.clok.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
    TopAppBar(
        title = {
            Text(
                text =
                when (currentDestination?.route) {
                    Screens.Settings.route -> {
                        Screens.Settings.name
                    }
                    Screens.SettingsStopwatchLabelStyle.route -> {
                        Screens.SettingsStopwatchLabelStyle.name
                    }
                    Screens.SettingsTimerProgressBarStyle.route -> {
                        Screens.SettingsTimerProgressBarStyle.name
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
            if (navController.previousBackStackEntry != null && navController.currentDestination?.route != Screens.Stopwatch.route &&
                currentDestination?.route != Screens.Timer.route
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
            if (navController.currentDestination?.route == Screens.Stopwatch.route ||
                navController.currentDestination?.route == Screens.Timer.route
            ) {
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

@Composable
fun ShowMenu(navController: NavController) {
    MaterialTheme(
        colors = MaterialTheme.colors.copy(surface = Color.Black.copy(0.4f)),
        shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(16.dp))
    ) {
        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    navController.navigate(Screens.Settings.route)
                    {
                        launchSingleTop = true
                        restoreState = true
                    }
                    showMenu = false
                },
            ) {
                Text(
                    text = "Settings",
                    fontSize = customFontSize(textUnit = 16.sp),
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Normal
                )
            }

            DropdownMenuItem(
                onClick = {
                    navController.navigate(Screens.BugReport.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                    showMenu = false
                },
            ) {
                Text(
                    text = "Bug report",
                    fontSize = customFontSize(textUnit = 16.sp),
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}