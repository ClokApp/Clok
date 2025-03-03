package com.kingfu.clok

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.kingfu.clok.stopwatch.screen.StopwatchScreenPreview
import org.junit.Rule
import org.junit.Test

class StopwatchTest {
    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun stopwatch_test() {
        rule.setContent { StopwatchScreenPreview() }


        val time = rule.onNodeWithContentDescription(label = "time/time")
        val lapTime = rule.onNodeWithContentDescription(label = "time/lap_time")

        time.assertIsDisplayed()
        lapTime.assertIsDisplayed()





    }
}