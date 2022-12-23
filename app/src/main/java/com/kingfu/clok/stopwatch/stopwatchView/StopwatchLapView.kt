package com.kingfu.clok.stopwatch.stopwatchView

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel.StopwatchViewModelVariable.stopwatchTime
import com.kingfu.clok.ui.theme.Black00
import com.kingfu.clok.util.customFontSize


@Composable
fun StopwatchLapView(
    vm: StopwatchViewModel,
    lazyColumnState: LazyListState,
    configurationOrientation: Int
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .alpha(if (vm.lapCounter > 0 || configurationOrientation == Configuration.ORIENTATION_LANDSCAPE) 1f else 0f)
            .fillMaxHeight(if (configurationOrientation == Configuration.ORIENTATION_PORTRAIT) 0.65f else 1f)
            .fillMaxWidth(if (configurationOrientation == Configuration.ORIENTATION_PORTRAIT) 0.8f else 0.5f)
            .padding(
                vertical = 0.dp,
                horizontal = if (configurationOrientation == Configuration.ORIENTATION_PORTRAIT) 0.dp else 40.dp
            ),

        ) {

        Row(Modifier.padding(10.dp)) {
            Text(
                text = "Lap",
                modifier = Modifier.weight(0.20f),
                fontSize = customFontSize(textUnit = 16.sp),
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                color = Color.White.copy(0.70f),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Lap times",
                modifier = Modifier.weight(0.40f),
                fontSize = customFontSize(textUnit = 16.sp),
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                color = Color.White.copy(0.70f),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Total times",
                modifier = Modifier.weight(0.40f),
                fontSize = customFontSize(textUnit = 16.sp),
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                color = Color.White.copy(0.70f),
                textAlign = TextAlign.Center
            )
        }

        Divider(
            color = Color.DarkGray,
            thickness = 0.8.dp,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth()
        )
        Box {
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                state = lazyColumnState,
            ) {

                items(vm.lapNumber.size) { index ->
                    Row(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                    ) {
                        Text(
                            text = if (vm.lapNumber[index].toInt() < 10) "0${vm.lapNumber[index]}" else vm.lapNumber[index],
                            fontSize = customFontSize(textUnit = 16.sp),
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Normal,
                            color = Color.White.copy(0.50f),
                            modifier = Modifier.weight(0.20f),
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = if (stopwatchTime > 3_600_000) vm.lapTime[index].takeWhile { it != '-' } else vm.lapTime[index].takeWhile { it != '-' },
                            fontSize = customFontSize(textUnit = 16.sp),
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Normal,
                            color = Color.White.copy(0.70f),
                            modifier = Modifier.weight(0.40f),
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = if (stopwatchTime > 3_600_000) vm.lapTotalTime[index].takeWhile { it != '-' } else vm.lapTotalTime[index].takeWhile { it != '-' },
                            fontSize = customFontSize(textUnit = 16.sp),
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Normal,
                            color = Color.White.copy(0.90f),
                            modifier = Modifier.weight(0.40f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                Color.Transparent,
                                Color.Transparent,
                                Color.Transparent,
                                Color.Transparent,
                                Color.Transparent,
                                Color.Transparent,
                                Color.Transparent,
                                Color.Transparent,
                                Black00
                            )
                        )
                    )
                    .matchParentSize()
            )
        }
    }

}
