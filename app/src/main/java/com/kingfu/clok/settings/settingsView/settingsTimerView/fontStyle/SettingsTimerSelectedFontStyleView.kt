package com.kingfu.clok.settings.settingsView.settingsTimerView.fontStyle

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
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer.SettingsViewModelTimerVariables.timerScrollsFontStyleSelectedOption
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer.SettingsViewModelTimerVariables.timerTimeFontStyleSelectedOption
import com.kingfu.clok.ui.theme.Black00
import com.kingfu.clok.ui.theme.Green50
import com.kingfu.clok.variable.Variable.settingsTimerSelectedFontStyleTopBarName

@OptIn(ExperimentalTextApi::class)
@Composable
fun SettingsTimerSelectedFontStyleView(
    vm: SettingsViewModelTimer,
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
            text = settingsTimerSelectedFontStyleTopBarName,
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
                                if (settingsTimerSelectedFontStyleTopBarName == "Scrolls font style") {
                                    vm.setTimerScrollsFontStyleSelectedOption(
                                        radioOptions.elementAt(index = i)
                                    )
                                    vm.saveTimerScrollsFontStyleSelectedOption()
                                } else {
                                    vm.setTimerTimeFontStyleSelectedOption(
                                        string = radioOptions.elementAt(index = i)
                                    )
                                    vm.saveTimerTimeFontStyleSelectedOption()
                                }
                            }
                            .padding(all = 8.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = if (settingsTimerSelectedFontStyleTopBarName == "Scrolls font style") {
                                radioOptions.elementAt(index = i) == timerScrollsFontStyleSelectedOption
                            } else {
                                radioOptions.elementAt(index = i) == timerTimeFontStyleSelectedOption
                            },
                            onClick = {
                                haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
                                if (settingsTimerSelectedFontStyleTopBarName == "Scrolls font style") {
                                    vm.setTimerScrollsFontStyleSelectedOption(
                                        radioOptions.elementAt(index = i)
                                    )
                                    vm.saveTimerScrollsFontStyleSelectedOption()
                                } else {
                                    vm.setTimerTimeFontStyleSelectedOption(string = radioOptions.elementAt(index = i))
                                    vm.saveTimerTimeFontStyleSelectedOption()
                                }
                            },
                            colors = RadioButtonDefaults.colors(selectedColor = Green50)

                        )
                        Text(
                            text = radioOptions.elementAt(index = i),
                            fontSize = 50.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Normal,
                            style = TextStyle(
                                drawStyle = if (radioOptions.elementAt(index = i) == "Inner stroke") Stroke() else null
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