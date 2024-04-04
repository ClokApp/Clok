package com.kingfu.clok.timer.component

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kingfu.clok.R
import com.kingfu.clok.notification.timer.TimerNotificationService
import com.kingfu.clok.ui.theme.ThemeType
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.typography
import com.kingfu.clok.ui.util.nonScaledSp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TimerResetButton(
    lazyListStateHr: LazyListState,
    lazyListStateMin: LazyListState,
    lazyListStateSec: LazyListState,
    coroutineScopeTimer: CoroutineScope,
    haptic: HapticFeedback,
    context: Context,
    timerHour: Int,
    timerMinute: Int,
    timerSecond: Int,
    isLoadInitialTime: Boolean,
    timerIsEditState: Boolean,
    resetTimer: () -> Unit,
    cancelTimer: () -> Unit
) {
    val resetButtonColor by animateColorAsState(
        targetValue = colorScheme.secondary,
        label = ""
    )

    val padding by animateDpAsState(
        targetValue =
        if ((timerHour != 0 || timerMinute != 0 || timerSecond != 0)
            && (!isLoadInitialTime || !timerIsEditState)
        ) 25.dp else 0.dp,
        animationSpec = tween(durationMillis = 100),
        label = ""
    )

    AnimatedVisibility(
        visible =
        (timerHour != 0 || timerMinute != 0 || timerSecond != 0) &&
                (!isLoadInitialTime || !timerIsEditState),
        enter = slideInHorizontally(
            initialOffsetX = { 250 },
            animationSpec = tween(
                durationMillis = 50,
                easing = LinearEasing
            )
        ),
        exit = slideOutHorizontally(
            targetOffsetX = { 250 },
            animationSpec = tween(
                durationMillis = 50,
                easing = LinearEasing
            )
        ),
        content = {
            Button(
                modifier = Modifier.padding(end = padding),
                shape = RoundedCornerShape(percent = 50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = resetButtonColor
                ),
                onClick = {
                    haptic.performHapticFeedback(hapticFeedbackType = LongPress)
                    if (timerIsEditState) {
                        resetTimer()
                        coroutineScopeTimer.launch {
                            lazyListStateHr.scrollToItem(index = Int.MAX_VALUE / 2 - 24)
                            lazyListStateMin.scrollToItem(index = Int.MAX_VALUE / 2 - 4)
                            lazyListStateSec.scrollToItem(index = Int.MAX_VALUE / 2 - 4)
                        }
                        TimerNotificationService(context = context).cancelNotification()
                    } else {
                        cancelTimer()
                    }
                }
            ) {
                Text(
                    text = if (timerIsEditState) {
                        stringResource(id = R.string.timer_reset_button_reset)
                    } else {
                        stringResource(id = R.string.timer_reset_button_cancel)
                    },
                    fontSize = typography.bodyLarge.fontSize.value.nonScaledSp,
                    modifier = Modifier.padding(horizontal = if (timerIsEditState) 10.dp else 8.dp),
                    style = TextStyle()
                )
            }
        }
    )
}

@Preview
@Composable
fun TimerResetButtonPreview() {
    ClokTheme(
        dynamicColor = true,
        theme = ThemeType.Light
    ) {

        TimerResetButton(
            lazyListStateHr = rememberLazyListState(),
            lazyListStateMin = rememberLazyListState(),
            lazyListStateSec = rememberLazyListState(),
            coroutineScopeTimer = rememberCoroutineScope(),
            haptic = LocalHapticFeedback.current,
            context = LocalContext.current,
            timerHour = 0,
            timerMinute = 0,
            timerSecond = 1,
            isLoadInitialTime = false,
            timerIsEditState = true,
            resetTimer = {},
            cancelTimer = {}
        )
    }
}