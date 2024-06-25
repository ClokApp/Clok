package com.kingfu.clok.stopwatch.screen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Arrangement.SpaceEvenly
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kingfu.clok.core.ThemeType
import com.kingfu.clok.navigation.TopBar
import com.kingfu.clok.stopwatch.repository.stopwatchRoom.StopwatchLapData
import com.kingfu.clok.stopwatch.screen.components.Lap
import com.kingfu.clok.stopwatch.screen.components.ResetButton
import com.kingfu.clok.stopwatch.screen.components.StartButton
import com.kingfu.clok.stopwatch.screen.components.Time
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.util.isPortrait


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun StopwatchScreen(
    toggleDrawer: () -> Unit,
    isDrawerOpen: () -> Boolean,
    time: Long,
    laps: List<StopwatchLapData>,
    shortestLapIndex: Int,
    longestLapIndex: Int,
    isActive: Boolean,
    reset: () -> Unit,
    pause: () -> Unit,
    saveLapOffset: () -> Unit,
    start: () -> Unit,
    setIsLap: (Boolean) -> Unit,
    setMaxAndMinLapIndex: () -> Unit
) {
    val lazyState = rememberLazyListState(
        initialFirstVisibleItemIndex = if (laps.isEmpty()) 0 else laps.size - 1
    )
    val scrollState = rememberScrollState()
    val lapTimeAlpha = if (laps.isEmpty()) 0f else 1f
    val lapTime = if (laps.isEmpty()) time else time - laps[laps.lastIndex].lapTotalTime
    val lapTimeFontSize = 30.sp
    val timeFontSize = 60.sp


    BackHandler(
        enabled = isDrawerOpen(),
        onBack = toggleDrawer
    )

    Scaffold(
        containerColor = Transparent,
        topBar = {
            TopBar(
                toggleDrawer = toggleDrawer,
                scrolledContainerColor = Transparent,
                containerColor = Transparent
            )
        }
    ) {
        if (isPortrait()) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Center,
                horizontalAlignment = CenterHorizontally,
            ) {
                Time(time = time, fontSize = timeFontSize)

                Time(
                    modifier = Modifier.alpha(alpha = lapTimeAlpha),
                    time = lapTime,
                    fontSize = lapTimeFontSize,
                    color = colorScheme.outline
                )

                Spacer(modifier = Modifier.height(height = 60.dp))

                Lap(
                    lazyState = lazyState,
                    laps = laps,
                    shortestLapIndex = shortestLapIndex,
                    longestLapIndex = longestLapIndex,
                    findMaxAndMinLap = setMaxAndMinLapIndex
                )

                Spacer(modifier = Modifier.height(height = 30.dp))

                Row(
                    modifier = Modifier
                        .width(width = 320.dp)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = SpaceEvenly,
                ) {
                    ResetButton(
                        modifier = Modifier.weight(weight = 0.5f),
                        time = time,
                        isActive = isActive,
                        reset = reset,
                        setIsLap = setIsLap,
                    )

                    Spacer(modifier = Modifier.width(width = 42.dp))

                    StartButton(
                        modifier = Modifier.weight(weight = 0.5f),
                        isActive = isActive,
                        pause = pause,
                        saveOffsetTime = saveLapOffset,
                        start = start
                    )
                }
            }
        } else {
            Row(modifier = Modifier.padding(horizontal = 40.dp)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(weight = 0.5f)
                        .verticalScroll(state = scrollState),
                    verticalArrangement = Center,
                    horizontalAlignment = CenterHorizontally
                ) {
                    Time(time = time, fontSize = timeFontSize)

                    Time(
                        modifier = Modifier.alpha(alpha = lapTimeAlpha),
                        time = lapTime,
                        fontSize = lapTimeFontSize,
                        color = colorScheme.outline
                    )

                    Spacer(modifier = Modifier.height(height = 30.dp))

                    Row(
                        modifier = Modifier
                            .width(width = 320.dp)
                            .padding(horizontal = 16.dp),
                        verticalAlignment = CenterVertically,
                        horizontalArrangement = SpaceEvenly,
                    ) {
                        ResetButton(
                            modifier = Modifier.weight(weight = 0.5f),
                            time = time,
                            isActive = isActive,
                            reset = reset,
                            setIsLap = setIsLap,
                        )

                        Spacer(modifier = Modifier.width(width = 42.dp))

                        StartButton(
                            modifier = Modifier.weight(weight = 0.5f),
                            isActive = isActive,
                            pause = pause,
                            saveOffsetTime = saveLapOffset,
                            start = start
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(weight = 0.5f)
                        .statusBarsPadding()
                        .navigationBarsPadding()
                ) {
                    Lap(
                        modifier = Modifier.fillMaxSize(),
                        lazyState = lazyState,
                        laps = laps,
                        shortestLapIndex = shortestLapIndex,
                        longestLapIndex = longestLapIndex,
                        findMaxAndMinLap = setMaxAndMinLapIndex
                    )
                }
            }
        }
    }
}


@Composable
fun StopwatchScreenPreview(theme: ThemeType) {
    ClokTheme(
        theme = theme,
        content = {
            Surface {
                StopwatchScreen(
                    toggleDrawer = { },
                    isDrawerOpen = { false },
                    time = 1000,
                    laps = listOf<StopwatchLapData>(
                        StopwatchLapData(
                            lapNumber = 1,
                            lapTime = 1000,
                            lapTotalTime = 2000
                        ),
                        StopwatchLapData(
                            lapNumber = 2,
                            lapTime = 2000,
                            lapTotalTime = 3000
                        ),
                        StopwatchLapData(
                            lapNumber = 3,
                            lapTime = 3000,
                            lapTotalTime = 4000
                        )
                    ),
                    shortestLapIndex = 0,
                    longestLapIndex = 0,
                    isActive = false,
                    reset = { },
                    pause = { },
                    saveLapOffset = { },
                    start = { },
                    setIsLap = { },
                    setMaxAndMinLapIndex = { },
                )
            }
        }
    )
}

@Preview
@Composable
fun StopwatchScreenPreviewDark() {
    StopwatchScreenPreview(theme = ThemeType.DARK)
}

@Preview
@Composable
fun StopwatchScreenPreviewLight() {
    StopwatchScreenPreview(theme = ThemeType.LIGHT)
}

