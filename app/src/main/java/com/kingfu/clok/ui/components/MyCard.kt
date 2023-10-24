package com.kingfu.clok.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType.Companion.LongPress
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kingfu.clok.settings.settingsScreen.settingsApp.settingsThemeScreen.ThemeType
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.RoundedCornerBody
import com.kingfu.clok.ui.theme.Shape
import com.kingfu.clok.ui.theme.TextBodyLarge


@Composable
fun MyCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    roundedCornerShape: RoundedCornerShape = RoundedCornerBody,
    backgroundColor: Color = colorScheme.inverseOnSurface.copy(alpha = 0.40f),
    content: @Composable RowScope.() -> Unit = {},
    horizontalPadding: Dp = 24.dp,
    verticalPadding: Dp = 16.dp,
    isClickable: Boolean = true
) {

    val haptic = LocalHapticFeedback.current

    Row(
        modifier = modifier
            .clip(shape = roundedCornerShape)
            .background(color = backgroundColor)
            .clickable(enabled = onClick != null && isClickable) {
                haptic.performHapticFeedback(hapticFeedbackType = LongPress)
                if (onClick != null) {
                    onClick()
                }
            }
            .padding(
                vertical = verticalPadding,
                horizontal = horizontalPadding
            ),
        verticalAlignment = Alignment.CenterVertically,
        content = { content() }
    )
}

@Preview
@Composable
fun CustomCardPreview() {
    ClokTheme(
        dynamicColor = true,
        theme = ThemeType.Dark
    ) {
        MyCard(
            roundedCornerShape = RoundedCornerShape(
                topStart = Shape.large.topStart,
                topEnd = CornerSize(size = 0.dp),
                bottomStart = CornerSize(size = 0.dp),
                bottomEnd = Shape.large.bottomEnd
            ),
            onClick = {},
            content = {
                TextBodyLarge(text = "Testing")
            }
        )
    }
}



