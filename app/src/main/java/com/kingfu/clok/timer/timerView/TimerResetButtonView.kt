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
import androidx.lifecycle.viewModelScope
import com.kingfu.clok.util.customFontSize
import com.kingfu.clok.timer.timerViewModel.TimerViewModel
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
    context: Context
) {
    val stopwatchResetButtonColor by animateColorAsState(
        targetValue = if (vm.timerIsEditState) Color.Yellow else Color.Magenta
    )

    OutlinedButton(
        modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp),
        shape = RoundedCornerShape(50),
        border = BorderStroke(0.5.dp, stopwatchResetButtonColor.copy(0.5f)),
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            if (vm.timerIsEditState) {
                vm.resetTimer()
                coroutineScopeTimer.launch {
                    lazyListStateHr.scrollToItem(Int.MAX_VALUE / 2 - 15)
                    lazyListStateMin.scrollToItem(Int.MAX_VALUE / 2 - 3)
                    lazyListStateSec.scrollToItem(Int.MAX_VALUE / 2 - 3)
                }
            } else {
                vm.cancelButton()
            }
            vm.timerCancelNotification(context)
        }
    ) {
        Text(
            text = if (vm.timerIsEditState) "Reset" else "Cancel",
            fontSize = customFontSize(textUnit = 20.sp),
            color = stopwatchResetButtonColor,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 10.dp),
        )
    }
}