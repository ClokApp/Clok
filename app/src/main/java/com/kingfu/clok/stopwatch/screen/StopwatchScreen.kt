package com.kingfu.clok.stopwatch.screen

import android.annotation.SuppressLint
import android.os.SystemClock
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.kingfu.clok.navigation.BottomNavigationBar
import com.kingfu.clok.navigation.topBar.TopBar
import com.kingfu.clok.stopwatch.repository.stopwatchRoom.StopwatchLapData
import com.kingfu.clok.stopwatch.screen.components.Lap
import com.kingfu.clok.stopwatch.screen.components.ResetButton
import com.kingfu.clok.stopwatch.screen.components.StartButton
import com.kingfu.clok.stopwatch.screen.components.Time
import com.kingfu.clok.stopwatch.viewModel.StopwatchState
import com.kingfu.clok.ui.theme.ClokThemePreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun StopwatchScreen(
    modifier: Modifier = Modifier,
    laps: List<StopwatchLapData>,
    state: StopwatchState,
    reset: () -> Unit,
    pause: () -> Unit,
    start: () -> Unit,
    setMaxAndMinLapIndex: () -> Unit,
    bottomBar: @Composable () -> Unit,
    topBar: @Composable () -> Unit,
    setIsLap: (Boolean) -> Unit,
) {
    val lazyState = rememberLazyListState(
        initialFirstVisibleItemIndex = if (laps.isEmpty()) 0 else laps.lastIndex
    )
    val lapTimeAlpha = if (laps.isEmpty()) 0f else 1f
    val lapTime = if (laps.isEmpty()) state.time else state.time - laps[laps.lastIndex].lapTotalTime

    Scaffold(
        modifier = modifier,
        containerColor = colorScheme.surface,
        topBar = { topBar() },
        bottomBar = { bottomBar() }
    ) { paddingValues ->
        BoxWithConstraints(modifier = Modifier.padding(paddingValues = paddingValues)) {
            if (maxWidth < 600.dp) {
                Column {
                    Time(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(weight = 0.5f),
                        lapTimeAlpha = if (laps.isEmpty()) 0f else 1f,
                        time = state.time,
                        lapTime = lapTime
                    )

                    Column(modifier = Modifier.weight(weight = 0.5f)) {
                        Lap(
                            modifier = Modifier
                                .alpha(alpha = lapTimeAlpha)
                                .weight(weight = 0.85f),
                            lazyState = lazyState,
                            laps = laps,
                            shortestLapIndex = state.shortestLapIndex,
                            longestLapIndex = state.longestLapIndex,
                            setMaxAndMinLapIndex = setMaxAndMinLapIndex
                        )


                        Row(modifier = Modifier.padding(vertical = 8.dp)) {
                            Box(
                                modifier = Modifier.weight(weight = 0.5f),
                                contentAlignment = Alignment.Center
                            ) {
                                ResetButton(
                                    modifier = Modifier.fillMaxWidth(fraction = 0.5f),
                                    reset = reset,
                                    isActive = state.isActive,
                                    time = state.time,
                                    setIsLap = setIsLap
                                )
                            }

                            Box(
                                modifier = Modifier.weight(weight = 0.5f),
                                contentAlignment = Alignment.Center
                            ) {
                                StartButton(
                                    modifier = Modifier.fillMaxWidth(fraction = 0.5f),
                                    isActive = state.isActive,
                                    pause = pause,
                                    start = start
                                )
                            }
                        }
                    }
                }
            } else {
                Row {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(weight = 0.5f),
                        contentAlignment = Center
                    ) {
                        Column(
                            modifier = Modifier
                                .heightIn(max = 200.dp)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = CenterHorizontally
                        ) {
                            Time(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(weight = 0.5f),
                                lapTimeAlpha = lapTimeAlpha,
                                time = state.time,
                                lapTime = lapTime
                            )

                            Spacer(modifier = Modifier.height(height = 4.dp))

                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Center
                            ) {
                                Row(modifier = Modifier.widthIn(max = 400.dp)) {
                                    Box(
                                        modifier = Modifier.weight(weight = 0.5f),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        ResetButton(
                                            modifier = Modifier.fillMaxWidth(fraction = 0.5f),
                                            reset = reset,
                                            isActive = state.isActive,
                                            time = state.time,
                                            setIsLap = setIsLap
                                        )
                                    }

                                    Box(
                                        modifier = Modifier.weight(weight = 0.5f),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        StartButton(
                                            modifier = Modifier.fillMaxWidth(fraction = 0.5f),
                                            isActive = state.isActive,
                                            pause = pause,
                                            start = start
                                        )
                                    }
                                }
                            }
                        }
                    }


                    Box(
                        modifier = Modifier
                            .weight(weight = 0.5f)
                            .fillMaxHeight(),
                        contentAlignment = Center
                    ) {
                        Lap(
                            modifier = Modifier
//                                .heightIn(max = 600.dp)
//                                .widthIn(max = 600.dp)
                                .sizeIn(maxHeight = 600.dp, maxWidth = 600.dp),
                            lazyState = lazyState,
                            laps = laps,
                            shortestLapIndex = state.shortestLapIndex,
                            longestLapIndex = state.longestLapIndex,
                            setMaxAndMinLapIndex = setMaxAndMinLapIndex
                        )
                    }
                }
            }
        }
    }
}




