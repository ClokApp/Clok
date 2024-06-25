package com.kingfu.clok.timer.screen.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType.Companion.LongPress
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Thin
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kingfu.clok.R
import com.kingfu.clok.core.ThemeType
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.TextBodyLarge
import com.kingfu.clok.ui.theme.TextLabelSmall
import com.kingfu.clok.ui.util.nonScaledSp

@Composable
fun Edit(
    lazyListHr: LazyListState,
    lazyListMin: LazyListState,
    lazyListSec: LazyListState,
    hour: Int,
    minute: Int,
    second: Int,
    setHour: (hour: Int) -> Unit,
    setMinute: (minute: Int) -> Unit,
    setSecond: (second: Int) -> Unit
) {

    val selectedHr by remember { derivedStateOf { lazyListHr.firstVisibleItemIndex + 1 } }
    val selectedMin by remember { derivedStateOf { lazyListMin.firstVisibleItemIndex + 1 } }
    val selectedSec by remember { derivedStateOf { lazyListSec.firstVisibleItemIndex + 1 } }

    LaunchedEffect(key1 = true) {
        lazyListHr.scrollToItem(index = Int.MAX_VALUE / 2 - 24 + hour)
        lazyListMin.scrollToItem(index = Int.MAX_VALUE / 2 - 4 + minute)
        lazyListSec.scrollToItem(index = Int.MAX_VALUE / 2 - 4 + second)
    }
    
    Row(
        modifier = Modifier
            .wrapContentWidth()
            .height(height = 230.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Scroll(
            label = stringResource(id = R.string.timer_edit_hour),
            lazyListState = lazyListHr,
            selected = selectedHr,
            setTime = setHour,
            timeUnit = 100
        )

        Scroll(
            label = stringResource(id = R.string.timer_edit_screen_minutes),
            lazyListState = lazyListMin,
            selected = selectedMin,
            setTime = setMinute,
            timeUnit = 60
        )

        Scroll(
            label =  stringResource(id = R.string.timer_edit_screen_seconds),
            lazyListState = lazyListSec,
            selected = selectedSec,
            setTime = setSecond,
            timeUnit = 60
        )
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Scroll(
    label: String,
    lazyListState: LazyListState,
    selected: Int,
    setTime: (int: Int) -> Unit,
    timeUnit: Int
) {
    val haptic = LocalHapticFeedback.current
    
    Column(
        verticalArrangement = Center,
        horizontalAlignment = CenterHorizontally
    ) {

        TextLabelSmall(
            text = label,
            textAlign = TextAlign.Center,
            fontSize = 18.dp.value.nonScaledSp,
            fontWeight = Thin
        )

        LazyColumn(
            state = lazyListState,
            modifier = Modifier.padding(horizontal = 10.dp),
            flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState),
        ) {
            items(count = Int.MAX_VALUE) { index ->
                val text =
                    if (index % timeUnit < 10) "0${index % timeUnit}" else "${index % timeUnit}"
                val color =
                    if (selected == index) colorScheme.onSurface else colorScheme.onSurface.copy(
                        alpha = 0.15f
                    )
                TextBodyLarge(
                    text = text,
                    fontSize = 60.dp.value.nonScaledSp,
                    color = color
                )

            }
        }

        LaunchedEffect(key1 = selected) {
            if (lazyListState.isScrollInProgress) {
                haptic.performHapticFeedback(hapticFeedbackType = LongPress)
            }
            setTime(selected % timeUnit)
        }
    }
}

@Composable
fun EditPreview(theme: ThemeType) {
    ClokTheme(
        theme = theme,
        content = {
            Surface {
                Edit(
                    lazyListHr = rememberLazyListState(),
                    lazyListMin = rememberLazyListState(),
                    lazyListSec = rememberLazyListState(),
                    hour = 0,
                    minute = 0,
                    second = 0,
                    setHour = { },
                    setMinute = { },
                    setSecond = { },
                )
            }
        }
    )
}

@Preview
@Composable
fun EditPreviewDark() {
    EditPreview(theme = ThemeType.DARK)
}

@Preview
@Composable
fun EditPreviewLight() {
    EditPreview(theme = ThemeType.LIGHT)
}






