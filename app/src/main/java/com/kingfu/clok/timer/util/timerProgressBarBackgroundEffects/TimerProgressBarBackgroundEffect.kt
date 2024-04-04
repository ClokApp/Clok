package com.kingfu.clok.timer.util.timerProgressBarBackgroundEffects

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntSize
import com.kingfu.clok.ui.theme.ThemeType
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