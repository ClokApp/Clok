package com.kingfu.clok.view.stopwatchView

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.kingfu.clok.ui.theme.Black00
import com.kingfu.clok.viewModel.stopwatchViewModel.StopwatchViewModel

@Composable
fun StopwatchView(
    navController: NavController,
    scaffoldState: ScaffoldState,
    vm: StopwatchViewModel = viewModel(),
) {

    val columnListStateStopwatch = rememberScrollState()
    val lazyColumnState = rememberLazyListState()

    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current
    val configurationOrientation = LocalConfiguration.current.orientation

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
                    .fillMaxSize()
                    .verticalScroll(columnListStateStopwatch),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                StopwatchTimeView(vm = vm)

                StopwatchLapView(
                    vm,
                    lazyColumnState,
                    configurationOrientation
                )
                Row {
                    StopwatchResetButton(
                        vm = vm,
                        haptic = haptic,
                    )
                    StopwatchStartButtonView(
                        vm = vm,
                        haptic = haptic
                    )
                }
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(columnListStateStopwatch),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    StopwatchTimeView(vm = vm)

                    Row {
                        StopwatchResetButton(
                            vm = vm,
                            haptic = haptic,
                        )
                        StopwatchStartButtonView(
                            vm = vm,
                            haptic = haptic
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