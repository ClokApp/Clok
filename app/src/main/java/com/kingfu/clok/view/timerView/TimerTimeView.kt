package com.kingfu.clok.view.timerView

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kingfu.clok.ui.theme.Cyan50
import com.kingfu.clok.ui.theme.Red50
import com.kingfu.clok.util.customFontSize
import com.kingfu.clok.viewModel.timerViewModel.TimerViewModel

@Composable
fun TimerTimeView(
    vm: TimerViewModel
) {

    LaunchedEffect(Unit) {
        vm.loadTimerLabelStyleOption()
    }

    Box {
        val configurationOrientation = LocalConfiguration.current.orientation

        val animatedProgress by animateFloatAsState(
            targetValue = vm.timerCurrentTimePercentage,
            animationSpec = tween(
                durationMillis = 100,
                easing = FastOutLinearInEasing
            ),
        )
        val animatedCircularProgressBarColor by animateColorAsState(
            targetValue = if (vm.timerTime > 6000) Cyan50 else Red50,
            animationSpec = tween(
                durationMillis = 500,
                easing = LinearEasing
            )
        )

        if (configurationOrientation == Configuration.ORIENTATION_PORTRAIT) {
            Canvas(
                modifier = Modifier
                    .size(320.dp, 320.dp)
                    .padding(5.dp)
            ) {
                drawArc(
                    color = Color.DarkGray,
                    startAngle = -90f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(10f),
                )
                drawArc(
                    brush = Brush.linearGradient(
                        colors =
                        when (vm.timerLabelStyle) {
                            "RGB" ->
                                listOf(
                                    Color(
                                        vm.timerLabelColorList[0],
                                        vm.timerLabelColorList[1],
                                        vm.timerLabelColorList[2],
                                    ),
                                    Color(
                                        vm.timerLabelColorList[3],
                                        vm.timerLabelColorList[4],
                                        vm.timerLabelColorList[5],
                                    )
                                )
                            else -> listOf(
                                animatedCircularProgressBarColor,
                                animatedCircularProgressBarColor
                            )
                        }
                    ),
                    startAngle = -90f,
                    sweepAngle = 360 * animatedProgress,
                    useCenter = false,
                    style = Stroke(20f, cap = StrokeCap.Round),
                )
            }
        }

        Box(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = vm.formatTimerTime(vm.timerTime),
                    color = Color.White,
                    fontSize = customFontSize(textUnit = 65.sp),
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Light,
                )
            }
        }
    }
}