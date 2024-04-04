package com.kingfu.clok.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.ThemeType


@Composable
fun MyRadioButton(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    isEnabled: Boolean = true,
    padding: Dp = 4.dp,
    selected: Boolean
) {
    RadioButton(
        modifier = modifier.padding(all = padding),
        selected = selected,
        onClick = onClick,
        enabled = isEnabled
    )
}

@Preview
@Composable
fun CustomRadioButtonPreview() {
    ClokTheme(
        dynamicColor = true,
        theme = ThemeType.Dark
    ) {
        MyRadioButton(
            selected = "Apple" == "Apple"
        )
    }
}