package com.kingfu.clok.settings.settingsView.settingsStopwatchView

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch.SettingsViewModelStopwatchVariable.stopwatchBackgroundEffectsSelectedOption
import com.kingfu.clok.ui.theme.Black00
import com.kingfu.clok.ui.theme.Green50

@Composable
fun SettingsStopwatchBackgroundEffects(
    vm: SettingsViewModelStopwatch = viewModel()
) {
    val haptic = LocalHapticFeedback.current
    val radioOptions = setOf("None", "Snow")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Black00)
    ) {

        Text(
            text = "Background Effects",
            modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 10.dp, bottom = 4.dp),
            fontSize = 16.sp,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.SemiBold,
            color = Green50.copy(alpha = 0.5f),
        )

        Card(shape = RoundedCornerShape(size = 30.dp)) {
            Column {
                for (i in radioOptions.indices) {
                    Row(
                        modifier = Modifier
                            .background(color = Color.Black.copy(alpha = 0.4f))
                            .fillMaxWidth()
                            .clickable {
                                haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
                                vm.setStopwatchBackgroundEffectSelectedOption(
                                    radioOptions.elementAt(index = i)
                                )
                                vm.saveStopwatchBackgroundEffectsSelectedOption()
                            }
                            .padding(all = 8.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (radioOptions.elementAt(index = i) == stopwatchBackgroundEffectsSelectedOption),
                            onClick = {
                                haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
                                vm.setStopwatchBackgroundEffectSelectedOption(
                                    radioOptions.elementAt(i)
                                )
                                vm.saveStopwatchBackgroundEffectsSelectedOption()
                            },
                            colors = RadioButtonDefaults.colors(selectedColor = Green50)

                        )
                        Text(
                            text = radioOptions.elementAt(index = i),
                            modifier = Modifier,
                            fontSize = 14.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Normal,
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