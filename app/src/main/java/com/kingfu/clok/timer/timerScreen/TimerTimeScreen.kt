package com.kingfu.clok.timer.timerScreen

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Light
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize.Companion.Zero
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer
import com.kingfu.clok.timer.backgroundEffects.TimerSnowEffect
import com.kingfu.clok.timer.styles.TimerRGBStyle
import com.kingfu.clok.timer.timerFontStyle.timerFontStyle
import com.kingfu.clok.timer.timerViewModel.TimerViewModel
import com.kingfu.clok.util.nonScaledSp
import com.kingfu.clok.variable.Variable.RGB

@Composable
fun TimerTimeView(
    vm: TimerViewModel,
    configurationOrientation: Int,
    settingsViewModelTimer: SettingsViewModelTimer
) {

    LaunchedEffect(key1 = settingsViewModelTimer.timerLabelStyle) {
        if (settingsViewModelTimer.timerLabelStyle == RGB) {
            TimerRGBStyle().timerUpdateStartAndEndRGB(initialize = true)
        }
    }

    Box(modifier = Modifier.clip(if (configurationOrientation == ORIENTATION_PORTRAIT) CircleShape else RectangleShape)) {
        val animatedProgress by animateFloatAsState(
            targetValue = if (!vm.timerCurrentTimePercentage.isNaN()) vm.timerCurrentTimePercentage else 0f,
            animationSpec = tween(
                durationMillis = if (vm.timerTime * 0.005 > 250) 250 else (vm.timerTime * 0.005).toInt(),
                easing = LinearEasing
            )
        )

        val circularProgressBarColor =
            if(vm.timerIsActive) {
                listOf(
                    colorScheme.tertiary,
                    colorScheme.tertiary
                )
            }else{
                listOf(
                    colorScheme.tertiaryContainer,
                    colorScheme.tertiaryContainer,
                )
            }

        if (configurationOrientation == ORIENTATION_PORTRAIT && !animatedProgress.isNaN()) {
            Canvas(
                modifier = Modifier
                    .size(width = 325.dp, height = 325.dp)
                    .padding(all = 5.dp)
            ) {
                drawArc(
                    color = DarkGray,
                    startAngle = -90f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(width = 5f),
                )
                drawArc(
                    brush = Brush.linearGradient(
                        colors =
                        when (settingsViewModelTimer.timerLabelStyle) {
                            RGB -> {
                                TimerRGBStyle().rgbStyleListRGB()
                            }

                            else -> {
                                circularProgressBarColor
                            }
                        }
                    ),
                    startAngle = -90f,
                    sweepAngle = 360f * animatedProgress,
                    useCenter = false,
                    style = Stroke(width = 20f, cap = StrokeCap.Round),
                )
            }
        }

        var backgroundEffectsBoxSize by remember { mutableStateOf(value = Zero) }

        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(all = 5.dp)
                .clip(shape = CircleShape)
                .zIndex(zIndex = -1f)
                .onSizeChanged { backgroundEffectsBoxSize = it }
        ) {
            if (vm.timerIsActive && backgroundEffectsBoxSize.width.dp != 0.dp
                && configurationOrientation == ORIENTATION_PORTRAIT
            ) {
                when (settingsViewModelTimer.timerBackgroundEffects) {
                    "Snow" -> {
                        TimerSnowEffect(size = backgroundEffectsBoxSize)
                    }
                }
            }
        }

        Box(modifier = Modifier.align(alignment = Alignment.Center)) {
            Row {
                TimerTime(
                    text = if (vm.isOverTime()) "-" else "",
                    color = colorScheme.primary,
                    settingsViewModelTimer = settingsViewModelTimer,
                    padding = if (vm.isOverTime()) 5.dp else 0.dp
                )

                TimerTime(
                    text = vm.formatTimerTimeHr(timeMillis = vm.timerTime),
                    color = colorScheme.primary,
                    settingsViewModelTimer = settingsViewModelTimer,
                )

                TimerTime(
                    text = if (vm.formatTimerTimeHr(timeMillis = vm.timerTime) != "") ":" else "",
                    color = colorScheme.tertiary,
                    settingsViewModelTimer = settingsViewModelTimer,
                    padding = if (vm.formatTimerTimeHr(timeMillis = vm.timerTime) != "")  5.dp else 0.dp
                )

                TimerTime(
                    text = vm.formatTimerTimeMin(timeMillis = vm.timerTime),
                    color = colorScheme.primary,
                    settingsViewModelTimer = settingsViewModelTimer,
                )

                TimerTime(
                    text = if (vm.formatTimerTimeMin(timeMillis = vm.timerTime) != "") ":" else "",
                    color = colorScheme.tertiary,
                    settingsViewModelTimer = settingsViewModelTimer,
                    padding = if (vm.formatTimerTimeMin(timeMillis = vm.timerTime) != "") 5.dp else 0.dp
                )

                TimerTime(
                    text = vm.formatTimerTimeSec(timeMillis = vm.timerTime),
                    color = colorScheme.primary,
                    settingsViewModelTimer = settingsViewModelTimer,
                )

                TimerTime(
                    text = if (vm.formatTimerTimeMs(timeMillis = vm.timerTime) != "") "." else "",
                    color = colorScheme.tertiary,
                    settingsViewModelTimer = settingsViewModelTimer,
                    padding = if (vm.formatTimerTimeMs(timeMillis = vm.timerTime) != "") 5.dp else 0.dp
                )

                TimerTime(
                    text = vm.formatTimerTimeMs(timeMillis = vm.timerTime),
                    color = colorScheme.primary,
                    settingsViewModelTimer = settingsViewModelTimer,
                )
            }
        }
    }
}

@Composable
fun TimerTime(
    text: String,
    color: Color,
    settingsViewModelTimer: SettingsViewModelTimer,
    padding: Dp = 0.dp
){
    Text(
        text = text,
        color = color,
        fontSize = 60.nonScaledSp,
        fontWeight = Light,
        style = TextStyle(
            drawStyle = timerFontStyle(
                string1 = settingsViewModelTimer.timerTimeFontStyle,
                string2 = settingsViewModelTimer.timerFontStyleRadioOptions.elementAt(
                    index = 1
                ),
                minter = 10f,
                width = 5f,
                join = StrokeJoin.Round,
                cap = StrokeCap.Round
            )
        ),
        modifier = Modifier.padding(horizontal = padding)
    )
}