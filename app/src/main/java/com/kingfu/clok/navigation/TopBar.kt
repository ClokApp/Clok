package com.kingfu.clok.navigation

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kingfu.clok.core.ThemeType
import com.kingfu.clok.core.formatEnumName
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.TextBodyLarge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    toggleDrawer: () -> Unit,
    route: String? = null,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    scrolledContainerColor: Color = Color.Unspecified,
    containerColor: Color = Color.Unspecified
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor,
            scrolledContainerColor = scrolledContainerColor
        ),
        title = { route?.formatEnumName()?.let { TextBodyLarge(text = it) } },
        navigationIcon = {
            IconButton(
                onClick = toggleDrawer,
            ) {
                Icon(
                    imageVector = Icons.Rounded.Menu,
                    contentDescription = null
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarPreview(theme: ThemeType) {
    ClokTheme(
        theme = theme,
        content = {
            Surface {
                TopBar(
                    toggleDrawer = { },
                    route = stringResource(id = Screen.Settings.nameId),
                    scrollBehavior = null
                )
            }
        }
    )
}

@Preview
@Composable
fun TopBarPreviewDark() {
    TopBarPreview(theme = ThemeType.DARK)
}

@Preview
@Composable
fun TopBarPreviewLight() {
    TopBarPreview(theme = ThemeType.LIGHT)
}
