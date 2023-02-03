package com.kingfu.clok.settings.settingsView.settingsStopwatchView

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
import com.kingfu.clok.navigation.Screens
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch.SettingsViewModelStopwatchVariable.stopwatchRefreshRateValue
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch.SettingsViewModelStopwatchVariable.stopwatchShowLabel
import com.kingfu.clok.ui.theme.Green50
import com.kingfu.clok.util.NoRippleTheme

@Composable
fun SettingsStopwatchView(
    navController: NavHostController,
    vm: SettingsViewModelStopwatch,
    haptic: HapticFeedback
) {
    Text(
        text = "Stopwatch",
        modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 10.dp, bottom = 4.dp),
        fontSize = 16.sp,
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        color = Green50.copy(alpha = 0.5f)
    )

    Card(
        modifier = Modifier,
        shape = RoundedCornerShape(size = 30.dp),
    ) {
        Column {
            Row(
                modifier = Modifier
                    .background(color = Color.Black.copy(alpha = 0.4f))
                    .fillMaxWidth()
                    .clickable {
                        haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
                        vm.stopwatchSetShowLabelCheckState()
                        vm.saveStopwatchShowLabel()
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Show label",
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(all = 8.dp)
                )
                CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                    Switch(
                        checked = stopwatchShowLabel,
                        onCheckedChange = {
                            haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
                            vm.stopwatchSetShowLabelCheckState()
                            vm.saveStopwatchShowLabel()
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            uncheckedThumbColor = Color.White,
                            checkedTrackColor = Green50,
                            uncheckedTrackColor = Color.Gray,
                        ),
                    )
                }
            }

            Divider(
                modifier = Modifier
                    .background(color = Color.Black.copy(alpha = 0.4f))
                    .padding(horizontal = 24.dp),
                color = Color.DarkGray,
            )

            Column {
                Row(
                    modifier = Modifier
                        .background(color = Color.Black.copy(alpha = 0.4f))
                        .fillMaxWidth()
                        .clickable {
                            haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
//                            vm.stopwatchSetShowLabelCheckState()
//                            vm.saveStopwatchShowLabel()
                            navController.navigate(route = Screens.SettingsStopwatchFontStyles.route)
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Font styles",
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(all = 8.dp)
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
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        navController.navigate("settingsStopwatchLabelStyles")
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp),

                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Label styles",
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
                    .clickable {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        navController.navigate("settingsStopwatchBackgroundEffects")
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp),

                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.padding(all = 8.dp),) {
                    Text(
                        text = "Label background effects",
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                    )

                    Text(
                        text = "Enable when show label is turned on and stopwatch is running.",
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

            Column(
                modifier = Modifier
                    .background(color = Color.Black.copy(alpha = 0.4f))
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 8.dp),
                ) {
                    Text(
                        text = "Stopwatch refresh rate",
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                    )

                    Text(
                        text = buildAnnotatedString {
                            append("Stopwatch is delayed by ")
                            withStyle(
                                style = SpanStyle(color = Green50)
                            ) {
                                append(stopwatchRefreshRateValue.toInt().toString())
                            }
                            append(" millisecond(s) before refreshing.")
                        },
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray
                    )

                    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                        Slider(
                            modifier = Modifier,
                            value = stopwatchRefreshRateValue,
                            valueRange = 1f..100f,
                            onValueChange = {
                                vm.setStopwatchRefreshRateValue(float = it)
                                vm.saveStopwatchRefreshRateValue()
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