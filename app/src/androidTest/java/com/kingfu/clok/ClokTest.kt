package com.kingfu.clok

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.kingfu.clok.stopwatch.screen.StopwatchScreenPreview
import org.junit.Rule
import org.junit.Test

class ClokTest {

    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun stopwatch_showStartButton() {
        rule.setContent { StopwatchScreenPreview() }

        // Do something
        rule.onNodeWithText(text = rule.activity.getString(R.string.start)).performClick()

        // Check something
        rule.onNodeWithText(text = rule.activity.getString(R.string.pause)).assertExists()

        rule.onNodeWithText(text = rule.activity.getString(R.string.pause)).performClick()

        rule.onNodeWithText(text = rule.activity.getString(R.string.start)).assertExists()

    }
}