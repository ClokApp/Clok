package com.kingfu.clok.stopwatch.stopwatchView

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.navigation.NavController
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel
import com.kingfu.clok.ui.theme.Black00

@Composable
fun StopwatchView(
    navController: NavController,
    scaffoldState: ScaffoldState,
    vm: StopwatchViewModel,
) {
    val lazyColumnState = rememberLazyListState()

    val haptic = LocalHapticFeedback.current
    val configurationOrientation = LocalConfiguration.current.orientation
    val coroutineScopeStopwatch = rememberCoroutineScope()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black00),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (configurationOrientation == Configuration.ORIENTATION_PORTRAIT) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                StopwatchTimeView(vm = vm)

                StopwatchLapView(
                    vm,
                    lazyColumnState,
                    configurationOrientation
                )

                Row(
                    Modifier
                        .fillMaxWidth()
                        .animateContentSize(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    StopwatchResetButton(
                        vm = vm,
                        haptic = haptic,
                        coroutineScopeStopwatch = coroutineScopeStopwatch,
                        scaffoldState = scaffoldState
                    )

                    StopwatchStartButtonView(
                        vm = vm,
                        haptic = haptic,
                        coroutineScopeStopwatch,
                    )
                }
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(0.5f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    StopwatchTimeView(vm = vm)

                    Row(
                        modifier = Modifier,
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        StopwatchResetButton(
                            vm = vm,
                            haptic = haptic,
                            coroutineScopeStopwatch = coroutineScopeStopwatch,
                            scaffoldState = scaffoldState
                        )
                        StopwatchStartButtonView(
                            vm = vm,
                            haptic = haptic,
                            coroutineScopeStopwatch = coroutineScopeStopwatch
                        )
                    }
                }

                StopwatchLapView(
                    vm,
                    lazyColumnState,
                    configurationOrientation
                )
            }
        }
    }

}