package com.kingfu.clok.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.kingfu.clok.ui.theme.ClokThemePreview
import com.kingfu.clok.ui.theme.typography
import com.kingfu.clok.ui.util.nonScaledSp

@Composable
fun MyButton(
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    containerColor: Color = colorScheme.primary,
    text: String,
    onClick: () -> Unit
) {
    Text(
        modifier = modifier
            .clip(shape = CircleShape)
            .background(color = containerColor)
            .clickable(enabled = isEnabled) { onClick() }
            .padding(vertical = 4.dp, horizontal = 6.dp),
        text = text,
        color = colorScheme.surface,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontSize = typography.bodyMedium.fontSize.value.nonScaledSp,
        textAlign = TextAlign.Center
    )
}

@PreviewLightDark
@Composable
private fun ButtonPreview() {
    ClokThemePreview {
        MyButton(
            text = "start",
            onClick = { }
        )
    }
}