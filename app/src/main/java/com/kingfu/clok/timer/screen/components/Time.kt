package com.kingfu.clok.timer.screen.components

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kingfu.clok.core.ThemeType
import com.kingfu.clok.core.formatTimeTimer
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.TextBodyLarge

@Composable
fun Time(
    time: Long,
    isActive: Boolean,
    isFinished: Boolean,
    totalTime: Double
) {
    val isPortrait = LocalConfiguration.current.orientation == ORIENTATION_PORTRAIT
    val arcColor = colorScheme.onSurface

    Box(
        modifier = Modifier
            .clip(shape = if (isPortrait) CircleShape else RectangleShape)
            .background(color = Transparent)
    ) {
        if (isPortrait) {
            Canvas(
                modifier = Modifier
                    .size(size = 350.dp)
                    .alpha(alpha = if (isActive) 1f else 0.5f)
                    .padding(all = 5.dp)
            ) {
                drawArc(
                    color = arcColor.copy(alpha = 0.25f),
                    startAngle = -90f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(width = 25f),
                )
                drawArc(
                    color = arcColor,
                    startAngle = -90f,
                    sweepAngle = if(isFinished) 0f else 360f * (time / totalTime).toFloat(),
                    useCenter = false,
                    style = Stroke(width = 25f, cap = StrokeCap.Round),
                )
            }
        }



        Box(modifier = Modifier.align(alignment = Alignment.Center)) {
            val formattedTime = (time+999).formatTimeTimer()
            val displayTime = if (isFinished && time >= 0) "- $formattedTime " else formattedTime
            TextBodyLarge(
                text = displayTime,
                fontSize = 60.sp
            )
        }
    }
}

@Composable
fun TimePreview(theme: ThemeType) {
    val totalTime = 200000000.0

    ClokTheme(
        theme = theme,
        content = {
            Surface {
                Time(
                    time = (totalTime/1.5).toLong(),
                    isActive = false,
                    isFinished = true,
                    totalTime = totalTime
                )
            }
        }
    )
}

@Preview
@Composable
fun TimePreviewDark() {
    TimePreview(theme = ThemeType.DARK)
}

@Preview
@Composable
fun TimePreviewLight() {
    TimePreview(theme = ThemeType.LIGHT)
}