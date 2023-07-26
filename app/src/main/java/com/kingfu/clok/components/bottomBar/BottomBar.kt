package com.kingfu.clok.components.bottomBar

import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import com.kingfu.clok.navigation.Screens
import com.kingfu.clok.ui.theme.Black00
import com.kingfu.clok.util.NoRippleTheme


@Composable
fun BottomBar(
    currentDestination: NavDestination?,
    navigateToStopwatch: () -> Unit,
    navigateToTimer: () -> Unit
) {
    val currentRoute = currentDestination?.route
    val items = listOf(Screens.Stopwatch, Screens.Timer)

    NavigationBar(
        containerColor = Black00,
        contentColor = White
    ) {
        for (screen in items.indices) {
            val selected = currentRoute == items[screen].route
            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                NavigationBarItem(
                    alwaysShowLabel = true,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = colorScheme.primary,
                        unselectedIconColor = colorScheme.secondary,
                        selectedTextColor = colorScheme.primary,
                        unselectedTextColor = colorScheme.secondary,
                        indicatorColor = Transparent
                    ),
                    icon = {
                        Icon(
                            imageVector = if (selected){
                                items[screen].filledIconId!!
                            } else{
                                items[screen].outlinedIconId!!
                            },
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(
                            text = items[screen].name,
                            fontSize = 10.sp,
                            style = TextStyle()
                        )
                    },
                    selected = selected,
                    onClick = {
                        when(items[screen].route){
                            Screens.Stopwatch.route -> navigateToStopwatch()
                            Screens.Timer.route -> navigateToTimer()
                        }
                    }
                )
            }
        }
    }
}
