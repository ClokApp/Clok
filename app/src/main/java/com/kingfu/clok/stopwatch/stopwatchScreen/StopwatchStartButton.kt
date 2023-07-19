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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun StopwatchStartButton(
    vm: StopwatchViewModel,
    haptic: HapticFeedback,
    coroutineScopeStopwatch: CoroutineScope,
) {
    val startStopWatchColor by animateColorAsState(if (vm.stopwatchIsActive) colorScheme.tertiary else colorScheme.primary)

    OutlinedButton(
        shape = RoundedCornerShape(percent = 50),
        border = BorderStroke(width = 0.5.dp, color = startStopWatchColor.copy(alpha = 0.5f)),
        onClick = {
            haptic.performHapticFeedback(hapticFeedbackType = LongPress)
            if (vm.stopwatchIsActive) {
                vm.pauseStopWatch()
                coroutineScopeStopwatch.launch {
                    vm.saveStopwatchLapPreviousTime()
                    vm.saveStopwatchOffsetTime()
                }
            } else {
                vm.startStopWatch()
            }
        }
    ) {
        Text(
            text = if (vm.stopwatchIsActive) "Pause" else "Start",
            modifier = Modifier.padding(horizontal = if(vm.stopwatchIsActive) 7.dp else 14.dp),
            fontSize = 20.nonScaledSp,
            color = startStopWatchColor,
            fontWeight = Bold,
            maxLines = 1,
            overflow = Ellipsis,
            style = TextStyle()
        )
    }

}