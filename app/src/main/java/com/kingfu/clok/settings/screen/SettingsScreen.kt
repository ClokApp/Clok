package com.kingfu.clok.settings.screen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kingfu.clok.R
import com.kingfu.clok.core.ThemeType
import com.kingfu.clok.navigation.Screen
import com.kingfu.clok.navigation.TopBar
import com.kingfu.clok.settings.screen.components.FullScreen
import com.kingfu.clok.settings.screen.components.PrivacyPolicy
import com.kingfu.clok.settings.screen.components.Theme
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.TextBodyMedium

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    toggleDrawer: () -> Unit,
    isDrawerOpen: () -> Boolean,
    theme: ThemeType,
    setTheme: (ThemeType) -> Unit,
    route: String?,
    setIsFullScreen: (Boolean) -> Unit,
    isFullScreen: Boolean,
    isShowDialogTheme: Boolean,
    setIsShowDialogTheme: (Boolean) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    BackHandler(
        enabled = isDrawerOpen(),
        onBack = toggleDrawer
    )

    Scaffold(
        modifier = Modifier.nestedScroll(
            connection = scrollBehavior.nestedScrollConnection,
        ),
        containerColor = Transparent,
        topBar = {
            TopBar(
                toggleDrawer = toggleDrawer,
                route = route,
                scrollBehavior = scrollBehavior
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues = paddingValues)
                    .fillMaxSize(),
                contentAlignment = TopCenter
            ) {
                Column(
                    modifier = Modifier
                        .verticalScroll(state = rememberScrollState())
                        .widthIn(min = 300.dp, max = 500.dp),
                    horizontalAlignment = CenterHorizontally
                ) {
                    TextBodyMedium(
                        text = stringResource(id = R.string.app),
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth()
                    )

                    Theme(
                        theme = theme,
                        setTheme = setTheme,
                        isShowDialog = isShowDialogTheme,
                        setIsShowDialog = setIsShowDialogTheme
                    )

                    FullScreen(
                        isFullScreen = isFullScreen,
                        setIsFullScreen = setIsFullScreen
                    )

                    TextBodyMedium(
                        text = stringResource(id = R.string.about),
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth(),
                    )

                    PrivacyPolicy()
                }
            }
        }
    )


}

@Composable
fun SettingsScreenPreview(theme: ThemeType) {
    var themeType by remember { mutableStateOf(value = theme) }
    var isShowDialogTheme by remember { mutableStateOf(value = false) }
    var isFullScreen by remember { mutableStateOf(value = false) }


    ClokTheme(
        theme = theme,
        content = {
            Surface {
                SettingsScreen(
                    toggleDrawer = { },
                    isDrawerOpen = { false },
                    theme = themeType,
                    isShowDialogTheme = isShowDialogTheme,
                    setIsShowDialogTheme = { isShowDialogTheme = it },
                    setTheme = { themeType = it },
                    route = stringResource(id = Screen.Settings.nameId),
                    setIsFullScreen = { isFullScreen = it },
                    isFullScreen = isFullScreen
                )
            }
        }
    )
}

@Preview
@Composable
fun SettingsScreenPreviewDark() {
    SettingsScreenPreview(theme = ThemeType.DARK)
}

@Preview
@Composable
fun SettingsScreenPreviewLight() {
    SettingsScreenPreview(theme = ThemeType.LIGHT)
}






