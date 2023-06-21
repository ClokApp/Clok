package com.kingfu.clok.components.bottomBar

import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.kingfu.clok.navigation.Screens
import com.kingfu.clok.repository.preferencesDataStore.NavigationPreferences
import com.kingfu.clok.ui.theme.Black00
import com.kingfu.clok.util.NoRippleTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun BottomBar(
    currentDestination: NavDestination?,
    navController: NavHostController,
    navigationPreferences: NavigationPreferences,
) {
    val currentRoute = currentDestination?.route
    val items = listOf(Screens.Stopwatch, Screens.Timer)

    NavigationBar(
        containerColor = Black00,
        contentColor = Color.White
    ) {
        for (screen in items.indices) {
            val selected = currentRoute == items[screen].route
            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                NavigationBarItem(
                    alwaysShowLabel = true,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.secondary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedTextColor = MaterialTheme.colorScheme.secondary,
                        indicatorColor = Color.Transparent
                    ),
                    icon = {
                        Icon(
                            imageVector = if (selected) items[screen].filledIconId!! else items[screen].outlinedIconId!!,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(
                            text = items[screen].name,
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Normal
                        )
                    },
                    selected = selected,
                    onClick = {
                        navController.navigate(route = items[screen].route)
                        {
                            CoroutineScope(context = Dispatchers.IO).launch {
                                // saving current destination when navigating
                                navigationPreferences.setStartDestination(string = items[screen].route)
                            }
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(id = navController.graph.findStartDestination().id) {
                                saveState = true
                            }

                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}
