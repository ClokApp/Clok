package com.kingfu.clok.timer.timerScreen

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Light
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.kingfu.clok.settings.settingsScreen.settingsApp.settingsThemeScreen.ThemeType
import com.kingfu.clok.timer.feature.timerFontStyle.TimerFontStyleType
import com.kingfu.clok.timer.feature.timerFontStyle.timerFontStyle
import com.kingfu.clok.timer.feature.timerProgressBarBackgroundEffects.TimerProgressBarBackgroundEffect
import com.kingfu.clok.timer.feature.timerProgressBarBackgroundEffects.TimerProgressBarBackgroundEffectType
import com.kingfu.clok.timer.feature.timerProgressBarStyle.TimerProgressBarStyle
import com.kingfu.clok.timer.feature.timerProgressBarStyle.TimerProgressBarStyleType
import com.kingfu.clok.ui.util.isPortrait
import com.kingfu.clok.ui.util.nonScaledSp

@Composable
fun TimerTimeScreen(
    timerTime: Long,
    updateTimerStyle: (selectedProgressBarStyle: TimerProgressBarStyleType) -> Unit,
    getTimerProgressBarStyle: () -> TimerProgressBarStyleType,
    timerCurrentTimePercentage: Float,
    timerIsActive: Boolean,
    getTimerProgressBarBackgroundEffects: () -> TimerProgressBarBackgroundEffectType,
    isOverTime: () -> Boolean,
    getTimerTimeFontStyle: () -> TimerFontStyleType,
    formatTimerTimeHr: (timeMillis: Long) -> String,
    formatTimerTimeMin: (timeMillis: Long) -> String,
    formatTimerTimeSec: (timeMillis: Long) -> String,
    formatTimerTimeMs: (timeMillis: Long) -> String,
    theme: ThemeType
) {


    LaunchedEffect(key1 = timerTime) {
        updateTimerStyle(getTimerProgressBarStyle())
    }

    Box(
        modifier = Modifier
            .clip(shape = if (isPortrait()) CircleShape else RectangleShape)
            .background(color = Transparent)
    ) {
        val animatedProgress by animateFloatAsState(
            targetValue = if (!timerCurrentTimePercentage.isNaN()) timerCurrentTimePercentage else 0f,
            animationSpec = tween(
                durationMillis = if (timerTime * 0.005 > 250) 250 else (timerTime * 0.005).toInt(),
                easing = LinearEasing,

                ),
            label = ""
        )

        val arcColor = TimerProgressBarStyle(progressBarStyleType = getTimerProgressBarStyle())

        if (!animatedProgress.isNaN() && isPortrait()) {
            Canvas(
                modifier = Modifier
                    .size(width = 325.dp, height = 325.dp)
                    .alpha(alpha = if (timerIsActive) 1f else 0.5f)
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
                    brush = Brush.linearGradient(colors = arcColor),
                    startAngle = -90f,
                    sweepAngle = 360f * animatedProgress,
                    useCenter = false,
                    style = Stroke(width = 20f, cap = StrokeCap.Round),
                )
            }
        }

        var backgroundEffectsBoxSize by remember { mutableStateOf(value = IntSize.Zero) }

        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(all = 5.dp)
                .clip(shape = CircleShape)
                .zIndex(zIndex = -1f)
                .onSizeChanged { backgroundEffectsBoxSize = it }
        ) {
            if (timerIsActive && backgroundEffectsBoxSize.width.dp != 0.dp && isPortrait()) {
                TimerProgressBarBackgroundEffect(
                    size = backgroundEffectsBoxSize,
                    backgroundEffect = getTimerProgressBarBackgroundEffects(),
                    theme = theme
                )
            }
        }

        Box(modifier = Modifier.align(alignment = Alignment.Center)) {
            Row {
                TimerTime(
                    text = if (isOverTime()) "-" else "",
                    color = colorScheme.primary,
                    padding = if (isOverTime()) 5.dp else 0.dp,
                    getTimerTimeFontStyle = getTimerTimeFontStyle
                )

                TimerTime(
                    text = formatTimerTimeHr(timerTime),
                    color = colorScheme.primary,
                    getTimerTimeFontStyle = getTimerTimeFontStyle
                )

                TimerTime(
                    text = if (formatTimerTimeHr(timerTime) != "") ":" else "",
                    color = colorScheme.tertiary,
                    padding = if (formatTimerTimeHr(timerTime) != "") 5.dp else 0.dp,
                    getTimerTimeFontStyle = getTimerTimeFontStyle
                )

                TimerTime(
                    text = formatTimerTimeMin(timerTime),
                    color = colorScheme.primary,
                    getTimerTimeFontStyle = getTimerTimeFontStyle
                )

                TimerTime(
                    text = if (formatTimerTimeMin(timerTime) != "") ":" else "",
                    color = colorScheme.tertiary,
                    padding = if (formatTimerTimeMin(timerTime) != "") 5.dp else 0.dp,
                    getTimerTimeFontStyle = getTimerTimeFontStyle
                )

                TimerTime(
                    text = formatTimerTimeSec(timerTime),
                    color = colorScheme.primary,
                    getTimerTimeFontStyle = getTimerTimeFontStyle

                )

                TimerTime(
                    text = if (formatTimerTimeMs(timerTime) != "") "." else "",
                    color = colorScheme.tertiary,
                    padding = if (formatTimerTimeMs(timerTime) != "") 5.dp else 0.dp,
                    getTimerTimeFontStyle = getTimerTimeFontStyle
                )

                TimerTime(
                    text = formatTimerTimeMs(timerTime) + if (isOverTime()) " " else "",
                    color = colorScheme.primary,
                    getTimerTimeFontStyle = getTimerTimeFontStyle
                )
            }
        }
    }
}

@Composable
fun TimerTime(
    text: String,
    color: Color,
    padding: Dp = 0.dp,
    getTimerTimeFontStyle: () -> TimerFontStyleType
) {
    Text(
        text = text,
        color = color,
        fontSize = 60.dp.value.nonScaledSp,
        fontWeight = Light,
        style = TextStyle(
            drawStyle = timerFontStyle(
                selectedStyle = getTimerTimeFontStyle(),
                miter = 10f,
                width = 2.5f,
                join = StrokeJoin.Round,
                cap = StrokeCap.Round
            )
        ),
        modifier = Modifier.padding(horizontal = padding)
    )
}