package com.kingfu.clok.timer.screen.components

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Thin
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.kingfu.clok.R
import com.kingfu.clok.ui.theme.ClokThemePreview
import com.kingfu.clok.ui.theme.typography
import com.kingfu.clok.ui.util.nonScaledSp

@Composable
fun Edit(
    lazyListHr: LazyListState ,
    lazyListMin: LazyListState,
    lazyListSec: LazyListState,
    setHour: (hour: Int) -> Unit,
    setMinute: (minute: Int) -> Unit,
    setSecond: (second: Int) -> Unit,
    isTallScreen: Boolean,
) {

    val hr by remember { derivedStateOf { lazyListHr.firstVisibleItemIndex + if (isTallScreen) 1 else 0 } }
    val min by remember { derivedStateOf { lazyListMin.firstVisibleItemIndex + if (isTallScreen) 1 else 0 } }
    val sec by remember { derivedStateOf { lazyListSec.firstVisibleItemIndex + if (isTallScreen) 1 else 0 } }


    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Scroll(
            label = stringResource(id = R.string.hour),
            lazyListState = lazyListHr,
            time = hr,
            setTime = setHour,
            timeUnit = 100,
            isTallScreen = isTallScreen
        )

        Scroll(
            label = stringResource(id = R.string.minutes),
            lazyListState = lazyListMin,
            time = min,
            setTime = setMinute,
            timeUnit = 60,
            isTallScreen = isTallScreen
        )

        Scroll(
            label = stringResource(id = R.string.seconds),
            lazyListState = lazyListSec,
            time = sec,
            setTime = setSecond,
            timeUnit = 60,
            isTallScreen = isTallScreen
        )
    }

}

@Composable
fun Scroll(
    modifier: Modifier = Modifier,
    label: String,
    lazyListState: LazyListState,
    time: Int,
    setTime: (int: Int) -> Unit,
    timeUnit: Int,
    isTallScreen: Boolean
) {

    Column(
        modifier = modifier,
        verticalArrangement = Center,
        horizontalAlignment = CenterHorizontally
    ) {
        if(isTallScreen) {
            Text(
                text = label,
                textAlign = TextAlign.Center,
                fontSize = typography.bodySmall.fontSize.value.nonScaledSp,
                fontWeight = Thin
            )
        }

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .height(height = if (isTallScreen) 200.dp else 65.dp),
            state = lazyListState,
            flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)
        ) {
            items(count = Int.MAX_VALUE) { index ->
                val isSelected = time == index

                Text(
                    modifier = Modifier.alpha(alpha = if (isSelected) 1f else 0.15f),
                    text = if (index % timeUnit < 10) "0${index % timeUnit}" else "${index % timeUnit}",
                    fontSize = typography.displayLarge.fontSize.value.nonScaledSp,

                )
            }
        }

        LaunchedEffect(key1 = time) { setTime(time % timeUnit) }
    }

}

//@PreviewScreenSizes
@PreviewLightDark
@Composable
private fun EditPreview() {

    val isTallScreen = true
    ClokThemePreview {
        Surface {
            Edit(
                lazyListHr = rememberLazyListState(
                    initialFirstVisibleItemIndex = Int.MAX_VALUE / 2 - 24 + if(isTallScreen) 0 else 1
                ),
                lazyListMin = rememberLazyListState(
                    initialFirstVisibleItemIndex = Int.MAX_VALUE / 2 - 4 + if(isTallScreen) 0 else 1
                ),
                lazyListSec = rememberLazyListState(
                    initialFirstVisibleItemIndex = Int.MAX_VALUE / 2 - 4 + if(isTallScreen) 0 else 1
                ),
                setHour = { },
                setMinute = { },
                setSecond = { },
                isTallScreen = isTallScreen
            )
        }
    }

}







