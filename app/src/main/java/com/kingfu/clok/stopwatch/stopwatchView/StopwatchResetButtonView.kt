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
import com.kingfu.clok.ui.theme.Cyan50
import com.kingfu.clok.ui.theme.Yellow50
import com.kingfu.clok.util.customFontSize
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun StopwatchResetButton(
    vm: StopwatchViewModel,
    haptic: HapticFeedback,
    coroutineScopeStopwatch: CoroutineScope,
) {

    val stopwatchResetButtonColor by animateColorAsState(
        targetValue = if (vm.stopwatchIsActive) Cyan50 else Yellow50
    )

    OutlinedButton(
        modifier = Modifier.padding(10.dp),
        shape = RoundedCornerShape(50),
        border = BorderStroke(0.5.dp, stopwatchResetButtonColor.copy(0.5f)),
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            if (vm.stopwatchIsActive) {
                vm.addLap()
                vm.saveLapTimes()
                coroutineScopeStopwatch.launch {
                    vm.saveStopwatchLapPreviousTime()
                }
            } else {
                vm.resetStopWatch()
                vm.clearLapTimes()
            }

            vm.saveLapTimes()
            coroutineScopeStopwatch.launch {
                vm.saveStopwatchLapPreviousTime()
            }

        }
    ) {
        Text(
            text = if (vm.stopwatchIsActive) "  Lap  " else "Reset",
            modifier = Modifier.padding(horizontal = 10.dp),
            fontSize = customFontSize(textUnit = 20.sp),
            color = if (vm.stopwatchIsActive) Cyan50 else Yellow50,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
        )
    }
}