package com.kingfu.clok.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kingfu.clok.navigation.bottomBar.BottomBar
import com.kingfu.clok.navigation.navigationRail.CustomNavigationRail
import com.kingfu.clok.navigation.topBar.TopBar
import com.kingfu.clok.repository.preferencesDataStore.NavigationPreferences
import com.kingfu.clok.repository.preferencesDataStore.StopwatchPreferences
import com.kingfu.clok.repository.preferencesDataStore.TimerPreferences
import com.kingfu.clok.repository.room.stopwatchRoom.StopwatchLapDatabase
import com.kingfu.clok.settings.settingsScreen.settingsApp.settingsThemeScreen.ThemeType
import com.kingfu.clok.settings.viewModel.settingsViewModel.SettingsViewModel
import com.kingfu.clok.settings.viewModel.settingsViewModelStopwatch.SettingsViewModelStopwatch
import com.kingfu.clok.settings.viewModel.settingsViewModelTimer.SettingsViewModelTimer
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchFactory
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel
import com.kingfu.clok.timer.timerViewModel.TimerFactory
import com.kingfu.clok.timer.timerViewModel.TimerViewModel
import com.kingfu.clok.ui.util.isPortrait
import com.kingfu.clok.ui.util.showSnackBar
import com.kingfu.clok.variable.Variable.isShowSnackbar
import com.kingfu.clok.variable.Variable.isShowTimerNotification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun AppScaffold(
    settingsViewModel: SettingsViewModel,
    theme: ThemeType
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    var isShowMenu by rememberSaveable { mutableStateOf(value = false) }
    val coroutine = rememberCoroutineScope()
    val stopwatchFactory = StopwatchFactory(
        stopwatchPreferences = StopwatchPreferences.getInstance(context = context),
        stopwatchLapDatabase = StopwatchLapDatabase.getInstance(context = context)
    )
    val timerFactory = TimerFactory(timerPreferences = TimerPreferences.getInstance(context = context))

    val stopwatchViewModel: StopwatchViewModel = viewModel(factory = stopwatchFactory)

    val timerViewModel: TimerViewModel = viewModel(factory = timerFactory)

    val settingsViewModelStopwatch: SettingsViewModelStopwatch = viewModel(factory = stopwatchFactory)

    val settingsViewModelTimer: SettingsViewModelTimer = viewModel(factory = timerFactory)

    val navigationPreferences: NavigationPreferences = NavigationPreferences.getInstance(context = context)

    val screens = listOf(Screen.Stopwatch, Screen.Timer)

    val floatAnimation = remember { Animatable(initialValue = 0f) }
    val animateFloatAnimation = {
        coroutine.launch {
            floatAnimation.animateTo(targetValue = 0f, keyframes {
                durationMillis = 1000
                -10f at 200
                10f at 400
                -5f at 600
                5f at 800
            })
        }
    }
    val goToStopwatch = {
        animateFloatAnimation()
        navController.navigate(route = Screen.Stopwatch.route) {
            coroutine.launch(context = Dispatchers.IO) {
                navigationPreferences.setStartRoute(string = Screen.Stopwatch.route)
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    val goToTimer = {
        animateFloatAnimation()
        navController.navigate(route = Screen.Timer.route) {
            coroutine.launch(context = Dispatchers.IO) {
                navigationPreferences.setStartRoute(string = Screen.Timer.route)
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    val goToSettingsScreen = { navController.navigate(route = Screen.Settings.route) }
    val goToBugReportScreen = { navController.navigate(route = Screen.BugReport.route) }
    val isNavigationVisible = (currentRoute == Screen.Stopwatch.route || currentRoute == Screen.Timer.route)
    val headerAnimation by animateFloatAsState(
        targetValue = if (isShowMenu) 90f else 0f,
        label = ""
    )

    val enter =  fadeIn(animationSpec = tween(durationMillis = 200))
    val exit = fadeOut(animationSpec = tween(durationMillis = 0))


    LaunchedEffect(key1 = isShowTimerNotification) {
        timerViewModel.timerNotification(context = context)
    }

    LaunchedEffect(key1 = isShowSnackbar) {
        showSnackBar(snackbarHostState = snackbarHostState)
    }

    fun toggleIsShowMenu() {
        isShowMenu = !isShowMenu
    }

    Scaffold(
        containerColor = Transparent,
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.imePadding()
            )
        },
        topBar = {
            AnimatedVisibility(
                visible = isNavigationVisible && isPortrait(),
                enter = enter,
                exit = exit
            ) {
                TopBar(
                    currentRoute = currentRoute,
                    goToSettingsScreen = goToSettingsScreen,
                    goToBugReportScreen = goToBugReportScreen,
                    isShowMenu = isShowMenu,
                    toggleIsShowMenu = { toggleIsShowMenu() },
                    animation = headerAnimation
                )
            }
        },
        bottomBar = {
            AnimatedVisibility(
                visible = isNavigationVisible && isPortrait(),
                enter = enter,
                exit = exit
            ) {
                BottomBar(
                    currentRoute = currentRoute,
                    goToStopwatch = goToStopwatch,
                    goToTimer = goToTimer,
                    theme = theme,
                    screens = screens,
                    floatAnimation = floatAnimation
                )
            }
        },
        content = { paddingValues ->
            AppHavHost(
                navController = navController,
                currentDestination = currentDestination,
                timerViewModel = timerViewModel,
                stopwatchViewModel = stopwatchViewModel,
                settingsViewModelStopwatch = settingsViewModelStopwatch,
                settingsViewModelTimer = settingsViewModelTimer,
                settingsViewModel = settingsViewModel,
                navigationPreferences = navigationPreferences,
                mainScaffoldPaddingValues = paddingValues,
                isPortrait = isPortrait(),
                theme = theme
            )
            AnimatedVisibility(
                visible = isNavigationVisible && !isPortrait(),
                enter = enter,
                exit = exit
            ) {
                CustomNavigationRail(
                    currentRoute = currentRoute,
                    goToStopwatch = goToStopwatch,
                    goToTimer = goToTimer,
                    goToSettingsScreen = goToSettingsScreen,
                    goToBugReportScreen = goToBugReportScreen,
                    theme = theme,
                    isShowMenu = isShowMenu,
                    toggleIsShowMenu = { toggleIsShowMenu() },
                    screens = screens,
                    headerAnimation = headerAnimation,
                    floatAnimation = floatAnimation
                )
            }
        }
    )
}
