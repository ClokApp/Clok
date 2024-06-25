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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType.Companion.LongPress
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kingfu.clok.R
import com.kingfu.clok.core.ThemeType
import com.kingfu.clok.timer.notification.TimerNotificationService
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.TextBodyLarge
import kotlinx.coroutines.launch

@Composable
fun ResetButton(
    modifier: Modifier = Modifier,
    lazyListHr: LazyListState,
    lazyListMin: LazyListState,
    lazyListSec: LazyListState,
    hour: Int,
    minute: Int,
    second: Int,
    isEdit: Boolean,
    reset: () -> Unit,
    cancel: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current
    val containerColor by animateColorAsState(
        targetValue = colorScheme.secondary,
        label = ""
    )

    Button(
        enabled = hour != 0 || minute != 0 || second != 0,
        modifier = modifier,
        shape = RoundedCornerShape(percent = 50),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor
        ),
        onClick = {
            haptic.performHapticFeedback(hapticFeedbackType = LongPress)
            if (isEdit) {
                reset()
                scope.launch {
                    lazyListHr.scrollToItem(index = Int.MAX_VALUE / 2 - 24)
                    lazyListMin.scrollToItem(index = Int.MAX_VALUE / 2 - 4)
                    lazyListSec.scrollToItem(index = Int.MAX_VALUE / 2 - 4)
                }
            } else {
                cancel()
            }
            TimerNotificationService(context = context).cancelNotification()
        }
    ) {
        TextBodyLarge(
            text = stringResource(id = if (isEdit) R.string.reset else R.string.cancel),
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
                    lazyListHr = rememberLazyListState(),
                    lazyListMin = rememberLazyListState(),
                    lazyListSec = rememberLazyListState(),
                    hour = 0,
                    minute = 0,
                    second = 1,
                    isEdit = true,
                    reset = {},
                    cancel = {}
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