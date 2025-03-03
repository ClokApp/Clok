package com.kingfu.clok.navigation.navGraphBuilder

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.kingfu.clok.navigation.BottomNavigationBar
import com.kingfu.clok.navigation.Screen
import com.kingfu.clok.navigation.topBar.TopBar
import com.kingfu.clok.timer.screen.TimerScreen
import com.kingfu.clok.timer.viewModel.TimerViewModel


@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.timerGraph(
    vm: TimerViewModel,
    navController: NavHostController,
    saveRoute: (String) -> Unit
) {
    composable<Screen.Timer> {
        if (vm.state.isLoaded) {
            TimerScreen(
                state = vm.state,
                setHour = { vm.setHour(it) },
                setMinute = { vm.setMinute(it) },
                setSecond = { vm.setSecond(it) },
                reset = { vm.reset() },
                cancel = { vm.cancel() },
                pause = { vm.pause() },
                setTotalTime = { vm.setTotalTime(it) },
                start = { vm.start(it) },
                setOffsetTime = { vm.setOffsetTime(it) },
                bottomBar = {
                    BottomNavigationBar(
                        onClick = {
                            navController.navigate(route = it.screen) {
                                launchSingleTop = true
                                restoreState = true
                            }
                            saveRoute(it.name)
                        },
                        navController = navController
                    )
                },
                topBar = { TopBar(navController = navController) }
            )
        }
    }
}