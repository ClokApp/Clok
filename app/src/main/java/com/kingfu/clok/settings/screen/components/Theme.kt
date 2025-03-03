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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.kingfu.clok.R
import com.kingfu.clok.core.ThemeType
import com.kingfu.clok.ui.theme.ClokThemePreview
import com.kingfu.clok.ui.theme.typography

@Composable
fun Theme(
    modifier: Modifier = Modifier,
    theme: ThemeType,
    goToDialogTheme: (ThemeType) -> Unit
) {
    Surface {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clickable { goToDialogTheme(theme) }
                .padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Brightness4,
                contentDescription = null,
                tint = colorScheme.onBackground
            )

            Spacer(modifier = Modifier.width(width = 16.dp))

            Column {
                Text(
                    text =  stringResource(id = R.string.theme),
                    style = typography.bodyLarge
                )

                Text(
                    text = when(theme){
                        ThemeType.LIGHT -> stringResource(id = R.string.light)
                        ThemeType.DARK -> stringResource(id = R.string.dark)
                    },
                    color = colorScheme.outline,
                    style = typography.bodyLarge
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun ThemePreview() {
    ClokThemePreview {
        Theme(
            theme = ThemeType.DARK,
            goToDialogTheme = { }
        )
    }
}

