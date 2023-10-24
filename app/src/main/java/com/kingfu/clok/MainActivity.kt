package com.kingfu.clok

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kingfu.clok.navigation.AppScaffold
import com.kingfu.clok.repository.preferencesDataStore.SettingsPreferences
import com.kingfu.clok.settings.viewModel.settingsViewModel.SettingsFactory
import com.kingfu.clok.settings.viewModel.settingsViewModel.SettingsViewModel
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.themeBackgroundColor
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
            val theme = settingsViewModel.state.appTheme

            ClokTheme(theme = theme) {
                NotificationPermissionForAndroid13()
                Surface(color = themeBackgroundColor(theme = theme)) {
                    AppScaffold(
                        settingsViewModel = settingsViewModel,
                        theme = theme
                    )
                }
            }
        }
    }
}