package com.kingfu.clok.settings.settingsScreen.settingsApp.settingsThemeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kingfu.clok.R
import com.kingfu.clok.ui.components.MyHorizontalDivider
import com.kingfu.clok.ui.components.MyRadioButton
import com.kingfu.clok.ui.layouts.OneUI
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.RoundedCornerFooter
import com.kingfu.clok.ui.theme.RoundedCornerHeader
import com.kingfu.clok.ui.theme.TextBodyLarge
import com.kingfu.clok.ui.theme.TextBodyMediumHeading
import com.kingfu.clok.ui.theme.themeBackgroundColor
import com.kingfu.clok.ui.util.screenHeight


@Composable
fun SettingsThemeScreen(
    updateAppTheme: (ThemeType) -> Unit,
    saveAppTheme: () -> Unit,
    theme: ThemeType
) {

    val dark = ThemeType.Dark
    val light = ThemeType.Light
    val system = ThemeType.System

    OneUI(
        header = { TextBodyMediumHeading(text = stringResource(id = R.string.app)) },
        middleColumn = { TextBodyLarge(text = stringResource(id = R.string.variable_dark)) },
        leftColumn = { MyRadioButton(selected = theme == dark) },
        roundedCornerShape = RoundedCornerHeader,
        onClick = {
            updateAppTheme(dark)
            saveAppTheme()
        }
    )

    MyHorizontalDivider()

    OneUI(
        middleColumn = { TextBodyLarge(text = stringResource(id = R.string.variable_light)) },
        leftColumn = { MyRadioButton(selected = theme == light) },
        onClick = {
            updateAppTheme(light)
            saveAppTheme()
        }
    )

    MyHorizontalDivider()

    OneUI(
        middleColumn = { TextBodyLarge(text = stringResource(id = R.string.variable_system)) },
        leftColumn = { MyRadioButton(selected = theme == system) },
        roundedCornerShape = RoundedCornerFooter,
        onClick = {
            updateAppTheme(system)
            saveAppTheme()
        }
    )

    Spacer(modifier = Modifier.height(height = screenHeight() * 0.90f))
}

@Preview(showBackground = true)
@Composable
fun SettingsThemeScreenPreview(){
    val theme = ThemeType.Dark
    ClokTheme(
        dynamicColor = true,
        theme = theme
    ) {
        Column(modifier = Modifier.background(color = themeBackgroundColor(theme = theme ))) {
            SettingsThemeScreen(
                updateAppTheme = {},
                saveAppTheme = {},
                theme = theme
            )
        }
    }
}