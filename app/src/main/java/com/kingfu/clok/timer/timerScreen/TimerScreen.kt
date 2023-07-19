package com.kingfu.clok.timer.timerScreen

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer
import com.kingfu.clok.timer.timerViewModel.TimerViewModel
import com.kingfu.clok.ui.theme.Black00

@Composable
fun TimerScreen(
    vm: TimerViewModel,
    settingsViewModelTimer: SettingsViewModelTimer,
) {
    val configurationOrientation = LocalConfiguration.current.orientation

    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current

    val coroutineScopeTimer = rememberCoroutineScope()

    val lazyListStateHr = rememberLazyListState()
    val lazyListStateMin = rememberLazyListState()
    val lazyListStateSec = rememberLazyListState()

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .alpha(if (vm.loadInitialTime && vm.timerIsEditState) 0f else 1f)
            .background(color = Black00)
            .verticalScroll(
                state = scrollState,
                enabled = !lazyListStateHr.isScrollInProgress &&
                        !lazyListStateMin.isScrollInProgress &&
                        !lazyListStateSec.isScrollInProgress
            ),
        verticalArrangement = Center,
        horizontalAlignment = CenterHorizontally
    ) {

        if (!vm.timerIsEditState) {
            TimerTimeView(
                vm = vm,
                configurationOrientation = configurationOrientation,
                settingsViewModelTimer = settingsViewModelTimer
            )
        } else {
            TimerEditScreen(
                vm = vm,
                lazyListStateHr = lazyListStateHr,
                lazyListStateMin = lazyListStateMin,
                lazyListStateSec = lazyListStateSec,
                haptic = haptic,
                configurationOrientation = configurationOrientation,
                settingsViewModelTimer = settingsViewModelTimer,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = if (configurationOrientation == Configuration.ORIENTATION_PORTRAIT) 10.dp else 0.dp)
                .animateContentSize(),
            horizontalArrangement = Center
        ) {
            TimerResetButton(
                vm = vm,
                lazyListStateHr = lazyListStateHr,
                lazyListStateMin = lazyListStateMin,
                lazyListStateSec = lazyListStateSec,
                coroutineScopeTimer = coroutineScopeTimer,
                haptic = haptic,
                configurationOrientation = configurationOrientation,
                context = context,
            )

            TimerStartButton(
                vm = vm,
                lazyListStateHr = lazyListStateHr,
                lazyListStateMin = lazyListStateMin,
                lazyListStateSec = lazyListStateSec,
                haptic = haptic,
                context = context,
                configurationOrientation = configurationOrientation,
            )
        }
    }
}