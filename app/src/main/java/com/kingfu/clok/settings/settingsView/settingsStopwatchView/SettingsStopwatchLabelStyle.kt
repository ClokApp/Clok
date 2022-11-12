package com.kingfu.clok.settings.settingsView.settingsStopwatchView

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
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
import com.kingfu.clok.ui.theme.Black00
import com.kingfu.clok.util.customFontSize
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch

@Composable
fun SettingsStopwatchLabelStyle(
    vm: SettingsViewModelStopwatch = viewModel()
) {
    val haptic = LocalHapticFeedback.current
    val radioOptions = setOf("Gray", "RGB")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black00)
    ) {

        Text(
            text = "Styles",
            modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 10.dp, bottom = 2.dp),
            fontSize = customFontSize(textUnit = 16.sp),
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.SemiBold,
            color = Color.Gray
        )

        Card(
            modifier = Modifier.padding(5.dp),
            shape = RoundedCornerShape(30.dp)
        ) {
            Column {
                for (i in radioOptions.indices) {
                    Row(
                        modifier = Modifier.background(Color.Black.copy(0.4f))
                            .fillMaxWidth()
                            .clickable {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                vm.stopwatchSetLabelStyleSelectedOption(radioOptions.elementAt(i))
                                vm.saveStopwatchLabelStyleSelectedOption()
                            }
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (radioOptions.elementAt(i) == vm.stopwatchLabelStyleSelectedOption),
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                vm.stopwatchSetLabelStyleSelectedOption(radioOptions.elementAt(i))
                                vm.saveStopwatchLabelStyleSelectedOption()
                            }
                        )
                        Text(
                            text = radioOptions.elementAt(i),
                            modifier = Modifier,
                            fontSize = customFontSize(textUnit = 14.sp),
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Normal,
                        )

                    }
                    if (i != radioOptions.size - 1) {
                        Divider(
                            modifier = Modifier
                                .background(Color.Black.copy(0.4f))
                                .padding(horizontal = 24.dp),
                            color = Color.DarkGray,
                        )
                    }
                }
            }

        }
    }
}