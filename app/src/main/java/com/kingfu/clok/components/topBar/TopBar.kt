package com.kingfu.clok.components.topBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.navigation.NavDestination
import com.kingfu.clok.navigation.Screens
import com.kingfu.clok.ui.theme.Black00
import com.kingfu.clok.variable.Variable.showMenu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    currentDestination: NavDestination?,
    navigateToSettingsScreen: () -> Unit,
    navigateToBugReportScreen: () -> Unit
) {
    val currentRoute by rememberSaveable { mutableStateOf(value = currentDestination?.route) }

    TopAppBar(
        title = {},
        colors = topAppBarColors(containerColor = Black00),
        actions = {
            IconButton(
                enabled = currentRoute == Screens.Timer.route || currentRoute == Screens.Stopwatch.route,
                onClick = { showMenu = !showMenu },
                modifier = Modifier.alpha(
                    alpha = if (
                        currentRoute == Screens.Timer.route ||
                        currentRoute == Screens.Stopwatch.route
                    ) 1f else 0f
                )
            ) {
                Icon(
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = null,
                    tint = colorScheme.primary,
                )
            }
            Menu(
                navigateToSettingsScreen = { navigateToSettingsScreen() },
                navigateToBugReportScreen = { navigateToBugReportScreen() }
            )
        }
    )
}