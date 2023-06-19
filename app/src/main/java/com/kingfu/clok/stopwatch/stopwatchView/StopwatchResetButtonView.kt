package com.kingfu.clok.stopwatch.stopwatchView

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel
import com.kingfu.clok.util.customFontSize


@Composable
fun StopwatchResetButton(
    vm: StopwatchViewModel,
    haptic: HapticFeedback,
) {

    val stopwatchResetButtonColor by animateColorAsState(
        targetValue = if(vm.stopwatchTime > 0) {
            MaterialTheme.colorScheme.secondary
        }else{
            MaterialTheme.colorScheme.inversePrimary
        }
    )

    OutlinedButton(
        modifier = Modifier.padding(end = 25.dp),
        enabled = vm.stopwatchTime > 0,
        shape = RoundedCornerShape(percent = 50),
        border = BorderStroke(width = 0.5.dp, stopwatchResetButtonColor.copy(alpha = 0.5f)),
        onClick = {
            haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
            if (vm.stopwatchIsActive) {
                vm.lap()
            } else {
                vm.resetStopWatch()
                vm.clearLapTimes()
            }
        },
    ) {
        Text(
            text = if (vm.stopwatchIsActive) "Lap" else "Reset",
            modifier = Modifier.padding(horizontal = if (vm.stopwatchIsActive) 18.dp else 10.dp),
            fontSize = customFontSize(textUnit = 20.sp),
            color = stopwatchResetButtonColor,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }

}