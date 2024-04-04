package com.kingfu.clok.navigation.topBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import com.kingfu.clok.navigation.Screen
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.ThemeType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    goToSettingsScreen: () -> Unit,
    goToBugReportScreen: () -> Unit,
    currentRoute: String?,
    isShowMenu: Boolean,
    toggleIsShowMenu: () -> Unit,
    animation: Float
) {

    TopAppBar(
        title = { },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Transparent),
        actions = {
            IconButton(
                enabled = currentRoute == Screen.Timer.route || currentRoute == Screen.Stopwatch.route,
                onClick = { toggleIsShowMenu() },
                modifier = Modifier.alpha(
                    alpha = if (
                        currentRoute == Screen.Timer.route ||
                        currentRoute == Screen.Stopwatch.route
                    ) 1f else 0f
                )
            ) {
                Icon(
                    modifier = Modifier.graphicsLayer {
                        rotationZ = animation
                    },
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = null,
                    tint = colorScheme.primary,
                )
            }
            Menu(
                goToSettingsScreen = { goToSettingsScreen() },
                goToBugReportScreen = { goToBugReportScreen() },
                isShowMenu = isShowMenu,
                toggleIsShowMenu = toggleIsShowMenu
            )
        }
    )
}


@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    ClokTheme(theme = ThemeType.Dark) {
        TopBar(
            goToSettingsScreen = { },
            goToBugReportScreen = { },
            currentRoute = Screen.Stopwatch.route,
            isShowMenu = true,
            toggleIsShowMenu = { },
            animation = 0f
        )
    }
}
