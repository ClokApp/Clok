package com.kingfu.clok.settings.screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Fullscreen
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType.Companion.LongPress
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kingfu.clok.R
import com.kingfu.clok.core.ThemeType
import com.kingfu.clok.ui.components.MySwitch
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.TextBodyLarge

@Composable
fun FullScreen(
    isFullScreen: Boolean,
    setIsFullScreen: (Boolean) -> Unit
) {
    val haptic = LocalHapticFeedback.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                haptic.performHapticFeedback(hapticFeedbackType = LongPress)
                setIsFullScreen(!isFullScreen)
            }
            .padding(all = 16.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            Icon(
                imageVector = Icons.Outlined.Fullscreen,
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(width = 16.dp))

            TextBodyLarge(
                text = stringResource(id = R.string.full_screen),
                modifier = Modifier.weight(weight = 1f)
            )

            MySwitch(
                isChecked = isFullScreen,
                onCheckedChange = {
                    haptic.performHapticFeedback(hapticFeedbackType = LongPress)
                    setIsFullScreen(it)
                }
            )
        }
    }
}

@Composable
fun FullScreenPreview(theme: ThemeType) {
    var isFullScreen by remember { mutableStateOf(value = false) }

    ClokTheme(
        theme = theme,
        content = {
            Surface {
                FullScreen(
                    isFullScreen = isFullScreen,
                    setIsFullScreen = { isFullScreen = it },
                )
            }
        }
    )
}

@Preview
@Composable
fun FullScreenPreviewDark() {
    FullScreenPreview(theme = ThemeType.DARK)
}

@Preview(showBackground = true)
@Composable
fun FullScreenPreviewLight() {
    FullScreenPreview(theme = ThemeType.LIGHT)
}
