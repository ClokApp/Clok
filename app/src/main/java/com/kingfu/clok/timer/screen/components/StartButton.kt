package com.kingfu.clok.timer.screen.components

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.kingfu.clok.R
import com.kingfu.clok.core.timeToMilliseconds
import com.kingfu.clok.ui.components.MyButton
import com.kingfu.clok.ui.theme.ClokThemePreview

@Composable
fun StartButton(
    modifier: Modifier = Modifier,
    hour: Int,
    minute: Int,
    second: Int,
    isEdit: Boolean,
    setOffsetTime: (Long) -> Unit,
    setTotalTime: (Long) -> Unit,
    isActive: Boolean,
    pause: () -> Unit,
    start: (Long) -> Unit,
    lazyListHr: LazyListState,
    lazyListMin: LazyListState,
    lazyListSec: LazyListState,
    text: String = stringResource(id = if (isActive) R.string.pause else R.string.start),
) {

    MyButton(
        modifier = modifier,
        onClick = {
            val timeToMilliseconds = timeToMilliseconds(
                hours = hour,
                minutes = minute,
                seconds = second
            )

            if (isEdit) {
                setOffsetTime(timeToMilliseconds)
                setTotalTime(timeToMilliseconds)
            }
            if (isActive) pause() else start(timeToMilliseconds)
        },
        containerColor = when {
            lazyListHr.isScrollInProgress ||
                    lazyListMin.isScrollInProgress ||
                    lazyListSec.isScrollInProgress ||
                    hour == 0 && minute == 0 && second == 0
                -> colorScheme.outline

            else -> colorScheme.primary
        },
        text = text,
        isEnabled = !(lazyListHr.isScrollInProgress ||
                lazyListMin.isScrollInProgress ||
                lazyListSec.isScrollInProgress ||
                hour == 0 && minute == 0 && second == 0)
    )
}

@PreviewLightDark
@Composable
fun StartButtonPreview(){
    ClokThemePreview {
        StartButton(
            lazyListHr = rememberLazyListState(),
            lazyListMin = rememberLazyListState(),
            lazyListSec = rememberLazyListState(),
            start = { },
            pause =  { },
            hour = 1,
            minute = 0,
            second = 0,
            isEdit = false,
            isActive = false,
            setOffsetTime =  { },
            setTotalTime =  { }
        )
    }
}