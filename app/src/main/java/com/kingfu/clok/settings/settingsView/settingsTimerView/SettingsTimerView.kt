package com.kingfu.clok.settings.settingsView.settingsTimerView

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kingfu.clok.navigation.Screens
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer
import com.kingfu.clok.util.NoRippleTheme
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTimerView(
    navController: NavHostController,
    vm: SettingsViewModelTimer,
    haptic: HapticFeedback,
    configuration: Configuration
) {

    val interactionSource = remember { MutableInteractionSource() }
    val orientation = configuration.orientation
    val screenHeight = configuration.screenHeightDp.dp

    Text(
        text = Screens.Timer.name,
        modifier = Modifier.padding(start = 26.dp, end = 32.dp, top = 12.dp, bottom = 6.dp),
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onTertiaryContainer
    )

    Card(
        shape = RoundedCornerShape(size = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.inverseOnSurface.copy(0.40f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = navController.currentDestination?.route == Screens.Settings.route) {
                    haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
                    navController.navigate(route = Screens.SettingsTimerProgressBarStyles.route)
                }
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = Screens.SettingsTimerProgressBarStyles.name,
                fontSize = 18.sp,
                color = Color.White
            )
        }

        Divider(
            modifier = Modifier.padding(horizontal = 24.dp),
            color = Color.DarkGray.copy(alpha = 0.75f),
            thickness = 0.8.dp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = navController.currentDestination?.route == Screens.Settings.route) {
                    haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
                    navController.navigate(route = Screens.SettingsTimerFontStyles.route)
                }
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = Screens.SettingsTimerFontStyles.name,
                fontSize = 18.sp,
                color = Color.White
            )
        }

        Divider(
            modifier = Modifier.padding(horizontal = 24.dp),
            color = Color.DarkGray.copy(alpha = 0.75f),
            thickness = 0.8.dp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = navController.currentDestination?.route == Screens.Settings.route) {
                    haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
                    navController.navigate(route = Screens.SettingsTimerBackgroundEffects.route)
                }
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    text = Screens.SettingsTimerBackgroundEffects.name,
                    fontSize = 18.sp,
                    color = Color.White
                )

                Text(
                    text = "Enable when progress bar is visible and timer is running.",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    lineHeight = 18.sp
                )
            }
        }

        Divider(
            modifier = Modifier.padding(horizontal = 24.dp),
            color = Color.DarkGray.copy(alpha = 0.75f),
            thickness = 0.8.dp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
                    vm.timerToggleCountOvertime()
                    vm.saveTimerCountOvertime()
                }
                .padding(horizontal = 24.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.fillMaxWidth(0.75f)) {
                Text(
                    text = "Count overtime",
                    fontSize = 18.sp,
                    color = Color.White
                )
                Text(
                    text = "Continue counting after the timer is finished.",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray,
                    lineHeight = 18.sp
                )
            }
            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                Switch(
                    modifier = Modifier.scale(scale = 0.85f),
                    checked = vm.timerCountOvertime,
                    onCheckedChange = {
                        haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
                        vm.timerToggleCountOvertime()
                        vm.saveTimerCountOvertime()
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        uncheckedThumbColor = Color.White,
                        checkedTrackColor = MaterialTheme.colorScheme.primary,
                        uncheckedTrackColor = Color.Gray,
                    )
                )
            }
        }

        Divider(
            modifier = Modifier.padding(horizontal = 24.dp),
            color = Color.DarkGray.copy(alpha = 0.75f),
            thickness = 0.8.dp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
                    vm.updateTimerEnableScrollsHapticFeedback()
                    vm.saveTimerEnableScrollsHapticFeedback()
                }
                .padding(horizontal = 24.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.fillMaxWidth(fraction = 0.75f)) {
                Text(
                    text = "Enable scrolls Haptic feedback",
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                Switch(
                    modifier = Modifier.scale(scale = 0.85f),
                    checked = vm.timerEnableScrollsHapticFeedback,
                    onCheckedChange = {
                        haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
                        vm.updateTimerEnableScrollsHapticFeedback()
                        vm.saveTimerEnableScrollsHapticFeedback()
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        uncheckedThumbColor = Color.White,
                        checkedTrackColor = MaterialTheme.colorScheme.primary,
                        uncheckedTrackColor = Color.Gray,
                    )
                )
            }
        }

        Divider(
            modifier = Modifier.padding(horizontal = 24.dp),
            color = Color.DarkGray,
            thickness = 0.8.dp
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Notification",
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White
            )
            Text(
                text = "Notifications will be sent when timer is finished.",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray,
                lineHeight = 18.sp
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .height(
                        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
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
                        activeTrackColor = MaterialTheme.colorScheme.primary,
                        inactiveTrackColor = MaterialTheme.colorScheme.primary.copy(0.5f),
                    ),
                    modifier = Modifier
                        .matchParentSize()
                        .padding(horizontal = 16.dp)
                        .clip(shape = RoundedCornerShape(size = 10.dp))
                        .scale(
                            scaleX = if (orientation == Configuration.ORIENTATION_PORTRAIT) 1.10f else 1.03f,
                            scaleY = 100f
                        ),
                    thumb = {
                        SliderDefaults.Thumb(
                            interactionSource = interactionSource,
                            thumbSize = DpSize.Zero
                        )
                    },
                )


                Row(
                    modifier = Modifier.matchParentSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Notifications,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.inversePrimary
                    )
                    Spacer(modifier = Modifier.width(width = 10.dp))

                    Text(
                        text = "${vm.timerNotification.roundToInt()} notification(s)",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.inversePrimary,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

            }
        }
    }
}

