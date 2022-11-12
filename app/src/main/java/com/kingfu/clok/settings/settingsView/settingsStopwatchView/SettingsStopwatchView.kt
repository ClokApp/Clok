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
import com.kingfu.clok.ui.theme.Cyan50
import com.kingfu.clok.ui.theme.Green50
import com.kingfu.clok.util.NoRippleTheme
import com.kingfu.clok.util.customFontSize
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch

@Composable
fun SettingsStopwatchView(
    navController: NavHostController,
    vm: SettingsViewModelStopwatch,
    haptic: HapticFeedback
) {
    Text(
        text = "Stopwatch",
        modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 10.dp, bottom = 2.dp),
        fontSize = customFontSize(textUnit = 16.sp),
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        color = Color.Gray
    )

    Card(
        modifier = Modifier,
        shape = RoundedCornerShape(30.dp),
    ) {
        Column{
            Row(
                modifier = Modifier
                    .background(Color.Black.copy(0.4f))
                    .fillMaxWidth()
                    .clickable {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        vm.stopwatchShowLabelCheckState()
                        vm.saveStopwatchShowLabel()
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                ) {
                    Text(
                        text = "Show label",
                        fontSize = customFontSize(textUnit = 18.sp),
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                    )
                }
                CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                    Switch(
                        checked = vm.stopwatchShowLabel,
                        onCheckedChange = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            vm.stopwatchShowLabelCheckState()
                            vm.saveStopwatchShowLabel()
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            uncheckedThumbColor = Color.White,
                            checkedTrackColor = Cyan50,
                            uncheckedTrackColor = Color.Gray,
                        ),
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
                        navController.navigate("settingsStopwatchLabelStyle")
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp),

                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                ) {
                    Text(
                        text = "Label styles",
                        fontSize = customFontSize(textUnit = 18.sp),
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                    )
                }
            }

//            Divider(
//                modifier = Modifier
//                    .background(Color.Black.copy(0.4f))
//                    .padding(horizontal = 24.dp),
//                color = Color.DarkGray,
//            )

//            Row(
//                modifier = Modifier
//                    .background(Color.Black.copy(0.4f))
//                    .fillMaxWidth()
//                    .clickable(enabled = navController.currentDestination?.route == "settings") {
//                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
//                        vm.saveStopwatchTimeCheckState()
//                        vm.saveStopwatchSaveTimeCheckState()
//                    }
//                    .padding(horizontal = 16.dp, vertical = 8.dp),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Column(
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(horizontal = 8.dp),
//                ) {
//                    Text(
//                        text = "Save stopwatch time",
//                        fontSize = customFontSize(textUnit = 18.sp),
//                        fontFamily = FontFamily.Default,
//                        fontWeight = FontWeight.Normal,
//                    )
//                    Text(
//                        text = "When pause button is pressed.",
//                        fontSize = customFontSize(textUnit = 14.sp),
//                        fontFamily = FontFamily.Default,
//                        fontWeight = FontWeight.Normal,
//                        color = Color.Gray
//                    )
//                }
//                CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
//                    Switch(
//                        checked = vm.stopwatchSaveTimeCheckState,
//                        onCheckedChange = {
//                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
//                            vm.saveStopwatchTimeCheckState()
//                            vm.saveStopwatchSaveTimeCheckState()
//                        },
//                        colors = SwitchDefaults.colors(
//                            checkedThumbColor = Color.White,
//                            uncheckedThumbColor = Color.White,
//                            checkedTrackColor = Cyan50,
//                            uncheckedTrackColor = Color.Gray,
//                        ),
//                    )
//                }
//            }

//            Divider(
//                modifier = Modifier
//                    .background(Color.Black.copy(0.4f))
//                    .padding(horizontal = 24.dp),
//                color = Color.DarkGray,
//            )

//            Row(
//                modifier = Modifier
//                    .background(Color.Black.copy(0.4f))
//                    .fillMaxWidth()
//                    .clickable {
//                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
//                        vm.stopwatchSaveLapTimeCheckState()
//                        vm.saveStopwatchSaveLapTimeCheckState()
//                    }
//                    .padding(horizontal = 16.dp, vertical = 8.dp),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Column(
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(horizontal = 8.dp),
//
//                    ) {
//                    Text(
//                        text = "Save lap times",
//                        fontSize = customFontSize(textUnit = 18.sp),
//                        fontFamily = FontFamily.Default,
//                        fontWeight = FontWeight.Normal,
//                    )
//                    Text(
//                        text = "When pause button is pressed.",
//                        fontSize = customFontSize(textUnit = 14.sp),
//                        fontFamily = FontFamily.Default,
//                        fontWeight = FontWeight.Normal,
//                        color = Color.Gray
//                    )
//                }
//                CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
//                    Switch(
//                        checked = vm.stopwatchSaveLapTimeCheckState,
//                        onCheckedChange = {
//                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
//                            vm.stopwatchSaveLapTimeCheckState()
//                            vm.saveStopwatchSaveLapTimeCheckState()
//                        },
//                        colors = SwitchDefaults.colors(
//                            checkedThumbColor = Color.White,
//                            uncheckedThumbColor = Color.White,
//                            checkedTrackColor = Cyan50,
//                            uncheckedTrackColor = Color.Gray,
//                        ),
//                    )
//                }
//            }

            Divider(
                modifier = Modifier
                    .background(Color.Black.copy(0.4f))
                    .padding(horizontal = 24.dp),
                color = Color.DarkGray,
            )

            Column(
                modifier = Modifier
                    .background(Color.Black.copy(0.4f))
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 8.dp),

                    ) {
                    Text(
                        text = "Stopwatch refresh rate",
                        fontSize = customFontSize(textUnit = 18.sp),
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                    )

                    Text(
                        text = buildAnnotatedString {
                            append("Stopwatch is delayed by ")
                            withStyle(
                                style = SpanStyle(
                                    color = Green50
                                )
                            ) {
                                append(vm.stopwatchRefreshRateValue.toInt().toString())
                            }
                            append(" millisecond(s) before refreshing.")
                        },
                        fontSize = customFontSize(textUnit = 14.sp),
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray
                    )

                    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                        Slider(
                            modifier = Modifier,
                            value = vm.stopwatchRefreshRateValue,
                            valueRange = 1f..100f,
                            onValueChange = {
                                vm.stopwatchRefreshRateValue(it)
                                vm.saveStopwatchRefreshRateValue()
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