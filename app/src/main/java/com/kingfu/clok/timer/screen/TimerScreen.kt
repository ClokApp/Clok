package com.kingfu.clok.timer.screen

import android.annotation.SuppressLint
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Arrangement.SpaceEvenly
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kingfu.clok.core.ThemeType
import com.kingfu.clok.navigation.TopBar
import com.kingfu.clok.timer.screen.components.Edit
import com.kingfu.clok.timer.screen.components.ResetButton
import com.kingfu.clok.timer.screen.components.StartButton
import com.kingfu.clok.timer.screen.components.Time
import com.kingfu.clok.ui.theme.ClokTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun TimerScreen(
    toggleDrawer: () -> Unit,
    isDrawerOpen: () -> Boolean,
    isEdit: Boolean,
    time: Long,
    isActive: Boolean,
    isFinished: Boolean,
    totalTime: Double,
    hour: Int,
    minute: Int,
    second: Int,
    setHour: (Int) -> Unit,
    setMinute: (Int) -> Unit,
    setSecond: (Int) -> Unit,
    reset: () -> Unit,
    cancel: () -> Unit,
    pause: () -> Unit,
    setTotalTime: (Long) -> Unit,
    start: (Long) -> Unit,
    setOffsetTime: (Long) -> Unit
) {
    val isPortrait = LocalConfiguration.current.orientation == ORIENTATION_PORTRAIT
    val lazyListHr = rememberLazyListState()
    val lazyListMin = rememberLazyListState()
    val lazyListSec = rememberLazyListState()
    val scrollState = rememberScrollState()

    BackHandler(
        enabled = isDrawerOpen(),
        onBack = toggleDrawer
    )

    Scaffold(
        containerColor = Transparent,
        topBar = {
            TopBar(toggleDrawer = toggleDrawer, containerColor = Transparent)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    state = scrollState,
                    enabled = !lazyListHr.isScrollInProgress &&
                            !lazyListMin.isScrollInProgress &&
                            !lazyListSec.isScrollInProgress
                ),
            verticalArrangement = Center,
            horizontalAlignment = CenterHorizontally
        ) {

            if (!isEdit) {
                Time(
                    time = time,
                    isActive = isActive,
                    isFinished = isFinished,
                    totalTime = totalTime,
                )
            } else {
                Edit(
                    lazyListHr = lazyListHr,
                    lazyListMin = lazyListMin,
                    lazyListSec = lazyListSec,
                    hour = hour,
                    minute = minute,
                    second = second,
                    setHour = setHour,
                    setMinute = setMinute,
                    setSecond = setSecond
                )
            }

            Row(
                modifier = Modifier
                    .width(width = 320.dp)
                    .padding(top = 8.dp)
                    .padding(horizontal = 24.dp),
                verticalAlignment = CenterVertically,
                horizontalArrangement = SpaceEvenly,
            ) {
                ResetButton(
                    modifier = Modifier.weight(weight = 0.5f),
                    lazyListHr = lazyListHr,
                    lazyListMin = lazyListMin,
                    lazyListSec = lazyListSec,
                    hour = hour,
                    minute = minute,
                    second = second,
                    isEdit = isEdit,
                    reset = reset,
                    cancel = cancel
                )

                Spacer(modifier = Modifier.width(width = 42.dp))

                StartButton(
                    modifier = Modifier.weight(weight = 0.5f),
                    lazyListHr = lazyListHr,
                    lazyListMin = lazyListMin,
                    lazyListSec = lazyListSec,
                    isEdit = isEdit,
                    hour = hour,
                    minute = minute,
                    second = second,
                    isActive = isActive,
                    pause = pause,
                    setTotalTime = setTotalTime,
                    start = start,
                    setOffsetTime = setOffsetTime
                )
            }
        }
    }
}

@Composable
fun TimerScreenPreview(theme: ThemeType) {
    ClokTheme(
        theme = theme,
        content = {
            Surface {
                TimerScreen(
                    toggleDrawer = { },
                    isDrawerOpen = { false },
                    isEdit = true,
                    time = 0,
                    isActive = false,
                    isFinished = false,
                    totalTime = 0.0,
                    hour = 0,
                    minute = 0,
                    second = 0,
                    setHour = { },
                    setMinute = { },
                    setSecond = { },
                    reset = { },
                    cancel = { },
                    pause = { },
                    setTotalTime = { },
                    start = { },
                    setOffsetTime = { }
                )
            }
        }
    )
}

@Preview
@Composable
fun TimerScreenPreviewDark() {
    TimerScreenPreview(theme = ThemeType.DARK)
}

@Preview
@Composable
fun TimerScreenPreviewLight() {
    TimerScreenPreview(theme = ThemeType.LIGHT)
}

