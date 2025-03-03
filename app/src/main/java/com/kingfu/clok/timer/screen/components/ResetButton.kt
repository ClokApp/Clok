package com.kingfu.clok.timer.screen.components

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.kingfu.clok.R
import com.kingfu.clok.ui.components.MyButton
import com.kingfu.clok.ui.theme.ClokThemePreview
import kotlinx.coroutines.launch


@Composable
fun ResetButton(
    modifier: Modifier = Modifier,
    isEdit: Boolean,
    reset: () -> Unit,
    lazyListHr: LazyListState,
    lazyListMin: LazyListState,
    lazyListSec: LazyListState,
    cancel: () -> Unit,
    isTallScreen: Boolean,
    hour: Int,
    minute: Int,
    second: Int,
    text: String = stringResource(id = if (isEdit) R.string.reset else R.string.cancel),
) {
    val scope = rememberCoroutineScope()

    MyButton(
        modifier = modifier,
        onClick = {
            if (isEdit) {
                reset()
                scope.launch {
                    lazyListHr.scrollToItem(index = Int.MAX_VALUE / 2 - 24 + if (isTallScreen) 0 else 1)
                    lazyListMin.scrollToItem(index = Int.MAX_VALUE / 2 - 4 + if (isTallScreen) 0 else 1)
                    lazyListSec.scrollToItem(index = Int.MAX_VALUE / 2 - 4 + if (isTallScreen) 0 else 1)
                }
            } else {
                cancel()
            }
        },
        containerColor = if (hour != 0 || minute != 0 || second != 0) colorScheme.primary else colorScheme.outline,
        text = text,
        isEnabled = (hour != 0 || minute != 0 || second != 0)
    )
}

@PreviewLightDark
@Composable
fun ResetButtonPreview() {
    ClokThemePreview {
        ResetButton(
            isEdit = true,
            reset = { },
            lazyListHr = rememberLazyListState(),
            lazyListMin = rememberLazyListState(),
            lazyListSec = rememberLazyListState(),
            isTallScreen = true,
            hour = 1,
            minute = 0,
            second = 0,
            cancel = { }
        )
    }
}