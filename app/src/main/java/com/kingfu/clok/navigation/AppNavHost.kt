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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kingfu.clok.bugReport.BugReport
import com.kingfu.clok.components.topBar.LargeTopBar
import com.kingfu.clok.repository.preferencesDataStore.NavigationPreferences
import com.kingfu.clok.settings.settingsView.SettingsView
import com.kingfu.clok.settings.settingsView.settingsStopwatchView.SettingsStopwatchBackgroundEffects
import com.kingfu.clok.settings.settingsView.settingsStopwatchView.SettingsStopwatchLabelStyle
import com.kingfu.clok.settings.settingsView.settingsStopwatchView.fontStyle.SettingsStopwatchFontStylesView
import com.kingfu.clok.settings.settingsView.settingsStopwatchView.fontStyle.SettingsStopwatchSelectedFontStyleView
import com.kingfu.clok.settings.settingsView.settingsTimerView.SettingsTimerBackgroundEffects
import com.kingfu.clok.settings.settingsView.settingsTimerView.SettingsTimerProgressBarStyle
import com.kingfu.clok.settings.settingsView.settingsTimerView.SettingsTimerScrollsHapticFeedback
import com.kingfu.clok.settings.settingsView.settingsTimerView.fontStyle.SettingsTimerFontStyles
import com.kingfu.clok.settings.settingsView.settingsTimerView.fontStyle.SettingsTimerSelectedFontStyleView
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer
import com.kingfu.clok.stopwatch.stopwatchView.StopwatchView
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel
import com.kingfu.clok.timer.timerView.TimerView
import com.kingfu.clok.timer.timerViewModel.TimerViewModel
import com.kingfu.clok.ui.theme.Black00
import com.kingfu.clok.variable.Variable.navigateToStartScreen
import com.kingfu.clok.variable.Variable.startDestination
import kotlinx.coroutines.flow.first


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppHavHost(
    navController: NavHostController,
    timerViewModel: TimerViewModel,
    stopwatchViewModel: StopwatchViewModel,
    settingsViewModelStopwatch: SettingsViewModelStopwatch,
    settingsViewModelTimer: SettingsViewModelTimer,
    navigationPreferences: NavigationPreferences,
    mainScaffoldPaddingValues: PaddingValues,
) {
    LaunchedEffect(key1 = Unit) {
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
                    StopwatchView(
                        navController = navController,
                        vm = stopwatchViewModel,
                        settingsViewModelStopwatch = settingsViewModelStopwatch
                    )
                }
            }

            composable(route = Screens.Timer.route) {
                Box(modifier = Modifier.padding(paddingValues = mainScaffoldPaddingValues)) {
                    TimerView(
                        navController = navController,
                        vm = timerViewModel,
                        settingsViewModelTimer = settingsViewModelTimer
                    )
                }
            }

            composable(route = Screens.Settings.route) {
                val topBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

                Scaffold(
                    modifier = Modifier.nestedScroll(connection = topBarScrollBehavior.nestedScrollConnection),
                    containerColor = Color.Transparent,
                    topBar = {
                        LargeTopBar(
                            navController = navController,
                            topBarScrollBehavior = topBarScrollBehavior,
                        )
                    },
                    content = { paddingValue ->
                        Box(modifier = Modifier.padding(paddingValues = paddingValue)) {
                            SettingsView(
                                navController = navController,
                                settingsViewModelStopwatch = settingsViewModelStopwatch,
                                settingsViewModelTimer = settingsViewModelTimer,
                            )
                        }
                    }
                )
            }

            composable(route = Screens.SettingsStopwatchLabelStyles.route) {
                val topBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

                Scaffold(
                    modifier = Modifier.nestedScroll(connection = topBarScrollBehavior.nestedScrollConnection),
                    containerColor = Color.Transparent,
                    topBar = {
                        LargeTopBar(
                            navController = navController,
                            topBarScrollBehavior = topBarScrollBehavior,
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
                    containerColor = Color.Transparent,
                    topBar = {
                        LargeTopBar(
                            navController = navController,
                            topBarScrollBehavior = topBarScrollBehavior,
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
                    containerColor = Color.Transparent,
                    topBar = {
                        LargeTopBar(
                            navController = navController,
                            topBarScrollBehavior = topBarScrollBehavior,
                        )
                    },
                    content = { paddingValue ->
                        Box(modifier = Modifier.padding(paddingValues = paddingValue)) {
                            BugReport()
                        }
                    }
                )
            }

            composable(route = Screens.SettingsStopwatchBackgroundEffects.route) {
                val topBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

                Scaffold(
                    modifier = Modifier.nestedScroll(connection = topBarScrollBehavior.nestedScrollConnection),
                    containerColor = Color.Transparent,
                    topBar = {
                        LargeTopBar(
                            navController = navController,
                            topBarScrollBehavior = topBarScrollBehavior,
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
                    containerColor = Color.Transparent,
                    topBar = {
                        LargeTopBar(
                            navController = navController,
                            topBarScrollBehavior = topBarScrollBehavior,
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
                    containerColor = Color.Transparent,
                    topBar = {
                        LargeTopBar(
                            navController = navController,
                            topBarScrollBehavior = topBarScrollBehavior,
                        )
                    },
                    content = { paddingValue ->
                        Box(modifier = Modifier.padding(paddingValues = paddingValue)) {
                            SettingsTimerFontStyles(
                                vm = settingsViewModelTimer,
                                navController = navController
                            )
                        }
                    }
                )
            }

            composable(route = Screens.SettingsTimerSelectedFontStyle.route) {
                val topBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

                Scaffold(
                    modifier = Modifier.nestedScroll(connection = topBarScrollBehavior.nestedScrollConnection),
                    containerColor = Color.Transparent,
                    topBar = {
                        LargeTopBar(
                            navController = navController,
                            topBarScrollBehavior = topBarScrollBehavior,
                        )
                    },
                    content = { paddingValue ->
                        Box(modifier = Modifier.padding(paddingValues = paddingValue)) {
                            SettingsTimerSelectedFontStyleView(vm = settingsViewModelTimer)
                        }
                    }
                )
            }

            composable(route = Screens.SettingsStopwatchFontStyles.route) {
                val topBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

                Scaffold(
                    modifier = Modifier.nestedScroll(connection = topBarScrollBehavior.nestedScrollConnection),
                    containerColor = Color.Transparent,
                    topBar = {
                        LargeTopBar(
                            navController = navController,
                            topBarScrollBehavior = topBarScrollBehavior,
                        )
                    },
                    content = { paddingValue ->
                        Box(modifier = Modifier.padding(paddingValues = paddingValue)) {
                            SettingsStopwatchFontStylesView(
                                vm = settingsViewModelStopwatch,
                                navController = navController
                            )
                        }
                    }
                )
            }

            composable(route = Screens.SettingsStopwatchSelectedFontStyle.route) {
                val topBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

                Scaffold(
                    modifier = Modifier.nestedScroll(connection = topBarScrollBehavior.nestedScrollConnection),
                    containerColor = Color.Transparent,
                    topBar = {
                        LargeTopBar(
                            navController = navController,
                            topBarScrollBehavior = topBarScrollBehavior,
                        )
                    },
                    content = { paddingValue ->
                        Box(modifier = Modifier.padding(paddingValues = paddingValue)) {
                            SettingsStopwatchSelectedFontStyleView(vm = settingsViewModelStopwatch)
                        }
                    }
                )
            }

            composable(route = Screens.SettingsTimerScrollsHapticFeedback.route) {
                val topBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

                Scaffold(
                    modifier = Modifier.nestedScroll(connection = topBarScrollBehavior.nestedScrollConnection),
                    containerColor = Color.Transparent,
                    topBar = {
                        LargeTopBar(
                            navController = navController,
                            topBarScrollBehavior = topBarScrollBehavior,
                        )
                    },
                    content = { paddingValue ->
                        Box(modifier = Modifier.padding(paddingValues = paddingValue)) {
                            SettingsTimerScrollsHapticFeedback(vm = settingsViewModelTimer)
                        }
                    }
                )
            }
        }
    }
}