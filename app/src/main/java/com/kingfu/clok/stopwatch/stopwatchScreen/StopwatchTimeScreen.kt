package com.kingfu.clok.stopwatch.stopwatchScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Bottom
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.text.font.FontWeight.Companion.ExtraLight
import androidx.compose.ui.text.font.FontWeight.Companion.Light
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize.Companion.Zero
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.kingfu.clok.repository.room.stopwatchRoom.StopwatchLapData
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch
import com.kingfu.clok.stopwatch.backgroundEffects.StopwatchSnowEffect
import com.kingfu.clok.stopwatch.fontStyle.stopwatchFontStyle
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel
import com.kingfu.clok.stopwatch.styles.StopwatchRGBStyle
import com.kingfu.clok.stopwatch.styles.StopwatchRGBStyle.RGBVariable.RGBHrColorList
import com.kingfu.clok.stopwatch.styles.StopwatchRGBStyle.RGBVariable.RGBMinColorList
import com.kingfu.clok.stopwatch.styles.StopwatchRGBStyle.RGBVariable.RGBMsColorList
import com.kingfu.clok.stopwatch.styles.StopwatchRGBStyle.RGBVariable.RGBSecColorList
import com.kingfu.clok.util.nonScaledSp
import com.kingfu.clok.variable.Variable.RGB


@Composable
fun StopwatchTime(
    vm: StopwatchViewModel,
    settingsViewModelStopwatch: SettingsViewModelStopwatch,
    lapList: List<StopwatchLapData>
) {

    Box {
        Row(modifier = Modifier.background(color = Transparent)) {
            if (vm.stopwatchTime >= 3_600_000) {
                DisplayStopwatchTimer(
                    RGBColorList = RGBHrColorList,
                    text = "hr",
                    formatTimeStopWatch = vm.formatTimeStopWatchHr(timeMillis = vm.stopwatchTime),
                    settingsViewModelStopwatch = settingsViewModelStopwatch,
                    vm = vm
                )
            }

            DisplayStopwatchTimer(
                RGBColorList = RGBMinColorList,
                text = "min",
                formatTimeStopWatch = vm.formatTimeStopWatchMin(timeMillis = vm.stopwatchTime),
                settingsViewModelStopwatch = settingsViewModelStopwatch,
                vm = vm
            )

            DisplayStopwatchTimer(
                RGBColorList = RGBSecColorList,
                text = "sec",
                formatTimeStopWatch = vm.formatTimeStopWatchSec(timeMillis = vm.stopwatchTime),
                settingsViewModelStopwatch = settingsViewModelStopwatch,
                vm = vm
            )

            DisplayStopwatchTimer(
                RGBColorList = RGBMsColorList,
                text = "ms",
                formatTimeStopWatch = vm.formatTimeStopWatchMs(timeMillis = vm.stopwatchTime),
                settingsViewModelStopwatch = settingsViewModelStopwatch,
                vm = vm
            )
        }

        var backgroundEffectsBoxSize by remember { mutableStateOf(value = Zero) }

        Box(
            modifier = Modifier
                .matchParentSize()
                .onSizeChanged { backgroundEffectsBoxSize = it }
                .zIndex(zIndex = -1f)
        ) {
            if (vm.stopwatchIsActive && backgroundEffectsBoxSize.width.dp != 0.dp && settingsViewModelStopwatch.stopwatchShowLabel) {
                when (settingsViewModelStopwatch.stopwatchBackgroundEffects) {
                    "Snow" -> StopwatchSnowEffect(size = backgroundEffectsBoxSize)
                }
            }
        }
    }

    Row(modifier = Modifier.alpha(alpha = if (lapList.isNotEmpty()) 1f else 0f)) {

        if (vm.isAtLestOneHour()) {
            DisplayStopwatchLapTimer(
                text = vm.formatTimeStopWatchHr(timeMillis = vm.stopwatchTime - vm.lapPreviousTime),
                settingsViewModelStopwatch = settingsViewModelStopwatch,
                color = colorScheme.secondary,
            )
        }

        DisplayStopwatchLapTimer(
            text = if (vm.isAtLestOneHour()) ":" else "",
            settingsViewModelStopwatch = settingsViewModelStopwatch,
            color = colorScheme.primary,
            padding = if (vm.isAtLestOneHour()) 5.dp else 0.dp,
        )

        DisplayStopwatchLapTimer(
            text = vm.formatTimeStopWatchMin(timeMillis = vm.stopwatchTime - vm.lapPreviousTime),
            settingsViewModelStopwatch = settingsViewModelStopwatch,
            color = colorScheme.secondary,
        )

        DisplayStopwatchLapTimer(
            text = ":",
            settingsViewModelStopwatch = settingsViewModelStopwatch,
            color = colorScheme.primary,
            padding = 5.dp,
        )

        DisplayStopwatchLapTimer(
            text = vm.formatTimeStopWatchSec(timeMillis = vm.stopwatchTime - vm.lapPreviousTime),
            settingsViewModelStopwatch = settingsViewModelStopwatch,
            color = colorScheme.secondary,
        )

        DisplayStopwatchLapTimer(
            text = ".",
            settingsViewModelStopwatch = settingsViewModelStopwatch,
            color = colorScheme.primary,
            padding = 5.dp,
        )

        DisplayStopwatchLapTimer(
            text = vm.formatTimeStopWatchMs(timeMillis = vm.stopwatchTime - vm.lapPreviousTime),
            settingsViewModelStopwatch = settingsViewModelStopwatch,
            color = colorScheme.secondary,
        )
    }
}


