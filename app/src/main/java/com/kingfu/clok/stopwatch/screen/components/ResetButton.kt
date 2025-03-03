package com.kingfu.clok.stopwatch.screen.components

import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.kingfu.clok.R
import com.kingfu.clok.ui.components.MyButton
import com.kingfu.clok.ui.theme.ClokThemePreview

@Composable
fun ResetButton(
    modifier: Modifier = Modifier,
    reset: () -> Unit,
    isActive: Boolean,
    time: Long,
    setIsLap: (Boolean) -> Unit,
    text: String = stringResource(id = if (isActive) R.string.lap else R.string.reset)
) {
    MyButton(
        modifier = modifier,
        onClick = { if (isActive) setIsLap(true) else reset() },
        text = text,
        isEnabled = time > 0,
        containerColor = if (time > 0) colorScheme.primary else colorScheme.outline
    )
}

@PreviewLightDark
@Composable
private fun ResetButtonPreview() {
    ClokThemePreview {
        ResetButton(
            reset = { },
            isActive = true,
            time = 200L,
            setIsLap = { }
        )
    }
}