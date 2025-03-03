package com.kingfu.clok.stopwatch.screen.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.kingfu.clok.R
import com.kingfu.clok.ui.components.MyButton
import com.kingfu.clok.ui.theme.ClokThemePreview


@Composable
fun StartButton(
    modifier: Modifier = Modifier,
    isActive: Boolean,
    pause: () -> Unit,
    start: () -> Unit,
    text: String = stringResource(id = if (isActive) R.string.pause else R.string.start)
) {
    MyButton(
        modifier = modifier,
        onClick = { if (isActive) pause() else start() },
        text = text,
    )
}

@PreviewLightDark
@Composable
private fun StartButtonPreview(){
    ClokThemePreview {
        StartButton(
            isActive = true,
            pause = { },
            start = { }
        )
    }
}