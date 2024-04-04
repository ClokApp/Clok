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
import com.kingfu.clok.ui.theme.ThemeType
import com.kingfu.clok.timer.util.timerProgressBarBackgroundEffects.TimerProgressBarBackgroundEffectType
import com.kingfu.clok.ui.components.MyHorizontalDivider
import com.kingfu.clok.ui.components.MyRadioButton
import com.kingfu.clok.ui.layouts.OneUI
import com.kingfu.clok.ui.theme.ClokTheme
import com.kingfu.clok.ui.theme.RoundedCornerFooter
import com.kingfu.clok.ui.theme.RoundedCornerHeader
import com.kingfu.clok.ui.theme.TextBodyLarge
import com.kingfu.clok.ui.theme.TextBodyMediumHeading
import com.kingfu.clok.ui.util.screenHeight

@Composable
fun SettingsTimerProgressBarBackgroundEffectsScreen(
    updateTimerBackgroundEffects: (String) -> Unit,
    saveTimerBackgroundEffects: () -> Unit,
    timerBackgroundEffects: String
) {
    val none = TimerProgressBarBackgroundEffectType.None.name
    val snow = TimerProgressBarBackgroundEffectType.Snow.name

    OneUI(
        header = { TextBodyMediumHeading(text = stringResource(id = R.string.timer_name_id)) },
        middleColumn = {
            TextBodyLarge(
                text = stringResource(id = R.string.settings_stopwatch_screen_none)
            )
        },
        leftColumn = { MyRadioButton(selected = none == timerBackgroundEffects) },
        roundedCornerShape = RoundedCornerHeader,
        onClick = {
            updateTimerBackgroundEffects(none)
            saveTimerBackgroundEffects()
        }
    )

    MyHorizontalDivider()

    OneUI(
        middleColumn = {
            TextBodyLarge(
                text = stringResource(id = R.string.settings_stopwatch_screen_snow)
            )
        },
        leftColumn = { MyRadioButton(selected = snow == timerBackgroundEffects) },
        roundedCornerShape = RoundedCornerFooter,
        onClick = {
            updateTimerBackgroundEffects(snow)
            saveTimerBackgroundEffects()
        }
    )

    Spacer(modifier = Modifier.height(height = screenHeight() * 0.90f))

}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsTimerProgressBarBackgroundEffectsScreen() {
    ClokTheme(
        dynamicColor = true,
        theme = ThemeType.Dark
    ) {
        Column(modifier = Modifier.verticalScroll(state = rememberScrollState())) {
            SettingsTimerProgressBarBackgroundEffectsScreen(
                updateTimerBackgroundEffects = {},
                saveTimerBackgroundEffects = {},
                timerBackgroundEffects = TimerProgressBarBackgroundEffectType.None.name
            )
        }
    }
}

