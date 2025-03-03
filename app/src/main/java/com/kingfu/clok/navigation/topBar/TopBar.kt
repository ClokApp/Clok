package com.kingfu.clok.navigation.topBar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kingfu.clok.navigation.Screen
import com.kingfu.clok.ui.theme.ClokThemePreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navController: NavController
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = { },
        actions = {
            TopBarMenu(
                goToSettings = {
                    navController.navigate(route = Screen.Settings) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    )
}

@PreviewLightDark
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBarPreview() {
    ClokThemePreview {
        TopBar(
            scrollBehavior = null,
            navController = rememberNavController()
        )
    }
}
