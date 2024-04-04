package com.kingfu.clok.timer.component

import android.content.Context
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.kingfu.clok.R
import com.kingfu.clok.notification.timer.TimerNotificationService
import com.kingfu.clok.ui.theme.typography
import com.kingfu.clok.ui.util.isPortrait
import com.kingfu.clok.ui.util.nonScaledSp

@Composable
fun TimerStartButton(
    lazyListStateHr: LazyListState,
    lazyListStateMin: LazyListState,
    lazyListStateSec: LazyListState,
    haptic: HapticFeedback,
    context: Context,
    timerIsEditState: Boolean,
    timerHour: Int,
    timerMinute: Int,
    timerSecond: Int,
    timerIsActive: Boolean,
    pauseTimer: () -> Unit,
    convertHrMinSecToMillis: () -> Unit,
    timerSetTotalTime: () -> Unit,
    startTimer: () -> Unit,
    timerIsFinished: Boolean,
    isCountOverTime: Boolean
) {

    val startTimerColor by animateColorAsState(
        if (lazyListStateHr.isScrollInProgress ||
            lazyListStateMin.isScrollInProgress ||
            lazyListStateSec.isScrollInProgress ||
            timerIsEditState &&
            timerHour == 0 &&
            timerMinute == 0 &&
            timerSecond == 0 ||
            (timerIsFinished && !isCountOverTime)
        ) {
            colorScheme.inversePrimary
        } else if (timerIsActive && !timerIsEditState) {
            colorScheme.tertiary
        } else {
            colorScheme.primary
        },
        label = "",
    )

    val enableStartBtn = startTimerColor != colorScheme.inversePrimary

    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = startTimerColor
        ),
        enabled = enableStartBtn,
        shape = RoundedCornerShape(percent = 50),
        onClick = {
            haptic.performHapticFeedback(hapticFeedbackType = LongPress)
            if (timerIsActive) {
                pauseTimer()
            } else {
                if (timerIsEditState) {
                    convertHrMinSecToMillis()
                    timerSetTotalTime()
                    TimerNotificationService(context = context).cancelNotification()
                }
                startTimer()
            }
        }
    ) {
        Text(
            text = if (timerIsActive) {
                stringResource(id = R.string.timer_start_button_pause)
            } else {
                stringResource(id = R.string.timer_start_button_start)
            },
            modifier = Modifier.padding(
                horizontal = if (timerIsActive) {
                    if (isPortrait()) 8.dp else 7.dp
                } else {
                    if (isPortrait()) 14.dp else 12.dp
                }
            ),
            fontSize = typography.bodyLarge.fontSize.value.nonScaledSp,
            style = TextStyle()
        )
    }
}

