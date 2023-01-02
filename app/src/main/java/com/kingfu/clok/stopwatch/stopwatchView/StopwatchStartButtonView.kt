package com.kingfu.clok.stopwatch.stopwatchView

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel.StopwatchViewModelVariable.stopwatchIsActive
import com.kingfu.clok.ui.theme.Green50
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
    val startStopWatchColor by animateColorAsState(if (stopwatchIsActive) Red50 else Green50)

    OutlinedButton(
        shape = RoundedCornerShape(50),
        border = BorderStroke(0.5.dp, startStopWatchColor.copy(0.5f)),
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            if (stopwatchIsActive) {
                vm.pauseStopWatch()
                coroutineScopeStopwatch.launch {
                    vm.saveStopwatchLapPreviousTime()
                    vm.saveStopwatchOffsetTime()
                }
                vm.saveLapTimes()
            } else {
                vm.startStopWatch()
            }
        }
    ) {

        Text(
            text = if (stopwatchIsActive) "Pause" else "Start",
            modifier = Modifier.padding(horizontal = if(stopwatchIsActive) 7.dp else 14.dp),
            fontSize = customFontSize(20.sp),
            color = startStopWatchColor,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
        )
    }

}