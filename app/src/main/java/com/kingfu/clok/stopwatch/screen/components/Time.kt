@file:JvmName("TimeKt")

package com.kingfu.clok.stopwatch.screen.components

import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.kingfu.clok.core.ThemeType
import com.kingfu.clok.core.formatTime
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.TextBodyLarge


@Composable
fun Time(
    modifier: Modifier = Modifier,
    fontSize: TextUnit,
    time: Long,
    color: Color = colorScheme.onSurface
) {
    TextBodyLarge(
        modifier = modifier,
        text = time.formatTime(),
        fontSize = fontSize,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        color = color
    )
}


@Composable
fun TimePreview(theme: ThemeType) {
    ClokTheme(
        theme = theme,
        content = {
            Surface {
                Time(fontSize = 60.sp, time = 10000000)
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