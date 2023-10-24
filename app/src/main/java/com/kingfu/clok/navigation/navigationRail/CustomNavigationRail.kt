package com.kingfu.clok.navigation.navigationRail

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kingfu.clok.navigation.Screen
import com.kingfu.clok.navigation.topBar.Menu
import com.kingfu.clok.settings.settingsScreen.settingsApp.settingsThemeScreen.ThemeType
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.TextLabelSmall
import com.kingfu.clok.ui.theme.themeBackgroundColor
import com.kingfu.clok.ui.theme.typography
import com.kingfu.clok.ui.util.NoRippleTheme
import com.kingfu.clok.ui.util.nonScaledSp

@Composable
fun CustomNavigationRail(
    currentRoute: String?,
    goToStopwatch: () -> Unit,
    goToTimer: () -> Unit,
    goToSettingsScreen: () -> Unit,
    goToBugReportScreen: () -> Unit,
    theme: ThemeType,
    isShowMenu: Boolean,
    toggleIsShowMenu: () -> Unit,
    screens: List<Screen>,
    headerAnimation: Float,
    floatAnimation: Animatable<Float, AnimationVector1D>
) {

    NavigationRail(
        containerColor = themeBackgroundColor(theme = theme),
        header = {
            IconButton(onClick = { toggleIsShowMenu() }) {
                Icon(
                    modifier = Modifier.graphicsLayer {
                        rotationZ = headerAnimation
                    },
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = null,
                    tint = colorScheme.primary,
                )
                Menu(
                    goToSettingsScreen = { goToSettingsScreen() },
                    goToBugReportScreen = { goToBugReportScreen() },
                    isShowMenu = isShowMenu,
                    toggleIsShowMenu = { toggleIsShowMenu() }
                )
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(space = 12.dp, alignment = Alignment.Bottom)
        ) {

            for (screen in screens.indices) {
                val selected = currentRoute == screens[screen].route
                CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                    NavigationRailItem(
                        alwaysShowLabel = false,
                        colors = NavigationRailItemDefaults.colors(
                            selectedIconColor = colorScheme.primary,
                            unselectedIconColor = if (isSystemInDarkTheme()) {
                                colorScheme.secondaryContainer
                            } else {
                                colorScheme.primary.copy(alpha = 0.5f)
                            },
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
                                    screens[screen].filledIconId!!
                                } else {
                                    screens[screen].outlinedIconId!!
                                },
                                contentDescription = null
                            )
                        },
                        label = {
                            TextLabelSmall(
                                text = stringResource(id = screens[screen].nameId),
                                overflow = Ellipsis,
                                fontSize = typography.labelSmall.fontSize.value.nonScaledSp
                            )
                        },
                        selected = selected,
                        onClick = {
                            when (screens[screen].route) {
                                Screen.Stopwatch.route -> goToStopwatch()
                                Screen.Timer.route -> goToTimer()
                            }
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomNavigationRailPreview() {
    val theme = ThemeType.Dark
    ClokTheme(
        dynamicColor = true,
        theme = theme
    ) {
        CustomNavigationRail(
            currentRoute = Screen.Timer.route,
            goToStopwatch = { },
            goToTimer = { },
            goToSettingsScreen = { },
            goToBugReportScreen = { },
            theme = theme,
            isShowMenu = false,
            toggleIsShowMenu = { },
            screens = listOf(Screen.Stopwatch, Screen.Timer),
            headerAnimation = 0f,
            floatAnimation = remember { Animatable(initialValue = 0f) }
        )
    }
}

