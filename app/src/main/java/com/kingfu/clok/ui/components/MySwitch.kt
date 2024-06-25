package com.kingfu.clok.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kingfu.clok.core.ThemeType
import com.kingfu.clok.ui.theme.ClokTheme

@Composable
fun MySwitch(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    scale: Float = 0.75f,
    height: Dp = 25.dp,
    isEnabled: Boolean = true
) {
    Switch(
        modifier = modifier
            .scale(scale = scale)
            .height(height = height),
        checked = isChecked,
        enabled = isEnabled,
        onCheckedChange = {
            if (onCheckedChange != null) {
                onCheckedChange(it)
            }
        }
    )
}


@Composable
fun MySwitchPreview(theme: ThemeType) {
    ClokTheme(
        theme = theme,
        content = {
            var isChecked by remember { mutableStateOf(value = true) }

            MySwitch(
                isChecked = isChecked,
                onCheckedChange = {
                    isChecked = !isChecked
                }
            )
        }
    )
}

@Preview
@Composable
fun MySwitchPreviewDark() {
    MySwitchPreview(theme = ThemeType.DARK)
}

@Preview
@Composable
fun MySwitchPreviewLight() {
    MySwitchPreview(theme = ThemeType.LIGHT)
}