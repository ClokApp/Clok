@file:JvmName("TimeKt")

package com.kingfu.clok.stopwatch.screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.TextUnit
import com.kingfu.clok.core.formatTime
import com.kingfu.clok.ui.theme.ClokThemePreview
import com.kingfu.clok.ui.theme.typography
import com.kingfu.clok.ui.util.nonScaledSp


@Composable
fun Time(
    modifier: Modifier = Modifier,
    time: Long,
    lapTime: Long,
    timerFontSize: TextUnit = typography.displayLarge.fontSize.value.nonScaledSp,
    lapFontSize: TextUnit = typography.headlineLarge.fontSize.value.nonScaledSp,
    lapTimeAlpha: Float = 1f

) {
    Surface(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally,
        ) {
            Text(
                modifier = Modifier.semantics {
                    contentDescription = "time/time"
                },
                text = time.formatTime(),
                fontSize = timerFontSize,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            Text(
                modifier = Modifier
                    .semantics { contentDescription = "time/lap_time" }
                    .alpha(alpha = lapTimeAlpha),
                text = lapTime.formatTime(),
                fontSize = lapFontSize,
                color = colorScheme.outline,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun TimePreview() {
    ClokThemePreview {
        Time(
            time = 10000,
            lapTime = 2000
        )
    }
}
