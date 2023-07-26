package com.kingfu.clok.timer.timerScreen

import android.content.Context
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType.Companion.LongPress
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import com.kingfu.clok.notification.timer.TimerNotificationService
import com.kingfu.clok.timer.timerViewModel.TimerViewModel
import com.kingfu.clok.util.nonScaledSp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TimerResetButton(
    vm: TimerViewModel,
    lazyListStateHr: LazyListState,
    lazyListStateMin: LazyListState,
    lazyListStateSec: LazyListState,
    coroutineScopeTimer: CoroutineScope,
    haptic: HapticFeedback,
    configurationOrientation: Int,
    context: Context,
) {
    val stopwatchResetButtonColor by animateColorAsState(
        targetValue = colorScheme.secondary,
        label = ""
    )

    val padding by animateDpAsState(
//        targetValue = if ((vm.timerHour != 0 || vm.timerMinute != 0 || vm.timerSecond != 0) && !vm.loadInitialTime) 25.dp else 0.dp,
        targetValue = if ((vm.timerHour != 0 || vm.timerMinute != 0 || vm.timerSecond != 0) && (!vm.loadInitialTime || !vm.timerIsEditState)) 25.dp else 0.dp,
        animationSpec = tween(durationMillis = 100),
        label = ""
    )

    AnimatedVisibility(
        visible = ((vm.timerHour != 0 || vm.timerMinute != 0 || vm.timerSecond != 0) && (!vm.loadInitialTime || !vm.timerIsEditState)),
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
            OutlinedButton(
                modifier = Modifier.padding(end = padding),
                shape = RoundedCornerShape(percent = 50),
                border = BorderStroke(
                    width = 0.5.dp,
                    color = stopwatchResetButtonColor.copy(alpha = 0.5f)
                ),
                onClick = {
                    haptic.performHapticFeedback(hapticFeedbackType = LongPress)
                    if (vm.timerIsEditState) {
                        vm.resetTimer()
                        coroutineScopeTimer.launch {
                            lazyListStateHr.scrollToItem(index = Int.MAX_VALUE / 2 - 24)
                            lazyListStateMin.scrollToItem(index = Int.MAX_VALUE / 2 - 4)
                            lazyListStateSec.scrollToItem(index = Int.MAX_VALUE / 2 - 4)
                        }
                        TimerNotificationService(context = context).cancelNotification()
                    } else {
                        vm.cancelTimer()
                    }
                }
            ) {
                Text(
                    text = if (vm.timerIsEditState) "Reset" else "Cancel",
                    fontSize =
                    if (configurationOrientation == ORIENTATION_LANDSCAPE && vm.timerIsEditState) {
                        12.nonScaledSp
                    } else {
                        20.nonScaledSp
                    },
                    color = stopwatchResetButtonColor,
                    fontWeight = Bold,
                    modifier = Modifier.padding(horizontal = if (vm.timerIsEditState) 10.dp else 8.dp),
                    style = TextStyle()
                )
            }
        }
    )
}