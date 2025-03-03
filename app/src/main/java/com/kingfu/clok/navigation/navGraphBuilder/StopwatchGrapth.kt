package com.kingfu.clok.navigation.navGraphBuilder

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.kingfu.clok.navigation.BottomNavigationBar
import com.kingfu.clok.navigation.Screen
import com.kingfu.clok.navigation.topBar.TopBar
import com.kingfu.clok.stopwatch.screen.StopwatchScreen
import com.kingfu.clok.stopwatch.viewModel.StopwatchViewModel


@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.stopwatchGraph(
    modifier: Modifier = Modifier,
    vm: StopwatchViewModel,
    navController: NavHostController,
    saveRoute: (String) -> Unit,
) {
    composable<Screen.Stopwatch> {
        StopwatchScreen(
            modifier = modifier,
            laps = vm.laps.collectAsState().value,
            state = vm.state,
            reset = vm::reset,
            pause = vm::pause,
            start = vm::start,
            setIsLap = vm::setIsLap,
            setMaxAndMinLapIndex = vm::setMaxAndMinLapIndex,
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
            topBar = { TopBar(navController = navController) },
        )
    }
}