package com.kingfu.clok

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.kingfu.clok.navigation.AppNavHost
import com.kingfu.clok.settings.repository.SettingsPreferences
import com.kingfu.clok.settings.viewModel.SettingsFactory
import com.kingfu.clok.settings.viewModel.SettingsViewModel
import com.kingfu.clok.ui.theme.ClokTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()

        setContent {
            val context = LocalContext.current
            val settingsViewModel: SettingsViewModel = viewModel(
                factory = SettingsFactory(
                    settingsPreferences = SettingsPreferences.getInstance(context = context)
                )
            )

            val navController = rememberNavController()

            if (settingsViewModel.state.isLoaded) {
                ClokTheme(
                    theme = settingsViewModel.state.theme,
                    content = { AppNavHost(navController = navController) }
                )
            }
        }
    }
}