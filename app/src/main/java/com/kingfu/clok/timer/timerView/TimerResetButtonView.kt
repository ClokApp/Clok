package com.kingfu.clok.timer.timerView

import android.content.Context
import android.content.res.Configuration
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
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kingfu.clok.timer.timerViewModel.TimerViewModel
import com.kingfu.clok.util.customFontSize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TimerResetButtonView(
    vm: TimerViewModel,
    lazyListStateHr: LazyListState,
    lazyListStateMin: LazyListState,
    lazyListStateSec: LazyListState,
    coroutineScopeTimer: CoroutineScope,
    haptic: HapticFeedback,
    context: Context,
    configurationOrientation: Int
) {
    val stopwatchResetButtonColor by animateColorAsState(
        targetValue = if (vm.timerIsEditState) Color.Yellow else Color.Magenta
    )

    val padding by animateDpAsState(
        targetValue = if (vm.timerHour != 0 || vm.timerMinute != 0 || vm.timerSecond != 0) 25.dp else 0.dp,
        animationSpec = tween(100)
    )

    AnimatedVisibility(
        visible = vm.timerHour != 0 || vm.timerMinute != 0 || vm.timerSecond != 0,
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
                shape = RoundedCornerShape(50),
                border = BorderStroke(0.5.dp, stopwatchResetButtonColor.copy(0.5f)),
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    if (vm.timerIsEditState) {
                        vm.resetTimer()
                        coroutineScopeTimer.launch {
                            lazyListStateHr.scrollToItem(Int.MAX_VALUE / 2 - 24)
                            lazyListStateMin.scrollToItem(Int.MAX_VALUE / 2 - 4)
                            lazyListStateSec.scrollToItem(Int.MAX_VALUE / 2 - 4)
                        }
                    } else {
                        vm.cancelTimer()
                    }
                    vm.timerCancelNotification(context)
                }
            ) {
                Text(
                    text = if (vm.timerIsEditState) "Reset" else "Cancel",
                    fontSize =
                    if (configurationOrientation == Configuration.ORIENTATION_LANDSCAPE && vm.timerIsEditState) {
                        customFontSize(textUnit = 12.sp)
                    } else {
                        customFontSize(textUnit = 20.sp)
                    },
                    color = stopwatchResetButtonColor,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = if (vm.timerIsEditState) 10.dp else 8.dp),
                )
            }
        }
    )
}