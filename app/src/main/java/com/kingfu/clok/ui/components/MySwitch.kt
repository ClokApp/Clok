package com.kingfu.clok.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.hapticfeedback.HapticFeedbackType.Companion.LongPress
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.ThemeType
import com.kingfu.clok.ui.util.NoRippleTheme

@Composable
fun MySwitch(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    onCheckedChange: (() -> Unit)? = null,
    scale: Float = 0.85f,
    height: Dp = 35.dp,
    isEnabled: Boolean = true
) {

    val haptic = LocalHapticFeedback.current

    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        Switch(
            modifier = modifier.scale(scale = scale).height(height = height),
            checked = isChecked,
            enabled = isEnabled,
            onCheckedChange = {
                haptic.performHapticFeedback(hapticFeedbackType = LongPress)
                if (onCheckedChange != null) {
                    onCheckedChange()
                }
            }
        )
    }
}


@Preview
@Composable
fun CustomSwitchPreview(){
    ClokTheme(
        dynamicColor = true,
        theme = ThemeType.Dark
    ) {
        var isChecked by remember{ mutableStateOf(value = true) }

        MySwitch(
            isChecked = isChecked,
            onCheckedChange = {
                isChecked = !isChecked
            }
        )
    }
}