package com.kingfu.clok.timer.feature.timerProgressBarBackgroundEffects

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntSize
import com.kingfu.clok.settings.settingsScreen.settingsApp.settingsThemeScreen.ThemeType
import com.kingfu.clok.ui.util.SnowEffect


@Composable
fun TimerProgressBarBackgroundEffect(
    backgroundEffect: TimerProgressBarBackgroundEffectType,
    size: IntSize,
    theme: ThemeType
) {
    when (backgroundEffect) {
        TimerProgressBarBackgroundEffectType.None -> {}
        TimerProgressBarBackgroundEffectType.Snow -> {
            SnowEffect(
                size = size,
                startHeightMultiplier = 0.4f,
                endWidthMultiplier = 2f,
                theme = theme
            )
        }
    }
}