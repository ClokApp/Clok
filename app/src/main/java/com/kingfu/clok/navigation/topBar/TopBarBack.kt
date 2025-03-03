package com.kingfu.clok.navigation.topBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBackIos
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.kingfu.clok.ui.theme.ClokThemePreview
import com.kingfu.clok.ui.theme.typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarBack(
    modifier: Modifier = Modifier,
    title: String = "",
    onClick: () -> Unit
) {
    Surface {
        TopAppBar(
            modifier = modifier,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Transparent
            ),
            title = {
                Text(
                    text = title,
                    style = typography.bodyLarge
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = { onClick() }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBackIos,
                        contentDescription = null
                    )
                }
            }
        )
    }
}

@PreviewLightDark
@Composable
private fun TopBarBackPreview() {
    ClokThemePreview {
        TopBarBack(
            onClick = { }
        )
    }
}
