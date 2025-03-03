package com.kingfu.clok.ui.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kingfu.clok.R
import com.kingfu.clok.core.ThemeType
import com.kingfu.clok.ui.theme.ClokThemePreview
import com.kingfu.clok.ui.theme.Shape
import kotlin.enums.EnumEntries


@Composable
fun DialogTheme(
    modifier: Modifier = Modifier,
    themes: EnumEntries<ThemeType> = ThemeType.entries,
    onDismiss: () -> Unit,
    onClick: (ThemeType) -> Unit,
    title: String = "Theme",
    theme: ThemeType
) {

    var selected by rememberSaveable { mutableStateOf(value = theme) }

    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            modifier = modifier,
            shape = Shape.extraLarge,
            color = colorScheme.background
        ) {
            Column(
                modifier = Modifier.verticalScroll(state = rememberScrollState()),
                horizontalAlignment = CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = title,
                        modifier = Modifier.padding(all = 24.dp),
                        style = typography.headlineSmall
                    )
                }

                themes.forEach {
                    Row(
                        modifier = Modifier
                            .clickable { selected = it }
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selected == it,
                            onClick = { selected = it }
                        )

                        Text(
                            text = when (it) {
                                ThemeType.LIGHT -> stringResource(id = R.string.light)
                                ThemeType.DARK -> stringResource(id = R.string.dark)
                            },
                            color = if (it == selected) colorScheme.primary else colorScheme.inverseSurface
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .padding(all = 24.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    Text(
                        modifier = Modifier
                            .clip(shape = CircleShape)
                            .clickable {
                                onClick(selected)
                                onDismiss()
                            }
                            .padding(vertical = 8.dp, horizontal = 16.dp),
                        text = "Confirm",
                        style = typography.bodyLarge
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun DialogThemePreview() {
    var selectedItem by remember { mutableStateOf(value = ThemeType.DARK) }

    ClokThemePreview(
        content = {
            DialogTheme(
                themes = ThemeType.entries,
                onDismiss = { },
                theme = selectedItem,
                onClick = { selectedItem = it }
            )
        }
    )
}
