package com.kingfu.clok

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kingfu.clok.navigation.NavigationDrawer
import com.kingfu.clok.settings.repository.SettingsPreferences
import com.kingfu.clok.settings.viewModel.settingsViewModel.SettingsFactory
import com.kingfu.clok.settings.viewModel.settingsViewModel.SettingsViewModel
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.util.NotificationPermissionForAndroid13


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            val context = LocalContext.current
            val settingsViewModel: SettingsViewModel = viewModel(
                factory = SettingsFactory(
                    settingsPreferences = SettingsPreferences.getInstance(context = context)
                )
            )

            ClokTheme(
                theme = settingsViewModel.state.theme,
                isFullScreen = settingsViewModel.state.isFullScreen,
                content = {
                    NotificationPermissionForAndroid13()
                    Surface { NavigationDrawer() }
                }
            )
        }
    }
}