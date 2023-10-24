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
import com.kingfu.clok.timer.feature.timerScrollsHapticFeedback.TimerScrollsHapticFeedbackType
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
fun SettingsTimerScrollsHapticFeedbackScreen(
    updateScrollHapticFeedback: (String) -> Unit,
    saveScrollsHapticFeedback: () -> Unit,
    timerScrollsHapticFeedback: String
) {
    val strong = TimerScrollsHapticFeedbackType.Strong.name
    val weak = TimerScrollsHapticFeedbackType.Weak.name
    val off = TimerScrollsHapticFeedbackType.Off.name

    OneUI(
        header = { TextBodyMediumHeading(text = stringResource(id = R.string.timer_name_id)) },
        middleColumn = {
            TextBodyLarge(
                text = stringResource(id = R.string.settings_timer_screen_strong_haptic)
            )
        },
        leftColumn = { MyRadioButton(selected = strong == timerScrollsHapticFeedback) },
        roundedCornerShape = RoundedCornerHeader,
        onClick = {
            updateScrollHapticFeedback(strong)
            saveScrollsHapticFeedback()
        }
    )

    MyHorizontalDivider()

    OneUI(
        middleColumn = { TextBodyLarge(text = stringResource(id = R.string.settings_timer_weak_haptic)) },
        leftColumn = { MyRadioButton(selected = weak == timerScrollsHapticFeedback) },
        onClick = {
            updateScrollHapticFeedback(weak)
            saveScrollsHapticFeedback()
        }
    )

    MyHorizontalDivider()

    OneUI(
        middleColumn = { TextBodyLarge(text = stringResource(id = R.string.settings_timer_off_haptic)) },
        leftColumn = { MyRadioButton(selected = off == timerScrollsHapticFeedback) },
        roundedCornerShape = RoundedCornerFooter,
        onClick = {
            updateScrollHapticFeedback(off)
            saveScrollsHapticFeedback()
        }
    )

    Spacer(modifier = Modifier.height(height = screenHeight() * 0.90f))

}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsTimerScrollsHapticFeedbackScreen() {
    ClokTheme(
        dynamicColor = true,
        theme = ThemeType.Dark
    ) {
        Column(modifier = Modifier.verticalScroll(state = rememberScrollState())) {
            SettingsTimerScrollsHapticFeedbackScreen(
                updateScrollHapticFeedback = {},
                saveScrollsHapticFeedback = {},
                timerScrollsHapticFeedback = TimerScrollsHapticFeedbackType.Strong.name
            )
        }
    }
}