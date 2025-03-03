package com.kingfu.clok.stopwatch.screen.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.kingfu.clok.R
import com.kingfu.clok.core.formatTime
import com.kingfu.clok.stopwatch.repository.stopwatchRoom.StopwatchLapData
import com.kingfu.clok.ui.theme.ClokThemePreview
import com.kingfu.clok.ui.theme.typography
import com.kingfu.clok.ui.util.nonScaledSp


@Composable
fun Lap(
    modifier: Modifier = Modifier,
    lazyState: LazyListState,
    laps: List<StopwatchLapData>,
    shortestLapIndex: Int,
    longestLapIndex: Int,
    setMaxAndMinLapIndex: () -> Unit
) {
    val fontSize = typography.bodyMedium.fontSize.value.nonScaledSp

    LaunchedEffect(key1 = laps.size) {
        setMaxAndMinLapIndex()
        lazyState.scrollToItem(
            index = lazyState.firstVisibleItemIndex + 1,
            scrollOffset = lazyState.firstVisibleItemScrollOffset
        )
    }

    Column(
        modifier = modifier.background(color = colorScheme.surface),
        horizontalAlignment = CenterHorizontally
    ) {

        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = stringResource(id = R.string.lap),
                modifier = Modifier.weight(weight = 0.25f),
                textAlign = TextAlign.Center,
                maxLines = 1,
                color = colorScheme.outline,
                overflow = Ellipsis,
                fontSize = fontSize
            )
            Text(
                text = stringResource(id = R.string.lap_time),
                modifier = Modifier.weight(weight = 0.37f),
                textAlign = TextAlign.Center,
                maxLines = 1,
                color = colorScheme.outline,
                overflow = Ellipsis,
                fontSize = fontSize
            )
            Text(
                text = stringResource(id = R.string.total_time),
                modifier = Modifier.weight(weight = 0.37f),
                textAlign = TextAlign.Center,
                maxLines = 1,
                color = colorScheme.outline,
                overflow = Ellipsis,
                fontSize = fontSize
            )
        }

        HorizontalDivider(modifier = Modifier.fillMaxWidth(fraction = 0.8f))


        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = lazyState,
            reverseLayout = true,
            verticalArrangement = Arrangement.Top
        ) {
            itemsIndexed(
                items = laps,
                key = { _, item -> item.lapNumber }
            ) { index, item ->
                Row(modifier = Modifier.padding(all = 16.dp)) {
                    val color by animateColorAsState(
                        targetValue =
                        when {
                            shortestLapIndex == index && laps.size >= 3 -> colorScheme.error
                            longestLapIndex == index && laps.size >= 3 -> colorScheme.primary
                            else -> colorScheme.outline
                        },
                        label = ""
                    )

                    val fontWeight =
                        if (shortestLapIndex == index || longestLapIndex == index) FontWeight.Bold
                        else FontWeight.Normal

                    Text(
                        modifier = Modifier.weight(weight = 0.25f),
                        text = item.lapNumber.toString(),
                        color = color,
                        textAlign = TextAlign.Center,
                        fontWeight = fontWeight,
                        fontSize = fontSize
                    )

                    Text(
                        modifier = Modifier.weight(weight = 0.37f),
                        text = item.lapTime.formatTime(),
                        color = color,
                        textAlign = TextAlign.Center,
                        fontWeight = fontWeight,
                        fontSize = fontSize
                    )

                    Text(
                        modifier = Modifier.weight(weight = 0.37f),
                        text = item.lapTotalTime.formatTime(),
                        color = color,
                        textAlign = TextAlign.Center,
                        fontWeight = fontWeight,
                        fontSize = fontSize
                    )
                }
            }
        }
    }
}


@PreviewLightDark
@Composable
private fun LapPreview() {
    ClokThemePreview {
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

        Lap(
            lazyState = rememberLazyListState(),
            laps = data,
            shortestLapIndex = 0,
            longestLapIndex = data.size - 1,
            setMaxAndMinLapIndex = { }
        )
    }
}



