package com.kingfu.clok.timer.timerView

import android.content.Context
import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.kingfu.clok.notification.timer.TimerNotificationService
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer.SettingsViewModelTimerVariables.timerBackgroundEffectsSelectedOption
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer.SettingsViewModelTimerVariables.timerLabelStyleSelectedOption
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer.SettingsViewModelTimerVariables.timerTimeFontStyleSelectedOption
import com.kingfu.clok.timer.backgroundEffects.TimerSnowEffect
import com.kingfu.clok.timer.styles.TimerCyanStyle
import com.kingfu.clok.timer.styles.TimerRGBStyle
import com.kingfu.clok.timer.timerViewModel.TimerViewModel
import com.kingfu.clok.timer.timerViewModel.TimerViewModel.TimerViewModelVariable.timerTime
import com.kingfu.clok.util.NoRippleTheme
import com.kingfu.clok.util.customFontSize

@OptIn(ExperimentalTextApi::class)
@Composable
fun TimerTimeView(
    vm: TimerViewModel,
    context: Context,
    haptic: HapticFeedback,
    configurationOrientation: Int,
) {

    Box(
        Modifier
            .zIndex(zIndex = 1f)
            .clip(if (configurationOrientation == Configuration.ORIENTATION_PORTRAIT) CircleShape else RectangleShape)
    ) {
        val animatedProgress by animateFloatAsState(
            targetValue = if (!vm.timerCurrentTimePercentage.isNaN()) vm.timerCurrentTimePercentage else 0f,
            animationSpec = tween(
                durationMillis = if (timerTime * 0.005 > 250) 250 else (timerTime * 0.005).toInt(),
                easing = LinearEasing
            )
        )

        val animatedCircularProgressBarColor by animateColorAsState(
            targetValue = TimerCyanStyle().cyanStyleListOfCyan(),
            animationSpec = tween(
                durationMillis = 500,
                easing = LinearEasing
            )
        )

        if (configurationOrientation == Configuration.ORIENTATION_PORTRAIT && !animatedProgress.isNaN()) {
            Canvas(
                modifier = Modifier
                    .size(width = 320.dp, height = 320.dp)
                    .padding(all = 5.dp)
            ) {
                drawArc(
                    color = Color.DarkGray,
                    startAngle = -90f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(width = 10f),
                )
                drawArc(
                    brush = Brush.linearGradient(
                        colors =
                        when (timerLabelStyleSelectedOption) {
                            "RGB" -> TimerRGBStyle().rgbStyleListRGB()
                            "Cyan" -> {
                                listOf(
                                    animatedCircularProgressBarColor,
                                    animatedCircularProgressBarColor,
                                )
                            }
                            else -> listOf(Color.Yellow, Color.Yellow)
                        }
                    ),
                    startAngle = -90f,
                    sweepAngle = 360f * animatedProgress,
                    useCenter = false,
                    style = Stroke(20f, cap = StrokeCap.Round),
                )
            }
        }

        var backgroundEffectsBoxSize by remember { mutableStateOf(IntSize.Zero) }

        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(all = 5.dp)
                .clip(shape = CircleShape)
                .zIndex(zIndex = -1f)
                .onSizeChanged { backgroundEffectsBoxSize = it }
        ) {
            if (vm.timerIsActive && backgroundEffectsBoxSize.width.dp != 0.dp
                && configurationOrientation == Configuration.ORIENTATION_PORTRAIT
            ) {
                when (timerBackgroundEffectsSelectedOption) {
                    "Snow" -> {
                        TimerSnowEffect(size = backgroundEffectsBoxSize)
                    }
                }

            }
        }

        Box(
            modifier = Modifier.align(alignment = Alignment.Center)
        ) {
            Row {
                CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                    Text(
                        text = vm.formatTimerTime(timeMillis = timerTime),
                        color = Color.White,
                        fontSize = customFontSize(textUnit = 65.sp),
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.clickable {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            if (vm.timerIsActive) {
                                vm.pauseTimer()
                            } else {
                                if (vm.timerIsEditState) {
                                    vm.convertHrMinSecToMillis()
                                    vm.timerSetTotalTime()
                                }
                                vm.startTimer()
                            }
                            TimerNotificationService(context).cancelNotification()
                        },
                        style = TextStyle(
                            drawStyle = if (timerTimeFontStyleSelectedOption == "Inner stroke")
                                Stroke(
                                    miter = 10f,
                                    width = 5f,
                                    join = StrokeJoin.Round,
                                    cap = StrokeCap.Round
                                ) else null
                        )
                    )
                }
            }
        }


    }
}