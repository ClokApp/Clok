package com.kingfu.clok.settings.screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Brightness4
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType.Companion.LongPress
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kingfu.clok.R
import com.kingfu.clok.core.ThemeType
import com.kingfu.clok.ui.components.MyDialog
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.TextBodyLarge
import com.kingfu.clok.ui.theme.TextBodyMedium

@Composable
fun Theme(
    theme: ThemeType,
    setTheme: (ThemeType) -> Unit,
    isShowDialog: Boolean,
    setIsShowDialog: (Boolean) -> Unit
) {
    val haptic = LocalHapticFeedback.current
    val choices = listOf(
        ThemeType.LIGHT to stringResource(id = R.string.light),
        ThemeType.DARK to stringResource(id = R.string.dark),
        ThemeType.SYSTEM to stringResource(id = R.string.system),
    )

    val title = stringResource(id = R.string.theme)

    if (isShowDialog) {
        MyDialog(
            title = title,
            choices = choices,
            onDismiss = { setIsShowDialog(false) },
            onClick = {
                setIsShowDialog(false)
                haptic.performHapticFeedback(hapticFeedbackType = LongPress)
                setTheme(it)
            },
            selectedItem = theme
        )
    }

    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            haptic.performHapticFeedback(hapticFeedbackType = LongPress)
            setIsShowDialog(true)
        }
        .padding(all = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Outlined.Brightness4,
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(width = 16.dp))

        Column {
            TextBodyLarge(text = title)

            TextBodyMedium(
                text = choices.first { it.first == theme }.second,
                color = colorScheme.outline
            )
        }
    }
}

@Composable
fun ThemePreview(theme: ThemeType) {
    ClokTheme(
        theme = theme,
        content = {
            Surface {
                Theme(
                    theme = theme,
                    setTheme = { },
                    isShowDialog = false,
                    setIsShowDialog = {  }
                )
            }
        }
    )
}

@Preview
@Composable
fun ThemePreviewDark() {
    ThemePreview(theme = ThemeType.DARK)
}

@Preview
@Composable
fun ThemePreviewLight() {
    ThemePreview(theme = ThemeType.LIGHT)
}
