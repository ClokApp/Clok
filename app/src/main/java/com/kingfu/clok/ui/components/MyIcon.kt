package com.kingfu.clok.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cabin
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kingfu.clok.settings.settingsScreen.settingsApp.settingsThemeScreen.ThemeType
import com.kingfu.clok.ui.theme.ClokTheme


@Composable
fun MyIcon(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String? = null,
    tint: Color = colorScheme.primary,
    padding: Dp = 8.dp
) {
    Icon(
        modifier = modifier.padding(all = padding),
        imageVector = imageVector,
        contentDescription = contentDescription,
        tint = tint
    )
}

@Preview
@Composable
fun CustomIconPreview(){
    ClokTheme(
        dynamicColor = true,
        theme = ThemeType.Dark
    )  {
        MyIcon(
            imageVector = Icons.Rounded.Cabin,
            contentDescription = "Cabin"
        )
    }
}