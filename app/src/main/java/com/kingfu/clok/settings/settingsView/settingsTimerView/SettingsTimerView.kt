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
import com.kingfu.clok.util.customFontSize

@Composable
fun SettingsTimerView(
    navController: NavHostController,
    vm: SettingsViewModelTimer,
    haptic: HapticFeedback
) {

    Text(
        text = "Timer",
        modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 10.dp, bottom = 2.dp),
        fontSize = customFontSize(textUnit = 16.sp),
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        color = Color.Gray
    )

    Card(
        modifier = Modifier,
        shape = RoundedCornerShape(30.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .background(Color.Black.copy(0.4f))
                    .fillMaxWidth()
                    .clickable(enabled = navController.currentDestination?.route == "settings") {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        navController.navigate("settingsTimerProgressBarStyle")
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),

                    ) {
                    Text(
                        text = "Progress bar styles",
                        fontSize = customFontSize(textUnit = 18.sp),
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                    )
                }
            }

            Divider(
                modifier = Modifier
                    .background(Color.Black.copy(0.4f))
                    .padding(horizontal = 24.dp),
                color = Color.DarkGray,
            )

            Row(
                modifier = Modifier
                    .background(Color.Black.copy(0.4f))
                    .fillMaxWidth()
                    .clickable(enabled = navController.currentDestination?.route == "settings") {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        navController.navigate("settingsTimerBackgroundEffects")
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),

                    ) {
                    Text(
                        text = "Progress bar background effects",
                        fontSize = customFontSize(textUnit = 18.sp),
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                    )

                    Text(
                        text = "Enable when progress bar is visible.",
                        fontSize = customFontSize(textUnit = 14.sp),
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray
                    )
                }
            }

            Divider(
                modifier = Modifier
                    .background(Color.Black.copy(0.4f))
                    .padding(horizontal = 24.dp),
                color = Color.DarkGray,
            )

            Row(
                modifier = Modifier
                    .background(Color.Black.copy(0.4f))
                    .fillMaxWidth()
                    .clickable {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        vm.timerCountOvertime()
                        vm.saveTimerCountOvertime()
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),

                    ) {
                    Text(
                        text = "Count overtime",
                        fontSize = customFontSize(textUnit = 18.sp),
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                    )
                    Text(
                        text = "Continue counting after the timer is finished.",
                        fontSize = customFontSize(textUnit = 14.sp),
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray
                    )
                }
                CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                    Switch(
                        checked = timerCountOvertime,
                        onCheckedChange = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            vm.timerCountOvertime()
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
                    .background(Color.Black.copy(0.4f))
                    .padding(horizontal = 24.dp),
                color = Color.DarkGray,
            )

            Row(
                modifier = Modifier
                    .background(Color.Black.copy(0.4f))
                    .fillMaxWidth()
                    .clickable {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        vm.timerSetEnableScrollsHapticFeedback()
                        vm.saveTimerEnableScrollsHapticFeedback()
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),

                    ) {
                    Text(
                        text = "Enable scrolls Haptic feedback",
                        fontSize = customFontSize(textUnit = 18.sp),
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                    )
                }
                CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                    Switch(
                        checked = timerEnableScrollsHapticFeedback,
                        onCheckedChange = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
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
                    .background(Color.Black.copy(0.4f))
                    .padding(horizontal = 24.dp),
                color = Color.DarkGray,
            )

            Row(
                modifier = Modifier
                    .background(Color.Black.copy(0.4f))
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                ) {

                    Text(
                        text = "Notification",
                        fontSize = customFontSize(textUnit = 18.sp),
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                    )

                    Text(
                        text = buildAnnotatedString {
                            append("Number of notifications: ")
                            withStyle(
                                style = SpanStyle(
                                    color = Green50
                                )
                            ) {
                                append(vm.timerNotification.toInt().toString())
                            }
                            append(" will be sent when timer is finished.")
                        },
                        fontSize = customFontSize(textUnit = 14.sp),
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray
                    )

                    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                        Slider(
                            modifier = Modifier,
                            value = vm.timerNotification,
                            valueRange = 1f..100f,
                            onValueChange = {
                                vm.timerSetNotification(it)
                                vm.saveTimerNotification()
                            },
                            colors = SliderDefaults.colors(
                                thumbColor = Green50,
                                activeTrackColor = Green50.copy(0.5f),
                            )
                        )
                    }
                }
            }

        }
    }
}
