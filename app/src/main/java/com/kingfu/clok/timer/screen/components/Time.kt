package com.kingfu.clok.timer.screen.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.kingfu.clok.core.formatTimeTimer
import com.kingfu.clok.ui.theme.ClokThemePreview
import com.kingfu.clok.ui.theme.typography
import com.kingfu.clok.ui.util.nonScaledSp

@Composable
fun Time(
    modifier: Modifier = Modifier,
    time: Long,
    isFinished: Boolean,
    totalTime: Double,
) {
    val arcColor = colorScheme.primary

    Surface(modifier = modifier) {
        BoxWithConstraints(
            modifier = Modifier,
            contentAlignment = Alignment.Center
        ) {

            if (maxHeight >= 350.dp && maxWidth >= 350.dp) {
                Canvas(
                    modifier = Modifier
                        .padding(all = 24.dp)
                        .size(size = 350.dp)
                        .aspectRatio(ratio = 1f)
                ) {
                    drawArc(
                        color = arcColor.copy(alpha = 0.05f),
                        startAngle = -90f,
                        sweepAngle = 360f,
                        useCenter = false,
                        style = Stroke(width = 20f),
                    )
                    drawArc(
                        color = arcColor,
                        startAngle = -90f,
                        sweepAngle = if (isFinished) 0f else 360f * (time / totalTime).toFloat(),
                        useCenter = false,
                        style = Stroke(width = 20f, cap = StrokeCap.Round),
                    )
                }
            }

            Text(
                text = "${if (isFinished && time >= 0) "-" else ""}${time.formatTimeTimer()}",
                fontSize = typography.displayLarge.fontSize.value.nonScaledSp
            )
        }
    }
}

//@PreviewScreenSizes
@PreviewLightDark
@Composable
private fun TimePreview() {
    val totalTime = 20000000.0

    ClokThemePreview {
        Time(
            time = totalTime.toLong(),
            isFinished = true,
            totalTime = totalTime
        )
    }
}
