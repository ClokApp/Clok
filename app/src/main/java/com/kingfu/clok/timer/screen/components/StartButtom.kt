package com.kingfu.clok.timer.screen.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType.Companion.LongPress
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kingfu.clok.R
import com.kingfu.clok.core.ThemeType
import com.kingfu.clok.core.timeToMilliseconds
import com.kingfu.clok.timer.notification.TimerNotificationService
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.TextBodyLarge

@Composable
fun StartButton(
    modifier: Modifier = Modifier,
    lazyListHr: LazyListState,
    lazyListMin: LazyListState,
    lazyListSec: LazyListState,
    isEdit: Boolean,
    hour: Int,
    minute: Int,
    second: Int,
    isActive: Boolean,
    pause: () -> Unit,
    setTotalTime: (Long) -> Unit,
    start: (long: Long) -> Unit,
    setOffsetTime: (Long) -> Unit
) {
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current
    val containerColor by animateColorAsState(
        when {
            lazyListHr.isScrollInProgress ||
                    lazyListMin.isScrollInProgress ||
                    lazyListSec.isScrollInProgress ||
                    hour == 0 && minute == 0 && second == 0
            -> colorScheme.inversePrimary

            isActive && !isEdit -> colorScheme.tertiary
            else -> colorScheme.primary
        },
        label = "",
    )

    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
        ),
        enabled = containerColor != colorScheme.inversePrimary,
        shape = RoundedCornerShape(percent = 50),
        onClick = {
            haptic.performHapticFeedback(hapticFeedbackType = LongPress)
            val time = timeToMilliseconds(
                hours = hour,
                minutes = minute,
                seconds = second
            )

            if (isEdit) {
                setOffsetTime(time)
                setTotalTime(time)
            }
            if (isActive) pause() else start(time)
            TimerNotificationService(context = context).cancelNotification()
        }
    ) {
        val text = stringResource(id = if (isActive) R.string.pause else R.string.start)
        TextBodyLarge(
            text = text,
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
                    lazyListHr = rememberLazyListState(),
                    lazyListMin = rememberLazyListState(),
                    lazyListSec = rememberLazyListState(),
                    isEdit = false,
                    hour = 1,
                    minute = 0,
                    second = 0,
                    isActive = false,
                    pause = { },
                    setTotalTime = { },
                    start = { },
                    setOffsetTime = { }
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



