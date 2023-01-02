package com.kingfu.clok.timer.timerView

import android.app.Activity
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer.SettingsViewModelTimerVariables.timerBackgroundEffectsSelectedOption
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer.SettingsViewModelTimerVariables.timerLabelStyleSelectedOption
import com.kingfu.clok.timer.backgroundEffects.TimerSnowEffect
import com.kingfu.clok.timer.styles.TimerCyanStyle
import com.kingfu.clok.timer.styles.TimerRGBStyle
import com.kingfu.clok.timer.timerViewModel.TimerViewModel
import com.kingfu.clok.timer.timerViewModel.TimerViewModel.TimerViewModelVariable.timerTime
import com.kingfu.clok.util.NoRippleTheme
import com.kingfu.clok.util.customFontSize

@Composable
fun TimerTimeView(
    vm: TimerViewModel,
    context: Context,
    haptic: HapticFeedback,
    configurationOrientation: Int,
) {
    val activity = LocalContext.current as Activity
    Box(
        Modifier
            .zIndex(1f)
            .clip(CircleShape)
    ) {

        val animatedProgress by animateFloatAsState(
            targetValue = vm.timerCurrentTimePercentage,
            animationSpec = tween(
                durationMillis = if (timerTime >= 10000) 100 else 50,
                easing = LinearEasing
            ),
        )
        val animatedCircularProgressBarColor by animateColorAsState(
            targetValue = TimerCyanStyle().cyanStyleListOfCyan(),
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
                        when (timerLabelStyleSelectedOption) {
                            "RGB" -> TimerRGBStyle().rgbStyleListRGB()

                            else -> {
                                listOf(
                                    animatedCircularProgressBarColor,
                                    animatedCircularProgressBarColor
                                )
                            }
                        }
                    ),
                    startAngle = -90f,
                    sweepAngle = 360 * animatedProgress,
                    useCenter = false,
                    style = Stroke(20f, cap = StrokeCap.Round),
                )
            }
        }

        var backgroundEffectsBoxSize by remember { mutableStateOf(IntSize.Zero) }

        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(5.dp)
                .clip(CircleShape)
                .zIndex(-1f)
                .onSizeChanged { backgroundEffectsBoxSize = it }
        ) {
            if (backgroundEffectsBoxSize.width.dp != 0.dp && configurationOrientation == Configuration.ORIENTATION_PORTRAIT) {
                when (timerBackgroundEffectsSelectedOption) {
                    "Snow" -> {
                        TimerSnowEffect(size = backgroundEffectsBoxSize)
                    }
                }

            }
        }

        Box(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Row {
                CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                    Text(
                        text = vm.formatTimerTime(timerTime),
                        color = Color.White,
                        fontSize = customFontSize(textUnit = 65.sp),
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.clickable {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            if (vm.timerIsActive) {
                                vm.pauseTimer(activity)
                            } else {
                                if (vm.timerIsEditState) {
                                    vm.convertHrMinSecToMillis()
                                }
                                vm.timerSetTotalTime()
                                vm.startTimer(activity)
                            }
                            vm.timerCancelNotification(context)
                        }
                    )
                }
            }
        }


    }
}