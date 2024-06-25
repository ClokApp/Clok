package com.kingfu.clok.settings.viewModel.settingsViewModel

import com.kingfu.clok.core.ThemeType
import com.kingfu.clok.navigation.Screen
import java.io.Serializable

data class SettingsState(
    val theme: ThemeType = ThemeType.SYSTEM,
    val isShowLabel: Boolean = true,
    val isShowDialog: Boolean = false,
    val isFullScreen: Boolean = false,
    val isShowDialogTheme: Boolean = false,
    val startRoute: String = Screen.Stopwatch.route,
    val isLoaded: Boolean = false
) : Serializable