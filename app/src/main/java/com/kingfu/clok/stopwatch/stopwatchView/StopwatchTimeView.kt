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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch.SettingsViewModelStopwatchVariable.stopwatchBackgroundEffectsSelectedOption
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch.SettingsViewModelStopwatchVariable.stopwatchLabelFontStyleSelectedOption
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch.SettingsViewModelStopwatchVariable.stopwatchLabelStyleSelectedOption
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch.SettingsViewModelStopwatchVariable.stopwatchLapTimeFontStyleSelectedOption
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch.SettingsViewModelStopwatchVariable.stopwatchShowLabel
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch.SettingsViewModelStopwatchVariable.stopwatchTimeFontStyleSelectedOption
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

@OptIn(ExperimentalTextApi::class)
@Composable
fun StopwatchTimeView(
    vm: StopwatchViewModel,
) {

    Box(Modifier.zIndex(0f)) {
        Row(Modifier.background(Color.Transparent)) {
            if (stopwatchTime >= 3_600_000) {
                DisplayStopwatchTimer(RGBHrColorList, "hr", vm.formatTimeStopWatchHr(stopwatchTime))
            }
            DisplayStopwatchTimer(
                RGBMinColorList,
                "min",
                vm.formatTimeStopWatchMin(stopwatchTime)
            )
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
        style = TextStyle(
            drawStyle = if (stopwatchLapTimeFontStyleSelectedOption == "Inner stroke") {
                Stroke(
                    miter = 10f,
                    width = 2.5f,
                    join = StrokeJoin.Round,
                    cap = StrokeCap.Round
                )
            } else null
        ),
        fontSize = customFontSize(textUnit = 35.sp),
        color = Color.Gray,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Light,
        modifier = Modifier
            .alpha(if (vm.lapList.isNotEmpty()) 1f else 0f)
            .padding(bottom = 20.dp)

    )

}

@OptIn(ExperimentalTextApi::class)
@Composable
fun DisplayStopwatchTimer(
    RGBColorList: List<Int>, name: String, formatTimeStopWatch: String
) {
    val stopwatchLabelFontSize = customFontSize(textUnit = 35.sp)
    val stopwatchTimeFontSize = customFontSize(textUnit = 60.sp)
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
            style = TextStyle(
                drawStyle = if (stopwatchTimeFontStyleSelectedOption == "Inner stroke") {
                    Stroke(
                        miter = 10f,
                        width = 5f,
                        join = StrokeJoin.Round,
                        cap = StrokeCap.Round
                    )
                } else null
            )
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
//                fontWeight = FontWeight.Thin,
                fontWeight = FontWeight.ExtraLight,
                fontStyle = FontStyle.Italic,
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = if (stopwatchIsActive) stopwatchListOfColorStart else stopwatchGrayStyleColorStyle
                    ),
                    drawStyle = if (stopwatchLabelFontStyleSelectedOption == "Inner stroke") Stroke(
                        miter = 10f,
                        width = 1.5f,
                        join = StrokeJoin.Round,
                        cap = StrokeCap.Round
                    ) else null
                )
            )
        }

        Text(
            text = formatTimeStopWatch,
            fontSize = stopwatchTimeFontSize,
            color = Color.White,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Light,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                drawStyle = if (stopwatchTimeFontStyleSelectedOption == "Inner stroke") {
                    Stroke(
                        miter = 10f,
                        width = 5f,
                        join = StrokeJoin.Round,
                        cap = StrokeCap.Round
                    )
                } else null
            )
        )
    }
}