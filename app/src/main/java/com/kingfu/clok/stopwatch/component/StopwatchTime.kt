package com.kingfu.clok.stopwatch.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.IntSize.Companion.Zero
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.kingfu.clok.R
import com.kingfu.clok.repository.room.stopwatchRoom.StopwatchLapData
import com.kingfu.clok.ui.theme.ThemeType
import com.kingfu.clok.stopwatch.util.fontStyle.StopwatchFontStyleType
import com.kingfu.clok.stopwatch.util.fontStyle.stopwatchFontStyle
import com.kingfu.clok.stopwatch.util.labelBackgroundEffects.StopwatchLabelBackgroundEffect
import com.kingfu.clok.stopwatch.util.labelBackgroundEffects.StopwatchLabelBackgroundEffectType
import com.kingfu.clok.stopwatch.util.labelStyle.StopwatchLabelStyleType
import com.kingfu.clok.stopwatch.util.labelStyle.stopwatchLabelStyle
import com.kingfu.clok.stopwatch.util.styles.StopwatchRGBStyle.RGBVariable.RGBHrColorList
import com.kingfu.clok.stopwatch.util.styles.StopwatchRGBStyle.RGBVariable.RGBMinColorList
import com.kingfu.clok.stopwatch.util.styles.StopwatchRGBStyle.RGBVariable.RGBMsColorList
import com.kingfu.clok.stopwatch.util.styles.StopwatchRGBStyle.RGBVariable.RGBSecColorList
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.themeBackgroundColor
import com.kingfu.clok.ui.util.nonScaledSp
import com.kingfu.clok.util.formatTimeHr
import com.kingfu.clok.util.formatTimeMin
import com.kingfu.clok.util.formatTimeMs
import com.kingfu.clok.util.formatTimeSec
import com.kingfu.clok.util.isAtLeastOneHour


@Composable
fun StopwatchTime(
    getStopwatchLapTimeFontStyle: () -> StopwatchFontStyleType,
    getStopwatchTimeFontStyle: () -> StopwatchFontStyleType,
    getStopwatchLabelFontStyle: () -> StopwatchFontStyleType,
    getStopwatchLabelStyle: () -> StopwatchLabelStyleType,
    getStopwatchLabelBackgroundEffect: () -> StopwatchLabelBackgroundEffectType,
    getStopwatchIsShowLabel: () -> Boolean,
    lapList: List<StopwatchLapData>,
    stopwatchTime: Long,
    updateStopwatchLabelStyle: (selectedLabelStyle: StopwatchLabelStyleType) -> Unit,
    stopwatchIsActive: Boolean,
    lapPreviousTime: Long,
    theme: ThemeType
) {

    val labelFontSize = 30.dp.value.nonScaledSp
    val timeFontSize = 54.dp.value.nonScaledSp
    val lapTimeFontSize = 30.dp.value.nonScaledSp

    LaunchedEffect(key1 = stopwatchTime) {
        updateStopwatchLabelStyle(getStopwatchLabelStyle())
    }
    data class Item(
//        val label: String = "",
        val label: Int = 0,
        val time: String = "",
        val lapTime: String = "",
        val labelStyle: StopwatchLabelStyleType = getStopwatchLabelStyle(),
        val labelFontStyle: StopwatchFontStyleType = getStopwatchLabelFontStyle(),
        val timeFontStyle: StopwatchFontStyleType = getStopwatchTimeFontStyle(),
        val rgbColorList: List<Int>,
        val isVisible: Boolean = true,
        val lapTimeFontStyle: StopwatchFontStyleType = getStopwatchLapTimeFontStyle()
    )


    val stopwatchTimeItems = listOf(
        Item(
            label = R.string.stopwatch_time_component_hr,
            time = stopwatchTime.formatTimeHr(),
            lapTime = (stopwatchTime - lapPreviousTime).formatTimeHr(),
            rgbColorList = RGBHrColorList,
            isVisible = stopwatchTime.isAtLeastOneHour()
        ),
        Item(
            label = R.string.stopwatch_time_component_min,
            time = stopwatchTime.formatTimeMin(),
            lapTime = (stopwatchTime - lapPreviousTime).formatTimeMin(),
            rgbColorList = RGBMinColorList
        ),
        Item(
            label = R.string.stopwatch_time_component_sec,
            time = stopwatchTime.formatTimeSec(),
            lapTime = (stopwatchTime - lapPreviousTime).formatTimeSec(),
            rgbColorList = RGBSecColorList
        ),
        Item(
            label = R.string.stopwatch_time_component_ms,
            time = stopwatchTime.formatTimeMs(),
            lapTime = (stopwatchTime - lapPreviousTime).formatTimeMs(),
            rgbColorList = RGBMsColorList
        )
    )


    Box {
        var backgroundEffectsBoxSize: IntSize by remember { mutableStateOf(value = Zero) }
        Row {
            for (item in stopwatchTimeItems) {
                if (!item.isVisible) {
                    continue
                }
                TopBottom(
                    top = {
                        Label(
                            text = item.label,
                            fontSize = labelFontSize,
                            selectedLabelStyle = item.labelStyle,
                            rgbColorList = item.rgbColorList,
                            isActive = stopwatchIsActive,
                            labelFontStyleType = item.labelFontStyle,
                            isVisible = getStopwatchIsShowLabel()
                        )
                    },
                    bottom = {
                        DisplayTime(
                            text = item.time,
                            fontSize = timeFontSize,
                            selectedFontStyle = item.timeFontStyle
                        )
                    }
                )

                Separator(
                    label = item.label,
                    topFontSize = labelFontSize,
                    bottomFontSize = timeFontSize,
                    stopwatchFontStyle = item.timeFontStyle,
                    isShowLabel = getStopwatchIsShowLabel
                )

            }
        }
        Box(
            modifier = Modifier
                .matchParentSize()
                .onSizeChanged { backgroundEffectsBoxSize = it }
                .zIndex(zIndex = -1f)
        ) {
            if (
                stopwatchIsActive &&
                backgroundEffectsBoxSize.width.dp != 0.dp &&
                getStopwatchIsShowLabel()

            ) {
                StopwatchLabelBackgroundEffect(
                    size = backgroundEffectsBoxSize,
                    labelBackgroundEffect = getStopwatchLabelBackgroundEffect(),
                    theme = theme
                )
            }
        }
    }

    Row {
        for (item in stopwatchTimeItems) {
            if (!item.isVisible) {
                continue
            }

            DisplayTime(
                text = if (lapList.isNotEmpty()) item.lapTime else "",
                fontSize = lapTimeFontSize,
                selectedFontStyle = item.lapTimeFontStyle,
                color = colorScheme.secondary
            )

            Separator(
                label = if (lapList.isNotEmpty()) item.label else -1,
                topFontSize = 0.sp,
                bottomFontSize = lapTimeFontSize,
                stopwatchFontStyle = item.lapTimeFontStyle,
                color = colorScheme.primary,
                isShowLabel = getStopwatchIsShowLabel
            )
        }
    }
}