@Composable
fun DisplayStopwatchTimer(
    RGBColorList: List<Int>,
    text: String,
    formatTimeStopWatch: String,
    settingsViewModelStopwatch: SettingsViewModelStopwatch,
    vm: StopwatchViewModel
) {
    val stopwatchLabelFontSize = 30.nonScaledSp
    val stopwatchTimeFontSize = 55.nonScaledSp
    val stopwatchDynamicColorStyle =
        if (vm.stopwatchIsActive) {
            listOf(colorScheme.tertiary, colorScheme.tertiary)
        } else {
            listOf(
                colorScheme.tertiaryContainer,
                colorScheme.tertiaryContainer
            )
        }

    Column(
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Bottom,
    ) {
        if (settingsViewModelStopwatch.stopwatchShowLabel) {
            Text(
                text = "",
                fontSize = stopwatchLabelFontSize,
                style = TextStyle(),
            )
        }

        Text(
            text = when (text) {
                "ms" -> "."
                "sec" -> ":"
                "min" -> if (vm.stopwatchTime > 3_600_000) ":" else ""
                else -> ""
            },
            fontSize = stopwatchTimeFontSize,
            color = colorScheme.tertiary,
            fontWeight = Light,
            modifier = Modifier.padding(horizontal = 5.dp),
            style = TextStyle(
                drawStyle = stopwatchFontStyle(
                    string1 = settingsViewModelStopwatch.stopwatchTimeFontStyle,
                    string2 = settingsViewModelStopwatch.stopwatchStyleRadioOptions.elementAt(
                        index = 1
                    ),
                    minter = 10f,
                    width = 5f
                )
            )
        )
    }

    Column(
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Center
    ) {
        val stopwatchListOfColorStart =
            when (settingsViewModelStopwatch.stopwatchLabelStyle) {
                RGB -> StopwatchRGBStyle().rgbStyleListRGB(RGBColorList = RGBColorList)
                else -> stopwatchDynamicColorStyle
            }

        if (settingsViewModelStopwatch.stopwatchShowLabel) {
            Text(
                text = text,
                fontSize = stopwatchLabelFontSize,
                fontWeight = ExtraLight,
                fontStyle = Italic,
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = if (vm.stopwatchIsActive) stopwatchListOfColorStart else stopwatchDynamicColorStyle
                    ),
                    drawStyle = stopwatchFontStyle(
                        string1 = settingsViewModelStopwatch.stopwatchLabelFontStyle,
                        string2 = settingsViewModelStopwatch.stopwatchStyleRadioOptions.elementAt(
                            index = 1
                        ),
                        minter = 10f,
                        width = 1.5f
                    )
                )
            )
        }

        Text(
            text = formatTimeStopWatch,
            fontSize = stopwatchTimeFontSize,
            color = colorScheme.primary,
            fontWeight = Light,
            maxLines = 1,
            overflow = Ellipsis,
            style = TextStyle(
                drawStyle = stopwatchFontStyle(
                    string1 = settingsViewModelStopwatch.stopwatchTimeFontStyle,
                    string2 = settingsViewModelStopwatch.stopwatchStyleRadioOptions.elementAt(
                        index = 1
                    ),
                    minter = 10f,
                    width = 5f
                )
            )
        )
    }
}

@Composable
fun DisplayStopwatchLapTimer(
    text: String,
    settingsViewModelStopwatch: SettingsViewModelStopwatch,
    color: Color,
    padding: Dp = 0.dp
) {
    Text(
        text = text,
        style = TextStyle(
            drawStyle = stopwatchFontStyle(
                string1 = settingsViewModelStopwatch.stopwatchLapTimeFontStyle,
                string2 = settingsViewModelStopwatch.stopwatchStyleRadioOptions.elementAt(
                    index = 1
                ),
                minter = 10f,
                width = 2.5f
            )
        ),
        fontSize = 30.nonScaledSp,
        color = color,
        fontWeight = ExtraLight,
        modifier = Modifier.padding(horizontal = padding)
    )
}