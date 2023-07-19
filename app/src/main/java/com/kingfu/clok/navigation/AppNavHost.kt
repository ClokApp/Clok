package com.kingfu.clok.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kingfu.clok.bugReport.BugReportScreen
import com.kingfu.clok.components.topBar.LargeTopBar
import com.kingfu.clok.repository.preferencesDataStore.NavigationPreferences
import com.kingfu.clok.settings.settingsScreen.SettingsScreen
import com.kingfu.clok.settings.settingsScreen.settingsStopwatchScreen.SettingsStopwatchBackgroundEffects
import com.kingfu.clok.settings.settingsScreen.settingsStopwatchScreen.SettingsStopwatchLabelStyle
import com.kingfu.clok.settings.settingsScreen.settingsStopwatchScreen.fontStyle.SettingsStopwatchFontStylesScreen
import com.kingfu.clok.settings.settingsScreen.settingsStopwatchScreen.fontStyle.SettingsStopwatchSelectedFontStyleScreen
import com.kingfu.clok.settings.settingsScreen.settingsTimerScreen.SettingsTimerBackgroundEffects
import com.kingfu.clok.settings.settingsScreen.settingsTimerScreen.SettingsTimerProgressBarStyle
import com.kingfu.clok.settings.settingsScreen.settingsTimerScreen.SettingsTimerScrollsHapticFeedback
import com.kingfu.clok.settings.settingsScreen.settingsTimerScreen.fontStyle.SettingsTimerFontStylesScreen
import com.kingfu.clok.settings.settingsScreen.settingsTimerScreen.fontStyle.SettingsTimerSelectedFontStyleScreen
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer
import com.kingfu.clok.stopwatch.stopwatchScreen.StopwatchScreen
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel
import com.kingfu.clok.timer.timerScreen.TimerScreen
import com.kingfu.clok.timer.timerViewModel.TimerViewModel
import com.kingfu.clok.ui.theme.Black00
import com.kingfu.clok.variable.Variable.navigateToStartScreen
import com.kingfu.clok.variable.Variable.startDestination
import kotlinx.coroutines.flow.first


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppHavHost(
    navController: NavHostController,
    currentDestination: NavDestination?,
    timerViewModel: TimerViewModel,
    stopwatchViewModel: StopwatchViewModel,
    settingsViewModelStopwatch: SettingsViewModelStopwatch,
    settingsViewModelTimer: SettingsViewModelTimer,
    navigationPreferences: NavigationPreferences,
    mainScaffoldPaddingValues: PaddingValues,

    ) {
    LaunchedEffect(key1 = Unit) {
//    LaunchedEffect(key1 = startDestination) {
        if (navigateToStartScreen) {
            startDestination = navigationPreferences.getStartDestination.first()
        }
        navigateToStartScreen = false
    }

    if (startDestination != null) {
        NavHost(
            navController = navController,
            startDestination = startDestination!!,
            modifier = Modifier.background(color = Black00)
        ) {

            composable(route = Screens.Stopwatch.route) {
                Box(modifier = Modifier.padding(paddingValues = mainScaffoldPaddingValues)) {
                    StopwatchScreen(
                        vm = stopwatchViewModel,
                        settingsViewModelStopwatch = settingsViewModelStopwatch,
                    )
                }
            }

            composable(route = Screens.Timer.route) {
                Box(modifier = Modifier.padding(paddingValues = mainScaffoldPaddingValues)) {
                    TimerScreen(
                        vm = timerViewModel,
                        settingsViewModelTimer = settingsViewModelTimer
                    )
                }
            }

            composable(route = Screens.Settings.route) {
                val topBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

                Scaffold(
                    modifier = Modifier.nestedScroll(connection = topBarScrollBehavior.nestedScrollConnection),
                    containerColor = Transparent,
                    topBar = {
                        LargeTopBar(
                            topBarScrollBehavior = topBarScrollBehavior,
                            currentDestination = currentDestination,
                            navigateUp = { navController.navigateUp() },
                        )
                    },
                    content = { paddingValue ->
                        Box(modifier = Modifier.padding(paddingValues = paddingValue)) {
                            val vm: SettingsViewModelStopwatch = settingsViewModelStopwatch
                            SettingsScreen(
                                settingsViewModelStopwatch = vm,
                                settingsViewModelTimer = settingsViewModelTimer,
                                navigateToSettingsStopwatchBackgroundEffects = {
                                    navController.navigate(
                                        route = Screens.SettingsStopwatchBackgroundEffects.route
                                    )
                                },
                                navigateToSettingsStopwatchFontStyles = {
                                    navController.navigate(
                                        route = Screens.SettingsStopwatchFontStyles.route
                                    )
                                },
                                navigateToSettingsStopwatchLabelStyles = {
                                    navController.navigate(
                                        route = Screens.SettingsStopwatchLabelStyles.route
                                    )
                                },
                                navigateToSettingsTimerProgressBarStyles = {
                                    navController.navigate(
                                        route = Screens.SettingsTimerProgressBarStyles.route
                                    )
                                },
                                navigateToSettingsTimerFontStyles = {
                                    navController.navigate(
                                        route = Screens.SettingsTimerFontStyles.route
                                    )
                                },
                                navigateToSettingsTimerBackgroundEffects = {
                                    navController.navigate(
                                        route = Screens.SettingsTimerBackgroundEffects.route
                                    )
                                },
                                navigateToSettingsTimerScrollsHapticFeedback = {
                                    navController.navigate(
                                        route = Screens.SettingsTimerScrollsHapticFeedback.route
                                    )
                                }
                            )
                        }
                    }
                )
            }

            composable(route = Screens.SettingsStopwatchLabelStyles.route) {
                val topBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

                Scaffold(
                    modifier = Modifier.nestedScroll(connection = topBarScrollBehavior.nestedScrollConnection),
                    containerColor = Transparent,
                    topBar = {
                        LargeTopBar(
                            topBarScrollBehavior = topBarScrollBehavior,
                            currentDestination = currentDestination,
                            navigateUp = { navController.navigateUp() },
                        )
                    },
                    content = { paddingValue ->
                        Box(modifier = Modifier.padding(paddingValues = paddingValue)) {
                            SettingsStopwatchLabelStyle(vm = settingsViewModelStopwatch)
                        }
                    }
                )
            }

            composable(route = Screens.SettingsTimerProgressBarStyles.route) {
                val topBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

                Scaffold(
                    modifier = Modifier.nestedScroll(connection = topBarScrollBehavior.nestedScrollConnection),
                    containerColor = Transparent,
                    topBar = {
                        LargeTopBar(
                            topBarScrollBehavior = topBarScrollBehavior,
                            currentDestination = currentDestination,
                            navigateUp = { navController.navigateUp() },
                        )
                    },
                    content = { paddingValue ->
                        Box(modifier = Modifier.padding(paddingValues = paddingValue)) {
                            SettingsTimerProgressBarStyle(vm = settingsViewModelTimer)
                        }
                    }
                )
            }

            composable(route = Screens.BugReport.route) {
                val topBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

                Scaffold(
                    modifier = Modifier.nestedScroll(connection = topBarScrollBehavior.nestedScrollConnection),
                    containerColor = Transparent,
                    topBar = {
                        LargeTopBar(
                            topBarScrollBehavior = topBarScrollBehavior,
                            currentDestination = currentDestination,
                            navigateUp = { navController.navigateUp() },
                        )
                    },
                    content = { paddingValue ->
                        Box(modifier = Modifier.padding(paddingValues = paddingValue)) {
                            BugReportScreen()
                        }
                    }
                )
            }

            composable(route = Screens.SettingsStopwatchBackgroundEffects.route) {
                val topBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

                Scaffold(
                    modifier = Modifier.nestedScroll(connection = topBarScrollBehavior.nestedScrollConnection),
                    containerColor = Transparent,
                    topBar = {
                        LargeTopBar(
                            topBarScrollBehavior = topBarScrollBehavior,
                            currentDestination = currentDestination,
                            navigateUp = { navController.navigateUp() },
                        )
                    },
                    content = { paddingValue ->
                        Box(modifier = Modifier.padding(paddingValues = paddingValue)) {
                            SettingsStopwatchBackgroundEffects(vm = settingsViewModelStopwatch)
                        }
                    }
                )
            }

            composable(route = Screens.SettingsTimerBackgroundEffects.route) {
                val topBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

                Scaffold(
                    modifier = Modifier.nestedScroll(connection = topBarScrollBehavior.nestedScrollConnection),
                    containerColor = Transparent,
                    topBar = {
                        LargeTopBar(
                            topBarScrollBehavior = topBarScrollBehavior,
                            currentDestination = currentDestination,
                            navigateUp = { navController.navigateUp() },
                        )
                    },
                    content = { paddingValue ->
                        Box(modifier = Modifier.padding(paddingValues = paddingValue)) {
                            SettingsTimerBackgroundEffects(vm = settingsViewModelTimer)
                        }
                    }
                )
            }

            composable(route = Screens.SettingsTimerFontStyles.route) {
                val topBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

                Scaffold(
                    modifier = Modifier.nestedScroll(connection = topBarScrollBehavior.nestedScrollConnection),
                    containerColor = Transparent,
                    topBar = {
                        LargeTopBar(
                            topBarScrollBehavior = topBarScrollBehavior,
                            currentDestination = currentDestination,
                            navigateUp = { navController.navigateUp() },
                        )
                    },
                    content = { paddingValue ->
                        Box(modifier = Modifier.padding(paddingValues = paddingValue)) {
                            SettingsTimerFontStylesScreen(
                                vm = settingsViewModelTimer,
                                navigateToSettingsTimerSelectedFontStyle = {
                                    navController.navigate(
                                        route = Screens.SettingsTimerSelectedFontStyle.route
                                    )
                                }
                            )
                        }
                    }
                )
            }

            composable(route = Screens.SettingsTimerSelectedFontStyle.route) {
                val topBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

                Scaffold(
                    modifier = Modifier.nestedScroll(connection = topBarScrollBehavior.nestedScrollConnection),
                    containerColor = Transparent,
                    topBar = {
                        LargeTopBar(
                            topBarScrollBehavior = topBarScrollBehavior,
                            currentDestination = currentDestination,
                            navigateUp = { navController.navigateUp() },
                        )
                    },
                    content = { paddingValues ->
                        Box(modifier = Modifier.padding(paddingValues = paddingValues)) {
                            SettingsTimerSelectedFontStyleScreen(vm = settingsViewModelTimer)
                        }
                    }
                )
            }

            composable(route = Screens.SettingsStopwatchFontStyles.route) {
                val topBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

                Scaffold(
                    modifier = Modifier.nestedScroll(connection = topBarScrollBehavior.nestedScrollConnection),
                    containerColor = Transparent,
                    topBar = {
                        LargeTopBar(
                            topBarScrollBehavior = topBarScrollBehavior,
                            currentDestination = currentDestination,
                            navigateUp = { navController.navigateUp() },
                        )
                    },
                    content = { paddingValues ->
                        Box(modifier = Modifier.padding(paddingValues = paddingValues)) {
                            SettingsStopwatchFontStylesScreen(
                                vm = settingsViewModelStopwatch,
                                navigateToSettingsStopwatchSelectedFontStyle = {
                                    navController.navigate(
                                        Screens.SettingsStopwatchSelectedFontStyle.route
                                    )
                                }
                            )
                        }
                    }
                )
            }

            composable(route = Screens.SettingsStopwatchSelectedFontStyle.route) {
                val topBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

                Scaffold(
                    modifier = Modifier.nestedScroll(connection = topBarScrollBehavior.nestedScrollConnection),
                    containerColor = Transparent,
                    topBar = {
                        LargeTopBar(
                            topBarScrollBehavior = topBarScrollBehavior,
                            currentDestination = currentDestination,
                            navigateUp = { navController.navigateUp() },
                        )
                    },
                    content = { paddingValues ->
                        Box(modifier = Modifier.padding(paddingValues = paddingValues)) {
                            SettingsStopwatchSelectedFontStyleScreen(vm = settingsViewModelStopwatch)
                        }
                    }
                )
            }

            composable(route = Screens.SettingsTimerScrollsHapticFeedback.route) {
                val topBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

                Scaffold(
                    modifier = Modifier.nestedScroll(connection = topBarScrollBehavior.nestedScrollConnection),
                    containerColor = Transparent,
                    topBar = {
                        LargeTopBar(
                            topBarScrollBehavior = topBarScrollBehavior,
                            currentDestination = currentDestination,
                            navigateUp = { navController.navigateUp() },
                        )
                    },
                    content = { paddingValues ->
                        Box(modifier = Modifier.padding(paddingValues = paddingValues)) {
                            SettingsTimerScrollsHapticFeedback(vm = settingsViewModelTimer)
                        }
                    }
                )
            }
        }
    }
}


