package com.kingfu.clok.navigation

import com.kingfu.clok.core.ThemeType
import kotlinx.serialization.Serializable


sealed interface Screen {
    @Serializable
    data object Stopwatch : Screen

    @Serializable
    data object Timer : Screen

    @Serializable
    data object Settings : Screen
}

sealed interface Dialog {
    @Serializable
    data class Theme(
        val theme: ThemeType
    )
}














