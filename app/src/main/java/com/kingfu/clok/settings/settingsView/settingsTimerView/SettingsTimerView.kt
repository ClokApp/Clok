package com.kingfu.clok.settings.settingsView.settingsTimerView

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer.SettingsViewModelTimerVariables.timerCountOvertime
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer.SettingsViewModelTimerVariables.timerEnableScrollsHapticFeedback
import com.kingfu.clok.ui.theme.Green50
import com.kingfu.clok.util.NoRippleTheme

@Composable
fun SettingsTimerView(
    navController: NavHostController,
    vm: SettingsViewModelTimer,
    haptic: HapticFeedback
) {

    Text(
        text = "Timer",
        modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 10.dp, bottom = 4.dp),
        fontSize = 16.sp,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        color = Green50.copy(alpha = 0.5f),
    )

    Card(
        modifier = Modifier,
        shape = RoundedCornerShape(size = 30.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .background(color = Color.Black.copy(alpha = 0.4f))
                    .fillMaxWidth()
                    .clickable(enabled = navController.currentDestination?.route == "settings") {
                        haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
                        navController.navigate(route = "settingsTimerProgressBarStyles")
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Progress bar styles",
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(all = 8.dp)
                )
            }

            Divider(
                modifier = Modifier
                    .background(color = Color.Black.copy(alpha = 0.4f))
                    .padding(horizontal = 24.dp),
                color = Color.DarkGray,
            )

            Row(
                modifier = Modifier
                    .background(color = Color.Black.copy(alpha = 0.4f))
                    .fillMaxWidth()
                    .clickable(enabled = navController.currentDestination?.route == "settings") {
                        haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
                        navController.navigate(route = "settingsTimerFontStyles")
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Font styles",
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(all = 8.dp)
                )
            }

            Divider(
                modifier = Modifier
                    .background(color = Color.Black.copy(alpha = 0.4f))
                    .padding(horizontal = 24.dp),
                color = Color.DarkGray,
            )

            Row(
                modifier = Modifier
                    .background(color = Color.Black.copy(alpha = 0.4f))
                    .fillMaxWidth()
                    .clickable(enabled = navController.currentDestination?.route == "settings") {
                        haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
                        navController.navigate(route = "settingsTimerBackgroundEffects")
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier.padding(all = 8.dp)) {
                    Text(
                        text = "Progress bar background effects",
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                    )

                    Text(
                        text = "Enable when progress bar is visible and timer is running.",
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray
                    )
                }
            }

            Divider(
                modifier = Modifier
                    .background(color = Color.Black.copy(alpha = 0.4f))
                    .padding(horizontal = 24.dp),
                color = Color.DarkGray,
            )

            Row(
                modifier = Modifier
                    .background(color = Color.Black.copy(alpha = 0.4f))
                    .fillMaxWidth()
                    .clickable {
                        haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
                        vm.timerToggleCountOvertime()
                        vm.saveTimerCountOvertime()
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                    Text(
                        text = "Count overtime",
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                    )
                    Text(
                        text = "Continue counting after the timer is finished.",
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray
                    )
                }
                CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                    Switch(
                        checked = timerCountOvertime,
                        onCheckedChange = {
                            haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
                            vm.timerToggleCountOvertime()
                            vm.saveTimerCountOvertime()
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            uncheckedThumbColor = Color.White,
                            checkedTrackColor = Green50,
                            uncheckedTrackColor = Color.Gray,
                        )
                    )
                }
            }

            Divider(
                modifier = Modifier
                    .background(color = Color.Black.copy(alpha = 0.4f))
                    .padding(horizontal = 24.dp),
                color = Color.DarkGray,
            )

            Row(
                modifier = Modifier
                    .background(color = Color.Black.copy(alpha = 0.4f))
                    .fillMaxWidth()
                    .clickable {
                        haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
                        vm.timerSetEnableScrollsHapticFeedback()
                        vm.saveTimerEnableScrollsHapticFeedback()
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                    Text(
                        text = "Enable scrolls Haptic feedback",
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                    )
                }
                CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                    Switch(
                        checked = timerEnableScrollsHapticFeedback,
                        onCheckedChange = {
                            haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
                            vm.timerSetEnableScrollsHapticFeedback()
                            vm.saveTimerEnableScrollsHapticFeedback()
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            uncheckedThumbColor = Color.White,
                            checkedTrackColor = Green50,
                            uncheckedTrackColor = Color.Gray,
                        )
                    )
                }
            }

            Divider(
                modifier = Modifier
                    .background(color = Color.Black.copy(alpha = 0.4f))
                    .padding(horizontal = 24.dp),
                color = Color.DarkGray,
            )

            Row(
                modifier = Modifier
                    .background(color = Color.Black.copy(alpha = 0.4f))
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                    Text(
                        text = "Notification",
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                    )

                    Text(
                        text = buildAnnotatedString {
                            append("Number of notifications: ")
                            withStyle(
                                style = SpanStyle(color = Green50)
                            ) {
                                append(text = vm.timerNotification.toInt().toString())
                            }
                            append(" will be sent when timer is finished.")
                        },
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray,
                    )

                    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                        Slider(
                            modifier = Modifier,
                            value = vm.timerNotification,
                            valueRange = 1f..100f,
                            onValueChange = {
                                vm.timerSetNotification(float = it)
                                vm.saveTimerNotification()
                            },
                            colors = SliderDefaults.colors(
                                thumbColor = Green50,
                                activeTrackColor = Green50.copy(alpha = 0.5f),
                            )
                        )
                    }
                }
            }

        }
    }
}

