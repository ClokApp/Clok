package com.kingfu.clok.timer.timerView

import android.content.Context
import androidx.compose.animation.animateColorAsState
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
import com.kingfu.clok.ui.theme.Green50
import com.kingfu.clok.ui.theme.Red50
import com.kingfu.clok.util.customFontSize
import com.kingfu.clok.timer.timerViewModel.TimerViewModel

@Composable
fun TimerStartButtonView(
    vm: TimerViewModel,
    lazyListStateHr: LazyListState,
    lazyListStateMin: LazyListState,
    lazyListStateSec: LazyListState,
    selectedHr: Int?,
    selectedMin: Int?,
    selectedSec: Int?,
    haptic: HapticFeedback,
    context: Context,
) {
    val startTimerColor by animateColorAsState(
        if (lazyListStateHr.isScrollInProgress ||
            lazyListStateMin.isScrollInProgress ||
            lazyListStateSec.isScrollInProgress ||
            vm.timerIsEditState &&
            selectedHr == 0 &&
            selectedMin == 0 &&
            selectedSec == 0
        )
            Color.Gray
        else if (vm.timerIsActive && !vm.timerIsEditState)
            Red50
        else
            Green50
    )


    val enableStartBtn = startTimerColor != Color.Gray

    OutlinedButton(
        modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp),
        enabled = enableStartBtn,
        shape = RoundedCornerShape(50),
        border = BorderStroke(0.5.dp, startTimerColor.copy(0.5f)),
        onClick =
        {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            if (vm.timerIsActive) {
                vm.pauseTimer()
            } else {
                if (vm.timerIsEditState) {
                    vm.convertHrMinSecToMillis()
                }
                vm.startTimer()
            }
            vm.timerCancelNotification(context)
        }
    ) {
        Text(
            text = if (vm.timerIsActive) "Pause" else " Start ",
            modifier = Modifier.padding(horizontal = 10.dp),
            fontSize = customFontSize(textUnit = 20.sp),
            color = startTimerColor,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
        )
    }
}

