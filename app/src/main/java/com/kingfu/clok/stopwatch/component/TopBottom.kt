package com.kingfu.clok.stopwatch.component

import android.os.SystemClock
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.kingfu.clok.stopwatch.util.fontStyle.StopwatchFontStyleType
import com.kingfu.clok.stopwatch.util.fontStyle.stopwatchFontStyle
import com.kingfu.clok.stopwatch.util.labelStyle.StopwatchLabelStyleType
import com.kingfu.clok.stopwatch.util.labelStyle.stopwatchLabelStyle
import com.kingfu.clok.stopwatch.util.styles.StopwatchRGBStyle
import com.kingfu.clok.stopwatch.util.styles.StopwatchRGBStyle.RGBVariable.RGBHrColorList
import com.kingfu.clok.stopwatch.util.styles.StopwatchRGBStyle.RGBVariable.RGBMinColorList
import com.kingfu.clok.stopwatch.util.styles.StopwatchRGBStyle.RGBVariable.RGBMsColorList
import com.kingfu.clok.stopwatch.util.styles.StopwatchRGBStyle.RGBVariable.RGBSecColorList
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.ThemeType
import com.kingfu.clok.ui.util.nonScaledSp
import com.kingfu.clok.util.formatTimeHr
import com.kingfu.clok.util.formatTimeMin
import com.kingfu.clok.util.formatTimeMs
import com.kingfu.clok.util.formatTimeSec
import kotlinx.coroutines.delay

@Composable
fun TopBottom(
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    top: @Composable () -> Unit,
    bottom: @Composable () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment
    ) {
        Row {
            top()
        }

        Row {
            bottom()
        }
    }
}

@Preview
@Composable
fun TopBottomPreview() {
    val topFontSize = 30.dp.value.nonScaledSp
    val bottomFontSize = 54.dp.value.nonScaledSp
    val isActive = true
    var stopwatchTime by rememberSaveable { mutableStateOf(value = 0L) }

    LaunchedEffect(Unit) {
        while (isActive) {
            delay(10)
            stopwatchTime = SystemClock.elapsedRealtime()
//            stopwatchTime += 9_999
            StopwatchRGBStyle().rgbStyleUpdateColors()
        }
    }
    data class test(
        val top: String = "",
        val bottom: String = "",
        val labelStyle: StopwatchLabelStyleType = StopwatchLabelStyleType.DynamicColor,
        val labelFontStyle: StopwatchFontStyleType = StopwatchFontStyleType.Default,
        val fontStyle: StopwatchFontStyleType = StopwatchFontStyleType.Default,
        val rgbColorList: List<Int>,
        val isVisible: Boolean = true
    )

    val testList = listOf(
        test(
            top = "hr",
            bottom = stopwatchTime.formatTimeHr(),
            rgbColorList = RGBHrColorList,
            isVisible = stopwatchTime >= 3_600_000
        ),
        test(
            top = "min",
            bottom = stopwatchTime.formatTimeMin(),
            rgbColorList = RGBMinColorList
        ),
        test(
            top = "sec",
            bottom = stopwatchTime.formatTimeSec(),
            rgbColorList = RGBSecColorList
        ),
        test(
            top = "ms",
            bottom = stopwatchTime.formatTimeMs(),
            rgbColorList = RGBMsColorList
        )
    )

    ClokTheme(
        dynamicColor = true,
        theme = ThemeType.Dark
    ) {
        Row {
            testList.forEachIndexed { index, test ->
                if (test.isVisible) {
                    TopBottom(
                        top = {
                            Top(
                                text = test.top,
                                fontSize = topFontSize,
                                selectedLabel = test.labelStyle,
                                rgbColorList = test.rgbColorList,
                                isActive = isActive,
                                labelFontStyleType = test.labelFontStyle
                            )
                        },
                        bottom = {
                            Bottom(
                                text = test.bottom,
                                fontSize = bottomFontSize,
                                selectedStyle = test.fontStyle
                            )
                        }
                    )

                    Separator(
                        text = test.top,
                        topFontSize = topFontSize,
                        bottomFontSize = bottomFontSize,
                        stopwatchFontStyle = test.fontStyle
                    )
                }
            }
        }
    }
}

@Composable
fun Top(
    text: String,
    fontSize: TextUnit,
    selectedLabel: StopwatchLabelStyleType,
    rgbColorList: List<Int>,
    isActive: Boolean,
    labelFontStyleType: StopwatchFontStyleType
) {
    Text(
        text = text,
        fontSize = fontSize,
        fontWeight = FontWeight.ExtraLight,
        fontStyle = FontStyle.Italic,
        style = TextStyle(
            brush = Brush.linearGradient(
                colors = stopwatchLabelStyle(
                    selectedLabelStyle = selectedLabel,
                    rgbColorList = rgbColorList,
                    stopwatchIsActive = isActive
                )
            ),
            drawStyle = stopwatchFontStyle(
                selectedFontStyle = labelFontStyleType,
                miter = 10f,
                width = 1.5f
            )
        ),
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
    )
}

@Composable
fun Bottom(
    text: String,
    fontSize: TextUnit,
    selectedStyle: StopwatchFontStyleType
){
    Text(
        text = text,
        fontSize = fontSize,
        color = colorScheme.primary,
        fontWeight = FontWeight.Light,
        style = TextStyle(
            drawStyle = stopwatchFontStyle(
                selectedFontStyle = selectedStyle,
                miter = 1f,
                width = 1.5f
            )
        )
    )
}

@Composable
fun Separator(
    text: String,
    topFontSize: TextUnit,
    bottomFontSize: TextUnit,
    stopwatchFontStyle: StopwatchFontStyleType
){
    Column {
        val separator = when (text) {
            "hr" -> ":"
            "min" -> ":"
            "sec" -> "."
            else -> ""
        }

        Text(
            text = "",
            fontSize = topFontSize,
            style = TextStyle()
        )

        Text(
            text = separator,
            fontSize = bottomFontSize,
            color = colorScheme.tertiary,
            fontWeight = FontWeight.Light,
            style = TextStyle(
                drawStyle = stopwatchFontStyle(
                    selectedFontStyle = stopwatchFontStyle,
                    miter = 1f,
                    width = 1.5f
                )
            ),
            modifier = Modifier.padding(horizontal = if (separator != "") 5.dp else 0.dp)
        )
    }
}