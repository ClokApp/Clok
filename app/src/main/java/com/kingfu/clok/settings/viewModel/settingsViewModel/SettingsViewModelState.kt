package com.kingfu.clok.settings.viewModel.settingsViewModel

import com.kingfu.clok.ui.theme.ThemeType
import java.io.Serializable

data class SettingsViewModelState(
    val theme: ThemeType = ThemeType.System
) : Serializable