package com.kingfu.clok.stopwatch.stopwatchScreen

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType.Companion.LongPress
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kingfu.clok.R
import com.kingfu.clok.settings.settingsScreen.settingsApp.settingsThemeScreen.ThemeType
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.themeBackgroundColor
import com.kingfu.clok.ui.theme.typography
import com.kingfu.clok.ui.util.nonScaledSp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun StopwatchStartButton(
    haptic: HapticFeedback,
    coroutineScopeStopwatch: CoroutineScope,
    stopwatchIsActive: Boolean,
    pauseStopwatch: () -> Unit,
    saveStopwatchLapPreviousTime: () -> Unit,
    saveStopwatchOffsetTime: () -> Unit,
    startStopwatch: () -> Unit
) {
    val startStopWatchColor by animateColorAsState(
        targetValue = if (stopwatchIsActive) colorScheme.tertiary else colorScheme.primary,
        label = ""
    )


    Button(
        shape = RoundedCornerShape(percent = 50),
        colors = ButtonDefaults.buttonColors(
            containerColor = startStopWatchColor
        ),
        onClick = {
            haptic.performHapticFeedback(hapticFeedbackType = LongPress)

            if (stopwatchIsActive) {
                pauseStopwatch()
                coroutineScopeStopwatch.launch {
                    saveStopwatchLapPreviousTime()
                    saveStopwatchOffsetTime()
                }
            } else {
                startStopwatch()
            }
        }
    ) {
        Text(
            text = if (stopwatchIsActive) {
                stringResource(id = R.string.stopwatch_start_button_pause)
            } else {
                stringResource(id = R.string.stopwatch_start_button_start)
            },
            modifier = Modifier.padding(horizontal = if (stopwatchIsActive) 7.dp else 14.dp),
            fontSize = typography.bodyLarge.fontSize.value.nonScaledSp,
            style = TextStyle()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StopwatchStartButtonPreview() {
    val theme = ThemeType.Dark
    ClokTheme(
        dynamicColor = true,
        theme = theme
    ) {
        Column(modifier = Modifier.background(color = themeBackgroundColor(theme = theme))) {
            StopwatchStartButton(
                haptic = LocalHapticFeedback.current,
                coroutineScopeStopwatch = rememberCoroutineScope(),
                stopwatchIsActive = false,
                pauseStopwatch = {},
                saveStopwatchLapPreviousTime = {},
                saveStopwatchOffsetTime = {},
                startStopwatch = {}
            )
        }
    }
}