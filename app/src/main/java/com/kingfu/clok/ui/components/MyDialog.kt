package com.kingfu.clok.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kingfu.clok.R
import com.kingfu.clok.core.ThemeType
import com.kingfu.clok.core.formatEnumName
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.Shape
import com.kingfu.clok.ui.theme.TextBodyLarge


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : Any?> MyDialog(
    modifier: Modifier = Modifier,
    choices: List<Pair<T, String>>,
    onDismiss: () -> Unit,
    onClick: (T) -> Unit = {},
    title: String? = null,
    selectedItem: T
) {

    var selected by rememberSaveable { mutableStateOf(value = selectedItem) }

    BasicAlertDialog(
        modifier = modifier.fillMaxWidth(),
        onDismissRequest = { onDismiss() }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .clip(shape = Shape.extraLarge)
                .background(color = colorScheme.background.copy(alpha = 0.95f)),
            horizontalAlignment = CenterHorizontally
        ) {

            if (title != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    TextBodyLarge(
                        text = title.formatEnumName(),
                        modifier = Modifier.padding(all = 24.dp)
                    )
                }
            }

            choices.forEachIndexed {  index, item ->
                Row(
                    modifier = Modifier
                        .clickable { selected = item.first }
                        .padding(horizontal = 8.dp)
                        .padding(end = 8.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selected == item.first,
                        onClick = { selected = item.first }
                    )

                    TextBodyLarge(
                        text = item.second,
                        modifier = Modifier.fillMaxWidth(),
                        color = if (item == selected) colorScheme.primary else colorScheme.inverseSurface
                    )
                }

                if (choices.size - 1 == index && title != null) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalAlignment = End,
                    ) {
                        Button(
                            onClick = { onClick(selected) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Transparent
                            )
                        ) {
                            TextBodyLarge(text = "OK")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MyDialogPreview(theme: ThemeType) {
    val choices = listOf(
        ThemeType.LIGHT to stringResource(id = R.string.light),
        ThemeType.DARK to stringResource(id = R.string.dark),
        ThemeType.SYSTEM to stringResource(id = R.string.system),
    )

    ClokTheme(
        theme = theme,
        content = {
            Surface {
                MyDialog(
                    choices = choices,
                    onDismiss = { },
                    title = "Theme",
                    selectedItem = theme
                )
            }
        }
    )
}

@Preview
@Composable
fun MyDialogPreviewDark() {
    MyDialogPreview(theme = ThemeType.DARK)
}


@Preview
@Composable
fun MyDialogPreviewLight() {
    MyDialogPreview(theme = ThemeType.LIGHT)
}

