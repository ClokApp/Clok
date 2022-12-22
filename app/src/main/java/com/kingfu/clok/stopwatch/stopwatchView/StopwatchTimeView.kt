package com.kingfu.clok.stopwatch.stopwatchView

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel.StopwatchViewModelVariable.stopwatchIsActive
import com.kingfu.clok.stopwatch.styles.Gray
import com.kingfu.clok.stopwatch.styles.RGB.RGBVariable.RGBHrColorList
import com.kingfu.clok.stopwatch.styles.RGB.RGBVariable.RGBMinColorList
import com.kingfu.clok.stopwatch.styles.RGB.RGBVariable.RGBMsColorList
import com.kingfu.clok.stopwatch.styles.RGB.RGBVariable.RGBSecColorList
import com.kingfu.clok.util.customFontSize

@OptIn(ExperimentalTextApi::class)
@Composable
fun StopwatchTimeView(
    vm: StopwatchViewModel,
    configurationOrientation: Int
) {

    LaunchedEffect(Unit) {
        vm.loadStopwatchRefreshRate()
        vm.loadStopwatchShowLabel()
        vm.loadStopwatchLabelStyle()
        vm.loadStopwatchRefreshRate()
    }

    Row {
        val stopwatchLabelFontSize = customFontSize(textUnit = 35.sp)
        val stopwatchTimeFontSize = customFontSize(textUnit = 65.sp)
//        val rgbColorStyle =
        val grayColorStyle = Gray().grayStyle()
//        val grayColorStyle = listOf(Color.Gray, Color.Gray)

        if (vm.stopwatchTime >= 3_600_000) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val stopwatchHrListOfColor =
                    when (vm.stopwatchLabelStyle) {
                        "RGB" ->
                            listOf(
                                Color(
                                    RGBHrColorList[0],
                                    RGBHrColorList[1],
                                    RGBHrColorList[2]
                                ),
                                Color(
                                    RGBHrColorList[3],
                                    RGBHrColorList[4],
                                    RGBHrColorList[5]
                                )
                            )
                        else -> grayColorStyle
                    }
                if (vm.stopwatchShowLabel) {
                    Text(
                        text = "hr",
                        fontSize = stopwatchLabelFontSize,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Thin,
                        fontStyle = FontStyle.Italic,
                        style = TextStyle(
                            brush = Brush.linearGradient(
                                colors = if (stopwatchIsActive) stopwatchHrListOfColor else grayColorStyle
                            )
                        )
                    )
                }

                Text(
                    text = vm.formatTimeStopWatchHr(vm.stopwatchTime),
                    fontSize = stopwatchTimeFontSize,
                    color = Color.White,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Light,
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (vm.stopwatchShowLabel) {
                    Text(text = "", fontSize = stopwatchLabelFontSize)
                }

                Text(
                    text = ":",
                    fontSize = stopwatchTimeFontSize,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Normal,
                    color = Color.White,
                )
            }

        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val stopwatchMinListOfColor =
                when (vm.stopwatchLabelStyle) {
                    "RGB" ->
                        listOf(
                            Color(
                                RGBMinColorList[0],
                                RGBMinColorList[1],
                                RGBMinColorList[2]
                            ),
                            Color(
                                RGBMinColorList[3],
                                RGBMinColorList[4],
                                RGBMinColorList[5]
                            )
                        )
                    else -> grayColorStyle
                }
            if (vm.stopwatchShowLabel) {
                Text(
                    text = "min",
                    fontSize = stopwatchLabelFontSize,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Thin,
                    fontStyle = FontStyle.Italic,
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            colors = if (stopwatchIsActive) stopwatchMinListOfColor else grayColorStyle
                        )
                    )
                )
            }

            Text(
                text = vm.formatTimeStopWatchMin(vm.stopwatchTime),
                fontSize = stopwatchTimeFontSize,
                color = Color.White,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Light,
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (vm.stopwatchShowLabel) {
                Text(text = "", fontSize = stopwatchLabelFontSize)
            }

            Text(
                text = ":",
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
            val stopwatchSecListOfColor =
                when (vm.stopwatchLabelStyle) {
                    "RGB" ->
                        listOf(
                            Color(
                                RGBSecColorList[0],
                                RGBSecColorList[1],
                                RGBSecColorList[2],
                            ),
                            Color(
                                RGBSecColorList[3],
                                RGBSecColorList[4],
                                RGBSecColorList[5],
                            )
                        )
                    else -> grayColorStyle

                }
            if (vm.stopwatchShowLabel) {
                Text(
                    text = "sec",
                    fontSize = stopwatchLabelFontSize,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Thin,
                    fontStyle = FontStyle.Italic,
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            colors = if (stopwatchIsActive) stopwatchSecListOfColor else grayColorStyle
                        )
                    )
                )
            }


            Text(
                text = vm.formatTimeStopWatchSec(vm.stopwatchTime),
                modifier = Modifier,
                fontSize = stopwatchTimeFontSize,
                color = Color.White,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Light,
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (vm.stopwatchShowLabel) {
                Text(text = "", fontSize = stopwatchLabelFontSize)
            }

            Text(
                text = ".",
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
            val stopwatchMsListOfColorStart =
                when (vm.stopwatchLabelStyle) {
                    "RGB" ->
                        listOf(
                            Color(
                                RGBMsColorList[0],
                                RGBMsColorList[1],
                                RGBMsColorList[2]
                            ),
                            Color(
                                RGBMsColorList[3],
                                RGBMsColorList[4],
                                RGBMsColorList[5]
                            )
                        )
                    else -> grayColorStyle
                }
            if (vm.stopwatchShowLabel) {
                Text(
                    text = "ms",
                    fontSize = stopwatchLabelFontSize,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Thin,
                    fontStyle = FontStyle.Italic,
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            colors = if (stopwatchIsActive) stopwatchMsListOfColorStart else grayColorStyle
                        )
                    )
                )
            }

            Text(
                text = vm.formatTimeStopWatchMs(vm.stopwatchTime),
                fontSize = stopwatchTimeFontSize,
                color = Color.White,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Light,
            )
        }
    }

    Text(
        text = vm.formatTimeStopWatch(vm.stopwatchTime - vm.lapPreviousTime),
        fontSize = customFontSize(textUnit = 35.sp),
        color = Color.Gray,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Light,
        modifier = Modifier
            .alpha(if (vm.lapCounter > 0) 1f else 0f)
            .padding(bottom = if (configurationOrientation == Configuration.ORIENTATION_PORTRAIT) 20.dp else  0.dp)
    )

}