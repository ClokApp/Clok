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
import com.kingfu.clok.ui.theme.Red50
import com.kingfu.clok.util.customFontSize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun StopwatchStartButtonView(
    vm: StopwatchViewModel,
    haptic: HapticFeedback,
    coroutineScopeStopwatch: CoroutineScope,
) {
    val startStopWatchColor by animateColorAsState(if (vm.stopwatchIsActive) Red50 else MaterialTheme.colorScheme.primary)

    OutlinedButton(
        shape = RoundedCornerShape(percent = 50),
        border = BorderStroke(width = 0.5.dp, color = startStopWatchColor.copy(alpha = 0.5f)),
        onClick = {
            haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
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
            fontSize = customFontSize(textUnit = 20.sp),
            color = startStopWatchColor,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }

}