package com.kingfu.clok.settings.settingsScreen.settingsTimerScreen.fontStyle

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
import com.kingfu.clok.ui.theme.ThemeType
import com.kingfu.clok.ui.components.MyHorizontalDivider
import com.kingfu.clok.ui.layouts.OneUI
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.RoundedCornerFooter
import com.kingfu.clok.ui.theme.RoundedCornerHeader
import com.kingfu.clok.ui.theme.TextBodyLarge
import com.kingfu.clok.ui.theme.TextBodyMediumHeading
import com.kingfu.clok.ui.util.screenHeight

@Composable
fun SettingsTimerFontStylesScreen(
    goToSettingsTimerTimeFontStyle: () -> Unit,
    goToSettingsTimerScrollsFontStyle: () -> Unit
) {

    OneUI(
        header = { TextBodyMediumHeading(stringResource(id = R.string.timer_name_id)) },
        middleColumn = {
            TextBodyLarge(
                text = stringResource(id = R.string.settings_timer_time_font_style_name_id)
            )
        },
        roundedCornerShape = RoundedCornerHeader,
        onClick = { goToSettingsTimerTimeFontStyle() }
    )

    MyHorizontalDivider()

    OneUI(
        middleColumn = {
            TextBodyLarge(
                text = stringResource(id = R.string.settings_timer_scrolls_font_style_name_id)
            )
        },
        roundedCornerShape = RoundedCornerFooter,
        onClick = { goToSettingsTimerScrollsFontStyle() }
    )

    Spacer(modifier = Modifier.height(height = screenHeight() * 0.90f))

}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsTimerFontStylesScreen() {
    ClokTheme(
        dynamicColor = true,
        theme = ThemeType.Dark
    ) {
        Column(modifier = Modifier.verticalScroll(state = rememberScrollState())) {
            SettingsTimerFontStylesScreen(
                goToSettingsTimerTimeFontStyle = {},
                goToSettingsTimerScrollsFontStyle = {}
            )
        }
    }
}
