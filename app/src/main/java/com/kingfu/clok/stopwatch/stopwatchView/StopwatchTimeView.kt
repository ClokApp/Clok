package com.kingfu.clok.stopwatch.stopwatchView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch.SettingsViewModelStopwatchVariable.stopwatchBackgroundEffectsSelectedOption
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch.SettingsViewModelStopwatchVariable.stopwatchLabelStyleSelectedOption
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch.SettingsViewModelStopwatchVariable.stopwatchShowLabel
import com.kingfu.clok.stopwatch.backgroundEffects.StopwatchSnowEffect
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel.StopwatchViewModelVariable.stopwatchIsActive
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel.StopwatchViewModelVariable.stopwatchTime
import com.kingfu.clok.stopwatch.styles.StopwatchGrayStyle
import com.kingfu.clok.stopwatch.styles.StopwatchRGBStyle
import com.kingfu.clok.stopwatch.styles.StopwatchRGBStyle.RGBVariable.RGBHrColorList
import com.kingfu.clok.stopwatch.styles.StopwatchRGBStyle.RGBVariable.RGBMinColorList
import com.kingfu.clok.stopwatch.styles.StopwatchRGBStyle.RGBVariable.RGBMsColorList
import com.kingfu.clok.stopwatch.styles.StopwatchRGBStyle.RGBVariable.RGBSecColorList
import com.kingfu.clok.util.customFontSize

@Composable
fun StopwatchTimeView(
    vm: StopwatchViewModel,
) {

    Box(Modifier.zIndex(0f)) {
        Row(Modifier.background(Color.Transparent)) {
            if (stopwatchTime >= 3_600_000) {
                DisplayStopwatchTimer(RGBHrColorList, "hr", vm.formatTimeStopWatchHr(stopwatchTime))
            }
            DisplayStopwatchTimer(RGBMinColorList, "min", vm.formatTimeStopWatchMin(stopwatchTime))
            DisplayStopwatchTimer(RGBSecColorList, "sec", vm.formatTimeStopWatchSec(stopwatchTime))
            DisplayStopwatchTimer(RGBMsColorList, "ms", vm.formatTimeStopWatchMs(stopwatchTime))
        }

        var backgroundEffectsBoxSize by remember { mutableStateOf(IntSize.Zero) }

        Box(
            modifier = Modifier
                .matchParentSize()
                .onSizeChanged { backgroundEffectsBoxSize = it }
                .zIndex(-1f)
        ) {
            if (stopwatchIsActive && backgroundEffectsBoxSize.width.dp != 0.dp && stopwatchShowLabel) {
                when (stopwatchBackgroundEffectsSelectedOption) {
                    "Snow" -> StopwatchSnowEffect(backgroundEffectsBoxSize)
                }
            }
        }
    }


    Text(
        text = vm.formatTimeStopWatch(stopwatchTime - vm.lapPreviousTime),
        fontSize = customFontSize(textUnit = 35.sp),
        color = Color.Gray,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Light,
        modifier = Modifier
            .alpha(if (vm.lapCounter > 0) 1f else 0f)
            .padding(bottom = 20.dp)
    )

}

@OptIn(ExperimentalTextApi::class)
@Composable
fun DisplayStopwatchTimer(
    RGBColorList: List<Int>, name: String, formatTimeStopWatch: String
) {
    val stopwatchLabelFontSize = customFontSize(textUnit = 35.sp)
    val stopwatchTimeFontSize = customFontSize(textUnit = 65.sp)
    val stopwatchGrayStyleColorStyle = remember { StopwatchGrayStyle().grayStyleListOfGray() }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (stopwatchShowLabel) {
            Text(text = "", fontSize = stopwatchLabelFontSize)
        }

        Text(
            text = when (name) {
                "ms" -> "."
                "sec" -> ":"
                "min" -> if (stopwatchTime > 3_600_000) ":" else ""
                else -> ""
            },
            fontSize = stopwatchTimeFontSize,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            color = Color.White,
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val stopwatchListOfColorStart =
            when (stopwatchLabelStyleSelectedOption) {
                "RGB" -> StopwatchRGBStyle().rgbStyleListRGB(RGBColorList = RGBColorList)
                else -> stopwatchGrayStyleColorStyle
            }
        if (stopwatchShowLabel) {
            Text(
                text = name,
                fontSize = stopwatchLabelFontSize,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Thin,
                fontStyle = FontStyle.Italic,
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = if (stopwatchIsActive) stopwatchListOfColorStart else stopwatchGrayStyleColorStyle
                    )
                )
            )
        }

        Text(
            text = formatTimeStopWatch,
            fontSize = stopwatchTimeFontSize,
            color = Color.White,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Light,
        )
    }
}