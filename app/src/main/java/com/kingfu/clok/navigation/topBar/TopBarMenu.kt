package com.kingfu.clok.navigation.topBar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kingfu.clok.navigation.AppDestination
import com.kingfu.clok.ui.theme.ClokThemePreview

@Composable
fun TopBarMenu(
    modifier: Modifier = Modifier,
    goToSettings: () -> Unit
) {
    var expanded by remember { mutableStateOf(value = false) }

    MaterialTheme(
        shapes = shapes.copy(
            extraSmall = shapes.extraLarge
        )
    ) {
        Surface(modifier = modifier) {
            IconButton(
                onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More",
                    tint = colorScheme.onBackground
                )
            }

            DropdownMenu(
                modifier = Modifier.crop(vertical = 8.dp),
                expanded = expanded,

                onDismissRequest = { expanded = false },
            ) {

                Text(
                    modifier = Modifier
                        .clickable {
                            expanded = false
                            goToSettings()
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    text = stringResource(id = AppDestination.SETTINGS.label)
                )
            }
        }
    }
}

private fun Modifier.crop(
    horizontal: Dp = 0.dp,
    vertical: Dp = 0.dp,
): Modifier = this.layout { measurable, constraints ->
    val placeable = measurable.measure(constraints = constraints)
    fun Dp.toPxInt(): Int = this.toPx().toInt()

    layout(
        width = placeable.width - (horizontal * 2).toPxInt(),
        height = placeable.height - (vertical * 2).toPxInt()
    ) {
        placeable.placeRelative(x = -horizontal.toPx().toInt(), y = -vertical.toPx().toInt())
    }
}


@PreviewLightDark
@Composable
private fun TopBarMenuPreview() {
    ClokThemePreview {
        TopBarMenu(
            goToSettings = { }
        )
    }
}


