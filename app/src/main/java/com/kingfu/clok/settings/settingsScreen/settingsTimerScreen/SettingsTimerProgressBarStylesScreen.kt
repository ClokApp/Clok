package com.kingfu.clok.settings.settingsScreen.settingsTimerScreen

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
import com.kingfu.clok.timer.feature.timerProgressBarStyle.TimerProgressBarStyleType
import com.kingfu.clok.ui.components.MyHorizontalDivider
import com.kingfu.clok.ui.components.MyRadioButton
import com.kingfu.clok.ui.layouts.OneUI
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.RoundedCornerFooter
import com.kingfu.clok.ui.theme.RoundedCornerHeader
import com.kingfu.clok.ui.theme.TextBodyLarge
import com.kingfu.clok.ui.theme.TextBodyMedium
import com.kingfu.clok.ui.theme.TextBodyMediumHeading
import com.kingfu.clok.ui.util.screenHeight

@Composable
fun SettingsTimerProgressBarStyleScreen(
    updateTimerLabelStyle: (String) -> Unit,
    saveTimerProgressBarStyle: () -> Unit,
    timerProgressBarStyle: String
) {
    val dynamicColor = TimerProgressBarStyleType.DynamicColor.name
    val rgb = TimerProgressBarStyleType.RGB.name

    OneUI(
        header = { TextBodyMediumHeading(text = stringResource(id = R.string.timer_name_id)) },
        middleColumn = {
            TextBodyLarge(text = stringResource(id = R.string.settings_timer_label_style_dynamic_color))
            TextBodyMedium(text = stringResource(id = R.string.settings_timer_dynamic_color_label_style_description))
        },
        leftColumn = { MyRadioButton(selected = dynamicColor == timerProgressBarStyle) },
        roundedCornerShape = RoundedCornerHeader,
        onClick = {
            updateTimerLabelStyle(dynamicColor)
            saveTimerProgressBarStyle()
        }
    )

    MyHorizontalDivider()

    OneUI(
        middleColumn = { TextBodyLarge(text = stringResource(id = R.string.variable_rgb)) },
        leftColumn = { MyRadioButton(selected = rgb == timerProgressBarStyle) },
        roundedCornerShape = RoundedCornerFooter,
        onClick = {
            updateTimerLabelStyle(rgb)
            saveTimerProgressBarStyle()
        }
    )

    Spacer(modifier = Modifier.height(height = screenHeight() * 0.90f))

}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsTimerProgressBarStyleScreen() {
    ClokTheme(
        dynamicColor = true,
        theme = ThemeType.Dark
    ) {
        Column(modifier = Modifier.verticalScroll(state = rememberScrollState())) {
            SettingsTimerProgressBarStyleScreen(
                updateTimerLabelStyle = {},
                saveTimerProgressBarStyle = {},
                timerProgressBarStyle = TimerProgressBarStyleType.DynamicColor.name
            )
        }
    }
}
