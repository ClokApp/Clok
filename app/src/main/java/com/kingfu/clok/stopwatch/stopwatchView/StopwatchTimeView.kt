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
        val defaultGrayColor = listOf(Color.Gray, Color.Gray)

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
                                    vm.stopwatchHrColorList[0],
                                    vm.stopwatchHrColorList[1],
                                    vm.stopwatchHrColorList[2]
                                ),
                                Color(
                                    vm.stopwatchHrColorList[3],
                                    vm.stopwatchHrColorList[4],
                                    vm.stopwatchHrColorList[5]
                                )
                            )
                        else -> defaultGrayColor
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
                                colors = if (stopwatchIsActive) stopwatchHrListOfColor else defaultGrayColor
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
                                vm.stopwatchMinColorList[0],
                                vm.stopwatchMinColorList[1],
                                vm.stopwatchMinColorList[2]
                            ),
                            Color(
                                vm.stopwatchMinColorList[3],
                                vm.stopwatchMinColorList[4],
                                vm.stopwatchMinColorList[5]
                            )
                        )
                    else -> defaultGrayColor
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
                            colors = if (stopwatchIsActive) stopwatchMinListOfColor else defaultGrayColor
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
                                vm.stopwatchSecColorList[0],
                                vm.stopwatchSecColorList[1],
                                vm.stopwatchSecColorList[2],
                            ),
                            Color(
                                vm.stopwatchSecColorList[3],
                                vm.stopwatchSecColorList[4],
                                vm.stopwatchSecColorList[5],
                            )
                        )
                    else -> defaultGrayColor

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
                            colors = if (stopwatchIsActive) stopwatchSecListOfColor else defaultGrayColor
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
                                vm.stopwatchMsColorList[0],
                                vm.stopwatchMsColorList[1],
                                vm.stopwatchMsColorList[2]
                            ),
                            Color(
                                vm.stopwatchMsColorList[3],
                                vm.stopwatchMsColorList[4],
                                vm.stopwatchMsColorList[5]
                            )
                        )
                    else -> defaultGrayColor
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
                            colors = if (stopwatchIsActive) stopwatchMsListOfColorStart else defaultGrayColor
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