package com.kingfu.clok.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material.icons.rounded.HourglassEmpty
import androidx.compose.material.icons.rounded.HourglassFull
import androidx.compose.ui.graphics.vector.ImageVector
import com.kingfu.clok.R


sealed class Screen(
    val route: String,
    @StringRes val nameId: Int,
    val filledIcon: ImageVector,
    val outlinedIcon: ImageVector
) {
    data object Settings : Screen(
        route = "settings",
        nameId = R.string.settings_name_id,
        filledIcon = Icons.Filled.Settings,
        outlinedIcon = Icons.Outlined.Settings
    )

    data object Stopwatch : Screen(
        route = "stopwatch",
        nameId = R.string.stopwatch_name_id,
        filledIcon = Icons.Filled.Timer,
        outlinedIcon = Icons.Outlined.Timer
    )

    data object Timer : Screen(
        route = "timer",
        nameId = R.string.timer_name_id,
        filledIcon = Icons.Rounded.HourglassFull,
        outlinedIcon = Icons.Rounded.HourglassEmpty
    )
}














