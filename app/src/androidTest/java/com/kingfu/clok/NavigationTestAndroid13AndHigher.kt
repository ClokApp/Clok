//package com.kingfu.clok
//
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.test.assertIsDisplayed
//import androidx.compose.ui.test.junit4.createAndroidComposeRule
//import androidx.compose.ui.test.onNodeWithText
//import androidx.compose.ui.test.performClick
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.compose.currentBackStackEntryAsState
//import androidx.navigation.compose.rememberNavController
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import androidx.test.rule.GrantPermissionRule
//import com.kingfu.clok.navigation.AppHavHost
//import com.kingfu.clok.navigation.Screen
//import com.kingfu.clok.navigation.bottomBar.BottomBar
//import com.kingfu.clok.repository.preferencesDataStore.NavigationPreferences
//import com.kingfu.clok.repository.preferencesDataStore.SettingsPreferences
//import com.kingfu.clok.repository.preferencesDataStore.StopwatchPreferences
//import com.kingfu.clok.repository.preferencesDataStore.TimerPreferences
//import com.kingfu.clok.repository.room.stopwatchRoom.StopwatchLapDatabase
//import com.kingfu.clok.settings.settingsScreen.settingsApp.settingsThemeScreen.ThemeType
//import com.kingfu.clok.settings.viewModel.settingsViewModel.SettingsFactory
//import com.kingfu.clok.settings.viewModel.settingsViewModel.SettingsViewModel
//import com.kingfu.clok.settings.viewModel.settingsViewModelStopwatch.SettingsViewModelStopwatch
//import com.kingfu.clok.settings.viewModel.settingsViewModelTimer.SettingsViewModelTimer
//import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchFactory
//import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel
//import com.kingfu.clok.timer.timerViewModel.TimerFactory
//import com.kingfu.clok.timer.timerViewModel.TimerViewModel
//import com.kingfu.clok.ui.theme.ClokTheme
//import com.kingfu.clok.ui.util.isPortrait
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//
//
//@RunWith(AndroidJUnit4::class)
//class NavigationTestAndroid13AndHigher {
//
//    @get:Rule
//    val composeRule = createAndroidComposeRule<MainActivity>()
//
//    @get:Rule
//    var mRuntimePermissionRule: GrantPermissionRule =
//        GrantPermissionRule.grant(android.Manifest.permission.POST_NOTIFICATIONS)
//
//    @Before
//    fun setUp() {
//
//        composeRule.activity.setContent {
//            val navController = rememberNavController()
//            val navBackStackEntry by navController.currentBackStackEntryAsState()
//            val currentDestination = navBackStackEntry?.destination
//            val context = LocalContext.current
//            val isPortrait = isPortrait()
//
//            val stopwatchViewModel: StopwatchViewModel = viewModel(
//                factory = StopwatchFactory(
//                    stopwatchPreferences = StopwatchPreferences.getInstance(context = context),
//                    stopwatchLapDatabase = StopwatchLapDatabase.getInstance(context = context)
//                )
//            )
//
//            val timerViewModel: TimerViewModel = viewModel(
//                factory = TimerFactory(timerPreferences = TimerPreferences.getInstance(context = context))
//            )
//
//            val settingsViewModelStopwatch: SettingsViewModelStopwatch = viewModel(
//                factory = StopwatchFactory(
//                    stopwatchPreferences = StopwatchPreferences.getInstance(context = context),
//                    stopwatchLapDatabase = StopwatchLapDatabase.getInstance(context = context)
//                )
//            )
//
//            val settingsViewModelTimer: SettingsViewModelTimer = viewModel(
//                factory = TimerFactory(timerPreferences = TimerPreferences.getInstance(context = context))
//            )
//
//            val navigationPreferences: NavigationPreferences =
//                NavigationPreferences.getInstance(context = context)
//
//            val settingsViewModel: SettingsViewModel = viewModel(
//                factory = SettingsFactory(
//                    settingsPreferences = SettingsPreferences.getInstance(context = context)
//                )
//            )
//
//            val theme = ThemeType.Dark
//
//            ClokTheme(
//                theme = theme
//            ) {
//                AppHavHost(
//                    navController = navController,
//                    currentDestination = currentDestination,
//                    timerViewModel = timerViewModel,
//                    stopwatchViewModel = stopwatchViewModel,
//                    settingsViewModelStopwatch = settingsViewModelStopwatch,
//                    settingsViewModelTimer = settingsViewModelTimer,
//                    settingsViewModel = settingsViewModel,
//                    navigationPreferences = navigationPreferences,
//                    mainScaffoldPaddingValues = PaddingValues(0.dp),
//                    isPortrait = isPortrait,
//                    theme = theme
//                )
//
//                BottomBar(
//                    currentRoute = currentDestination?.route,
//                    goToStopwatch = {
//                        navController.navigate(route = Screen.Stopwatch.route) {
//                            CoroutineScope(context = Dispatchers.IO).launch {
//                                navigationPreferences.setStartDestinationRoute(string = Screen.Stopwatch.route)
//                            }
//
//                            launchSingleTop = true
//                            restoreState = true
//                        }
//                    },
//                    goToTimer = {
//                        navController.navigate(route = Screen.Timer.route) {
//                            CoroutineScope(context = Dispatchers.IO).launch {
//                                navigationPreferences.setStartDestinationRoute(string = Screen.Timer.route)
//                            }
//
//                            launchSingleTop = true
//                            restoreState = true
//                        }
//                    },
//                    theme = theme,
//                    screens = screens,
//                    coroutine = coroutine
//                )
//            }
//        }
//    }
//
//
//    @Test
//    fun testAppNavigationWorksProperly() {
//        val context = composeRule.activity.applicationContext
//
//        composeRule
//            .onNodeWithText(text = context.getString(R.string.stopwatch_start_button_start))
//            .assertIsDisplayed()
//
//        composeRule
//            .onNodeWithText(text = context.getString(R.string.timer_name_id))
//            .performClick()
//
//        composeRule
//            .onNodeWithText(text = context.getString(R.string.timer_start_button_start))
//            .assertIsDisplayed()
//    }
//}