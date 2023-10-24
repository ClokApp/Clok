package com.kingfu.clok.settings.viewModel.settingsViewModel

import com.kingfu.clok.settings.settingsScreen.settingsApp.settingsThemeScreen.ThemeType
import java.io.Serializable

data class SettingsViewModelState(
    val appTheme: ThemeType = ThemeType.System
) : Serializable