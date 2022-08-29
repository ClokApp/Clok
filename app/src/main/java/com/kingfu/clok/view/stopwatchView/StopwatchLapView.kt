package com.kingfu.clok.view.stopwatchView

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kingfu.clok.util.customFontSize
import com.kingfu.clok.viewModel.stopwatchViewModel.StopwatchViewModel

@Composable
fun StopwatchLapView(
    vm: StopwatchViewModel,
    lazyColumnState: LazyListState,
    configurationOrientation: Int
) {

    LaunchedEffect(Unit) {
        vm.clearLapTimes()
        vm.loadStopwatchLapCounter()
        vm.loadStopwatchLapNumber()
        vm.loadStopwatchLapTime()
        vm.loadStopwatchLapTotalTime()
    }

    if (vm.lapCounter > 0) {

        if (configurationOrientation == Configuration.ORIENTATION_PORTRAIT) {
            Spacer(modifier = Modifier.padding(top = 40.dp, bottom = 5.dp))
        } else {
            Spacer(modifier = Modifier.padding(horizontal = 40.dp))
        }

        Column(
            modifier = Modifier
                .height(if (configurationOrientation == Configuration.ORIENTATION_PORTRAIT) 340.dp else 200.dp)
                .width(300.dp)
        ) {
            Row(
                Modifier
                    .padding(10.dp)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = "Lap",
                    modifier = Modifier,
                    fontSize = customFontSize(textUnit = 16.sp),
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Normal,
                    color = Color.White.copy(0.70f)
                )

                Text(
                    text = "Lap times",
                    modifier = Modifier,
                    fontSize = customFontSize(textUnit = 16.sp),
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Normal,
                    color = Color.White.copy(0.70f)
                )

                Text(
                    text = "Total times",
                    modifier = Modifier,
                    fontSize = customFontSize(textUnit = 16.sp),
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Normal,
                    color = Color.White.copy(0.70f)
                )
            }
            Spacer(
                modifier = Modifier
                    .height(0.8.dp)
                    .fillMaxWidth()
                    .background(Color.DarkGray)
            )
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
                            .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(
                            text = if (vm.lapNumber[index].toInt() < 10) "0${vm.lapNumber[index]}" else vm.lapNumber[index],
                            fontSize = customFontSize(textUnit = 16.sp),
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Normal,
                            color = Color.White.copy(0.50f)
                        )

                        Text(
                            text = vm.lapTime[index].take(8),
                            fontSize = customFontSize(textUnit = 16.sp),
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Normal,
                            color = Color.White.copy(0.70f)
                        )

                        Text(
                            text = vm.lapTotalTime[index].take(8),
                            fontSize = customFontSize(textUnit = 16.sp),
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Normal,
                            color = Color.White.copy(0.90f)
                        )
                    }
                }
            }
        }
    }
}