package com.kingfu.clok.stopwatch.stopwatchScreen

import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Arrangement.SpaceEvenly
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.kingfu.clok.settings.settingsScreen.settingsApp.settingsThemeScreen.ThemeType
import com.kingfu.clok.stopwatch.feature.labelStyle.StopwatchLabelStyleType
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel
import com.kingfu.clok.ui.util.isPortrait


@Composable
fun StopwatchScreen(
    vm: StopwatchViewModel,
    theme: ThemeType
) {
    val lazyColumnState = rememberLazyListState()
    val haptic = LocalHapticFeedback.current
    val coroutineScopeStopwatch = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    if (isPortrait()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Center,
            horizontalAlignment = CenterHorizontally,
        ) {
            StopwatchTime(
                getStopwatchLapTimeFontStyle = {vm.getStopwatchLapTimeFontStyle()},
                getStopwatchTimeFontStyle = { vm.getStopwatchTimeFontStyle() },
                getStopwatchLabelFontStyle = { vm.getStopwatchLabelFontStyle() },
                getStopwatchLabelStyle = { vm.getStopwatchLabelStyle() },
                getStopwatchLabelBackgroundEffect = { vm.getStopwatchLabelBackgroundEffect() },
                getStopwatchIsShowLabel = { vm.getStopwatchIsShowLabel() },
                lapList = vm.lapList.collectAsState().value,
                stopwatchTime = vm.state.stopwatchTime,
                updateStopwatchLabelStyle = { selectedLabelStyle: StopwatchLabelStyleType ->
                    vm.updateStopwatchLabelStyle(selectedLabelStyle)
                },
                stopwatchIsActive = vm.state.stopwatchIsActive,
                lapPreviousTime = vm.state.lapPreviousTime,
                theme = theme
            )

            Spacer(modifier = Modifier.height(height = 20.dp))

            StopwatchLapScreen(
                lazyColumnState = lazyColumnState,
                lapList = vm.lapList.collectAsState().value,
                isScrollLazyColumn = vm.state.isScrollLazyColumn,
                toggleIsScrollLazyColumn = { vm.toggleIsScrollLazyColumn() },
                shortestLapIndex = vm.state.shortestLapIndex,
                longestLapIndex = vm.state.longestLapIndex,
                theme = theme
            )

            Spacer(modifier = Modifier.height(height = 20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = CenterVertically,
                horizontalArrangement = SpaceEvenly,
            ) {
                StopwatchResetButton(
                    haptic = haptic,
                    stopwatchTime = vm.state.stopwatchTime,
                    stopwatchIsActive = vm.state.stopwatchIsActive,
                    lap = { vm.lap() },
                    isScrollLazyColumn = vm.state.isScrollLazyColumn,
                    toggleIsScrollLazyColumn = { vm.toggleIsScrollLazyColumn() },
                    resetStopwatch = { vm.resetStopwatch() },
                    clearLapTimes = { vm.clearLapTimes() }
                )

                StopwatchStartButton(
                    haptic = haptic,
                    coroutineScopeStopwatch = coroutineScopeStopwatch,
                    stopwatchIsActive = vm.state.stopwatchIsActive,
                    pauseStopwatch = { vm.pauseStopwatch() },
                    saveStopwatchLapPreviousTime = { vm.saveStopwatchLapPreviousTime() },
                    saveStopwatchOffsetTime = { vm.saveStopwatchOffsetTime() },
                    startStopwatch = { vm.startStopwatch() }
                )
            }
        }
    } else {
        Row(
            modifier = Modifier.padding(horizontal = 40.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(weight = 0.5f)
                    .verticalScroll(state = scrollState),
                verticalArrangement = Center,
                horizontalAlignment = CenterHorizontally
            ) {
                StopwatchTime(
                    getStopwatchLapTimeFontStyle = {vm.getStopwatchLapTimeFontStyle()},
                    getStopwatchTimeFontStyle = { vm.getStopwatchTimeFontStyle() },
                    getStopwatchLabelFontStyle = { vm.getStopwatchLabelFontStyle() },
                    getStopwatchLabelStyle = { vm.getStopwatchLabelStyle() },
                    getStopwatchLabelBackgroundEffect = { vm.getStopwatchLabelBackgroundEffect() },
                    getStopwatchIsShowLabel = { vm.getStopwatchIsShowLabel() },
                    lapList = vm.lapList.collectAsState().value,
                    stopwatchTime = vm.state.stopwatchTime,
                    updateStopwatchLabelStyle = { selectedLabelStyle: StopwatchLabelStyleType ->
                        vm.updateStopwatchLabelStyle(selectedLabelStyle)
                    },
                    stopwatchIsActive = vm.state.stopwatchIsActive,
                    lapPreviousTime = vm.state.lapPreviousTime,
                    theme = theme
                )

                Spacer(modifier = Modifier.height(height = 20.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = SpaceEvenly,
                    verticalAlignment = CenterVertically
                ) {
                    StopwatchResetButton(
                        haptic = haptic,
                        stopwatchTime = vm.state.stopwatchTime,
                        stopwatchIsActive = vm.state.stopwatchIsActive,
                        lap = { vm.lap() },
                        isScrollLazyColumn = vm.state.isScrollLazyColumn,
                        toggleIsScrollLazyColumn = { vm.toggleIsScrollLazyColumn() },
                        resetStopwatch = { vm.resetStopwatch() },
                        clearLapTimes = { vm.clearLapTimes() }
                    )
                    StopwatchStartButton(
                        haptic = haptic,
                        coroutineScopeStopwatch = coroutineScopeStopwatch,
                        stopwatchIsActive = vm.state.stopwatchIsActive,
                        pauseStopwatch = { vm.pauseStopwatch() },
                        saveStopwatchLapPreviousTime = { vm.saveStopwatchLapPreviousTime() },
                        saveStopwatchOffsetTime = { vm.saveStopwatchOffsetTime() },
                        startStopwatch = { vm.startStopwatch() }
                    )
                }
            }

            Box(
                modifier = Modifier
                    .weight(weight = 0.5f)
                    .statusBarsPadding()
                    .navigationBarsPadding()
            ) {
                StopwatchLapScreen(
                    lazyColumnState = lazyColumnState,
                    lapList = vm.lapList.collectAsState().value,
                    isScrollLazyColumn = vm.state.isScrollLazyColumn,
                    toggleIsScrollLazyColumn = { vm.toggleIsScrollLazyColumn() },
                    shortestLapIndex = vm.state.shortestLapIndex,
                    longestLapIndex = vm.state.longestLapIndex,
                    theme = theme
                )
            }
        }
    }
}


