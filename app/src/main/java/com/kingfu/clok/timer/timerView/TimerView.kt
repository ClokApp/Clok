package com.kingfu.clok.timer.timerView

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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

    val lazyListStateHr = rememberLazyListState()
    val lazyListStateMin = rememberLazyListState()
    val lazyListStateSec = rememberLazyListState()


    var loadInitialTime by rememberSaveable { mutableStateOf(true) }


    LaunchedEffect(Unit) {
        if (loadInitialTime) {
            lazyListStateHr.scrollToItem(Int.MAX_VALUE / 2 - 24 + vm.timerHour)
            lazyListStateMin.scrollToItem(Int.MAX_VALUE / 2 - 4 + vm.timerMinute)
            lazyListStateSec.scrollToItem(Int.MAX_VALUE / 2 - 4 + vm.timerSecond)
            loadInitialTime = false
        }
    }

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
                .padding(
                    top = if (configurationOrientation == Configuration.ORIENTATION_PORTRAIT) 20.dp else 0.dp
                ),
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
                configurationOrientation
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