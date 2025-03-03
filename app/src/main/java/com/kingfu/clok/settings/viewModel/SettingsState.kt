package com.kingfu.clok.settings.viewModel

import com.kingfu.clok.core.ThemeType
import com.kingfu.clok.navigation.AppDestination
import java.io.Serializable

data class SettingsState(
    val theme: ThemeType = ThemeType.LIGHT,
    val isShowLabel: Boolean = true,
    val startRoute: AppDestination = AppDestination.STOPWATCH,
    val isLoaded: Boolean = false
) : Serializable