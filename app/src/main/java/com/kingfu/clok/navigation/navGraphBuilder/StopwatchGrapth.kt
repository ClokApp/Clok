package com.kingfu.clok.navigation.navGraphBuilder

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kingfu.clok.navigation.Screen
import com.kingfu.clok.stopwatch.screen.StopwatchScreen
import com.kingfu.clok.stopwatch.viewModel.StopwatchViewModel


fun NavGraphBuilder.stopwatchGraph(
    vm: StopwatchViewModel,
    toggleDrawer: () -> Unit,
    isDrawerOpen: () -> Boolean,
) {
    composable(route = Screen.Stopwatch.route) {
        Box {
            StopwatchScreen(
                toggleDrawer = toggleDrawer,
                isDrawerOpen = isDrawerOpen,
                time = vm.state.time,
                laps = vm.laps.collectAsState().value,
                shortestLapIndex = vm.state.shortestLapIndex,
                longestLapIndex = vm.state.longestLapIndex,
                isActive = vm.state.isActive,
                reset = { vm.reset() },
                pause = { vm.pause() },
                saveLapOffset = { vm.saveOffsetTime() },
                start = { vm.start() },
                setIsLap = { vm.setIsLap(it) },
                setMaxAndMinLapIndex = {vm.setMaxAndMinLapIndex()}
            )
        }
    }
}