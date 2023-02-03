package com.kingfu.clok.timer.timerView

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kingfu.clok.timer.timerViewModel.TimerViewModel
import com.kingfu.clok.ui.theme.Black00

@Composable
fun TimerView(
    navController: NavController,
    scaffoldState: ScaffoldState,
    vm: TimerViewModel,
) {
    val configurationOrientation = LocalConfiguration.current.orientation

    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current

    val coroutineScopeTimer = rememberCoroutineScope()

    val lazyListStateHr = rememberLazyListState(initialFirstVisibleItemScrollOffset = 2)
    val lazyListStateMin = rememberLazyListState()
    val lazyListStateSec = rememberLazyListState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black00),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        if (!vm.timerIsEditState) {
            TimerTimeView(
                vm = vm,
                context = context,
                haptic = haptic,
                configurationOrientation = configurationOrientation,
            )
        } else {
            TimerEditView(
                vm = vm,
                lazyListStateHr = lazyListStateHr,
                lazyListStateMin = lazyListStateMin,
                lazyListStateSec = lazyListStateSec,
                haptic = haptic,
                configurationOrientation = configurationOrientation
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
                context = context,
                configurationOrientation = configurationOrientation
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