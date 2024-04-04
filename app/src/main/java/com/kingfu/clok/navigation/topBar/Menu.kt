package com.kingfu.clok.navigation.topBar

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kingfu.clok.R
import com.kingfu.clok.ui.theme.ThemeType
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.Shape
import com.kingfu.clok.ui.theme.TextBodyLarge

@Composable
fun Menu(
    goToSettingsScreen: () -> Unit,
    goToBugReportScreen: () -> Unit,
    isShowMenu: Boolean,
    toggleIsShowMenu: () -> Unit
) {
    MaterialTheme(shapes = shapes.copy(extraSmall = Shape.large)) {
        DropdownMenu(
            expanded = isShowMenu,
            onDismissRequest = { toggleIsShowMenu() },
        ) {
            DropdownMenuItem(
                text = { TextBodyLarge(text = stringResource(id = R.string.settings_name_id)) },
                onClick = {
                    goToSettingsScreen()
                    toggleIsShowMenu()
                }
            )

            DropdownMenuItem(
                text = { TextBodyLarge(text = stringResource(id = R.string.bug_report_name_id)) },
                onClick = {
                    goToBugReportScreen()
                    toggleIsShowMenu()
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuPreview() {
    ClokTheme(
        dynamicColor = true,
        theme = ThemeType.Dark
    ) {
        Menu(
            goToSettingsScreen = { },
            goToBugReportScreen = { },
            isShowMenu = true,
            toggleIsShowMenu = { }
        )
    }
}