package com.kingfu.clok.stopwatch.screen.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType.Companion.LongPress
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kingfu.clok.R
import com.kingfu.clok.core.ThemeType
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.TextBodyLarge


@Composable
fun ResetButton(
    modifier: Modifier = Modifier,
    time: Long,
    isActive: Boolean,
    reset: () -> Unit,
    setIsLap: (Boolean) -> Unit,
) {
    val haptic = LocalHapticFeedback.current
    val isEnabled = time > 0
    val containerColor by animateColorAsState(
        targetValue = if (isEnabled) colorScheme.secondary else colorScheme.inversePrimary,
        label = ""
    )

    Button(
        modifier = modifier,
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor
        ),
        onClick = {
            haptic.performHapticFeedback(hapticFeedbackType = LongPress)
            if (isActive) setIsLap(true) else reset()
        }
    ) {
        TextBodyLarge(
            text = stringResource(id = if (isActive) R.string.lap else R.string.reset),
            color = colorScheme.surface,
            maxLines = 1
        )
    }
}

@Composable
fun ResetButtonPreview(theme: ThemeType) {
    ClokTheme(
        theme = theme,
        content = {
            Surface {
                ResetButton(
                    time = 1L,
                    isActive = false,
                    reset = { },
                    setIsLap = { }
                )
            }
        }
    )
}

@Preview
@Composable
fun ResetButtonPreviewDark() {
    ResetButtonPreview(theme = ThemeType.DARK)
}

@Preview
@Composable
fun ResetButtonPreviewLight() {
    ResetButtonPreview(theme = ThemeType.LIGHT)
}