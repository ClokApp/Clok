package com.kingfu.clok.settings.settingsScreen.settingsTimerScreen

import android.content.res.Configuration
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType.Companion.LongPress
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.DpSize.Companion.Zero
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kingfu.clok.navigation.Screens
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer
import com.kingfu.clok.util.NoRippleTheme
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTimerScreen(
    vm: SettingsViewModelTimer,
    haptic: HapticFeedback,
    configuration: Configuration,
    navigateToSettingsTimerProgressBarStyles: () -> Unit,
    navigateToSettingsTimerFontStyles: () -> Unit,
    navigateToSettingsTimerBackgroundEffects: () -> Unit,
    navigateToSettingsTimerScrollsHapticFeedback: () -> Unit,
) {

    val interactionSource = remember { MutableInteractionSource() }
    val orientation = configuration.orientation
    val screenHeight = configuration.screenHeightDp.dp

    Text(
        text = Screens.Timer.name,
        modifier = Modifier.padding(start = 26.dp, end = 32.dp, top = 12.dp, bottom = 6.dp),
        fontSize = 16.sp,
        fontWeight = SemiBold,
        color = colorScheme.onTertiaryContainer,
        style = TextStyle()
    )

    Card(
        shape = RoundedCornerShape(size = 20.dp),
        colors = cardColors(
            containerColor = colorScheme.inverseOnSurface.copy(alpha = 0.40f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    haptic.performHapticFeedback(hapticFeedbackType = LongPress)
                    navigateToSettingsTimerProgressBarStyles()
                }
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = SpaceBetween,
            verticalAlignment = CenterVertically,
        ) {
            Text(
                text = Screens.SettingsTimerProgressBarStyles.name,
                fontSize = 18.sp,
                color = White,
                style = TextStyle()
            )
        }

        Divider(
            modifier = Modifier.padding(horizontal = 24.dp),
            color = DarkGray.copy(alpha = 0.75f),
            thickness = 0.8.dp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    haptic.performHapticFeedback(hapticFeedbackType = LongPress)
                    navigateToSettingsTimerFontStyles()
                }
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = SpaceBetween,
            verticalAlignment = CenterVertically,
        ) {
            Text(
                text = Screens.SettingsTimerFontStyles.name,
                fontSize = 18.sp,
                color = White,
                style = TextStyle()
            )
        }

        Divider(
            modifier = Modifier.padding(horizontal = 24.dp),
            color = DarkGray.copy(alpha = 0.75f),
            thickness = 0.8.dp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    haptic.performHapticFeedback(hapticFeedbackType = LongPress)
                    navigateToSettingsTimerBackgroundEffects()
                }
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = SpaceBetween,
            verticalAlignment = CenterVertically,
        ) {
            Column {
                Text(
                    text = Screens.SettingsTimerProgressBarBackgroundEffects.name,
                    fontSize = 18.sp,
                    color = White,
                    style = TextStyle()
                )

                Text(
                    text = "Enable when progress bar is visible and timer is running.",
                    fontSize = 14.sp,
                    color = Gray,
                    lineHeight = 18.sp,
                    style = TextStyle()
                )
            }
        }

        Divider(
            modifier = Modifier.padding(horizontal = 24.dp),
            color = DarkGray.copy(alpha = 0.75f),
            thickness = 0.8.dp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = vm.timerIsEdit()) {
                    haptic.performHapticFeedback(hapticFeedbackType = LongPress)
                    vm.timerToggleCountOvertime()
                    vm.saveTimerCountOvertime()
                }
                .padding(horizontal = 24.dp, vertical = 8.dp),
            horizontalArrangement = SpaceBetween,
            verticalAlignment = CenterVertically
        ) {
            Column(modifier = Modifier.fillMaxWidth(fraction = 0.75f)) {
                if(!vm.timerIsEdit()) {
                    Text(
                        text = "Enabled when timer is in edit mode.",
                        fontSize = 14.sp,
                        color = colorScheme.error,
                        lineHeight = 18.sp,
                        style = TextStyle()
                    )
                }

                Text(
                    text = "Count overtime",
                    fontSize = 18.sp,
                    color = if (vm.timerIsEdit()) White else Gray,
                    style = TextStyle()
                )
                Text(
                    text = "Continue counting after the timer is finished.",
                    fontSize = 14.sp,
                    color = Gray,
                    lineHeight = 18.sp,
                    style = TextStyle()
                )


            }
            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                Switch(
                    enabled = vm.timerIsEdit(),
                    modifier = Modifier.scale(scale = 0.85f),
                    checked = vm.timerCountOvertime,
                    onCheckedChange = {
                        haptic.performHapticFeedback(hapticFeedbackType = LongPress)
                        vm.timerToggleCountOvertime()
                        vm.saveTimerCountOvertime()
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = White,
                        uncheckedThumbColor = White,
                        checkedTrackColor = colorScheme.primary,
                        uncheckedTrackColor = Gray,
                    )
                )
            }
        }

        Divider(
            modifier = Modifier.padding(horizontal = 24.dp),
            color = DarkGray.copy(alpha = 0.75f),
            thickness = 0.8.dp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    haptic.performHapticFeedback(hapticFeedbackType = LongPress)
                    navigateToSettingsTimerScrollsHapticFeedback()
                }
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = SpaceBetween,
            verticalAlignment = CenterVertically
        ) {
            Text(
                text = "Scrolls haptic feedback",
                fontSize = 18.sp,
                color = White,
                style = TextStyle()
            )
        }

        Divider(
            modifier = Modifier.padding(horizontal = 24.dp),
            color = DarkGray,
            thickness = 0.8.dp
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Start,
            verticalArrangement = Center
        ) {
            Text(
                text = "Notification",
                fontSize = 18.sp,
                color = White,
                style = TextStyle()
            )
            Text(
                text = "Notifications will be sent when timer is finished.",
                fontSize = 14.sp,
                color = Gray,
                lineHeight = 18.sp,
                style = TextStyle()
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .height(
                        if (orientation == ORIENTATION_PORTRAIT) {
                            screenHeight * 0.05f
                        } else {
                            screenHeight * 0.12f
                        }
                    )
            ) {
                Slider(
                    value = vm.timerNotification,
                    valueRange = 1f..100f,
                    onValueChange = {
                        vm.updateTimerNotification(float = it)
                        vm.saveTimerNotification()
                    },
                    colors = SliderDefaults.colors(
                        activeTrackColor = colorScheme.primary,
                        inactiveTrackColor = colorScheme.primary.copy(alpha = 0.5f),
                    ),
                    modifier = Modifier
                        .matchParentSize()
                        .padding(horizontal = 16.dp)
                        .clip(shape = RoundedCornerShape(size = 10.dp))
                        .scale(
                            scaleX = if (orientation == ORIENTATION_PORTRAIT) 1.10f else 1.03f,
                            scaleY = 100f
                        ),
                    thumb = {
                        SliderDefaults.Thumb(
                            interactionSource = interactionSource,
                            thumbSize = Zero
                        )
                    },
                )

                Row(
                    modifier = Modifier.matchParentSize(),
                    horizontalArrangement = Center,
                    verticalAlignment = CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Notifications,
                        contentDescription = null,
                        tint = colorScheme.inversePrimary
                    )
                    Spacer(modifier = Modifier.width(width = 10.dp))

                    Text(
                        text = "${vm.timerNotification.roundToInt()} notification(s)",
                        fontWeight = Bold,
                        color = colorScheme.inversePrimary,
                        overflow = Ellipsis,
                        fontSize = 18.sp,
                        style = TextStyle()
                    )
                }
            }
        }
    }
}

