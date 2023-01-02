package com.kingfu.clok.stopwatch.stopwatchView

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedButton
import androidx.compose.material.ScaffoldState
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
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel.StopwatchViewModelVariable.stopwatchTime
import com.kingfu.clok.ui.theme.Cyan50
import com.kingfu.clok.ui.theme.Yellow50
import com.kingfu.clok.util.customFontSize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun StopwatchResetButton(
    vm: StopwatchViewModel,
    haptic: HapticFeedback,
    coroutineScopeStopwatch: CoroutineScope,
    scaffoldState: ScaffoldState
) {


    val stopwatchResetButtonColor by animateColorAsState(
        targetValue = if (stopwatchIsActive) Cyan50 else Yellow50
    )

    val padding by animateDpAsState(
        targetValue = if (stopwatchTime > 0) 25.dp else 0.dp,
        animationSpec = tween(100)
    )

    AnimatedVisibility(
        visible = stopwatchTime > 0,
        enter = slideInHorizontally(
            initialOffsetX = { 250 },
            animationSpec = tween(
                durationMillis = 50,
//                easing = EaseInOut
                easing = LinearEasing
            )
        ),
        exit = slideOutHorizontally(
            targetOffsetX = { 250 },
            animationSpec = tween(
                durationMillis = 50,
//                easing = EaseInOut
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
                    if (stopwatchIsActive) {
                        vm.addLap(scaffoldState = scaffoldState)
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
                    text = if (stopwatchIsActive) "Lap" else "Reset",
                    modifier = Modifier.padding(horizontal = if(stopwatchIsActive) 18.dp else 10.dp),
                    fontSize = customFontSize(textUnit = 20.sp),
                    color = if (stopwatchIsActive) Cyan50 else Yellow50,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    )

}