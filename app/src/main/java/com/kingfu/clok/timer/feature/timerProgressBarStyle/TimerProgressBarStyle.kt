package com.kingfu.clok.timer.feature.timerProgressBarStyle

import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun TimerProgressBarStyle(
    progressBarStyleType: TimerProgressBarStyleType,
): List<Color> {

    return when (progressBarStyleType) {
        TimerProgressBarStyleType.RGB -> {
            TimerProgressBarRgbStyle().timerProgressBarRgbStyleColorList()
        }

        TimerProgressBarStyleType.DynamicColor -> {
            listOf(colorScheme.primary, colorScheme.primary)
        }
    }

}