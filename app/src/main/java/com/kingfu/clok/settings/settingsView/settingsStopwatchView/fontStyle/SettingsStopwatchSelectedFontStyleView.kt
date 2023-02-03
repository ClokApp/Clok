package com.kingfu.clok.settings.settingsView.settingsStopwatchView.fontStyle

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch.SettingsViewModelStopwatchVariable.stopwatchLabelFontStyleSelectedOption
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch.SettingsViewModelStopwatchVariable.stopwatchLapTimeFontStyleSelectedOption
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch.SettingsViewModelStopwatchVariable.stopwatchTimeFontStyleSelectedOption
import com.kingfu.clok.ui.theme.Black00
import com.kingfu.clok.ui.theme.Green50
import com.kingfu.clok.variable.Variable.settingsStopwatchSelectedFontStyleTopBarName


@OptIn(ExperimentalTextApi::class)
@Composable
fun SettingsStopwatchSelectedFontStyleView(
    vm: SettingsViewModelStopwatch
) {
    val haptic = LocalHapticFeedback.current
    val radioOptions = setOf("Default", "Inner stroke")
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Black00)
            .verticalScroll(state = scrollState)
    ) {

        Text(
            text = settingsStopwatchSelectedFontStyleTopBarName,
            modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 10.dp, bottom = 4.dp),
            fontSize = 16.sp,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.SemiBold,
            color = Green50.copy(0.5f),
        )

        Card(
            shape = RoundedCornerShape(size = 30.dp)
        ) {
            Column {
                for (i in radioOptions.indices) {
                    Row(
                        modifier = Modifier
                            .background(color = Color.Black.copy(alpha = 0.4f))
                            .fillMaxWidth()
                            .clickable {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
//                                if (settingsStopwatchSelectedFontStyleTopBarName == "Label font style") {
//                                    vm.setStopwatchLabelFontStyleSelectedOption(
//                                        radioOptions.elementAt(index = i)
//                                    )
//                                    vm.saveStopwatchLabelFontStyleSelectedOption()
//                                } else if (settingsStopwatchSelectedFontStyleTopBarName == "Stopwatch time font style") {
//                                    vm.setStopwatchTimeFontStyleSelectedOption(
//                                        radioOptions.elementAt(index = i)
//                                    )
//                                    vm.saveStopwatchTimeFontStyleSelectedOption()
//                                } else {
//                                    vm.setStopwatchLapTimeFontStyleSelectedOption(
//                                        radioOptions.elementAt(index = i)
//                                    )
//                                    vm.saveStopwatchLapTimeFontStyleSelectedOption()
//                                }

                                when (settingsStopwatchSelectedFontStyleTopBarName) {
                                    "Label font style" -> {
                                        vm.setStopwatchLabelFontStyleSelectedOption(
                                            radioOptions.elementAt(index = i)
                                        )
                                        vm.saveStopwatchLabelFontStyleSelectedOption()
                                    }
                                    "Stopwatch time font style" -> {
                                        vm.setStopwatchTimeFontStyleSelectedOption(
                                            radioOptions.elementAt(index = i)
                                        )
                                        vm.saveStopwatchTimeFontStyleSelectedOption()
                                    }
                                    "Lap time font style" -> {
                                        vm.setStopwatchLapTimeFontStyleSelectedOption(
                                            radioOptions.elementAt(index = i)
                                        )
                                        vm.saveStopwatchLapTimeFontStyleSelectedOption()
                                    }
                                }


                            }
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
//                            selected = if (Variable.settingsTimerSelectedFontStyleTopBarName == "Scrolls font style") {
//                                radioOptions.elementAt(i) == SettingsViewModelTimer.SettingsViewModelTimerVariables.timerScrollsFontStyleSelectedOption
//                            } else {
//                                radioOptions.elementAt(i) == SettingsViewModelTimer.SettingsViewModelTimerVariables.timerTimeFontStyleSelectedOption
//                            }
                            selected = when (settingsStopwatchSelectedFontStyleTopBarName) {
                                "Label font style" -> {
                                    radioOptions.elementAt(index = i) == stopwatchLabelFontStyleSelectedOption
                                }
                                "Stopwatch time font style" -> {
                                    radioOptions.elementAt(index = i) == stopwatchTimeFontStyleSelectedOption
                                }
                                else -> {
                                    radioOptions.elementAt(index = i) == stopwatchLapTimeFontStyleSelectedOption

                                }
                            },
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
//                                if (Variable.settingsTimerSelectedFontStyleTopBarName == "Scrolls font style") {
//                                    vm.setTimerScrollsFontStyleSelectedOption(
//                                        radioOptions.elementAt(i)
//                                    )
//                                    vm.saveTimerScrollsFontStyleSelectedOption()
//                                } else {
//                                    vm.setTimerTimeFontStyleSelectedOption(radioOptions.elementAt(i))
//                                    vm.saveTimerTimeFontStyleSelectedOption()
//                                }
                                when (settingsStopwatchSelectedFontStyleTopBarName) {
                                    "Label font style" -> {
                                        vm.setStopwatchLabelFontStyleSelectedOption(
                                            radioOptions.elementAt(index = i)
                                        )
                                        vm.saveStopwatchLabelFontStyleSelectedOption()
                                    }
                                    "Stopwatch time font style" -> {
                                        vm.setStopwatchTimeFontStyleSelectedOption(
                                            radioOptions.elementAt(index = i)
                                        )
                                        vm.saveStopwatchTimeFontStyleSelectedOption()
                                    }
                                    "Lap time font style" -> {
                                        vm.setStopwatchLapTimeFontStyleSelectedOption(
                                            radioOptions.elementAt(index = i)
                                        )
                                        vm.saveStopwatchLapTimeFontStyleSelectedOption()
                                    }
                                }
                            },
                            colors = RadioButtonDefaults.colors(selectedColor = Green50)

                        )
                        Text(
                            text = radioOptions.elementAt(i),
                            fontSize = 50.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Normal,
                            style = TextStyle(
                                drawStyle = if (radioOptions.elementAt(i) == "Inner stroke") Stroke() else null
                            )
                        )

                    }
                    if (i != radioOptions.size - 1) {
                        Divider(
                            modifier = Modifier
                                .background(color = Color.Black.copy(alpha = 0.4f))
                                .padding(horizontal = 24.dp),
                            color = Color.DarkGray,
                        )
                    }
                }
            }

        }
    }
}