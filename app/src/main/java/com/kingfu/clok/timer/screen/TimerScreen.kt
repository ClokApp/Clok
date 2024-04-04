package com.kingfu.clok.timer.screen

import androidx.compose.animation.animateContentSize
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.kingfu.clok.timer.component.TimerEdit
import com.kingfu.clok.timer.component.TimerResetButton
import com.kingfu.clok.timer.component.TimerStartButton
import com.kingfu.clok.timer.component.TimerTimeScreen
import com.kingfu.clok.timer.util.timerProgressBarStyle.TimerProgressBarStyleType
import com.kingfu.clok.timer.viewModel.TimerViewModel
import com.kingfu.clok.ui.theme.ThemeType
import com.kingfu.clok.ui.util.isPortrait

@Composable
fun TimerScreen(
    vm: TimerViewModel,
    theme: ThemeType
) {
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
            .alpha(alpha = if (vm.state.isLoadInitialTime && vm.state.timerIsEditState) 0f else 1f)
            .padding(horizontal = if (isPortrait()) 0.dp else 40.dp)
            .verticalScroll(
                state = scrollState,
                enabled = !lazyListStateHr.isScrollInProgress &&
                        !lazyListStateMin.isScrollInProgress &&
                        !lazyListStateSec.isScrollInProgress
            ),
        verticalArrangement = Center,
        horizontalAlignment = CenterHorizontally
    ) {

        if (!vm.state.timerIsEditState) {
            TimerTimeScreen(
                timerTime = vm.state.timerTime,
                updateTimerStyle = { selectedProgressBarStyle: TimerProgressBarStyleType ->
                    vm.updateTimerStyle(selectedProgressBarStyle = selectedProgressBarStyle)
                },
                getTimerProgressBarStyle = { vm.getTimerProgressBarStyle() },
                timerCurrentTimePercentage = vm.state.timerCurrentTimePercentage,
                timerIsActive = vm.state.timerIsActive,
                getTimerProgressBarBackgroundEffects = { vm.getTimerProgressBarBackgroundEffects() },
                isOverTime = { vm.isOverTime() },
                getTimerTimeFontStyle = { vm.getTimerTimeFontStyle() },
                formatTimerTimeHr = { timeMillis: Long ->
                    vm.formatTimerTimeHr(timeMillis = timeMillis)
                },
                formatTimerTimeMin = { timeMillis: Long ->
                    vm.formatTimerTimeMin(timeMillis = timeMillis)
                },
                formatTimerTimeSec = { timeMillis: Long ->
                    vm.formatTimerTimeSec(timeMillis = timeMillis)
                },
                formatTimerTimeMs = { timeMillis: Long ->
                    vm.formatTimerTimeMs(timeMillis = timeMillis)
                },
                theme = theme
            )
        } else {
            TimerEdit(
                lazyListStateHr = lazyListStateHr,
                lazyListStateMin = lazyListStateMin,
                lazyListStateSec = lazyListStateSec,
                haptic = haptic,
                timerHour = vm.state.timerHour,
                timerMinute = vm.state.timerMinute,
                timerSecond = vm.state.timerSecond,
                setLoadInitialTimeToFalse = { vm.setIsLoadInitialTimeToFalse() },
                updateTimerHour = { hour: Int ->
                    vm.updateTimerHour(hour = hour)
                },
                saveTimerHour = { vm.saveTimerHour() },
                updateTimerMinute = { minute: Int ->
                    vm.updateTimerMinute(minute = minute)
                },
                saveTimerMinute = { vm.saveTimerMinute() },
                updateTimerSecond = { second: Int ->
                    vm.updateTimerSecond(second = second)
                },
                saveTimerSecond = { vm.saveTimerSecond() },
                getTimerScrollsFontStyle = { vm.getTimerScrollsFontStyle() },
                getTimerScrollsHapticFeedback = { vm.getTimerScrollsHapticFeedback() },
                theme = theme
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .animateContentSize(),
            horizontalArrangement = Center
        ) {
            TimerResetButton(
                lazyListStateHr = lazyListStateHr,
                lazyListStateMin = lazyListStateMin,
                lazyListStateSec = lazyListStateSec,
                coroutineScopeTimer = coroutineScopeTimer,
                haptic = haptic,
                context = context,
                timerHour = vm.state.timerHour,
                timerMinute = vm.state.timerMinute,
                timerSecond = vm.state.timerSecond,
                isLoadInitialTime = vm.state.isLoadInitialTime,
                timerIsEditState = vm.state.timerIsEditState,
                resetTimer = { vm.resetTimer() },
                cancelTimer = { vm.cancelTimer() }
            )
            TimerStartButton(
                lazyListStateHr = lazyListStateHr,
                lazyListStateMin = lazyListStateMin,
                lazyListStateSec = lazyListStateSec,
                haptic = haptic,
                context = context,
                timerIsEditState = vm.state.timerIsEditState,
                timerHour = vm.state.timerHour,
                timerMinute = vm.state.timerMinute,
                timerSecond = vm.state.timerSecond,
                timerIsActive = vm.state.timerIsActive,
                pauseTimer = { vm.pauseTimer() },
                convertHrMinSecToMillis = { vm.convertHrMinSecToMillis() },
                timerSetTotalTime = { vm.timerSetTotalTime() },
                startTimer = { vm.startTimer() },
                timerIsFinished = vm.state.timerIsFinished,
                isCountOverTime = vm.getTimerIsCountOverTime()
            )
        }
    }
}

