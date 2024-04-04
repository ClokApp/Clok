package com.kingfu.clok.stopwatch.util.labelStyle

import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.kingfu.clok.stopwatch.util.styles.StopwatchRGBStyle

@Composable
fun stopwatchLabelStyle(
    selectedLabelStyle: StopwatchLabelStyleType,
    rgbColorList: List<Int>,
    stopwatchIsActive: Boolean
): List<Color> {
    return when (selectedLabelStyle) {
        StopwatchLabelStyleType.Rgb ->
            if (stopwatchIsActive) {
                listOf(
                    StopwatchRGBStyle().rgbStart(rgbColorList = rgbColorList),
                    StopwatchRGBStyle().rgbEnd(rgbColorList = rgbColorList)
                )
            } else {
                listOf(
                    StopwatchRGBStyle().rgbStart(rgbColorList = rgbColorList).copy(alpha = 0.50f),
                    StopwatchRGBStyle().rgbEnd(rgbColorList = rgbColorList).copy(alpha = 0.50f)
                )
            }

        StopwatchLabelStyleType.DynamicColor -> {
            if (stopwatchIsActive) {
                listOf(colorScheme.tertiary, colorScheme.tertiary)
            } else {
                listOf(colorScheme.tertiary.copy(alpha = 0.5f), colorScheme.tertiary.copy(alpha = 0.5f))
            }
        }
    }
}