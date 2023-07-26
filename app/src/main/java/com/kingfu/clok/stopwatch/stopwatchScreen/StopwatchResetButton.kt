package com.kingfu.clok.stopwatch.stopwatchScreen

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel
import com.kingfu.clok.util.nonScaledSp


@Composable
fun StopwatchResetButton(
    vm: StopwatchViewModel,
    haptic: HapticFeedback
) {

    val stopwatchResetButtonColor by animateColorAsState(
        targetValue = if (vm.stopwatchTime > 0) {
            colorScheme.secondary
        } else {
            colorScheme.inversePrimary
        },
        label = ""
    )

    OutlinedButton(
        modifier = Modifier.padding(end = 25.dp),
        enabled = vm.stopwatchTime > 0,
        shape = RoundedCornerShape(percent = 50),
        border = BorderStroke(width = 0.5.dp, stopwatchResetButtonColor.copy(alpha = 0.5f)),
        onClick = {
            haptic.performHapticFeedback(hapticFeedbackType = LongPress)
            if (vm.stopwatchIsActive) {
                vm.lap()
                if(!vm.isScrollLazyColumn){
                    vm.toggleIsScrollLazyColumn()
                }
            } else {
                vm.resetStopWatch()
                vm.clearLapTimes()
            }
        },
    ) {
        Text(
            text = if (vm.stopwatchIsActive) "Lap" else "Reset",
            modifier = Modifier.padding(horizontal = if (vm.stopwatchIsActive) 18.dp else 10.dp),
            fontSize = 20.nonScaledSp,
            color = stopwatchResetButtonColor,
            fontWeight = Bold,
            maxLines = 1,
            overflow = Ellipsis,
            style = TextStyle()
        )
    }

}