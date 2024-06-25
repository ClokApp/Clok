package com.kingfu.clok.navigation.navGraphBuilder

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kingfu.clok.navigation.Screen
import com.kingfu.clok.timer.screen.TimerScreen
import com.kingfu.clok.timer.viewModel.TimerViewModel


fun NavGraphBuilder.timerGraph(
    vm: TimerViewModel,
    toggleDrawer: () -> Unit,
    isDrawerOpen: () -> Boolean,
) {
    composable(route = Screen.Timer.route) {
        TimerScreen(
            toggleDrawer = toggleDrawer,
            isDrawerOpen = isDrawerOpen,
            isEdit = vm.state.isEdit,
            time = vm.state.time,
            isActive = vm.state.isActive,
            isFinished = vm.state.isFinished,
            totalTime = vm.state.totalTime,
            hour = vm.state.hour,
            minute = vm.state.minute,
            second = vm.state.second,
            setHour = { vm.setHour(it) },
            setMinute = { vm.setMinute(it) },
            setSecond = { vm.setSecond(it) },
            reset = { vm.reset() },
            cancel = { vm.cancel() },
            pause = { vm.pause() },
            setTotalTime = { vm.setTotalTime(it) },
            start = { vm.start(it) },
            setOffsetTime = { vm.setOffsetTime(it) }
        )
    }
}