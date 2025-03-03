package com.kingfu.clok

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import androidx.navigation.testing.TestNavHostController
import com.kingfu.clok.navigation.AppNavHost
import com.kingfu.clok.ui.theme.ClokTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTest {
    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        rule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            navController.navigatorProvider.addNavigator(DialogNavigator())
            ClokTheme {
                AppNavHost(navController = navController)
            }
        }
    }


    // Unit test
    @Test
    fun appNavHost_verifyStartDestination() {
        rule
            .onNodeWithText(text = rule.activity.getString(R.string.stopwatch))
            .assertIsDisplayed()
    }

    @Test
    fun goToTimerScreen(){
        rule
            .onNodeWithText(text = rule.activity.getString(R.string.timer))
            .performClick()

        rule
            .onNodeWithText(text = rule.activity.getString(R.string.seconds))
            .assertIsDisplayed()
    }


}