//@PreviewScreenSizes
//@Preview(heightDp = 360, widthDp = 800)
@PreviewLightDark
//@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StopwatchScreenPreview() {
    val laps: MutableList<StopwatchLapData> = remember { mutableStateListOf<StopwatchLapData>() }
    val scope = rememberCoroutineScope()
    var state by remember { mutableStateOf(StopwatchState()) }

    fun lap() {
        if (laps.size == 1_000_000) return

        val lapTotalTime = state.time - (state.time % 10)
        val latestTotalLapTime =
            if (laps.isEmpty()) lapTotalTime else laps.last().lapTotalTime

        val lap = StopwatchLapData(
            lapNumber = laps.size + 1,
            lapTime = if (laps.isEmpty()) lapTotalTime else lapTotalTime - latestTotalLapTime,
            lapTotalTime = lapTotalTime
        )

        laps.add(element = lap)
    }

    fun setMaxAndMinLapIndex() {
        if (laps.size == 1_000_000 || laps.isEmpty()) return

        val lapTime = laps.last().lapTime

        if (laps[state.shortestLapIndex].lapTime > lapTime) state =
            state.copy(shortestLapIndex = laps.lastIndex)
        if (laps[state.longestLapIndex].lapTime < lapTime) state =
            state.copy(longestLapIndex = laps.lastIndex)
    }

    ClokThemePreview {
        StopwatchScreen(
            state = state,
            laps = laps,
            reset = {
                state = state.copy(
                    time = 0,
                    offsetTime = 0
                )
                laps.clear()
            },
            pause = {
                state = state.copy(isActive = false)
                state = state.copy(offsetTime = state.time)
            },
            start = {
                state = state.copy(isActive = true)
                state = state.copy(initialTime = SystemClock.elapsedRealtime())
                scope.launch {
                    while (state.isActive) {
                        state =
                            state.copy(time = SystemClock.elapsedRealtime() - state.initialTime + state.offsetTime)
                        delay(timeMillis = 1)
                        if (state.isLap) {
                            state = state.copy(isLap = false)
                            lap()
                        }
                    }
                }
            },
            setMaxAndMinLapIndex = { setMaxAndMinLapIndex() },
            bottomBar = { BottomNavigationBar(navController = rememberNavController()) },
            topBar = { TopBar(navController = rememberNavController()) },
            setIsLap = { state = state.copy(isLap = true) }
        )
    }
}

