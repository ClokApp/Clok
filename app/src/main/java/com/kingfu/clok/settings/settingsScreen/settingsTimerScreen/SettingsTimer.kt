package com.kingfu.clok.settings.settingsScreen.settingsTimerScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import kotlin.math.ceil


@Composable
fun SettingsTimer(
    goToSettingsTimerProgressBarStyles: () -> Unit,
    goToSettingsTimerFontStyles: () -> Unit,
    goToSettingsTimerBackgroundEffects: () -> Unit,
    goToSettingsTimerScrollsHapticFeedback: () -> Unit,
    timerIsEdit: () -> Boolean,
    timerToggleCountOvertime: () -> Unit,
    saveTimerCountOvertime: () -> Unit,
    timerIsCountOvertime: Boolean,
    timerNotification: Float,
    updateTimerNotification: (notification: Float) -> Unit,
    saveTimerNotification: () -> Unit
) {

    OneUI(
        header = { TextBodyMediumHeading(text = stringResource(id = R.string.timer_name_id)) },
        middleColumn = { TextBodyLarge(text = stringResource(id = R.string.settings_timer_progress_bar_styles_name_id)) },
        roundedCornerShape = RoundedCornerHeader,
        onClick = { goToSettingsTimerProgressBarStyles() }
    )

    MyHorizontalDivider()

    OneUI(
        middleColumn = { TextBodyLarge(text = stringResource(id = R.string.settings_timer_font_styles_name_id)) },
        onClick = { goToSettingsTimerFontStyles() }
    )

    MyHorizontalDivider()

    OneUI(
        middleColumn = {
            TextBodyLarge(text = stringResource(id = R.string.settings_timer_progress_bar_background_effects_name_id))
            TextBodyMedium(text = stringResource(id = R.string.settings_timer_progress_bar_background_effect_description))
        },
        onClick = { goToSettingsTimerBackgroundEffects() }
    )

    MyHorizontalDivider()

    OneUI(
        middleColumn = {
            if (!timerIsEdit()) {
                TextBodyMedium(
                    text = stringResource(id = R.string.settings_timer_count_overtime_error_description),
                    color = colorScheme.error
                )
            }
            TextBodyLarge(
                text = stringResource(id = R.string.settings_timer_count_overtime),
                modifier = Modifier.alpha(alpha = if (timerIsEdit()) 1f else 0.5f)
            )
            TextBodyMedium(
                text = stringResource(id = R.string.settings_timer_count_overtime_description),
                modifier = Modifier.alpha(alpha = if (timerIsEdit()) 1f else 0.5f)
            )
        },
        rightColumn = {
            MySwitch(
                isChecked = timerIsCountOvertime,
                onCheckedChange = {
                    timerToggleCountOvertime()
                    saveTimerCountOvertime()
                },
                isEnabled = timerIsEdit()
            )
        },
        isClickable = timerIsEdit(),
        onClick = {
            timerToggleCountOvertime()
            saveTimerCountOvertime()
        }
    )

    MyHorizontalDivider()

    OneUI(
        middleColumn = { TextBodyLarge(text = stringResource(id = R.string.settings_timer_scrolls_haptic_feedback)) },
        onClick = { goToSettingsTimerScrollsHapticFeedback() }
    )

    MyHorizontalDivider()

    OneUI(
        middleColumn = {
            TextBodyLarge(text = stringResource(id = R.string.settings_timer_notification))
            TextBodyMedium(text = stringResource(id = R.string.settings_timer_notification_description))
            MySlider(
                value = timerNotification,
                onValueChange = {
                    updateTimerNotification(it)
                    saveTimerNotification()
                },
                content = {
                    MyIcon(
                        imageVector = Icons.Rounded.Notifications,
                        tint = colorScheme.inversePrimary
                    )
                    TextTitleMedium(
                        text = "${ceil(timerNotification).toInt()} ${
                            stringResource(id = R.string.settings_timer_progress_bar_title)
                        }"
                    )
                }
            )
        },
        roundedCornerShape = RoundedCornerFooter
    )

}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsTimer() {
    ClokTheme(
        dynamicColor = true,
        theme = ThemeType.Dark
    ) {
        Column(modifier = Modifier.verticalScroll(state = rememberScrollState())) {
            SettingsTimer(
                goToSettingsTimerProgressBarStyles = {},
                goToSettingsTimerFontStyles = {},
                goToSettingsTimerBackgroundEffects = {},
                goToSettingsTimerScrollsHapticFeedback = {},
                timerIsEdit = { false },
                timerToggleCountOvertime = {},
                saveTimerCountOvertime = {},
                timerIsCountOvertime = true,
                timerNotification = 5f,
                updateTimerNotification = {},
                saveTimerNotification = {}
            )
        }
    }
}



