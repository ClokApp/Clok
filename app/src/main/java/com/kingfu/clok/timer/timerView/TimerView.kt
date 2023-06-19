package com.kingfu.clok.timer.timerView

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer
import com.kingfu.clok.timer.timerViewModel.TimerViewModel
import com.kingfu.clok.ui.theme.Black00

@Composable
fun TimerView(
    navController: NavController,
    vm: TimerViewModel,
    settingsViewModelTimer: SettingsViewModelTimer,
) {
    val configurationOrientation = LocalConfiguration.current.orientation

    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current

    val coroutineScopeTimer = rememberCoroutineScope()

    val lazyListStateHr = rememberLazyListState(initialFirstVisibleItemScrollOffset = 2)
    val lazyListStateMin = rememberLazyListState()
    val lazyListStateSec = rememberLazyListState()
    val scrollState = rememberScrollState()



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Black00)
            .verticalScroll(state = scrollState, enabled = !lazyListStateHr.isScrollInProgress && !lazyListStateMin.isScrollInProgress && !lazyListStateSec.isScrollInProgress),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        if (!vm.timerIsEditState) {
            TimerTimeView(
                vm = vm,
                configurationOrientation = configurationOrientation,
                settingsViewModelTimer = settingsViewModelTimer
            )
        } else {
            TimerEditView(
                vm = vm,
                lazyListStateHr = lazyListStateHr,
                lazyListStateMin = lazyListStateMin,
                lazyListStateSec = lazyListStateSec,
                haptic = haptic,
                configurationOrientation = configurationOrientation,
                settingsViewModelTimer = settingsViewModelTimer
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = if (configurationOrientation == Configuration.ORIENTATION_PORTRAIT) 20.dp else 0.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            TimerResetButtonView(
                vm = vm,
                lazyListStateHr = lazyListStateHr,
                lazyListStateMin = lazyListStateMin,
                lazyListStateSec = lazyListStateSec,
                coroutineScopeTimer = coroutineScopeTimer,
                haptic = haptic,
                configurationOrientation = configurationOrientation,
                context = context
            )

            TimerStartButtonView(
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