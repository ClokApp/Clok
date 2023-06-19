package com.kingfu.clok.settings.settingsView.settingsTimerView.fontStyle

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kingfu.clok.navigation.Screens
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer
import com.kingfu.clok.variable.Variable.settingsTimerSelectedFontStyle

@Composable
fun SettingsTimerSelectedFontStyleView(
    vm: SettingsViewModelTimer,
) {
    val haptic = LocalHapticFeedback.current
    val scrollState = rememberScrollState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(size = 20.dp))
            .verticalScroll(state = scrollState)
    ) {

        Text(
            text = "${Screens.Timer.name} ${Screens.SettingsTimerFontStyles.name}",
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
            for (i in vm.timerFontStyleRadioOptions.indices) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
                            vm.fontStyleOptionSelected(
                                index = i,
                                radioOptions = vm.timerFontStyleRadioOptions
                            )
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        selected =
                        if (settingsTimerSelectedFontStyle == vm.fontStyleOptions.elementAt(
                                index = 0
                            )
                        ) {
                            vm.timerFontStyleRadioOptions.elementAt(index = i) == vm.timerScrollsFontStyle
                        } else {
                            vm.timerFontStyleRadioOptions.elementAt(index = i) == vm.timerTimeFontStyle
                        },
                        onClick = null,
                        colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                    )
                    Text(
                        text = vm.timerFontStyleRadioOptions.elementAt(index = i),
                        fontSize = 50.sp,
                        color = Color.White,
                        style = TextStyle(
                            drawStyle =
                            if (vm.timerFontStyleRadioOptions.elementAt(index = i) == vm.timerFontStyleRadioOptions.elementAt(
                                    index = 1
                                )
                            ) Stroke() else null
                        )
                    )

                }
                if (i != vm.timerFontStyleRadioOptions.size - 1) {
                    Divider(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        color = Color.DarkGray.copy(alpha = 0.75f),
                        thickness = 0.8.dp
                    )
                }
            }
        }
    }
}
