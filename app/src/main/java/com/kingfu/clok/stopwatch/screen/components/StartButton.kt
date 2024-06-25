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
fun StartButton(
    modifier: Modifier = Modifier,
    isActive: Boolean,
    pause: () -> Unit,
    saveOffsetTime: () -> Unit,
    start: () -> Unit
) {
    val haptic = LocalHapticFeedback.current
    val containerColor by animateColorAsState(
        targetValue = if (isActive) colorScheme.tertiary else colorScheme.primary,
        label = ""
    )

    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor
        ),
        onClick = {
            haptic.performHapticFeedback(hapticFeedbackType = LongPress)
            if (isActive) {
                pause()
                saveOffsetTime()
            } else {
                start()
            }
        }
    ) {
        TextBodyLarge(
            text = stringResource(id = if (isActive) R.string.pause else R.string.start),
            color = colorScheme.surface,
            maxLines = 1
        )
    }
}

@Composable
fun StartButtonPreview(theme: ThemeType) {
    ClokTheme(
        theme = theme,
        content = {
            Surface {
                StartButton(
                    isActive = false,
                    pause = {},
                    saveOffsetTime = {},
                    start = {}
                )
            }
        }
    )
}

@Preview
@Composable
fun StartButtonPreviewDark() {
    StartButtonPreview(theme = ThemeType.DARK)
}

@Preview
@Composable
fun StartButtonPreviewLight() {
    StartButtonPreview(theme = ThemeType.LIGHT)
}