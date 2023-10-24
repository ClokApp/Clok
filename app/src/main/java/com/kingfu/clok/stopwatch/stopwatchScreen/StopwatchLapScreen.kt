package com.kingfu.clok.stopwatch.stopwatchScreen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush.Companion.verticalGradient
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kingfu.clok.R
import com.kingfu.clok.repository.room.stopwatchRoom.StopwatchLapData
import com.kingfu.clok.settings.settingsScreen.settingsApp.settingsThemeScreen.ThemeType
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.TextBodyLarge
import com.kingfu.clok.ui.theme.themeBackgroundColor
import com.kingfu.clok.ui.theme.typography
import com.kingfu.clok.ui.util.isPortrait
import com.kingfu.clok.ui.util.nonScaledSp
import com.kingfu.clok.util.formatLapTime
import com.kingfu.clok.util.formatLapTotalTime


@Composable
fun StopwatchLapScreen(
    lazyColumnState: LazyListState,
    lapList: List<StopwatchLapData>,
    isScrollLazyColumn: Boolean,
    toggleIsScrollLazyColumn: () -> Unit,
    shortestLapIndex: Int,
    longestLapIndex: Int,
    theme: ThemeType,
) {

    LaunchedEffect(key1 = lapList) {
        if (isScrollLazyColumn) {
            if (lazyColumnState.firstVisibleItemIndex in 0..10) {
                lazyColumnState.animateScrollToItem(index = 0)
            } else {
                lazyColumnState.animateScrollToItem(index = lazyColumnState.firstVisibleItemIndex - 1)
            }
            toggleIsScrollLazyColumn()
        }
    }

    Column(
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Center,
        modifier = Modifier
            .alpha(alpha = if (lapList.isEmpty() && isPortrait()) 0f else 1f)
            .fillMaxHeight(fraction = if (isPortrait()) 0.65f else 1f)
            .fillMaxWidth(fraction = if (isPortrait()) 0.8f else 1f),
    ) {

        Row(modifier = Modifier.padding(all = 10.dp)) {
            LapLabel(name = stringResource(id = R.string.stopwatch_lap_screen_lap), weight = 0.25f)
            LapLabel(
                name = stringResource(id = R.string.stopwatch_lap_screen_lap_time),
                weight = 0.37f
            )
            LapLabel(
                name = stringResource(id = R.string.stopwatch_lap_screen_total_time),
                weight = 0.37f
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 12.dp),
            thickness = 0.8.dp,
        )

        Box {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .animateContentSize(),
                state = lazyColumnState,
            ) {
                itemsIndexed(
                    items = lapList,
                    key = { index, item ->
                        item.lapNumber
                    }
                ) { index, item ->
                    Row(
                        modifier = Modifier
                            .padding(all = 10.dp)
                            .fillMaxWidth()
                    ) {
                        val lapNumber = item.lapNumber
                        LapContent(
                            text = if (lapNumber < 10) "0${lapNumber}" else lapNumber.toString(),
                            weight = 0.25f,
                            alpha = 0.50f,
                            index = index,
                            lapList = lapList,
                            shortestLapIndex = shortestLapIndex,
                            longestLapIndex = longestLapIndex
                        )

                        LapContent(
                            text = formatLapTime(
                                timeMillis = item.lapTime,
                                totalTime = item.lapTotalTime
                            ),
                            weight = 0.37f,
                            alpha = 0.70f,
                            index = index,
                            lapList = lapList,
                            shortestLapIndex = shortestLapIndex,
                            longestLapIndex = longestLapIndex
                        )

                        LapContent(
                            text = formatLapTotalTime(totalTime = item.lapTotalTime),
                            weight = 0.37f,
                            alpha = 0.90f,
                            index = index,
                            lapList = lapList,
                            shortestLapIndex = shortestLapIndex,
                            longestLapIndex = longestLapIndex
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .background(
                        verticalGradient(
                            0.9f to Transparent,
                            1f to themeBackgroundColor(theme = theme)
                        )
                    )
                    .matchParentSize()
            )
        }
    }
}

@Composable
fun RowScope.LapLabel(name: String, weight: Float) {
    TextBodyLarge(
        text = name,
        modifier = Modifier.weight(weight = weight),
        textAlign = TextAlign.Center,
        maxLines = 1,
        color = colorScheme.onSurfaceVariant.copy(alpha = 0.70f),
        fontSize = typography.bodyLarge.fontSize.value.nonScaledSp,
        overflow = Ellipsis,
        style = TextStyle()
    )
}

@Composable
fun RowScope.LapContent(
    text: String,
    weight: Float,
    alpha: Float,
    index: Int,
    lapList: List<StopwatchLapData>,
    shortestLapIndex: Int,
    longestLapIndex: Int
) {
    val color by animateColorAsState(
        targetValue =
        if (shortestLapIndex == index && lapList.size >= 3) {
            colorScheme.tertiary
        } else if (longestLapIndex == index && lapList.size >= 3) {
            if (isSystemInDarkTheme()) colorScheme.tertiaryContainer else colorScheme.primary
        } else {
            colorScheme.onSurfaceVariant.copy(alpha = alpha)
        },
        label = ""
    )
    TextBodyLarge(
        text = text,
        fontSize = typography.bodyLarge.fontSize.value.nonScaledSp,
        color = color,
        textAlign = TextAlign.Center,
        maxLines = 1,
        overflow = Ellipsis,
        modifier = Modifier
            .weight(weight = weight)
            .padding(vertical = 8.dp),
        style = TextStyle()
    )
}

@Preview
@Composable
fun StopwatchLapScreenPreview() {
    val theme = ThemeType.Light
    ClokTheme(
        dynamicColor = true,
        theme = theme
    ) {

        val data = listOf<StopwatchLapData>(
            StopwatchLapData(
                lapNumber = 1,
                lapTime = 1000L,
                lapTotalTime = 2000L,
            ),
            StopwatchLapData(
                lapNumber = 2,
                lapTime = 2000L,
                lapTotalTime = 3000L,
            ),
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = themeBackgroundColor(theme = theme)),
            horizontalAlignment = CenterHorizontally
        ) {
            StopwatchLapScreen(
                lazyColumnState = rememberLazyListState(),
                lapList = data,
                isScrollLazyColumn = true,
                toggleIsScrollLazyColumn = {},
                shortestLapIndex = 0,
                longestLapIndex = 0,
                theme = theme,
            )
        }
    }
}