@Composable
fun Label(
    text: Int,
    fontSize: TextUnit,
    selectedLabelStyle: StopwatchLabelStyleType,
    rgbColorList: List<Int>,
    isActive: Boolean,
    labelFontStyleType: StopwatchFontStyleType,
    isVisible: Boolean = true
) {
    Text(
        text = if (isVisible) stringResource(id = text) else "",
        fontSize = if (isVisible) fontSize else 0.sp,
        fontWeight = FontWeight.ExtraLight,
        fontStyle = FontStyle.Italic,
        style = TextStyle(
            brush = Brush.linearGradient(
                colors = stopwatchLabelStyle(
                    selectedLabelStyle = selectedLabelStyle,
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
fun DisplayTime(
    text: String,
    fontSize: TextUnit,
    selectedFontStyle: StopwatchFontStyleType,
    color: Color = colorScheme.primary
) {
    Text(
        text = text,
        fontSize = fontSize,
        color = color,
        fontWeight = FontWeight.Light,
        style = TextStyle(
            drawStyle = stopwatchFontStyle(
                selectedFontStyle = selectedFontStyle,
                miter = 1f,
                width = 1.5f
            )
        )
    )
}

@Composable
fun Separator(
//    label: String,
    label: Int,
    topFontSize: TextUnit,
    bottomFontSize: TextUnit,
    stopwatchFontStyle: StopwatchFontStyleType,
    color: Color = colorScheme.tertiary,
    isShowLabel: () -> Boolean
) {

    Column {
        val separator = when (label) {
            R.string.stopwatch_time_component_hr -> ":"
            R.string.stopwatch_time_component_min -> ":"
            R.string.stopwatch_time_component_sec -> "."
            else -> ""
        }

        if (isShowLabel()) {
            Text(
                text = "",
                fontSize = topFontSize,
                style = TextStyle(),
            )
        }

        Text(
            text = separator,
            fontSize = bottomFontSize,
            color = color,
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

@Preview
@Composable
fun StopwatchTimePreview() {
    val theme = ThemeType.Dark
    ClokTheme(
        dynamicColor = true,
        theme = theme
    ) {
        Column(
            modifier = Modifier.background(color = themeBackgroundColor(theme = theme)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StopwatchTime(
                getStopwatchLapTimeFontStyle = { StopwatchFontStyleType.Default },
                getStopwatchTimeFontStyle = { StopwatchFontStyleType.Default },
                getStopwatchLabelFontStyle = { StopwatchFontStyleType.Default },
                getStopwatchLabelStyle = { StopwatchLabelStyleType.DynamicColor },
                getStopwatchLabelBackgroundEffect = { StopwatchLabelBackgroundEffectType.Snow },
                getStopwatchIsShowLabel = { true },
                lapList = listOf<StopwatchLapData>(
                    StopwatchLapData(
                        lapNumber = 1,
                        lapTime = 1200L,
                        lapTotalTime = 73_012_000L
                    )
                ),
                stopwatchTime = 73_012_000L,
                updateStopwatchLabelStyle = {},
                stopwatchIsActive = true,
                lapPreviousTime = 0L,
                theme = theme
            )

        }
    }
}