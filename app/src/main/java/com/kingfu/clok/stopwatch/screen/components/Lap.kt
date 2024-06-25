package com.kingfu.clok.stopwatch.screen.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kingfu.clok.R
import com.kingfu.clok.core.ThemeType
import com.kingfu.clok.core.formatTime
import com.kingfu.clok.stopwatch.repository.stopwatchRoom.StopwatchLapData
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.TextBodyLarge
import com.kingfu.clok.ui.util.isPortrait


@Composable
fun Lap(
    modifier: Modifier = Modifier,
    lazyState: LazyListState,
    laps: List<StopwatchLapData>,
    shortestLapIndex: Int,
    longestLapIndex: Int,
    findMaxAndMinLap: () -> Unit
) {
    val height = if (isPortrait()) 300.dp else 500.dp
    val alphaValue = if (laps.isEmpty() && isPortrait()) 0f else 1f
    var currentLapSize by rememberSaveable { mutableIntStateOf(value = laps.size) }


    LaunchedEffect(key1 = laps.size) {
        if (laps.isEmpty()) {
            currentLapSize = 0
        }
        if (laps.isNotEmpty() && currentLapSize < laps.size) {
            findMaxAndMinLap()
            currentLapSize = laps.size
            lazyState.scrollToItem(index = lazyState.firstVisibleItemIndex + 1)
        }
    }


    Column(
        modifier = modifier.alpha(alpha = alphaValue),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            modifier = Modifier
                .width(width = 350.dp)
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            TextBodyLarge(
                text = stringResource(id = R.string.stopwatch_lap),
                modifier = Modifier.weight(weight = 0.25f),
                textAlign = TextAlign.Center,
                maxLines = 1,
                color = colorScheme.outline,
                overflow = Ellipsis
            )
            TextBodyLarge(
                text = stringResource(id = R.string.stopwatch_lap_time),
                modifier = Modifier.weight(weight = 0.37f),
                textAlign = TextAlign.Center,
                maxLines = 1,
                color = colorScheme.outline,
                overflow = Ellipsis,
            )
            TextBodyLarge(
                text = stringResource(id = R.string.stopwatch_lap_total_time),
                modifier = Modifier.weight(weight = 0.37f),
                textAlign = TextAlign.Center,
                maxLines = 1,
                color = colorScheme.outline,
                overflow = Ellipsis,
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 48.dp),
            thickness = 0.8.dp
        )


        LazyColumn(
            modifier = Modifier
                .width(width = 350.dp)
                .height(height = height),
            state = lazyState,
            reverseLayout = true,
            verticalArrangement = Arrangement.Top
        ) {
            itemsIndexed(
                items = laps,
                key = { _, item -> item.lapNumber }
            ) { index, item ->
                Row(
                    modifier = Modifier
                        .padding(all = 12.dp)
                        .fillMaxWidth()
                ) {
                    val color by animateColorAsState(
                        targetValue =
                        when {
                            shortestLapIndex == index && laps.size >= 3 -> colorScheme.tertiary
                            longestLapIndex == index && laps.size >= 3 -> colorScheme.primary
                            else -> colorScheme.outline
                        },
                        label = ""
                    )

                    val fontWeight =
                        if (shortestLapIndex == index || longestLapIndex == index) FontWeight.Bold
                        else FontWeight.Normal


                    TextBodyLarge(
                        modifier = Modifier
                            .weight(weight = 0.25f)
                            .padding(vertical = 8.dp),
                        text = item.lapNumber.toString(),
                        color = color,
                        textAlign = TextAlign.Center,
                        fontWeight = fontWeight
                    )

                    TextBodyLarge(
                        modifier = Modifier
                            .weight(weight = 0.37f)
                            .padding(vertical = 8.dp),
                        text = item.lapTime.formatTime(),
                        color = color,
                        textAlign = TextAlign.Center,
                        fontWeight = fontWeight
                    )

                    TextBodyLarge(
                        modifier = Modifier
                            .weight(weight = 0.37f)
                            .padding(vertical = 8.dp),
                        text = item.lapTotalTime.formatTime(),
                        color = color,
                        textAlign = TextAlign.Center,
                        fontWeight = fontWeight
                    )
                }
            }
        }
    }
}

@Composable
fun LapPreview(theme: ThemeType) {

    ClokTheme(
        theme = theme,
        content = {
            val data = listOf(
                StopwatchLapData(
                    lapNumber = 1,
                    lapTime = 1000L,
                    lapTotalTime = 2000L
                ),
                StopwatchLapData(
                    lapNumber = 2,
                    lapTime = 2000L,
                    lapTotalTime = 3000L
                ),
                StopwatchLapData(
                    lapNumber = 3,
                    lapTime = 3000L,
                    lapTotalTime = 4000L
                )
            )

            Surface(modifier = Modifier.fillMaxSize()) {
                Lap(
                    lazyState = rememberLazyListState(),
                    laps = data,
                    shortestLapIndex = 0,
                    longestLapIndex = data.size - 1,
                    findMaxAndMinLap = { }
                )
            }
        }
    )
}

@Preview
@Composable
fun LapPreviewDark() {
    LapPreview(theme = ThemeType.DARK)
}

@Preview
@Composable
fun LapPreviewLight() {
    LapPreview(theme = ThemeType.LIGHT)
}



