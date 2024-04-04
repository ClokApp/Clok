package com.kingfu.clok.settings.settingsScreen.settingsStopwatchScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ShutterSpeed
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kingfu.clok.R
import com.kingfu.clok.ui.components.MyHorizontalDivider
import com.kingfu.clok.ui.components.MyIcon
import com.kingfu.clok.ui.components.MySlider
import com.kingfu.clok.ui.components.MySwitch
import com.kingfu.clok.ui.layouts.OneUI
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.RoundedCornerFooter
import com.kingfu.clok.ui.theme.RoundedCornerHeader
import com.kingfu.clok.ui.theme.TextBodyLarge
import com.kingfu.clok.ui.theme.TextBodyMedium
import com.kingfu.clok.ui.theme.TextBodyMediumHeading
import com.kingfu.clok.ui.theme.TextTitleMedium
import com.kingfu.clok.ui.theme.ThemeType
import kotlin.math.roundToInt

@Composable
fun SettingsStopwatch(
    goToSettingsStopwatchBackgroundEffects: () -> Unit,
    goToSettingsStopwatchFontStyles: () -> Unit,
    goToSettingsStopwatchLabelStyles: () -> Unit,
    stopwatchSetShowLabelCheckState: () -> Unit,
    saveStopwatchShowLabel: () -> Unit,
    updateStopwatchRefreshRateValue: (Float) -> Unit,
    saveStopwatchRefreshRateValue: () -> Unit,
    stopwatchShowLabel: Boolean,
    stopwatchRefreshRateValue: Float,
) {

    OneUI(
        header = { TextBodyMediumHeading(text = stringResource(id = R.string.stopwatch_name_id)) },
        middleColumn = { TextBodyLarge(text = stringResource(id = R.string.settings_stopwatch_screen_show_label)) },
        rightColumn = {
            MySwitch(
                isChecked = stopwatchShowLabel,
                onCheckedChange = {
                    stopwatchSetShowLabelCheckState()
                    saveStopwatchShowLabel()
                }
            )
        },
        onClick = {
            stopwatchSetShowLabelCheckState()
            saveStopwatchShowLabel()
        },
        roundedCornerShape = RoundedCornerHeader
    )

    MyHorizontalDivider()

    OneUI(
        middleColumn = {
            TextBodyLarge(text = stringResource(id = R.string.settings_stopwatch_font_styles_name_id))
        },
        onClick = { goToSettingsStopwatchFontStyles() }
    )

    MyHorizontalDivider()

    OneUI(
        middleColumn = { TextBodyLarge(text = stringResource(id = R.string.settings_stopwatch_label_styles_name_id)) },
        onClick = { goToSettingsStopwatchLabelStyles() }
    )

    MyHorizontalDivider()

    OneUI(
        middleColumn = {
            TextBodyLarge(text = stringResource(id = R.string.label_background_effects_name_id))
            TextBodyMedium(text = stringResource(id = R.string.settings_stopwatch_label_background_effects_description))
        },
        onClick = { goToSettingsStopwatchBackgroundEffects() }
    )

    MyHorizontalDivider()

    OneUI(
        middleColumn = {
            TextBodyLarge(text = stringResource(id = R.string.settings_stopwatch_refresh_rate))
            TextBodyMedium(text = stringResource(id = R.string.settings_stopwatch_refresh_rate_description))
            MySlider(
                value = stopwatchRefreshRateValue,
                onValueChange = {
                    updateStopwatchRefreshRateValue(it)
                    saveStopwatchRefreshRateValue()
                },
                content = {
                    MyIcon(
                        imageVector = Icons.Rounded.ShutterSpeed,
                        tint = colorScheme.inversePrimary
                    )
                    TextTitleMedium(
                        text = "${stopwatchRefreshRateValue.roundToInt()} ${stringResource(id = R.string.settings_stopwatch_screen_ms_delay)}"
                    )
                }
            )
        },
        roundedCornerShape = RoundedCornerFooter,
    )

}

@Preview(showBackground = true)
@Composable
fun CustomCardPreviewSettingsStopwatch() {
    ClokTheme(
        dynamicColor = true,
        theme = ThemeType.Dark
    ) {
        Column(modifier = Modifier.verticalScroll(state = rememberScrollState())) {
            SettingsStopwatch(
                goToSettingsStopwatchBackgroundEffects = {},
                goToSettingsStopwatchFontStyles = {},
                goToSettingsStopwatchLabelStyles = {},
                stopwatchSetShowLabelCheckState = {},
                saveStopwatchShowLabel = {},
                updateStopwatchRefreshRateValue = {},
                saveStopwatchRefreshRateValue = {},
                stopwatchShowLabel = true,
                stopwatchRefreshRateValue = 80f,
            )
        }
    }
}