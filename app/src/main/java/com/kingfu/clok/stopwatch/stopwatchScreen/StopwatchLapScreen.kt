package com.kingfu.clok.stopwatch.stopwatchScreen

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
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
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush.Companion.verticalGradient
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import com.kingfu.clok.repository.room.stopwatchRoom.StopwatchLapData
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel
import com.kingfu.clok.ui.theme.Black00
import com.kingfu.clok.util.nonScaledSp


@Composable
fun StopwatchLapScreen(
    vm: StopwatchViewModel,
    lazyColumnState: LazyListState,
    configurationOrientation: Int,
    lapList: () -> List<StopwatchLapData>
) {

    LaunchedEffect(key1 = lapList()) {
        if(vm.isScrollLazyColumn) {
            if (lazyColumnState.firstVisibleItemIndex in 0..10) {
                lazyColumnState.animateScrollToItem(index = 0)
            } else {
                lazyColumnState.animateScrollToItem(index = lazyColumnState.firstVisibleItemIndex - 1)
            }
            vm.toggleIsScrollLazyColumn()
        }
    }

    Column(
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Center,
        modifier = Modifier
            .alpha(if (lapList().isEmpty() && configurationOrientation == ORIENTATION_PORTRAIT) 0f else 1f)
            .fillMaxHeight(if (configurationOrientation == ORIENTATION_PORTRAIT) 0.65f else 1f)
            .fillMaxWidth(if (configurationOrientation == ORIENTATION_PORTRAIT) 0.8f else 1f)
            .padding(
                horizontal = if (configurationOrientation == ORIENTATION_PORTRAIT) 0.dp else 40.dp
            ),

        ) {

        Row(modifier = Modifier.padding(all = 10.dp)) {
            LapLabel(name = "Lap", weight = 0.25f)
            LapLabel(name = "Lap times", weight = 0.37f)
            LapLabel(name = "Total times", weight = 0.37f)
        }

        Divider(
            color = DarkGray,
            thickness = 0.8.dp,
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        Box {
            LazyColumn(
                modifier = Modifier.fillMaxSize().animateContentSize(),
                state = lazyColumnState,
            ) {
                items(count = lapList().size, key = { lapList()[it].lapNumber }) { index ->

                    Row(
                        modifier = Modifier
                            .padding(all = 10.dp)
                            .fillMaxWidth()
                    ) {

                        LapContent(
                            name = { vm.getLapNumber(index = index) },
                            weight = 0.25f,
                            alpha = 0.50f,
                            vm = vm,
                            index = index,
                            lapList = lapList,
                        )

                        LapContent(
                            name = { vm.getLapTime(index = index) },
                            weight = 0.37f,
                            alpha = 0.70f,
                            vm = vm,
                            index = index,
                            lapList = lapList,
                        )

                        LapContent(
                            name = { vm.getLapTotalTime(index = index) },
                            weight = 0.37f,
                            alpha = 0.90f,
                            vm = vm,
                            index = index,
                            lapList = lapList,
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .background(
                        verticalGradient(
                            0.9f to Transparent,
                            1f to Black00
                        )
                    )
                    .matchParentSize()
            )
        }
    }

}

@Composable
fun RowScope.LapLabel(name: String, weight: Float) {
    Text(
        text = name,
        modifier = Modifier.weight(weight = weight),
        fontSize = 16.nonScaledSp,
        color = colorScheme.onSurfaceVariant.copy(alpha = 0.70f),
        textAlign = TextAlign.Center,
        maxLines = 1,
        overflow = Ellipsis,
        style = TextStyle()
    )
}

@Composable
fun RowScope.LapContent(
    name: () -> String,
    weight: Float,
    alpha: Float,
    vm: StopwatchViewModel,
    index: Int,
    lapList: () -> List<StopwatchLapData>,
) {
    val color by animateColorAsState(targetValue =
        if (vm.shortestLapIndex == index && lapList().size >= 3) {
            colorScheme.tertiary
        } else if (vm.longestLapIndex == index && lapList().size >= 3) {
            colorScheme.tertiaryContainer
        } else {
            colorScheme.onSurfaceVariant.copy(alpha = alpha)
        }
    )

    Text(
        text = name(),
        fontSize = 16.nonScaledSp,
        color = color,
        modifier = Modifier
            .weight(weight = weight)
            .padding(vertical = 8.dp),
        textAlign = TextAlign.Center,
        maxLines = 1,
        overflow = Ellipsis,
        style = TextStyle()
    )

}



