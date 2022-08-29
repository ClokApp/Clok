package com.kingfu.clok.view.timerView

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.kingfu.clok.ui.theme.Black00
import com.kingfu.clok.variable.Variable.TIMER_HR
import com.kingfu.clok.variable.Variable.TIMER_MIN
import com.kingfu.clok.variable.Variable.TIMER_SEC
import com.kingfu.clok.viewModel.timerViewModel.TimerViewModel
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberLazyListSnapperLayoutInfo

@OptIn(ExperimentalSnapperApi::class)
@Composable
fun TimerView(
    navController: NavController,
    scaffoldState: ScaffoldState,
    vm: TimerViewModel = viewModel(),
) {
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current

    val coroutineScopeTimer = rememberCoroutineScope()

    val lazyListStateHr = rememberLazyListState()
    val lazyListStateMin = rememberLazyListState()
    val lazyListStateSec = rememberLazyListState()

    val layoutInfoHr = rememberLazyListSnapperLayoutInfo(lazyListStateHr)
    val layoutInfoMin = rememberLazyListSnapperLayoutInfo(lazyListStateMin)
    val layoutInfoSec = rememberLazyListSnapperLayoutInfo(lazyListStateSec)

    val selectedHr = layoutInfoHr.currentItem?.index?.rem(TIMER_HR)
    val selectedMin = layoutInfoMin.currentItem?.index?.rem(TIMER_MIN)
    val selectedSec = layoutInfoSec.currentItem?.index?.rem(TIMER_SEC)

    val columnListStateTimer = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black00)
            .verticalScroll(columnListStateTimer),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        if (!vm.timerIsEditState) {
            TimerTimeView(vm)
        } else {
            TimerEditView(
                vm = vm,
                lazyListStateHr = lazyListStateHr,
                lazyListStateMin = lazyListStateMin,
                lazyListStateSec = lazyListStateSec,
                selectedHr = selectedHr,
                selectedMin = selectedMin,
                selectedSec = selectedSec,
                haptic = haptic,
            )
        }

        Row {
            TimerResetButtonView(
                vm = vm,
                lazyListStateHr = lazyListStateHr,
                lazyListStateMin = lazyListStateMin,
                lazyListStateSec = lazyListStateSec,
                coroutineScopeTimer = coroutineScopeTimer,
                haptic = haptic,
                context = context
            )
            TimerStartButtonView(
                vm = vm,
                lazyListStateHr = lazyListStateHr,
                lazyListStateMin = lazyListStateMin,
                lazyListStateSec = lazyListStateSec,
                selectedHr = selectedHr,
                selectedMin = selectedMin,
                selectedSec = selectedSec,
                haptic = haptic,
                coroutineScopeTimer = coroutineScopeTimer,
                context = context
            )
        }
    }
}