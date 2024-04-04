package com.kingfu.clok.navigation.bottomBar

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kingfu.clok.navigation.Screen
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.TextLabelSmall
import com.kingfu.clok.ui.theme.ThemeType
import com.kingfu.clok.ui.theme.themeBackgroundColor
import com.kingfu.clok.ui.theme.typography
import com.kingfu.clok.ui.util.NoRippleTheme
import com.kingfu.clok.ui.util.nonScaledSp


@Composable
fun BottomBar(
    currentRoute: String?,
    goToStopwatch: () -> Unit,
    goToTimer: () -> Unit,
    theme: ThemeType,
    screens: List<Screen>,
    floatAnimation: Animatable<Float, AnimationVector1D>
) {

    NavigationBar(containerColor = themeBackgroundColor(theme = theme)) {
        screens.forEachIndexed { _, screen ->
            val selected = currentRoute == screen.route
            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                NavigationBarItem(
                    alwaysShowLabel = false,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = colorScheme.primary,
                        unselectedIconColor = colorScheme.secondaryContainer,
                        selectedTextColor = colorScheme.primary,
                        unselectedTextColor = colorScheme.secondaryContainer,
                        indicatorColor = themeBackgroundColor(theme = theme)
                    ),
                    icon = {
                        Icon(
                            modifier = Modifier.graphicsLayer {
                                if (selected) { rotationZ = floatAnimation.value }
                            },
                            imageVector = if (selected) {
                                screen.filledIconId!!
                            } else {
                                screen.outlinedIconId!!
                            },
                            contentDescription = null
                        )
                    },
                    label = {
                        TextLabelSmall(
                            text = stringResource(id = screen.nameId),
                            fontSize = typography.labelSmall.fontSize.value.nonScaledSp
                        )
                    },
                    selected = selected,
                    onClick = {
                        when (screen.route) {
                            Screen.Stopwatch.route -> goToStopwatch()
                            Screen.Timer.route -> goToTimer()
                        }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun BottomBarPreview() {
    val theme = ThemeType.Dark
    ClokTheme(
        dynamicColor = true,
        theme = theme
    ) {
        BottomBar(
            currentRoute = Screen.Timer.route,
            goToStopwatch = { },
            goToTimer = { },
            theme = theme,
            screens = listOf(Screen.Stopwatch, Screen.Timer),
            floatAnimation = remember { Animatable(initialValue = 0f) }
        )
    }
}
