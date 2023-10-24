package com.kingfu.clok.stopwatch.feature.labelBackgroundEffects

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntSize
import com.kingfu.clok.settings.settingsScreen.settingsApp.settingsThemeScreen.ThemeType
import com.kingfu.clok.ui.util.SnowEffect

@Composable
fun StopwatchLabelBackgroundEffect(
    labelBackgroundEffect: StopwatchLabelBackgroundEffectType,
    size: IntSize,
    theme: ThemeType
) {
    when (labelBackgroundEffect) {
        StopwatchLabelBackgroundEffectType.Snow -> SnowEffect(
            size = size,
            startHeightMultiplier = 0.4f,
            endHeightMultiplier = 0.5f,
            endWidthMultiplier = 2f,
            theme = theme
        )
        StopwatchLabelBackgroundEffectType.None -> { }
    }
}