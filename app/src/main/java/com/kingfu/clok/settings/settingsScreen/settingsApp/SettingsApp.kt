package com.kingfu.clok.settings.settingsScreen.settingsApp

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kingfu.clok.R
import com.kingfu.clok.settings.settingsScreen.settingsApp.settingsThemeScreen.ThemeType
import com.kingfu.clok.ui.layouts.OneUI
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.RoundedCornerHeaderAndFooter
import com.kingfu.clok.ui.theme.TextBodyLarge
import com.kingfu.clok.ui.theme.TextBodyMediumHeading


@Composable
fun SettingsApp(goToTheme: () -> Unit) {
    OneUI(
        roundedCornerShape = RoundedCornerHeaderAndFooter,
        header = { TextBodyMediumHeading(text = stringResource(id = R.string.app)) },
        middleColumn = { TextBodyLarge(text = stringResource(id = R.string.theme)) },
        onClick = { goToTheme() }
    )
}

@Preview(showBackground = true)
@Composable
fun SettingsAppPreview(){
    ClokTheme(
        dynamicColor = true,
        theme = ThemeType.Dark
    ) {
        SettingsApp(goToTheme = { })
    }
}
