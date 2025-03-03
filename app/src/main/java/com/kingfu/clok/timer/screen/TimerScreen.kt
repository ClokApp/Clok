package com.kingfu.clok.timer.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.unit.dp
import com.kingfu.clok.timer.screen.components.Edit
import com.kingfu.clok.timer.screen.components.ResetButton
import com.kingfu.clok.timer.screen.components.StartButton
import com.kingfu.clok.timer.screen.components.Time
import com.kingfu.clok.timer.viewModel.TimerState
import com.kingfu.clok.ui.theme.ClokThemePreview

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun TimerScreen(
    state: TimerState,
    setHour: (Int) -> Unit,
    setMinute: (Int) -> Unit,
    setSecond: (Int) -> Unit,
    reset: () -> Unit,
    cancel: () -> Unit,
    pause: () -> Unit,
    setTotalTime: (Long) -> Unit,
    start: (Long) -> Unit,
    setOffsetTime: (Long) -> Unit,
    bottomBar: @Composable () -> Unit = {},
    topBar: @Composable () -> Unit = {},
) {

    Scaffold(
        containerColor = colorScheme.surface,
        topBar = { topBar() },
        bottomBar = { bottomBar() }
    ) { paddingValues ->
        BoxWithConstraints {
            val isTallScreen = maxHeight > 600.dp
            val lazyListHr = rememberLazyListState(
                initialFirstVisibleItemIndex = Int.MAX_VALUE / 2 - 24 + state.hour + if (isTallScreen) 0 else 1
            )
            val lazyListMin = rememberLazyListState(
                initialFirstVisibleItemIndex = Int.MAX_VALUE / 2 - 4 + state.minute + if (isTallScreen) 0 else 1
            )
            val lazyListSec = rememberLazyListState(
                initialFirstVisibleItemIndex = Int.MAX_VALUE / 2 - 4 + state.second + if (isTallScreen) 0 else 1
            )

            LaunchedEffect(key1 = true, key2 = isTallScreen) {
                lazyListHr.scrollToItem(index = Int.MAX_VALUE / 2 - 24 + state.hour + if (isTallScreen) 0 else 1)
                lazyListMin.scrollToItem(index = Int.MAX_VALUE / 2 - 4 + state.minute + if (isTallScreen) 0 else 1)
                lazyListSec.scrollToItem(index = Int.MAX_VALUE / 2 - 4 + state.second + if (isTallScreen) 0 else 1)
            }

            Column(modifier = Modifier.padding(paddingValues = paddingValues)) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(weight = 1f),
                    verticalArrangement = Center,
                    horizontalAlignment = CenterHorizontally
                ) {
                    if (!state.isEdit) {
                        Time(
                            time = state.time,
                            isFinished = state.isFinished,
                            totalTime = state.totalTime,
                        )
                    } else {
                        Edit(
                            lazyListHr = lazyListHr,
                            lazyListMin = lazyListMin,
                            lazyListSec = lazyListSec,
                            setHour = setHour,
                            setMinute = setMinute,
                            setSecond = setSecond,
                            isTallScreen = isTallScreen
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Row(modifier = Modifier.widthIn(max = 600.dp)) {
                        Box(
                            modifier = Modifier.weight(weight = 0.5f),
                            contentAlignment = Alignment.Center
                        ) {
                            ResetButton(
                                modifier = Modifier.fillMaxWidth(fraction = 0.5f),
                                hour = state.hour,
                                minute = state.minute,
                                second = state.second,
                                cancel = cancel,
                                reset = reset,
                                isEdit = state.isEdit,
                                isTallScreen = isTallScreen,
                                lazyListHr = lazyListHr,
                                lazyListMin = lazyListMin,
                                lazyListSec = lazyListSec
                            )
                        }

                        Box(
                            modifier = Modifier.weight(weight = 0.5f),
                            contentAlignment = Alignment.Center
                        ) {
                            StartButton(
                                modifier = Modifier.fillMaxWidth(fraction = 0.5f),
                                lazyListHr = lazyListHr,
                                lazyListMin = lazyListMin,
                                lazyListSec = lazyListSec,
                                hour = state.hour,
                                minute = state.minute,
                                second = state.second,
                                isEdit = state.isEdit,
                                setOffsetTime = setOffsetTime,
                                setTotalTime = setTotalTime,
                                isActive = state.isActive,
                                pause = pause,
                                start = start
                            )
                        }
                    }
                }
            }
        }
    }
}


//@PreviewScreenSizes
@PreviewFontScale
//@Preview(heightDp = 360, widthDp = 800)
//@PreviewLightDark
@Composable
private fun TimerScreenPreview() {
    ClokThemePreview {
        TimerScreen(
            setHour = { },
            setMinute = { },
            setSecond = { },
            reset = { },
            cancel = { },
            pause = { },
            setTotalTime = { },
            start = { },
            setOffsetTime = { },
            state = TimerState(
                isEdit = false,
                time = 10000000000
            )
        )
    }
}

