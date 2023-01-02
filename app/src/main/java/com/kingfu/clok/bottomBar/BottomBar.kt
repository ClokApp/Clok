package com.kingfu.clok.bottomBar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.kingfu.clok.navigation.Screens
import com.kingfu.clok.navigation.items
import com.kingfu.clok.repository.preferencesDataStore.NavigationPreferences
import com.kingfu.clok.ui.theme.Black00
import com.kingfu.clok.util.NoRippleTheme
import com.kingfu.clok.util.customFontSize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun BottomBarNavigation(
    currentDestination: NavDestination?,
    navController: NavHostController,
    navigationPreferences: NavigationPreferences,
) {
    val currentRoute = currentDestination?.route

    AnimatedVisibility(
        visible = currentRoute == Screens.Stopwatch.route || currentRoute == Screens.Timer.route,
        enter = slideInVertically(
            initialOffsetY = { 100 },
            animationSpec = tween(
                durationMillis = 100,
                easing = LinearEasing
            )
        ),
        exit = slideOutVertically(
            targetOffsetY = { 100 },
            animationSpec = tween(
                durationMillis = 100,
                easing = LinearEasing
            )
        ),
        modifier = Modifier.background(Black00),
        content = {
            BottomNavigation(backgroundColor = Black00) {
                for (screen in 0 until 2) {
                    val selected =
                        currentDestination?.hierarchy?.any { it.route == items[screen].route } == true
                    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                        BottomNavigationItem(
                            selectedContentColor = Color.White,
                            unselectedContentColor = Color.DarkGray,
                            icon = {
                                Icon(
                                    painterResource(if (selected) items[screen].filledIconId else items[screen].outlinedIconId),
                                    contentDescription = null
                                )
                            },
                            label = {
                                Text(
                                    items[screen].name,
                                    fontSize = customFontSize(textUnit = 10.sp),
                                    fontFamily = FontFamily.Default,
                                    fontWeight = FontWeight.Normal
                                )
                            },
                            selected = selected,
                            onClick = {
                                navController.navigate(items[screen].route)
                                {
                                    CoroutineScope(Dispatchers.Main).launch(Dispatchers.Default) {
                                        // saving current destination when navigating
                                        navigationPreferences.setStartDestination(items[screen].route)
                                    }
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    // on the back stack as users select items
                                    popUpTo(navController.graph.findStartDestination().id) {
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
    )
}