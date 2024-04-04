package com.kingfu.clok.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kingfu.clok.ui.theme.ThemeType
import com.kingfu.clok.ui.theme.ClokTheme

@Composable
fun MyHorizontalDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 1.0.dp,
    color: Color = DividerDefaults.color.copy(alpha = if(isSystemInDarkTheme()) 1f else 0.5f),
    backgroundColor: Color = colorScheme.inverseOnSurface.copy(alpha = 0.40f),
    horizontalPadding: Dp = 24.dp
) {

    HorizontalDivider(
        modifier = modifier
            .background(color = backgroundColor)
            .padding(horizontal = horizontalPadding),
        thickness = thickness,
        color = color
    )
}

@Preview
@Composable
fun PreviewMyHorizontalDivider() {
    ClokTheme(
        dynamicColor = true,
        theme = ThemeType.Dark
    ) {
        MyHorizontalDivider()
    }
}
