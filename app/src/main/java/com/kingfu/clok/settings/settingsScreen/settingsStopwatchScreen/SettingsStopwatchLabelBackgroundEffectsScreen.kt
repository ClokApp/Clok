package com.kingfu.clok.settings.settingsScreen.settingsStopwatchScreen

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
import com.kingfu.clok.stopwatch.util.labelBackgroundEffects.StopwatchLabelBackgroundEffectType
import com.kingfu.clok.ui.components.MyHorizontalDivider
import com.kingfu.clok.ui.components.MyRadioButton
import com.kingfu.clok.ui.layouts.OneUI
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.RoundedCornerFooter
import com.kingfu.clok.ui.theme.RoundedCornerHeader
import com.kingfu.clok.ui.theme.TextBodyLarge
import com.kingfu.clok.ui.theme.TextBodyMediumHeading
import com.kingfu.clok.ui.theme.ThemeType
import com.kingfu.clok.ui.util.screenHeight

@Composable
fun SettingsStopwatchLabelBackgroundEffectsScreen(
    updateStopwatchBackgroundEffect: (String) -> Unit,
    saveStopwatchBackgroundEffects: () -> Unit,
    stopwatchBackgroundEffects: String
) {

    val none = StopwatchLabelBackgroundEffectType.None.name
    val snow = StopwatchLabelBackgroundEffectType.Snow.name

    OneUI(
        header = { TextBodyMediumHeading(text = stringResource(id = R.string.stopwatch_name_id)) },
        leftColumn = { MyRadioButton(selected = none == stopwatchBackgroundEffects) },
        middleColumn = { TextBodyLarge(text = stringResource(id = R.string.settings_stopwatch_screen_none)) },
        onClick = {
            updateStopwatchBackgroundEffect(none)
            saveStopwatchBackgroundEffects()
        },
        roundedCornerShape = RoundedCornerHeader
    )

    MyHorizontalDivider()

    OneUI(
        leftColumn = { MyRadioButton(selected = snow == stopwatchBackgroundEffects) },
        middleColumn = { TextBodyLarge(text = stringResource(id = R.string.settings_stopwatch_screen_snow)) },
        onClick = {
            updateStopwatchBackgroundEffect(snow)
            saveStopwatchBackgroundEffects()
        },
        roundedCornerShape = RoundedCornerFooter
    )

    Spacer(modifier = Modifier.height(height = screenHeight() * 0.90f))
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsStopwatchLabelBackgroundEffectsScreen() {
    ClokTheme(
        dynamicColor = true,
        theme = ThemeType.Dark
    ) {
        Column(modifier = Modifier.verticalScroll(state = rememberScrollState())) {
            SettingsStopwatchLabelBackgroundEffectsScreen(
                updateStopwatchBackgroundEffect = {},
                saveStopwatchBackgroundEffects = {},
                stopwatchBackgroundEffects = StopwatchLabelBackgroundEffectType.None.name
            )
        }
    }
}
