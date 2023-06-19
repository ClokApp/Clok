package com.kingfu.clok.components.topBar

import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.navigation.NavController
import com.kingfu.clok.navigation.Screens
import com.kingfu.clok.ui.theme.Black00
import com.kingfu.clok.variable.Variable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavController,
) {
    val currentRoute by rememberSaveable{ mutableStateOf(navController.currentDestination?.route) }

    TopAppBar(
        title = {},
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Black00),
        navigationIcon =
        {
            IconButton(
                enabled = navController.previousBackStackEntry != null && currentRoute != Screens.Stopwatch.route && currentRoute != Screens.Timer.route,
                onClick = { navController.navigateUp() },
                modifier = Modifier
                    .alpha(
                        if (
                            navController.previousBackStackEntry != null &&
                            currentRoute != Screens.Stopwatch.route &&
                            currentRoute != Screens.Timer.route
                        ) 1f else 0f
                    )
                    .wrapContentHeight(align = Alignment.Bottom),

                ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBackIosNew,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        actions = {
            IconButton(
                enabled = currentRoute == Screens.Timer.route || currentRoute == Screens.Stopwatch.route,
                onClick = { Variable.showMenu = !Variable.showMenu },
                modifier = Modifier.alpha(alpha = if (currentRoute == Screens.Timer.route || currentRoute == Screens.Stopwatch.route) 1f else 0f)
            ) {
                Icon(
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
            Menu(navController = navController)
        }
    )
}