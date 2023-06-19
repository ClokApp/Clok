package com.kingfu.clok.timer.timerView

import android.content.Context
import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kingfu.clok.notification.timer.TimerNotificationService
import com.kingfu.clok.timer.timerViewModel.TimerViewModel
import com.kingfu.clok.ui.theme.Red50
import com.kingfu.clok.util.customFontSize

@Composable
fun TimerStartButtonView(
    vm: TimerViewModel,
    lazyListStateHr: LazyListState,
    lazyListStateMin: LazyListState,
    lazyListStateSec: LazyListState,
    haptic: HapticFeedback,
    context: Context,
    configurationOrientation: Int,
) {

    val startTimerColor by animateColorAsState(
        if (lazyListStateHr.isScrollInProgress ||
            lazyListStateMin.isScrollInProgress ||
            lazyListStateSec.isScrollInProgress ||
            vm.timerIsEditState &&
            vm.timerHour == 0 &&
            vm.timerMinute == 0 &&
            vm.timerSecond == 0
        )
            MaterialTheme.colorScheme.inversePrimary
        else if (vm.timerIsActive && !vm.timerIsEditState)
            Red50
        else
            MaterialTheme.colorScheme.primary,
    )

    val enableStartBtn = startTimerColor != MaterialTheme.colorScheme.inversePrimary

    OutlinedButton(
        enabled = enableStartBtn,
        shape = RoundedCornerShape(percent = 50),
        border = BorderStroke(width = 0.5.dp, color = startTimerColor.copy(alpha = 0.5f)),
        onClick =
        {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            if (vm.timerIsActive) {
                vm.pauseTimer()
            } else {
                if (vm.timerIsEditState) {
                    vm.convertHrMinSecToMillis()
                    vm.timerSetTotalTime()
                    TimerNotificationService(context = context).cancelNotification()
                }
                vm.startTimer()
            }
        }
    ) {
        Text(
            text = if (vm.timerIsActive) "Pause" else "Start",
            modifier = Modifier.padding(
                horizontal =
                if (vm.timerIsActive) {
                    if (configurationOrientation == Configuration.ORIENTATION_PORTRAIT) 8.dp else 7.dp
                } else {
                    if (configurationOrientation == Configuration.ORIENTATION_PORTRAIT) 14.dp else 12.dp
                }
            ),
            fontSize =
            if (configurationOrientation == Configuration.ORIENTATION_LANDSCAPE && vm.timerIsEditState) {
                customFontSize(textUnit = 14.sp)
            } else {
                customFontSize(textUnit = 20.sp)
            },
            color = startTimerColor,
            fontWeight = FontWeight.Bold,
        )
    }
}

