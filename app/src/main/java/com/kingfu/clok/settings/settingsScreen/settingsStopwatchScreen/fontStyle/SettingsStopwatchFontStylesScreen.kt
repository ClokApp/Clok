package com.kingfu.clok.settings.settingsScreen.settingsStopwatchScreen.fontStyle

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kingfu.clok.R
import com.kingfu.clok.settings.settingsScreen.settingsApp.settingsThemeScreen.ThemeType
import com.kingfu.clok.ui.components.MyHorizontalDivider
import com.kingfu.clok.ui.layouts.OneUI
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.RoundedCornerFooter
import com.kingfu.clok.ui.theme.RoundedCornerHeader
import com.kingfu.clok.ui.theme.TextBodyLarge
import com.kingfu.clok.ui.theme.TextBodyMediumHeading
import com.kingfu.clok.ui.util.screenHeight

@Composable
fun SettingsStopwatchFontStylesScreen(
    goToSettingsStopwatchLabelFontStyle: () -> Unit,
    goToSettingsStopwatchLapTimeFontStyle: () -> Unit,
    goToSettingsStopwatchTimeFontStyle: () -> Unit
) {

    OneUI(
        header = { TextBodyMediumHeading(text = stringResource(id = R.string.stopwatch_name_id)) },
        middleColumn = { TextBodyLarge(text = stringResource(id = R.string.settings_stopwatch_radio_option_label)) },
        onClick = { goToSettingsStopwatchLabelFontStyle() },
        roundedCornerShape = RoundedCornerHeader
    )

    MyHorizontalDivider()

    OneUI(
        middleColumn = { TextBodyLarge(text = stringResource(id = R.string.settings_stopwatch_radio_option_time)) },
        onClick = { goToSettingsStopwatchTimeFontStyle() }
    )

    MyHorizontalDivider()

    OneUI(
        middleColumn = { TextBodyLarge(text = stringResource(id = R.string.settings_stopwatch_radio_option_lap_time)) },
        onClick = { goToSettingsStopwatchLapTimeFontStyle() },
        roundedCornerShape = RoundedCornerFooter
    )

    Spacer(modifier = Modifier.height(height = screenHeight() * 0.90f))
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsStopwatchFontStylesScreen() {
    ClokTheme(
        dynamicColor = true,
        theme = ThemeType.Dark
    ) {
        Column(modifier = Modifier.verticalScroll(state = rememberScrollState())) {
            SettingsStopwatchFontStylesScreen(
                goToSettingsStopwatchLabelFontStyle = {},
                goToSettingsStopwatchLapTimeFontStyle = {},
                goToSettingsStopwatchTimeFontStyle = {}
            )
        }
    }
}