package com.kingfu.clok.stopwatch.stopwatchScreen

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.kingfu.clok.ui.theme.PreviewDynamicColors
import com.kingfu.clok.ui.theme.typography
import com.kingfu.clok.ui.util.nonScaledSp


@Composable
fun StopwatchResetButton(
    haptic: HapticFeedback,
    stopwatchTime: Long,
    stopwatchIsActive: Boolean,
    lap: () -> Unit,
    isScrollLazyColumn: Boolean,
    toggleIsScrollLazyColumn: () -> Unit,
    resetStopwatch: () -> Unit,
    clearLapTimes: () -> Unit
) {

    val resetButtonColor by animateColorAsState(
        targetValue = if (stopwatchTime > 0) {
            colorScheme.secondary
        } else {
            colorScheme.inversePrimary
        },
        label = ""
    )

    Button(
        enabled = stopwatchTime > 0,
        shape = RoundedCornerShape(percent = 50),
        colors = ButtonDefaults.buttonColors(
            containerColor = resetButtonColor
        ),
        onClick = {
            haptic.performHapticFeedback(hapticFeedbackType = LongPress)
            if (stopwatchIsActive) {
                lap()
                if (!isScrollLazyColumn) {
                    toggleIsScrollLazyColumn()
                }
            } else {
                resetStopwatch()
                clearLapTimes()
            }
        },
    ) {
        Text(
            text = if (stopwatchIsActive) {
                stringResource(id = R.string.stopwatch_reset_button_lap)
            } else {
                stringResource(id = R.string.stopwatch_reset_button_reset)
            },
            modifier = Modifier.padding(horizontal = if (stopwatchIsActive) 18.dp else 10.dp),
            fontSize = typography.bodyLarge.fontSize.value.nonScaledSp,
            maxLines = 1,
            style = TextStyle()
        )
    }
}

@PreviewDynamicColors
@Preview(showBackground = true)
@Composable
fun StopwatchResetButtonPreview() {
    ClokTheme(
        dynamicColor = true,
        theme = ThemeType.Light
    ) {
        StopwatchResetButton(
            haptic = LocalHapticFeedback.current,
            stopwatchTime = 1L,
            stopwatchIsActive = false,
            lap = {},
            isScrollLazyColumn = false,
            toggleIsScrollLazyColumn = {},
            resetStopwatch = {},
            clearLapTimes = {}
        )
    }
}