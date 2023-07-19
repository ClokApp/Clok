package com.kingfu.clok.stopwatch.stopwatchScreen

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Center
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
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel
import com.kingfu.clok.ui.theme.Black00


@Composable
fun StopwatchScreen(
    vm: StopwatchViewModel,
    settingsViewModelStopwatch: SettingsViewModelStopwatch,
) {
    val lazyColumnState = rememberLazyListState()

    val haptic = LocalHapticFeedback.current
    val configurationOrientation = LocalConfiguration.current.orientation
    val coroutineScopeStopwatch = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    val lapList = vm.lapList.collectAsState().value


    if (configurationOrientation == ORIENTATION_PORTRAIT) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Black00),
            verticalArrangement = Center,
            horizontalAlignment = CenterHorizontally,
        ) {
            StopwatchTime(
                vm = vm,
                settingsViewModelStopwatch = settingsViewModelStopwatch,
                lapList = lapList
            )

            Spacer(modifier = Modifier.height(height = 20.dp))

            StopwatchLapScreen(
                vm = vm,
                lazyColumnState = lazyColumnState,
                configurationOrientation = configurationOrientation,
                lapList = { lapList }
            )

            Spacer(modifier = Modifier.height(height = 20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(state = scrollState),
                horizontalArrangement = Center
            ) {
                StopwatchResetButton(
                    vm = vm,
                    haptic = haptic
                )

                StopwatchStartButton(
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
                verticalArrangement = Center,
                horizontalAlignment = CenterHorizontally
            ) {
                StopwatchTime(
                    vm = vm,
                    settingsViewModelStopwatch = settingsViewModelStopwatch,
                    lapList = lapList
                )

                Spacer(modifier = Modifier.height(height = 20.dp))


                Row(
                    horizontalArrangement = Center,
                    verticalAlignment = CenterVertically
                ) {
                    StopwatchResetButton(
                        vm = vm,
                        haptic = haptic
                    )
                    StopwatchStartButton(
                        vm = vm,
                        haptic = haptic,
                        coroutineScopeStopwatch = coroutineScopeStopwatch,
                    )
                }
            }

            Box(modifier = Modifier.weight(weight = 0.5f)) {
                StopwatchLapScreen(
                    vm = vm,
                    lazyColumnState = lazyColumnState,
                    configurationOrientation = configurationOrientation,
                    lapList = { lapList }
                )
            }
        }
    }
}

