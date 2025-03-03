package com.kingfu.clok.navigation

import android.annotation.SuppressLint
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.kingfu.clok.navigation.navGraphBuilder.settingsGraph
import com.kingfu.clok.navigation.navGraphBuilder.stopwatchGraph
import com.kingfu.clok.navigation.navGraphBuilder.timerGraph
import com.kingfu.clok.settings.repository.SettingsPreferences
import com.kingfu.clok.settings.viewModel.SettingsFactory
import com.kingfu.clok.settings.viewModel.SettingsViewModel
import com.kingfu.clok.stopwatch.repository.StopwatchPreferences
import com.kingfu.clok.stopwatch.repository.stopwatchRoom.StopwatchLapDatabase
import com.kingfu.clok.stopwatch.viewModel.StopwatchFactory
import com.kingfu.clok.stopwatch.viewModel.StopwatchViewModel
import com.kingfu.clok.timer.repository.TimerPreferences
import com.kingfu.clok.timer.viewModel.TimerFactory
import com.kingfu.clok.timer.viewModel.TimerViewModel
import com.kingfu.clok.ui.dialogs.DialogTheme
import com.kingfu.clok.ui.theme.ClokTheme


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val context = LocalContext.current
    val stopwatchFactory = StopwatchFactory(
        stopwatchPreferences = StopwatchPreferences.getInstance(context = context),
        stopwatchLapDatabase = StopwatchLapDatabase.getInstance(context = context)
    )
    val timerFactory =
        TimerFactory(timerPreferences = TimerPreferences.getInstance(context = context))
    val stopwatchViewModel: StopwatchViewModel = viewModel(factory = stopwatchFactory)
    val timerViewModel: TimerViewModel = viewModel(factory = timerFactory)
    val settingsViewModel: SettingsViewModel = viewModel(
        factory = SettingsFactory(settingsPreferences = SettingsPreferences.getInstance(context = context))
    )

    ModalBottomSheet(
        isFinished = timerViewModel.state.isFinished,
        time = timerViewModel.state.time,
        cancel = timerViewModel::cancel
    )

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = settingsViewModel.state.startRoute.screen,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        stopwatchGraph(
            vm = stopwatchViewModel,
            navController = navController,
            saveRoute = settingsViewModel::saveStartRoute
        )

        timerGraph(
            vm = timerViewModel,
            navController = navController,
            saveRoute = settingsViewModel::saveStartRoute,
        )

        settingsGraph(
            vm = settingsViewModel,
            navController = navController
        )


        dialog<Dialog.Theme> { backStackEntry ->
            val data: Dialog.Theme = backStackEntry.toRoute()

            DialogTheme(
                onDismiss = navController::navigateUp,
                onClick = { settingsViewModel.setTheme(it) },
                theme = data.theme
            )
        }
    }
}
//}

@Preview
@Composable
fun AppNavHostPreview() {
    ClokTheme {
        AppNavHost(
            navController = rememberNavController()
        )
    }
}


