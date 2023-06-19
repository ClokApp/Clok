package com.kingfu.clok.stopwatch.stopwatchView

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel
import com.kingfu.clok.ui.theme.Black00


@Composable
fun StopwatchView(
    navController: NavController,
    vm: StopwatchViewModel,
    settingsViewModelStopwatch: SettingsViewModelStopwatch
) {
    val lazyColumnState = rememberLazyListState()

    val haptic = LocalHapticFeedback.current
    val configurationOrientation = LocalConfiguration.current.orientation
    val coroutineScopeStopwatch = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    val lapList = vm.lapList.collectAsState().value

    if (configurationOrientation == Configuration.ORIENTATION_PORTRAIT) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Black00),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            StopwatchTimeView(
                vm = vm,
                settingsViewModelStopwatch = settingsViewModelStopwatch,
                lapList = lapList
            )

            Spacer(modifier = Modifier.height(height = 20.dp))

            StopwatchLapView(
                vm = vm,
                lazyColumnState = lazyColumnState,
                configurationOrientation = configurationOrientation,
                lapList = lapList
            )

            Spacer(modifier = Modifier.height(height = 20.dp))

            Row(
                Modifier
                    .fillMaxWidth()
                    .verticalScroll(state = scrollState),
                horizontalArrangement = Arrangement.Center
            ) {
                StopwatchResetButton(
                    vm = vm,
                    haptic = haptic,
                )

                StopwatchStartButtonView(
                    vm = vm,
                    haptic = haptic,
                    coroutineScopeStopwatch = coroutineScopeStopwatch,
                )
            }
        }
    } else {
        Row {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(weight = 0.5f)
                    .verticalScroll(state = scrollState)
                    .background(color = Black00),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                StopwatchTimeView(
                    vm = vm,
                    settingsViewModelStopwatch = settingsViewModelStopwatch,
                    lapList = lapList
                )

                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StopwatchResetButton(
                        vm = vm,
                        haptic = haptic,
                    )
                    StopwatchStartButtonView(
                        vm = vm,
                        haptic = haptic,
                        coroutineScopeStopwatch = coroutineScopeStopwatch
                    )
                }
            }

            Box(modifier = Modifier.weight(weight = 0.5f)) {
                StopwatchLapView(
                    vm = vm,
                    lazyColumnState = lazyColumnState,
                    configurationOrientation = configurationOrientation,
                    lapList = lapList
                )
            }
        }
    }
}

